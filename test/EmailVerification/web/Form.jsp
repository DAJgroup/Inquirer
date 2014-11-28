<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Форма отправки</title>
</head>
<body>
<form action="Sender" method="post">
    <table border="0" width="35%" align="center">
        <caption><h2>Подтверждение адреса e-mail</h2></caption>
        <tr>
            <td width="50%">Адрес e-mail для подтверждения:</td>
            <td><input type="text" name="recipient" size="50" title="e-mail address"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Отправить проверочное письмо"/></td>
        </tr>
    </table>
</form>
</body>
</html>

