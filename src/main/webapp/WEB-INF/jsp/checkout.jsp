<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="cart.checkout" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="container centered">
            <div class="row">
                <div class="col-xs-2"></div>
                <div class="col-xs-8">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <t:get_errors errors="${checkoutErrors}"/>
                            <c:if test="${(empty cart) || (cart.size ==0)}"><h1 align="center"><fmt:message
                                    key="cart.empty"/></h1></c:if>
                            <c:if test="${(not empty cart) && (cart.size > 0)}">
                            <c:forEach items="${cart.cart}" var="cartEntrySet">
                                <c:set var="product" value="${cartEntrySet.getKey()}"/>
                                <c:set var="quantity" value="${cartEntrySet.getValue()}"/>
                                <div class="row">
                                    <div class="col-xs-2"><img class="img-responsive"
                                                               src="${pageContext.request.contextPath}/image${product.imagePath}">
                                    </div>
                                    <div class="col-xs-4">
                                        <h4 class="product-name"><strong>${product.name}</strong></h4>
                                    </div>
                                    <div class="col-xs-6">
                                        <div class="col-xs-6 text-right">
                                            <h6><strong>${product.price}<span class="text-muted">x</span></strong></h6>
                                        </div>
                                        <div class="col-xs-4">
                                            <input type="text" class="form-control input-sm" value="${quantity}"
                                                   onchange="window.location='${pageContext.request.contextPath}/cart.do?change=${product.id}&quantity='+this.value">
                                        </div>
                                        <div class="col-xs-2">
                                            <a href="${pageContext.request.contextPath}/cart.do?delete=${product.id}">
                                                <span class="glyphicon glyphicon-trash"> </span>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <hr>
                            </c:forEach>
                        </div>
                        <div class="panel-footer">
                            <div class="row text-center">
                                <div class="col-xs-4">
                                    <h4 class="text-left"><fmt:message key="check.account"/> ${user.account}</h4>
                                </div>
                                <div class="col-xs-5">
                                    <h4 class="text-right"><fmt:message key="cart.total"/> ${cart.allCost}</h4>
                                </div>
                                <div class="col-xs-3">
                                    <c:if test="${empty checkoutErrors}">
                                        <a role="button" class="btn btn-success btn-block"
                                           href="${pageContext.request.contextPath}/order.do">
                                            <fmt:message key="confirm"/>
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:template_page>
