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
                    <form class="form-horizontal" method="post" action="addCategory.do">
                        <fieldset>

                            <div class="form-group">
                                <label class="col-md-4 control-label"></label>
                                <div class="col-md-4">
                                    <t:get_errors errors="${categoryErrors}"/>
                                    <t:get_messages messages="${categoryMessages}"/>
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
            </t:menu>
        </div>
    </jsp:body>
</t:template_page>
