<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="tittle.error" var="tittle"/>
<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="col-sm-12 alert-success">
            <h1><fmt:message key="error.occured"/></h1>
        </div>
    </jsp:body>
</t:template_page>
