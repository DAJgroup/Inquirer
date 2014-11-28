<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Форма проверки</title>
</head>
<body>
<div style="text-align: center;">
    <h3><%=request.getAttribute("Message")%>
    </h3>
</div>
<form action="Checker" method="post">
    <table border="0" width="35%" align="center">
        <caption><h2>Подтверждение адреса e-mail</h2></caption>
        <tr>
            <td width="50%">Введите полученную строку:</td>
            <td><input type="text" name="ClientStr" size="50" title="string"/></td>
        </tr>
        <tr>
            <td colspan="2" align="center"><input type="submit" value="Send"/></td>
        </tr>
    </table>
</form>
</body>
</html>

