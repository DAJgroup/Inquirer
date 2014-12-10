package Inquirer.Util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;
import java.util.zip.CRC32;


public class UtilMail extends HttpServlet {



    public static void SendEmail(HttpServletResponse response, String toAddress)
            throws ServletException, IOException, MessagingException {
        response.setCharacterEncoding("UTF-8");


// Получение параметров для почтового клиента из web.xml
//        ServletContext context = getServletContext();                 // TODO УБРАТЬ КОСТЫЛЬ
//        String host = context.getInitParameter("host");               // TODO УБРАТЬ КОСТЫЛЬ
//        String port = context.getInitParameter("port");               // TODO УБРАТЬ КОСТЫЛЬ
//        final String userName = context.getInitParameter("user");     // TODO УБРАТЬ КОСТЫЛЬ
//        final String password = context.getInitParameter("pass");     // TODO УБРАТЬ КОСТЫЛЬ
        String host = "smtp.mail.ru";                   // TODO УБРАТЬ КОСТЫЛЬ
        String port = "587";                            // TODO УБРАТЬ КОСТЫЛЬ
        final String userName = "dag.group@list.ru";    // TODO УБРАТЬ КОСТЫЛЬ
        final String password = "zxcv71237";            // TODO УБРАТЬ КОСТЫЛЬ

        String Xstring = String.valueOf(System.currentTimeMillis());
        CRC32 crc = new CRC32();
        crc.update(Xstring.getBytes());
        Xstring = Long.toHexString(crc.getValue()).toUpperCase();

        // Хешируем проверочную строку
        String HasXstring = null;
        try {
            HasXstring = UtilHash.getHash(Xstring); // TODO солить
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String subject = "Подтверждение регистрации.";
        String content = "http://localhost:8080/MailCheckerServlet?ClientStr=" + Xstring; //request.getRequestURL().toString(); TODO подсунуть динамически.

        // Запись куки в браузер клиенту.
        try {
            Cookie c1 = new Cookie("MailCheck", HasXstring);
            c1.setMaxAge(60 * 60);
            response.addCookie(c1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");


        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);


        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(content);


        Transport.send(msg);

    }
}