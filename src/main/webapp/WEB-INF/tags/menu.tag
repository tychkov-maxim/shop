<%@tag description="Tag get errors from list of errors" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<c:set value="${sessionScope.user.role}" var="role"/>

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
            <a href="${pageContext.request.contextPath}/addProducts.do">
                <li class="list-group-item">Добавить товар</li>
            </a>
        </c:if>
    </ul>

</div>

<div class="col-sm-10">

    <jsp:doBody/>

</div>



