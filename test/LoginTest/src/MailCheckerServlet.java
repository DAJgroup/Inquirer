import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "MailCheckerServlet", urlPatterns = "/MailCheckerServlet")
public class MailCheckerServlet extends HttpServlet {

    protected static boolean checked;

    protected void doPost(javax.servlet.http.HttpServletRequest request,
                          javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("====>>> ClassNotFoundException");
        }


        String CookieString = null;

        // Ищем нужный куки
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("XString")) {
                CookieString = cookie.getValue();
            }
        }
        // Получаем от клиента строку и хэшируем её.
        String ClientString = request.getParameter("ClientStr");
        System.out.println(ClientString);
        String HASHClientString = "";
        try {
            HASHClientString = UtilHash.getHash(ClientString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String Result;
        if (CookieString != null) {         // FALSE == Нужный куки не найден.
            if (HASHClientString.equals(CookieString)) {
                Result = "Адрес " + RegServlet.NewUserEmail + " подтверждён!<br>";
                checked = true;


                String dbusername = "postgres";
                String dbpwd = "123";
                String dburl = "jdbc:postgresql://localhost:5432/poll_2";

                try {
                    System.out.println("====>>> Start Connection");
                    Connection db = DriverManager.getConnection(dburl, dbusername, dbpwd);
                    System.out.println("Connection success!");
                    Statement st = db.createStatement();
                    String sql = "INSERT INTO group_entries " +
                            "(user_id, group_id, entry_author) VALUES " +
                            "('" + RegServlet.user_id + "', '3', '" + RegServlet.user_id + "')";
                    st.executeUpdate(sql);


                    st.close();
                    db.close();


                } catch (SQLException e) {
                    e.printStackTrace();
                }


            } else {
                Result = "Подтверждение адреса e-mail провалилось! <br>\n";
                checked = false;
            }

        } else {
            Result = "Cookie Устарели либо не были установлены.<br>\n";
        }
        request.setAttribute("Message", Result);
        getServletContext().getRequestDispatcher("/index.jsp").forward(
                request, response);
    }
}