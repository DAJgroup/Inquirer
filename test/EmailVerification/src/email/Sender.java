package email;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;


@WebServlet("/Sender")
public class Sender extends HttpServlet {
    private String host;
    private String port;
    private String user;
    private String pass;


    public void init() {

        // Получение параметров из web.xml
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request,
                          javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Генерация псевдослучайной строки
        String RandString = String.valueOf(System.currentTimeMillis());
        CRC32 crc = new CRC32();
        crc.update(RandString.getBytes());
        RandString = Long.toHexString(crc.getValue()).toUpperCase();

        String recipient = request.getParameter("recipient");
        String subject = "E-Mail Verification";
        String content = "Проверочная строка: " + RandString + "\n";

        // Хешируем строку
        String HashStr = null;
        try {
            HashStr = UtilHash.getHash(RandString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String resultMessage = "";

        // отправляем e-mail
        try {
            UtilMail.sendEmail(host, port, user, pass,
                    recipient, subject, content);

            // Запись куки в браузер клиенту.
            Cookie c1 = new Cookie("XString", HashStr);
            c1.setMaxAge(60);
            response.addCookie(c1);
            resultMessage = "Письмо отправлено! <br>" + "Куки установлены! <br>";
        } catch (Exception ex) {
            ex.printStackTrace();
            resultMessage = "Что-то пошло не так...   " + ex.getMessage();
        } finally {
            request.setAttribute("Message", resultMessage);
            getServletContext().getRequestDispatcher("/Report.jsp").forward(
                    request, response);
        }
    }
}
