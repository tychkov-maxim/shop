<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="tittle.product" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="container-fluid">
        <t:get_messages messages="${productMessage}"/>
        <c:if test="${not empty product}">
            <div class="col-sm-4"></div>
            <div class="col-sm-4">
            <div class="panel panel-primary">
                <div class="panel-heading">${product.name}</div>
                <div class="panel-body" align="center">
                    <img src="${pageContext.request.contextPath}/image${product.imagePath}" class="img-responsive"
                         alt="Image"/>
                </div>
                <div class="panel-footer">${product.price}</div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-body" align="center">
                        ${product.description}
                </div>
            </div>

            <div class="panel panel-primary">
                <div class="panel-body">


                    <a href="${pageContext.request.contextPath}/cart.do?add=${product.id}" id="buyProduct"
                       class="btn btn-success" role="button"><fmt:message key="buy"/></a>
                    <br/>
                    <br/>
                    <form class="form-inline">
                        <button onclick="$.get('${pageContext.request.contextPath}/cart.do?add=${product.id}&quantity='+$('#inlineFormInputQuantity').val());location.reload();"
                                class="btn btn-primary" type="reset"><fmt:message key="cart.add"/></button>
                        <input type="number" class="form-control mb-2 mr-sm-2 mb-sm-0" id="inlineFormInputQuantity"
                               name="quantity" value="1">
                    </form>
                </div>
            </div>
            <div class="col-sm-4"></div>
        </c:if>
        </div>
    </jsp:body>
</t:template_page>
