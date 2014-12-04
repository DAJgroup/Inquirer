<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Регистрация</title>
</head>
<body>
<form action="RegServlet" method="post">
    <table border="0" width="35%" align="center">
        <caption><h2>Страница регистрации</h2></caption>
        <tr>
            <td width="30%">Логин</td>
            <td><input type="text" name="NewUserName" size="35" title="NewUserName"/></td>
        </tr>
        <tr>
            <td>E-mail</td>
            <td><input type="text" name="NewUserEmail" size="35" title="NewUserEmail"/></td>
        </tr>
        <tr>
            <td>Пароль</td>
            <td><input type="text" name="NewUserPWD" size="35" title="NewUserPWD"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Вход"/></td>
        </tr>
    </table>
</form>
</body>
</html>