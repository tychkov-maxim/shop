<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="autorization" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <form class="form-horizontal" method="post" action='${pageContext.request.contextPath}/login.do' name="login_form">

            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <t:get_errors errors="${loginError}"/>
                </div>
            </div>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="login"><fmt:message key="login"/></label>
                <div class="col-md-4">
                    <input id="login" name="login" type="text" placeholder="<fmt:message key="enter.login"/>" class="form-control input-md" required>
                </div>
            </div>
            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="password"><fmt:message key="pass"/></label>
                <div class="col-md-4">
                    <input id="password" name="password" type="text" placeholder="<fmt:message key="enter.pass"/>" class="form-control input-md" required>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label" for="signup"></label>
                <div class="col-md-4">
                    <button id ="signup" type="submit" class="btn btn-primary"><fmt:message key="sign.in"/></button>
                </div>
            </div>
        </form>
        <div class="col-md-8"></div>
        <div class="col-md-2">
            <a href="${pageContext.request.contextPath}/showRegister.do" class="btn btn-primary"><fmt:message key="register"/></a>
        </div>
    </jsp:body>
</t:template_page>
