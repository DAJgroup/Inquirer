package Inquirer;

import Inquirer.Util.UtilHash;
import Inquirer.Util.UtilMail;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "sender", urlPatterns = "/sender")
public class sender extends HttpServlet {


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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String UserEmail = request.getParameter("UserEmail");
        String JspRedirect = request.getParameter("JspRedirect");
        String UserName = request.getParameter("UserName");
        String message = "";
        boolean error;


        try {
            // Генерация ссылки
            String Host = request.getServerName();
            String Port = Integer.toString(request.getServerPort());
            String Path = getServletContext().getContextPath();
            String ServerURL = "http://" + Host + ":" + Port + Path + "/MailCheckerServlet";

            // Генерируем проверочную строку
            String symbols = "0123456789ABCDEF";
            StringBuilder Xstring = new StringBuilder();
            int count = 16;
            for (int i = 0; i < count; i++)
                Xstring.append(symbols.charAt((int) (Math.random() * symbols.length())));

            // Хешируем проверочную строку
            String HasXstring = null;
            try {
                HasXstring = UtilHash.getHash(Xstring.toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                error = true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                error = true;
            }

            String subject = "Подтверждение регистрации.";
            // <a href="URL">текст ссылки</a>
            String content = "\n<a href=\"" + ServerURL + "?ClientStr=" + Xstring.toString() + "\">" + ServerURL + "</a>\n";
            System.out.println("Send \"" + content + "\" to " + UserEmail);

            // Запись куки в браузер клиенту.
            try {
                Cookie c1 = new Cookie("MailCheck", HasXstring);
                c1.setMaxAge(60 * 60);
                response.addCookie(c1);
                Cookie c2 = new Cookie("MailAddr", UserEmail);
                c2.setMaxAge(60 * 60);
                response.addCookie(c2);
            } catch (Exception ex) {
                ex.printStackTrace();
                error = true;
            }


            UtilMail.sendEmail(host, port, user, pass, UserEmail, subject, content);
            message += "<br>\n<br>\nНа вашу почту было выслано письмо для подтверждения регистрации.<br>";


        } catch (MessagingException e) {
            e.printStackTrace();
            message += "ОШИБКА: Невозможно отправить письмо на указаный Вами адрес!";
            error = true;
        }


        message = "<b>\n" + message + "\n</b>\n";
        request.setAttribute("Message", message);
        request.setAttribute("Nickname", UserName);
        getServletContext().getRequestDispatcher(JspRedirect).forward(
                request, response);

    }
}
