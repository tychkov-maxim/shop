<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="tittle.register" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="container-fluid">
            <t:menu>
                <div class="col-sm-6">
                    <form class="form-horizontal" method="post" action="addCategory.do">
                        <fieldset>

                            <div class="form-group">
                                <label class="col-md-4 control-label"></label>
                                <div class="col-md-4">
                                    <t:get_errors errors="${categoryErrors}"/>
                                    <fmt:message key="add.category"/>
                                </div>
                            </div>
                            <!-- Text input-->
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="categoryName"><fmt:message
                                        key="category.name"/></label>
                                <div class="col-md-4">
                                    <input id="categoryName" name="name" type="text"
                                           placeholder="<fmt:message key="enter.category.name"/>"
                                           class="form-control input-md"
                                           value="${oldName}" required>
                                    <t:get_errors errors="${nameErrors}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label" for="description"><fmt:message
                                        key="description"/></label>
                                <div class="col-md-4">
                                    <input id="description" name="description" type="text"
                                           class="form-control input-md"
                                           value="${oldDescription}">
                                    <t:get_errors errors="${descriptionErrors}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-4 control-label" for="category"></label>
                                <div class="col-md-4">
                                    <button id="category" name="category" class="btn btn-primary"><fmt:message
                                            key="add"/></button>
                                </div>
                            </div>

                        </fieldset>
                    </form>

                </div>
                <div class="col-sm-6">
                    <form class="form-horizontal" method="post" action="register.do">
                        <fieldset>

                            <div class="form-group">
                                <label class="col-md-4 control-label"></label>
                                <div class="col-md-4">
                                    Добавить товар
                                </div>
                            </div>
                            <!-- Text input-->
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="login"><fmt:message key="login"/></label>
                                <div class="col-md-4">
                                    <input id="login" name="login" type="text"
                                           placeholder="<fmt:message key="enter.login"/>" class="form-control input-md"
                                           value="${regLogin}" required>
                                    <t:get_errors errors="${loginErrors}"/>
                                </div>
                            </div>

                            <!-- Text input-->
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="passwd"><fmt:message key="pass"/></label>
                                <div class="col-md-4">
                                    <select class="form-control" id="exampleSelect1">
                                        <option>tv</option>
                                        <option>tech</option>
                                        <option>andonemore</option>
                                    </select>
                                    <t:get_errors errors="${passwdErrors}"/>
                                </div>
                            </div>


                            <!-- Password input-->
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="first_name"><fmt:message
                                        key="first_name"/></label>
                                <div class="col-md-4">
                                    <input id="first_name" name="first_name" type="text"
                                           placeholder="<fmt:message key="enter.first_name"/>"
                                           class="form-control input-md"
                                           value="${regFirstName}">
                                    <t:get_errors errors="${first_nameErrors}"/>
                                </div>
                            </div>

                            <!-- Text input-->
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="second_name"><fmt:message
                                        key="second_name"/></label>
                                <div class="col-md-4">
                                    <input id="second_name" name="second_name" type="text"
                                           placeholder="<fmt:message key="enter.second_name"/>"
                                           class="form-control input-md" value="${regSecondName}">
                                    <t:get_errors errors="${second_nameErrors}"/>
                                </div>
                            </div>

                            <!-- Text input-->
                            <div class="form-group">
                                <label class="col-md-4 control-label" for="address"><fmt:message key="address"/></label>
                                <div class="col-md-4">
                                    <input id="address" name="address" type="text"
                                           placeholder="<fmt:message key="enter.address"/>"
                                           class="form-control input-md"
                                           value="${regAddress}">
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

                </div>


            </t:menu>


        </div>
    </jsp:body>
</t:template_page>
