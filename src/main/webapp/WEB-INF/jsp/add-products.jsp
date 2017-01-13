<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="tittle.add.product" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="container-fluid">
            <t:menu>
                <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/addProduct.do" enctype="multipart/form-data">
                    <fieldset>

                        <div class="form-group">
                            <label class="col-md-4 control-label"></label>
                            <div class="col-md-4">
                                <t:get_errors errors="${productsErrors}"/>
                                <t:get_messages messages="${productsMessages}"/>
                                <fmt:message key="enter.product"/>
                            </div>
                        </div>
                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="name"><fmt:message key="product.name"/></label>
                            <div class="col-md-4">
                                <input id="name" name="name" type="text"
                                       placeholder="<fmt:message key="enter.product.name"/>" class="form-control input-md"
                                       value="${oldName}" required>
                                <t:get_errors errors="${nameErrors}"/>
                            </div>
                        </div>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="category"><fmt:message key="category.name"/></label>
                            <div class="col-md-4">
                                <select class="form-control" id="category" name="category">
                                    <c:forEach items="${categories}" var="category">
                                        <option>${category.name}</option>
                                    </c:forEach>
                                </select>
                                <t:get_errors errors="${categoryErrors}"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-md-4 control-label" for="description"><fmt:message
                                    key="description"/></label>
                            <div class="col-md-4">
                                <input id="description" name="description" type="text"
                                       placeholder="<fmt:message key="enter.description"/>"
                                       class="form-control input-md"
                                       value="${oldDescription}">
                                <t:get_errors errors="${descriptionErrors}"/>
                            </div>
                        </div>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="price"><fmt:message
                                    key="price"/></label>
                            <div class="col-md-4">
                                <input id="price" name="price" type="text"
                                       placeholder="<fmt:message key="enter.price"/>"
                                       class="form-control input-md" value="${oldPrice}">
                                <t:get_errors errors="${priceErrors}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label" for="file"><fmt:message key="choose.image"/></label>
                            <div class="col-md-4">
                                <input id="file" type="file" class="file" name="image">
                                <t:get_errors errors="${imageErrors}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label" for="add"></label>
                            <div class="col-md-4">
                                <button id="add" name="add" class="btn btn-primary"><fmt:message
                                        key="add"/></button>
                            </div>
                        </div>

                    </fieldset>
                </form>

            </t:menu>

        </div>
    </jsp:body>
</t:template_page>
