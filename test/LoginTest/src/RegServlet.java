import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.zip.CRC32;


@WebServlet(name = "RegServlet", urlPatterns = "/RegServlet")
public class RegServlet extends HttpServlet {

    protected static String NewUserName;
    protected static String NewUserEmail;
    protected static String NewUserPWD;
    protected static int user_id;


    private String host;
    private String port;
    private String user;
    private String pass;


//    public static String fixSqlFieldValue(String value) {
//        if (value == null)
//            return null;
//        int length = value.length();
//        StringBuffer fixedValue = new StringBuffer((int) (length * 1.1));
//        for (int i = 0; i < length; i++) {
//            char c = value.charAt(i);
//            if (c == '\'')
//                fixedValue.append("''");
//            else
//                fixedValue.append(c);
//        }
//        return fixedValue.toString();
//    }


    public void init() {


        // Загрузка драйвера БД
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("====>>> ClassNotFoundException");
        }

        // Получение параметров из web.xml
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");


        NewUserName = request.getParameter("NewUserName");
        NewUserEmail = request.getParameter("NewUserEmail");
        NewUserPWD = request.getParameter("NewUserPWD");


        try {
            NewUserPWD = UtilHash.getHash(NewUserPWD);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        boolean error = false;
        String message = null;

        String dbusername = "postgres";
        String dbpwd = "123";
        String dburl = "jdbc:postgresql://localhost:5432/poll_2";


        if (!NewUserEmail.equals("") || !NewUserName.equals("") || !NewUserPWD.equals(""))
            try {
                System.out.println("====>>> Start Connection");
                Connection db = DriverManager.getConnection(dburl, dbusername, dbpwd);
                System.out.println("Connection success!");
                Statement st = db.createStatement();
                String sql = "SELECT user_name FROM users WHERE user_name ='" + NewUserName + "'";
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    rs.close();
                    message = "Пользователь с иенем  " + NewUserName + "УЖЕ существует";
                    error = true;
                } else {
                    rs.close();
                    sql = "INSERT INTO users (user_name, user_email, user_pwd) VALUES ('" +
                            NewUserName + "', '" + NewUserEmail + "', '" + NewUserPWD + "')";
                    System.out.println("====>>> Write OK!");
                    System.out.println(NewUserName + " :: " + NewUserEmail + " :: " + NewUserPWD);

                    int i = st.executeUpdate(sql);
                    if (i == 1) {


                        message = "Юзер успешно добавлен\n<br>\n";



                        sql = "SELECT user_id FROM users WHERE user_email ='" + NewUserEmail + "'";
                        rs = st.executeQuery(sql);
                        rs.next();
                        user_id = rs.getInt(1);
                        rs.close();


                        if (user_id == 1) {
                            sql = "INSERT INTO groups " +
                                    "(group_title, group_description, rights, group_author) VALUES " +
                                    "('ADMINS','standard root group','{TRUE,TRUE}','1'), " +
                                    "('USERS','standard user group','{TRUE,FALSE}','1')," +
                                    "('Mail_OK', 'standard group for confirmed users','{TRUE,FALSE}','1');" +
                                    "INSERT INTO group_entries" +
                                    "(user_id, group_id, entry_author) VALUES " +
                                    "('1', '1', '1')";
                            i = st.executeUpdate(sql);
                            if (i == 3)
                                message += "Группы успешно инициализированны\n<br>\n" +
                                        "Привет АДМИН!\n<br>\n";
                        } else {
                            sql = "INSERT INTO group_entries " +
                                    "(user_id, group_id, entry_author) VALUES " +
                                    "('" + user_id + "', '2', '" + user_id + "')";
                            i = st.executeUpdate(sql);
                            if (i == 1)
                                message += "Привет, Юзер!";


                        }
                        st.close();


                        // Создаём проверочную строку
                        String Xstring = String.valueOf(System.currentTimeMillis());
                        CRC32 crc = new CRC32();
                        crc.update(Xstring.getBytes());
                        Xstring = Long.toHexString(crc.getValue()).toUpperCase();

                        // Хешируем проверочную строку
                        String HasXstring = null;
                        try {
                            HasXstring = UtilHash.getHash(Xstring);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        String subject = "Подтверждение регистрации";
                        String content = "Проверочная строка ==>  " + Xstring;

                        try {

                            // Отправляем письмо
                            UtilMail.sendEmail(host, port, user, pass,
                                    NewUserEmail, subject, content);

                            // Запись куки в браузер клиенту.
                            Cookie c1 = new Cookie("XString", HasXstring);
                            c1.setMaxAge(120);
                            response.addCookie(c1);


                            message += "\n<br>Письмо отправлено! <br>\n" + "Куки установлены! <br>\n";
                            message += "<form action=\"MailCheckerServlet\" method=\"post\">\n" +
                                    "  <table border=\"0\" width=\"35%\" align=\"center\">\n" +
                                    "    <caption><h2>Подтверждение адреса e-mail</h2></caption>\n" +
                                    "    <tr>\n" +
                                    "      <td width=\"50%\">Введите полученную строку:</td>\n" +
                                    "      <td><input type=\"text\" name=\"ClientStr\" size=\"50\" title=\"string\"/></td>\n" +
                                    "    </tr>\n" +
                                    "    <tr>\n" +
                                    "      <td colspan=\"2\" align=\"center\"><input type=\"submit\" value=\"Send\"/></td>\n" +
                                    "    </tr>\n" +
                                    "  </table>\n" +
                                    "</form>";
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            message += "\n<br>\n Что-то пошло не так...    " + ex.getMessage();
                        }



                    }
                }
                db.close();
                st.close();
            } catch (SQLException e) {
                message = "Ошибка. " + e.toString();
                error = true;
            } catch (Exception e) {
                message = "Ошибка. " + e.toString();
                error = true;
            }


        if (error) {
            message += " \n<br>\n<br>\n Регистрация провалилась! Попробуйте снова!";
        }


        if (message != null) {
            request.setAttribute("Message", message);
            getServletContext().getRequestDispatcher("/index.jsp").forward(
                    request, response);
        }
    }
}
