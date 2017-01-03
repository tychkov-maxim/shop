<%@tag description="Tag get errors from list of errors" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<%@attribute name="errors" required="true" %>

<c:forEach items="${errors}" var="error">
    <div class="alert alert-danger col-md-12">
        <fmt:message key="${error}"/><br/>
    </div>
</c:forEach>
