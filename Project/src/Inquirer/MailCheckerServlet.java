package Inquirer;

import Inquirer.Util.UtilHash;

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


    protected void doGet(javax.servlet.http.HttpServletRequest request,
                         javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {


        // Ищем наш куки.
        String CookieString = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("MailCheck")) {
                CookieString = cookie.getValue();
            }
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
        if (CookieString != null) {         // FALSE == Нужный куки не найден.
            if (HASHClientString.equals(CookieString)) {
                Result = "Адрес " + RegServlet.NewUserEmail + " подтверждён!<br>\n";


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

                    // Добавляем пользоваетля в группу
                    // указывающую на успешно подтверждённый адрес e-mail.
                    // TODO заменить наличие в группе Mail_OK на отсутствие в групе Mail_NOT_OK.
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
                Result = "Подтверждение адреса e-mail провалилось!<br>\n";
            }
        } else {
            Result = "Cookie Устарели либо не были установлены.<br>\n";
        }

        // Окрываем страницу index.jsp
        // и передаём ей сообщение о результатах аутентифакации.
        request.setAttribute("Message", Result);
        getServletContext().getRequestDispatcher("/index.jsp").forward(
                request, response);
    }
}