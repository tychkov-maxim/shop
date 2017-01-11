<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>
<c:set value="${sessionScope.user.role}" var="role"/>

<t:template_page tittle="Online Store">
    <jsp:body>
        <div class="container-fluid">
        <t:get_messages messages="${ordersMessages}"/>
        <div class="col-sm-2">
            <ul class="list-group">
                <a href="${pageContext.request.contextPath}/profile.do">
                    <li class="list-group-item">Информация о профиле</li>
                </a>
                <a href="${pageContext.request.contextPath}/orders.do?status=shipping">
                    <li class="list-group-item">Доставляемые заказы</li>
                </a>
                <a href="${pageContext.request.contextPath}/orders.do?status=processing">
                    <li class="list-group-item">Обрабатываемые заказы</li>
                </a>
                <a href="${pageContext.request.contextPath}/orders.do?status=completed">
                    <li class="list-group-item">Завершенные заказы</li>
                </a>

                <c:if test="${role.id == 3}">
                    <a href="${pageContext.request.contextPath}/orders.do?status=all">
                        <li class="list-group-item">Обработать заказы</li>
                    </a>
                    <a href="${pageContext.request.contextPath}/find.do">
                        <li class="list-group-item">Работа с пользователями</li>
                    </a>
                </c:if>

            </ul>
        </div>

        <div class="col-sm-10">
            <div class="col-sm-2"></div>
            <div class="col-sm-6">

                <c:forEach items="${Orders}" var="order">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <div class="text-left">Код заказа: ${order.id}</div>
                            <c:if test="${param.status == 'all'}"> <div class="text-left">Пользователь: ${order.user.login}</div></c:if>
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
                            <div class="text-right"><strong>Total: ${order.total}</strong></div>
                        </div>
                        <c:if test="${param.status == 'shipping'}">
                        <div class="panel-footer ">
                            <div><a href="${pageContext.request.contextPath}/changeStatus.do?order=${order.id}&status=completed" class="btn btn-primary" role="button">Получил</a></div>
                        </div>
                        </c:if>
                        <c:if test="${(param.status == 'all') && (role.id == 3)}">
                            <div class="panel-footer ">
                                <div><a href="${pageContext.request.contextPath}/changeStatus.do?order=${order.id}&status=shipping" class="btn btn-primary" role="button">Отправить заказ</a></div>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>

            </div>

        </div>
    </jsp:body>
</t:template_page>
