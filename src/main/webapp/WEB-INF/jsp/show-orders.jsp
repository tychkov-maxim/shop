<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="tittle.orders" var="tittle"/>

<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="container-fluid">
            <t:get_messages messages="${ordersMessages}"/>

            <t:menu>

                <div class="col-sm-2"></div>
                <div class="col-sm-6">

                    <c:forEach items="${Orders}" var="order">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="text-left"><fmt:message key="order.code"/> ${order.id}</div>
                                <c:if test="${param.status == 'all'}">
                                    <div class="text-left"><fmt:message key="user"/> ${order.user.login}</div>
                                </c:if>
                            </div>
                            <div class="panel-body">
                                <c:forEach items="${order.cart.cart}" var="cartEntrySet">
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
                                                <h6><strong>${product.price}</strong>
                                                </h6>
                                            </div>
                                            <div class="col-xs-3">
                                                <h6><strong>X<span
                                                        class="text-muted"></span></strong>
                                                </h6>
                                            </div>
                                            <div class="col-xs-3">
                                                <h6><strong>${quantity}<span
                                                        class="text-muted"></span></strong>
                                                </h6>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>

                                </c:forEach>
                                <div class="text-right"><strong><fmt:message key="cart.total"/> ${order.total}</strong>
                                </div>
                            </div>
                            <c:if test="${param.status == 'shipping'}">
                                <div class="panel-footer ">
                                    <div>
                                        <a href="${pageContext.request.contextPath}/changeStatus.do?order=${order.id}&status=completed"
                                           class="btn btn-primary" role="button"><fmt:message key="order.got"/></a>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${(param.status == 'all')}">
                                <div class="panel-footer ">
                                    <div>
                                        <a href="${pageContext.request.contextPath}/changeStatus.do?order=${order.id}&status=shipping"
                                           class="btn btn-primary" role="button"><fmt:message key="order.send"/></a>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>

                </div>

            </t:menu>
        </div>
    </jsp:body>
</t:template_page>
