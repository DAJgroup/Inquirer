package Inquirer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "LogOut", urlPatterns = "/LogOut")
public class LogOut extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");



        String message = "";

        String SessionID = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("SessionID"))
                    SessionID = cookie.getValue();
            }
        if (SessionID != null) {


            // Подключаем драйвер и базы.
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String dbusername = "postgres";
            String dbpwd = "123";
            String dburl = "jdbc:postgresql://localhost:5432/poll";
            try {
                Connection db = DriverManager.getConnection(dburl, dbusername, dbpwd);
                System.out.println("LogOut  -DB-  Connection success!");
                Statement st = db.createStatement();
                String sql = "DELETE FROM user_sessions WHERE user_id =(Select user_id FROM user_sessions WHERE session_id ='" + SessionID + "')";
                st.executeUpdate(sql);

                message = "Все сессии успешно закрыты!\n<br>\n";


            } catch (SQLException e) {
                message += e.toString();
            }
        }


        request.setAttribute("Message", message);
        getServletContext().getRequestDispatcher("/index.jsp").forward(
                request, response);
    }

}


