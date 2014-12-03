import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

@WebServlet(name = "AuthServlet", urlPatterns = "/AuthServlet")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String UserLogin = request.getParameter("UserName");
        String UserPWD = request.getParameter("UserPWD");

        try {
            UserPWD = UtilHash.getHash(UserPWD);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("====>>> ClassNotFoundException");
        }

        String dbusername = "postgres";
        String dbpwd = "123";
        String dburl = "jdbc:postgresql://localhost:5432/poll_2";

        String LoginMessage = "";

        String RemoteIP = getIP.getRemoteIP(request);
        System.out.println(RemoteIP);


        try {
            System.out.println("====>>> Start Connection");
            Connection db = DriverManager.getConnection(dburl, dbusername, dbpwd);
            System.out.println("Connection success!");
            Statement st = null;
            st = db.createStatement();
            String sql = "SELECT user_id FROM users WHERE user_name='" + UserLogin + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int user_id = rs.getInt(1);
                sql = "SELECT user_pwd FROM users WHERE user_pwd ='" + UserPWD + "'";
                rs = st.executeQuery(sql);
                if (rs.next()) {


                    String session_Xkey = String.valueOf(1 + (int) (Math.random() * 1999999999));
                    try {
                        session_Xkey = UtilHash.getHash(String.valueOf(session_Xkey));
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }


                    sql = "INSERT INTO user_sessions (user_id, session_key, session_ip) VALUES " +
                            "('" + user_id + "', '" + session_Xkey + "', '" + RemoteIP + "')";
                    st.executeUpdate(sql);

                    sql = "SELECT session_id FROM user_sessions WHERE session_key='" + session_Xkey + "'";
                    rs = st.executeQuery(sql);
                    rs.next();
                    int session_ID = rs.getInt(1);


                    Cookie c_key = new Cookie("session_Xkey", session_Xkey);
                    c_key.setMaxAge(900);
                    response.addCookie(c_key);

                    Cookie c_id = new Cookie("session_Xkey", String.valueOf(session_ID));
                    c_id.setMaxAge(900);
                    response.addCookie(c_id);

                    LoginMessage += "Успешная авторизация\n<br>\n" +
                            "Куки установленны\n<br>\n<br>\n<br>\n" +
                            UserLogin + " из групп(ы) : <br>\n";

                    sql = "SELECT group_title FROM groups WHERE group_id IN (SELECT group_id FROM group_entries WHERE user_id='" + user_id + "')";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        LoginMessage +=  rs.getString(1) + "<br>\n";
                    }
                    LoginMessage += "\n<br>\nclient_ip = "+ RemoteIP;

                } else {
                    LoginMessage += "Пароли не совпадают!\n<br>\n";
                }
            } else {
                LoginMessage += "Логин не найден\n<br>\n";
            }
            rs.close();
            st.close();
            db.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("Message", LoginMessage);
        getServletContext().getRequestDispatcher("/Report.jsp").forward(
                request, response);

    }
}
