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

        String JspRedirect = "/index.jsp";

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

                    sql = "UPDATE user_sessions SET last_entry=NOW() WHERE session_id='" + SessionID + "'";
                    st.executeUpdate(sql);


                    JspRedirect = "/userpage.jsp";
                    sql = "SELECT group_title FROM groups WHERE group_id IN " +
                            "(SELECT group_id FROM group_entries WHERE user_id='" + UserID + "')";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        if (rs.getString(1).equals("ADMINS"))
                            JspRedirect = "/adminpage.jsp";
                    }

                    boolean mailbool = false;
                    sql = "SELECT group_title FROM groups WHERE group_id IN " +
                            "(SELECT group_id FROM group_entries WHERE user_id='" + UserID + "')";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        if (rs.getString(1).equals("Mail_OK"))
                            mailbool = true;
                    }
                    if (!mailbool) {

                        sql = "SELECT user_email FROM users WHERE user_id ='" + UserID + "'";
                        rs = st.executeQuery(sql);
                        rs.next();
                        String UserEmail = rs.getString(1);

                        message += "\n<br><font color=\"#CC0000\">Ваша учетная запись не подтверждена!</font><br>\n";

                        message += "<center>" +
                                "<form action=\"" + getServletContext().getContextPath() + "/sender\" method=\"post\" name=\"send\">\n" +
                                " <input type=\"hidden\" name=\"UserEmail\" value=\"" + UserEmail + "\">\n" +
                                " <input type=\"hidden\" name=\"JspRedirect\" value=\"" + JspRedirect + "\">\n" +
                                " <input type=\"hidden\" name=\"UserName\" value=\"" + UserLogin + "\">\n" +
                                "<button type=\"submit\">Повторно отправить письмо<br>\n" +
                                "для подтверждения регистрации</button>\n" +
                                "</form>" +
                                "</center>";
                    }

                    st.close();
                    rs.close();
                    db.close();

                } else {
                    message = "";
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            message = "";
        }


        message = "\n<b>" + message + "</b>\n";
        request.setAttribute("Message", message);
        request.setAttribute("Nickname", UserLogin);
        getServletContext().getRequestDispatcher(JspRedirect).forward(
                request, response);
    }
}