<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="tittle.product" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="container-fluid">
            <t:get_errors errors="${productMessage}"/>
            <c:if test="${not empty product}">
                <div class="col-sm-4"></div>
                <div class="col-sm-4">
                    <div class="panel panel-primary">
                        <div class="panel-heading">${product.name}</div>
                        <div class="panel-body" align="center">
                            <img src="/image${product.imagePath}" class="img-responsive" alt="Image"/>
                        </div>
                        <div class="panel-footer">${product.price}</div>
                    </div>
                <div class="panel panel-primary">
                    <div class="panel-body" align="center">
                            ${product.description}
                    </div>
                    <div class="panel-footer">
                        <button class="button btn-primary">Buy</button>
                        <a href="${pageContext.request.contextPath}/cart.do?add=${product.id}">Add to cart</a>
                    </div>
                </div>
                <div class="col-sm-4"></div>
            </c:if>
        </div>
    </jsp:body>
</t:template_page>
