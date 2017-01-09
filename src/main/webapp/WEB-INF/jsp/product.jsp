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
            </div>

            <div class="panel panel-primary">
                <div class="panel-body" align="center">

                    <div class="col-xs-12">
                        <div class="col-xs-10">
                            <a href="${pageContext.request.contextPath}/cart.do?add=${product.id}" id="buyProduct"
                               class="btn btn-success" role="button"><fmt:message key="buy"/></a>
                            <a href="${pageContext.request.contextPath}/cart.do?add=${product.id}" id="addToCart"
                               class="btn btn-primary" role="button"><fmt:message key="cart.add"/></a>
                        </div>
                        <div class="col-xs-2">
                            <input type="text" class="form-control input-sm" value="1" name="quantity">
                        </div>
                    </div>

                </div>
            </div>
            <div class="col-sm-4"></div>
        </c:if>
        </div>
        <script>
            link = document.getElementById('addToCart');
            ref = document.referrer;
            found = ref.search('[?]');
            if (found != -1)
                link.href = link.href + '&' + ref.substr(found + 1);
        </script>
    </jsp:body>
</t:template_page>
