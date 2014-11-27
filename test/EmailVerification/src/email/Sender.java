package email;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;


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

        // Получение донных из Form.jsp
        String recipient = request.getParameter("recipient");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");


        String resultMessage = "";

        try {
            Util.sendEmail(host, port, user, pass,
                    recipient, subject, content);
            resultMessage = "Письмо отправлено!  > > >  " + recipient + " : : " + subject + " : : " + content;
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
