<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="lang"/>
<html>
<head>
    <title><fmt:message key="welcome"/></title>
    <meta http-equiv="refresh" content="1;URL=${pageContext.request.contextPath}/"/>
</head>
<body>
<h1>
    <fmt:message key="hello">
        <fmt:param value="${user.firstName}"/>
    </fmt:message>
</h1>
</body>
</html>
