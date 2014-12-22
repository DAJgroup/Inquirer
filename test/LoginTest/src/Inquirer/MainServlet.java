package Inquirer;

import Inquirer.Util.getIP;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "MainServlet", urlPatterns = "")
public class MainServlet extends HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request,
                         javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

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

        String RemoteIP = getIP.getRemoteIP(request);
        String message = null;
        String UserName;


        // Ищем наши куки с ключём и ID сессии.
        String SessionKey = null;
        String SessionID = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SessionKey"))
                    SessionKey = cookie.getValue();
                if (cookie.getName().equals("SessionID"))
                    SessionID = cookie.getValue();
            }

        if (SessionID != null && SessionKey != null) {

            // Подключаем базу данных
            Connection db;
            try {
                db = DriverManager.getConnection(dburl, dbusername, dbpwd);
                Statement st = db.createStatement();
                ResultSet rs;
                String sql = "SELECT user_id FROM user_sessions WHERE session_key='" + SessionKey + "' " +
                        "AND session_id='" + SessionID + "' AND session_ip='" + RemoteIP + "'";
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    int UserID = rs.getInt(1);
                    sql = "SELECT user_name FROM users WHERE user_id='" + UserID + "'";
                    rs = st.executeQuery(sql);
                    rs.next();
                    UserName = rs.getString(1);
                    message = "Привет, " + UserName + "!<br>\n";

                    sql = "SELECT last_entry FROM user_sessions WHERE session_id='" + SessionID + "'";
                    rs = st.executeQuery(sql);
                    rs.next();
                    message += "В последний раз Вы заходили  " + rs.getString(1);

                    sql = "UPDATE user_sessions SET last_entry=NOW() WHERE session_id='" + SessionID + "'";
                    st.executeUpdate(sql);
                } else {
                    message = "";
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            message = "";
        }

        request.setAttribute("Message", message);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
