package DAG.Iquirer;

import DAG.Iquirer.Util.UtilHash;
import DAG.Iquirer.Util.getIP;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

@WebServlet(name = "AuthServlet", urlPatterns = "/AuthServlet") //TODO Проверка продтверждённой почты
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Получаем логин/пароль из index.jsp
        String UserLogin = request.getParameter("UserName");
        String UserPWD = request.getParameter("UserPWD");

        // Хэшируем пароль
        try {
            UserPWD = UtilHash.getHash(UserPWD);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Подключаем драйвер базы данных.
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Параметры подключения базы данных
        String dbusername = "postgres";
        String dbpwd = "123";
        String dburl = "jdbc:postgresql://localhost:5432/poll_2";

        // Сообщение о результате атентификации
        String LoginMessage = "";

        // Полученаем IP клиента
        String RemoteIP = getIP.getRemoteIP(request);

        try {
            // Подключаем базу данных
            Connection db = DriverManager.getConnection(dburl, dbusername, dbpwd);
            System.out.println("Database connection  ==>  OK!");
            Statement st;
            st = db.createStatement();

            //Проверка на наличие Логина в БД
            String sql = "SELECT user_id FROM users WHERE user_name='" + UserLogin + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int user_id = rs.getInt(1);

                // Проверка на наличие Пароля в БД
                sql = "SELECT user_pwd FROM users WHERE user_pwd ='" + UserPWD + "'";
                rs = st.executeQuery(sql);
                if (rs.next()) {


                    // Генерация ключа сессии
                    String session_Key = String.valueOf(1 + (int) (Math.random() * 1999999999)); // TODO сделать генерацию строки.
                    try {
                        session_Key = UtilHash.getHash(String.valueOf(session_Key)); // TODO солить
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    // Запсиь сесии в БД
                    sql = "INSERT INTO user_sessions (user_id, session_key, session_ip) VALUES " +
                            "('" + user_id + "', '" + session_Key + "', '" + RemoteIP + "')";
                    st.executeUpdate(sql);

                    // Получение уникального идентификатора сессии
                    sql = "SELECT session_id FROM user_sessions WHERE session_key='" + session_Key + "' " +
                            "AND user_id='"+user_id+"'";
                    rs = st.executeQuery(sql);
                    rs.next();
                    int session_ID = rs.getInt(1);

                    // Запись куки с ключом и индетификатором сессии
                    Cookie c_key = new Cookie("SessionKey", session_Key);
                    c_key.setMaxAge(10*60);
                    response.addCookie(c_key);
                    Cookie c_id = new Cookie("SessionID", String.valueOf(session_ID));
                    c_id.setMaxAge(10*60);
                    response.addCookie(c_id);


                    // Запись сообщения об удачной атентификации
                    LoginMessage += "Успешная авторизация\n<br>\n" +
                            "Куки установленны\n<br>\n<br>\n<br>\n";


                    // Пулучам список групп пользоваетля и добавляем его к сообщению
                    LoginMessage += UserLogin + " из групп(ы) : <br>\n";
                    sql = "SELECT group_title FROM groups WHERE group_id IN " +
                            "(SELECT group_id FROM group_entries WHERE user_id='" + user_id + "')";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        LoginMessage += rs.getString(1) + "<br>\n";
                    }


                    // Добывляем к сообщению IP адрес клиента
                    LoginMessage += "\n<br>\nclient_ip = " + RemoteIP;


                } else {
                    // Сообщение о неудачной утентификации
                    LoginMessage += "Пароли не совпадают!\n<br>\n";
                }

            } else {
                // Сообщение о неудачной утентификации
                LoginMessage += "Логин не найден\n<br>\n";
            }
            // Закрываем соеденение с базой данных
            rs.close();
            st.close();
            db.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Окрываем страницу index.jsp
        // и передаём ей сообщение о результатах аутентифакации.
        request.setAttribute("Message", LoginMessage);
        getServletContext().getRequestDispatcher("/index.jsp").forward(
                request, response);

    }
}
