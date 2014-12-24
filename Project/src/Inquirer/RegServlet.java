package Inquirer;

import Inquirer.Util.UtilHash;
import Inquirer.Util.UtilMail;
import Inquirer.Util.getIP;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(name = "RegServlet", urlPatterns = "/RegServlet")
public class RegServlet extends HttpServlet {

    // Переменные для регистрационных данных.
    protected static String NewUserName;
    protected static String NewUserEmail;
    protected static String NewUserPWD;
    protected static String NewUserREPWD;
    protected static String NewUserFirstName;
    protected static String NewUserLastName;
    protected static int user_id;
    protected static String ServerURL;


    // Переменные для параметров почтового клиента.
    private String host;
    private String port;
    private String user;
    private String pass;


    public void init() {

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
        response.setCharacterEncoding("UTF-8");

        // получение регистрационных данных.
        NewUserName = request.getParameter("NewUserName");
        NewUserEmail = request.getParameter("NewUserEmail").toLowerCase();
        NewUserPWD = request.getParameter("NewUserPWD");
        NewUserREPWD = request.getParameter("NeUserREPWD");
        NewUserFirstName = request.getParameter("NewUserFirstName");
        NewUserLastName = request.getParameter("NewUserLastName");


        boolean error = false;
        String message = "";


// логин авторизация и реавторизация, пароль авторизация и реавторизация  /^[a-zA-Z0-9_ +-`'*]+$/gi
// регистрация логин и пароль, репароль                                   /^[a-zA-Z0-9_ +-`'*]+$/gi
// е-маил и  рее-маил /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/gi
// имя и фамилия /^[a-zA-Zа-яА-ЯёЁ_ -`'][^0-9]+$/gi
        Pattern p;
        Matcher m;
        p = Pattern.compile("^[a-zA-Z0-9_-]+$");
        m = p.matcher(NewUserName);
        if (NewUserName.length() >= 5 && !m.matches()) {
            error = true;
            message += "Значение \"Логин\" некорректно!\n<br>\n";
        }
        System.out.println("REG NewUserName       --  \"" + NewUserName + "\" - " + (NewUserName.length() >= 5 && m.matches()));

        p = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
        m = p.matcher(NewUserEmail);
        if (!m.matches()) {
            error = true;
            message += "\nЗначение \"E-mail\" некорректно!";
        }
        System.out.println("REG NewUserEmail      --  \"" + NewUserEmail + "\" - " + m.matches());

        p = Pattern.compile("^[a-zA-Z0-9_ +-`'*]+$");
        m = p.matcher(NewUserPWD);
        if (NewUserPWD.length() >= 6 && !m.matches()) {
            error = true;
            message += "Значение \"Пароль:1\" некорректно!\n<br>\n";
        }
        System.out.println("REG NewUserPWD        --  \"" + NewUserPWD + "\" - " + m.matches());

        m = p.matcher(NewUserREPWD);
        if (NewUserREPWD.length() >= 6 && !m.matches()) {
            error = true;
            message += "Значение \"Пароль:2\" некорректно!\n<br>\n";
        }
        System.out.println("REG NewUserREPWD      --  \"" + NewUserREPWD + "\" - " + m.matches());

        p = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ_ -`'][^0-9]+$");
        m = p.matcher(NewUserFirstName);
        if (NewUserFirstName.length() >= 6 && !m.matches()) {
            error = true;
            message += "Значение \"Имя\" некорректно!\n<br>\n";
        }
        System.out.println("REG NewUserFirstName  --  \"" + NewUserFirstName + "\" - " + m.matches());

        m = p.matcher(NewUserLastName);
        if (NewUserLastName.length() >= 6 && !m.matches()) {
            error = true;
            message += "Значение \"Фамилия\" некорректно!\n<br>\n";
        }
        System.out.println("REG NewUserLastName   --  \"" + NewUserLastName + "\" - " + m.matches());

        if (!NewUserPWD.equals(NewUserREPWD)) {
            error = true;
            message += "При подтверждении пароля допущена ошибка!";
        }


        if (!error) {
            try {
                // Генерация ссылки
                String Host = request.getServerName();
                String Port = Integer.toString(request.getServerPort());
                String Path = getServletContext().getContextPath();
                ServerURL = "http://" + Host + ":" + Port + Path + "/MailCheckerServlet";

                // Генерируем проверочную строку
                String symbols = "0123456789ABCDEF";
                StringBuilder Xstring = new StringBuilder();
                int count = 16;
                for (int i = 0; i < count; i++)
                    Xstring.append(symbols.charAt((int) (Math.random() * symbols.length())));

                // Хешируем проверочную строку
                String HasXstring = null;
                try {
                    HasXstring = UtilHash.getHash(Xstring.toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String subject = "Подтверждение регистрации.";
                // <a href="URL">текст ссылки</a>
                String content = "\n<a href=\"" + ServerURL + "?ClientStr=" + Xstring.toString() + "\">" + ServerURL + "</a>\n";
                System.out.println("Send \"" + content + "\" to " + NewUserEmail);

                // Запись куки в браузер клиенту.
                try {
                    Cookie c1 = new Cookie("MailCheck", HasXstring);
                    c1.setMaxAge(60 * 60);
                    response.addCookie(c1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                UtilMail.sendEmail(host, port, user, pass, NewUserEmail, subject, content);
                message += "<br>\n<br>\nНа вашу почту было выслано письмо для подтверждения регистрации.<br>";
            } catch (MessagingException e) {
                e.printStackTrace();
                message += "ОШИБКА: Невозможно отправить письмо на указаный Вами адрес!";
                error = true;
            }


            if (!error) {
                // Кэшируем пароль
                try {
                    NewUserPWD = UtilHash.getHash(NewUserPWD);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                // Загрузка драйвера БД
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // Параметры подключения базы данных
                String dbusername = "postgres";
                String dbpwd = "123";
                String dburl = "jdbc:postgresql://localhost:5432/poll";
                try {
                    // Подключаем базу данных.
                    Connection db = DriverManager.getConnection(dburl, dbusername, dbpwd);
                    System.out.println("Reg  -DB-  Connection success!");
                    Statement st = db.createStatement();
                    ResultSet rs;
                    String sql;


                    // Проверка на наличие логина/мыла в БД.
                    sql = "SELECT user_name FROM users WHERE user_name ILIKE '" + NewUserName + "' " +
                            "OR user_email ='" + NewUserEmail + "'";
                    rs = st.executeQuery(sql);
                    if (rs.next()) {
                        message = "Учётная запись с таким именем и/или e-mail уже существует\n<br>\n";
                        error = true;
                    }
                    rs.close();


                    if (!error) {
                        sql = "INSERT INTO users (user_name, user_email, user_pwd," +
                                " user_firstname, user_lastname) " +
                                "VALUES ('" + NewUserName + "', '" + NewUserEmail + "', '" + NewUserPWD +
                                "', '" + NewUserFirstName + "', '" + NewUserLastName + "')";
                        int i = st.executeUpdate(sql);
                        if (i == 1) {
                            message = "Учётная запись создана.\n<br>\n";

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
                                if (i == 3) {
                                    message += "Группы инициализированны.<br>\n" +
                                            "Пользователь включен в группу \"ADMINS\".<br>\n";
                                } else {
                                    message += "ОШИБКА ЗАПИСИ В БД: Код работает некоректно!!!<br>.\n";
                                    error = true;
                                }
                            }

                            sql = "INSERT INTO group_entries " +
                                    "(user_id, group_id, entry_author) VALUES " +
                                    "('" + user_id + "', '2', '" + user_id + "')";
                            i = st.executeUpdate(sql);
                            if (i == 1) {
                                message += "Пользователь включен в группу \"USERS\".<br>\n";
                            } else {
                                message += "ОШИБКА ЗАПИСИ В БД: Код работает некоректно!!!<br>\n";
                                error = true;
                            }
                        } else { // if(i==1){
                            message += "Ошибка при добавления пользователя в базу!!!\n<br>\n";
                            error = true;
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
            }
        }


        if (error) {  //if (!NewUserEmail.equals("")&&!NewUserName.equals("")&&!NewUserPWD.equals("")){
            message += "<br>\n<br>\n Регистрация провалилась!\n<br>\n";

            request.setAttribute("Message", message);
            getServletContext().getRequestDispatcher("/index.jsp").forward(
                    request, response);
        } else {


            // Загрузка драйвера БД
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // Параметры подключения базы данных
            String dbusername = "postgres";
            String dbpwd = "123";
            String dburl = "jdbc:postgresql://localhost:5432/poll";
            try {
                // Подключаем базу данных.
                Connection db = DriverManager.getConnection(dburl, dbusername, dbpwd);
                System.out.println("Reg  -DB-  Connection success!");
                Statement st = db.createStatement();
                ResultSet rs;
                String sql;


                // Генерация ключа сессии
                String symbols = "0123456789ABCDEF";
                StringBuilder session_Key = new StringBuilder();
                int count = 64;
                for (int i = 0; i < count; i++)
                    session_Key.append(symbols.charAt((int) (Math.random() * symbols.length())));


                // Полученаем IP клиента
                String RemoteIP = getIP.getRemoteIP(request);
                // Запсиь сесии в БД
                sql = "INSERT INTO user_sessions (user_id, session_key, session_ip) VALUES " +
                        "('" + user_id + "', '" + session_Key + "', '" + RemoteIP + "')";
                st.executeUpdate(sql);

                // Получение уникального идентификатора сессии
                sql = "SELECT session_id FROM user_sessions WHERE session_key='" + session_Key + "' " +
                        "AND user_id='" + user_id + "'";
                rs = st.executeQuery(sql);
                rs.next();
                int session_ID = rs.getInt(1);

                // Запись куки с ключом и индетификатором сессии
                Cookie c_key = new Cookie("SessionKey", session_Key.toString());
                c_key.setMaxAge(72 * 60 * 60);
                response.addCookie(c_key);
                Cookie c_id = new Cookie("SessionID", String.valueOf(session_ID));
                c_id.setMaxAge(72 * 60 * 60);
                response.addCookie(c_id);

                request.setAttribute("Message", message);
                getServletContext().getRequestDispatcher("/authorizeduser.jsp").forward(
                        request, response);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
