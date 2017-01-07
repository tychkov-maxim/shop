<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="tittle.product" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="container-fluid">
                <div class="col-sm-2">
                    <div class="panel panel-primary">
                        <div class="panel-heading">${product.name}</div>
                        <div class="panel-body"><img
                                src="/image${product.imagePath}"
                                class="img-responsive" style="width:100%" alt="Image"></div>
                        <div class="panel-footer">${product.price}</div>
                    </div>
                </div>
        </div>
    </jsp:body>
</t:template_page>
