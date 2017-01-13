<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="tittle.register" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <form class="form-horizontal" method="post" action="register.do">
            <fieldset>

                <div class="form-group">
                    <label class="col-md-4 control-label"></label>
                    <div class="col-md-4">
                        <t:get_errors errors="${registerError}"/>
                    </div>
                </div>
                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="login"><fmt:message key="login"/></label>
                    <div class="col-md-4">
                        <input id="login" name="login" type="text" placeholder="<fmt:message key="enter.login"/>"
                               class="form-control input-md" value="${regLogin}" required>
                        <t:get_errors errors="${loginErrors}"/>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="passwd"><fmt:message key="pass"/></label>
                    <div class="col-md-4">
                        <input id="passwd" name="passwd" type="password" placeholder="<fmt:message key="enter.pass"/>"
                               class="form-control input-md">
                        <t:get_errors errors="${passwdErrors}"/>
                    </div>
                </div>


                <!-- Password input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="first_name"><fmt:message key="first_name"/></label>
                    <div class="col-md-4">
                        <input id="first_name" name="first_name" type="text"
                               placeholder="<fmt:message key="enter.first_name"/>" class="form-control input-md"
                               value="${regFirstName}">
                        <t:get_errors errors="${first_nameErrors}"/>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="second_name"><fmt:message key="second_name"/></label>
                    <div class="col-md-4">
                        <input id="second_name" name="second_name" type="text"
                               placeholder="<fmt:message key="enter.second_name"/>" class="form-control input-md"
                               value="${regSecondName}">
                        <t:get_errors errors="${second_nameErrors}"/>
                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="address"><fmt:message key="address"/></label>
                    <div class="col-md-4">
                        <input id="address" name="address" type="text" placeholder="<fmt:message key="enter.address"/>"
                               class="form-control input-md" value="${regAddress}">
                        <t:get_errors errors="${addressErrors}"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="register"></label>
                    <div class="col-md-4">
                        <button id="register" name="register" class="btn btn-primary"><fmt:message
                                key="register"/></button>
                    </div>
                </div>

            </fieldset>
        </form>

    </jsp:body>
</t:template_page>
