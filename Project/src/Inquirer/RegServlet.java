package Inquirer;

import Inquirer.Util.UtilHash;
import Inquirer.Util.UtilMail;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


@WebServlet(name = "RegServlet", urlPatterns = "/RegServlet")
public class RegServlet extends HttpServlet {

    // Переменные для регистрационных данных.
    protected static String NewUserName;
    protected static String NewUserEmail;
    protected static String NewUserPWD;
    protected static int user_id;
    protected static String ServerURL;


    // Переменные для параметров почтового клиента.
    private String host;
    private String port;
    private String user;
    private String pass;


    public void init() {


        // Загрузка драйвера БД
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // получение регистрационных данных.
        NewUserName = request.getParameter("NewUserName");
        NewUserEmail = request.getParameter("NewUserEmail");
        NewUserPWD = request.getParameter("NewUserPWD");
        System.out.println("REG NewUserName   --  " + NewUserName);
        System.out.println("REG NewUserEmail  --  " + NewUserEmail);
        System.out.println("REG NewUserPWD    --  " + NewUserPWD);


        URL path = this.getClass().getClassLoader().getResource("");
        System.out.println(path);

        String Host = request.getServerName();
        String Port = Integer.toString(request.getServerPort());
        String Path = request.getServletPath();
        ServerURL = "http://" + Host + ":" + Port + Path + getServletContext().getContextPath() + "/MailCheckerServlet";
        System.out.println(ServerURL);


        boolean error = false;
        String message = "";

        // Параметры подключения базы данных
        String dbusername = "postgres";
        String dbpwd = "123";
        String dburl = "jdbc:postgresql://localhost:5432/poll";

        if (!NewUserEmail.equals("") && !NewUserName.equals("") && !NewUserPWD.equals("")) {
            // Кэшируем пароль
            try {
                NewUserPWD = UtilHash.getHash(NewUserPWD);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                // Подключаем базу данных.
                Connection db = DriverManager.getConnection(dburl, dbusername, dbpwd);
                System.out.println("Connection success!");
                Statement st = db.createStatement();
                ResultSet rs;
                String sql;


                // Проверка на наличие логина в БД.
                sql = "SELECT user_name FROM users WHERE user_name ='" + NewUserName + "'";
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    message = "Имя \"" + NewUserName + "\" Занято!\n<br>\n";
                    error = true;
                }

                // Проверка на наличие мыла в БД.
                sql = "SELECT user_name FROM users WHERE user_email ='" + NewUserEmail + "'";
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    message = "Адрес \"" + NewUserEmail + "\" Занят!\n<br>\n";
                    error = true;
                }
                rs.close();

                if (!error) {
                    sql = "INSERT INTO users (user_name, user_email, user_pwd) VALUES ('" +
                            NewUserName + "', '" + NewUserEmail + "', '" + NewUserPWD + "')";
                    System.out.println(NewUserName + " :: " + NewUserEmail + " :: " + NewUserPWD);
                    int i = st.executeUpdate(sql);
                    if (i == 1) {
                        message = "Юзер успешно добавлен.\n<br>\n";

                        // Создание базовых групп и включение его в группы "ADMINS" если юзер получает ID == 1
                        sql = "SELECT user_id FROM users WHERE user_name='" + NewUserName + "'";
                        rs = st.executeQuery(sql);
                        rs.next();
                        user_id = rs.getInt(1);
                        if (user_id == 1) {
                            sql = "INSERT INTO groups " +
                                    "(group_title, group_description, rights, group_author) VALUES " +
                                    "('ADMINS','Standard root group','{TRUE,TRUE}','" + user_id + "'), " +
                                    "('USERS','Standard user group','{TRUE,FALSE}','" + user_id + "')," +
                                    "('Mail_OK', 'Standard group for confirmed users','{TRUE,FALSE}','" + user_id + "');" +
                                    "INSERT INTO group_entries" +
                                    "(user_id, group_id, entry_author) VALUES " +
                                    "('1', '1', '1')";
                            i = st.executeUpdate(sql);
                            if (i == 3)
                                message += "Группы инициализированны.<br>\nПользователь включен в группу \"ADMINS\".<br>\n";
                            else message += "ОШИБКА ЗАПИСИ В БД: Код работает некоректно!!!<br>.\n";
                        }

                        sql = "INSERT INTO group_entries " +
                                "(user_id, group_id, entry_author) VALUES " +
                                "('" + user_id + "', '2', '" + user_id + "')";
                        i = st.executeUpdate(sql);
                        if (i == 1) message += "Пользователь включен в группу \"USERS\".<br>\n";
                        else message += "ОШИБКА ЗАПИСИ В БД: Код работает некоректно!!!<br>\n";


                    } else { // if(i==1){
                        error = true;
                        message += "Ошибка при добавления пользователя в базу!!!\n<br>\n";
                    }

                }//end  }else{

                db.close();
                st.close();

            } catch (SQLException e) {
                message = "Ошибка. " + e.toString();
                error = true;
            } catch (Exception e) {
                message = "Ошибка. " + e.toString();
                error = true;
            }
        } else {  //if (!NewUserEmail.equals("")&&!NewUserName.equals("")&&!NewUserPWD.equals("")){
            message = "Некоректные данные!";
        }

        if (error) {
            message += "<br>\n<br>\n Регистрация провалилась! Попробуйте снова!";
        } else {
            try {
                UtilMail.SendEmail(response, NewUserEmail, ServerURL);
                message += "<br>\n<br>\nНа вышу почту было выслано письмо с ссылкой для подтверждения регистрации.<br>";
            } catch (MessagingException e) {
                e.printStackTrace();
            }


        }
        request.setAttribute("Message", message);
        getServletContext().getRequestDispatcher("/index.jsp").forward(
                request, response);
    }
}
