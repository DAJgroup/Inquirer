<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div style="text-align: center;">
    <h3><%=request.getAttribute("Message")%>
    </h3>
    <br/>
    <br/>

    <form action="Form.jsp">
        <button type="submit">Вернуться на начальную страницу</button>
    </form>
</div>
</body>
</html>
