<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Форма отправки</title>
</head>
<body>
<form action="Sender" method="post">
    <table border="0" width="35%" align="center">
        <caption><h2>Подтверждение e-mail.</h2></caption>
        <tr>
            <td width="50%">E-mail для подтверждения:</td>
            <td><input type="text" name="recipient" size="50"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Send"/></td>
        </tr>
    </table>
</form>
</body>
</html>

