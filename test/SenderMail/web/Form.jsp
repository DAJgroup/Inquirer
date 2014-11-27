<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Форма отправки</title>
</head>
<body>
<form action="Sender" method="post">
    <table border="0" width="35%" align="center">
        <caption><h2>Форма отправки е-email.</h2></caption>
        <tr>
            <td width="50%">Адрес получателя:</td>
            <td><input type="text" name="recipient" size="50"/></td>
        </tr>
        <tr>
            <td>Тема:</td>
            <td><input type="text" name="subject" size="50"/></td>
        </tr>
        <tr>
            <td>Текст:</td>
            <td><textarea rows="10" cols="39" name="content"></textarea></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Send"/></td>
        </tr>
    </table>

</form>
</body>
</html>

