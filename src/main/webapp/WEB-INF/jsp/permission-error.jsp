<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>
<c:set value="${sessionScope.user.role}" var="role"/>
<fmt:message key="tittle.permission.error" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="panel panel-default">
            <div class="panel-body" align="center">
                <c:if test="${empty role}">
                    <h3><fmt:message key="anonymous.permission.error"/></h3>
                    <h3><a href="${pageContext.request.contextPath}/showLogin.do"><fmt:message key="sign.in"/></a></h3>
                    <h3><a href="${pageContext.request.contextPath}/showRegister.do"><fmt:message key="register"/></a>
                    </h3>
                </c:if>
                <c:if test="${not empty role}">
                    <h3><fmt:message key="permission.error"/></h3>
                </c:if>
            </div>
        </div>
    </jsp:body>
</t:template_page>
