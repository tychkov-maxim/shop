<%@tag description="Tag get errors from list of errors" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<%@attribute name="messages" required="true" type="java.util.List" %>

<c:forEach items="${messages}"  var="message">
    <div class="alert alert-info col-md-12 text-center">
        <fmt:message key="${message}"/><br/>
        <br/>
    </div>
</c:forEach>
