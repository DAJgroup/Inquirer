<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Главная</title>
</head>
<body>
<form action="AuthServlet" method="post">
    <table border="0" width="35%" align="center">
        <caption><h2>Главная страница</h2></caption>
        <tr>
            <td width="30%">Логин/e-mail</td>
            <td><input type="text" name="UserName" size="25" title="UserLogin"/></td>
        </tr>
        <tr>
            <td>Пароль</td>
            <td><input type="text" name="UserPWD" size="25" title="UserPWD"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Подтвердить"/></td>
        </tr>
    </table>
</form>
<br/>
<br/>

<form action="Register.jsp" method="post">
    <div style="text-align: center;">
        <button type="submit">Регистрация</button>
    </div>
</form>
</body>
</html>