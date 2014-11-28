package email;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


@WebServlet("/Checker")
public class Checker extends HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request,
                          javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

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
                Result = "Адрес e-mail подтверждён!<br>";
            } else {
                Result = "Подтверждение адреса e-mail провалилось! <br>\n" +
                        "— Вернитесь на начальную страницу<br>\n" +
                        "— Повторите отправку проверочного письма.<br>";
            }

        } else {
            Result = "Cookie Устарели либо не были установлены.<br>\n";
        }
        request.setAttribute("Message", Result);
        getServletContext().getRequestDispatcher("/Report.jsp").forward(
                request, response);
    }
}