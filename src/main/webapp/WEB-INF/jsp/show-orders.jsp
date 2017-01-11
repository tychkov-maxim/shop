<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

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
            </ul>
        </div>

        <div class="col-sm-10">
            <div class="col-sm-2"></div>
            <div class="col-sm-6">

                <c:forEach items="${shippingOrders}" var="order">
                    <div class="panel panel-primary">
                        <div class="panel-heading">${order.id}</div>
                        <div class="panel-body text-center">
                            <div> ${order.status} </div>
                        </div>
                        <div class="panel-footer">
                            <div> ${order.total} </div>
                        </div>
                    </div>
                </c:forEach>


                <c:forEach items="${processingOrders}" var="order">
                    <div class="panel panel-primary">
                        <div class="panel-heading">${order.id}</div>
                        <div class="panel-body text-center">
                            <div> ${order.status.name} </div>
                        </div>
                        <div class="panel-footer">
                            <div> ${order.total} </div>
                        </div>
                    </div>
                </c:forEach>

                <c:forEach items="${completedOrders}" var="order">
                    <div class="panel panel-primary">
                        <div class="panel-heading">${order.id}</div>
                        <div class="panel-body text-center">
                            <div> ${order.status.name} </div>
                        </div>
                        <div class="panel-footer">
                            <div> ${order.total} </div>
                        </div>
                    </div>
                </c:forEach>


            </div>

        </div>
    </jsp:body>
</t:template_page>
