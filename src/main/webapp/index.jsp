<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="welcome" var="welcome"/>
<t:template_page tittle="${welcome}">
    <jsp:body>
        <div class="col-sm-12 alert-success">
            <h1>${welcome}</h1>
        </div>
        <c:redirect url="${pageContext.request.contextPath}/show.do"/>
    </jsp:body>
</t:template_page>
