package Inquirer;

import Inquirer.Util.UtilHash;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

@WebServlet(name = "MailCheckerServlet", urlPatterns = "/MailCheckerServlet")
public class MailCheckerServlet extends HttpServlet {


    protected void doGet(javax.servlet.http.HttpServletRequest request,
                         javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

        String JspRedirect = "/index.jsp";
        String UserName = null;
        
        // Ищем наш куки.
        String CookieString = null;
        String CookieMail = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("MailCheck"))
                CookieString = cookie.getValue();
            if (cookie.getName().equals("MailAddr"))
                CookieMail = cookie.getValue();

        }
        // Получаем от клиента строку и хэшируем её.
        String MailChekStr = request.getParameter("ClientStr");
        System.out.println("MailChecker  -Get String- " + MailChekStr);
        String HASHClientString = "";
        try {
            HASHClientString = UtilHash.getHash(MailChekStr);  // TODO солить
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Сравниваем строку, полученную от клинта, с той,
        // которую отправили на e-mail.
        String Result;
        if (CookieString != null && CookieMail != null) {         // FALSE == Нужный куки не найден.
            
            if (HASHClientString.equals(CookieString)) {


                // Подключаем драйвер базы данных.
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // Параметры подключения базы данных.
                String dbusername = "postgres";
                String dbpwd = "123";
                String dburl = "jdbc:postgresql://localhost:5432/poll";
                // Подключаем базу данных.
                try {
                    Connection db = DriverManager.getConnection(dburl, dbusername, dbpwd);
                    System.out.println("MailChecker  -DB-  Connection success!");
                    Statement st = db.createStatement();
                    ResultSet rs;

                    // Добавляем пользоваетля в группу
                    // указывающую на успешно подтверждённый адрес e-mail.
                    // TODO заменить наличие в группе Mail_OK на отсутствие в групе Mail_NOT_OK.
                    String sql = "SELECT user_id, user_name FROM users WHERE user_email = '" + CookieMail + "'";
                    rs = st.executeQuery(sql);
                    rs.next();
                    String user_id = rs.getString(1);
                    UserName = rs.getString(2);


                    sql = "INSERT INTO group_entries " +
                            "(user_id, group_id, entry_author) VALUES " +
                            "('" + user_id + "', '3', '" + user_id + "')";
                    st.executeUpdate(sql);

                    Result = "Адрес " + CookieMail + " подтверждён!<br>\n";

                    JspRedirect = "/userpage.jsp";
                    sql = "SELECT group_title FROM groups WHERE group_id IN " +
                            "(SELECT group_id FROM group_entries WHERE user_id='" + user_id + "')";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        if (rs.getString(1).equals("ADMINS"))
                            JspRedirect = "/adminpage.jsp";
                    }

                    st.close();
                    rs.close();
                    db.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Result = "Подтверждение адреса e-mail провалилось!<br>\n";
                }
            } else {
                Result = "Подтверждение адреса e-mail провалилось!<br>\n";
            }
        } else {
            Result = "Cookie устарели либо не были установлены.<br>\n";
        }

        // Окрываем страницу index.jsp
        // и передаём ей сообщение о результатах аутентифакации.
        Result = "<b>\n" + Result + "\n</b>\n";
        request.setAttribute("Message", Result);
        request.setAttribute("Nickname", UserName);
        getServletContext().getRequestDispatcher(JspRedirect).forward(
                request, response);
    }
}