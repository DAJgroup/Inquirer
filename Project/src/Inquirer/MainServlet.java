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

        boolean AuthBool = false;
        String JspRedirect;

        String RemoteIP = getIP.getRemoteIP(request);
        String message = "";
        String UserLogin = null;


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

            // Подключаем драйвер базы данных.
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // Параметры подключения базы данных
            String dbusername = "postgres";
            String dbpwd = "123";
            String dburl = "jdbc:postgresql://localhost:5432/poll";
            // Подключаем базу данных
            Connection db;
            try {
                db = DriverManager.getConnection(dburl, dbusername, dbpwd);
                System.out.println("Main  -DB-  Connection success!");
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
                    UserLogin = rs.getString(1);

                    sql = "SELECT last_entry, session_ip FROM user_sessions WHERE session_id='" + SessionID + "'";
                    rs = st.executeQuery(sql);
                    rs.next();
                    message += "Последний визит: \n<br>\n" + rs.getString(1) + "\n<br>\n" + rs.getString(2);
                    AuthBool = true;

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

        if (AuthBool) {
            JspRedirect = "/authorizeduser.jsp";
        } else {
            JspRedirect = "/index.jsp";
        }

        request.setAttribute("Message", message);
        request.setAttribute("Nickname", UserLogin);
        getServletContext().getRequestDispatcher(JspRedirect).forward(
                request, response);
    }
}
