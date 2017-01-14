<%@tag description="Tag get errors from list of errors" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<c:set value="${sessionScope.user.role}" var="role"/>

<div class="col-sm-2">
    <ul class="list-group">
        <a href="${pageContext.request.contextPath}/profile.do">
            <li class="list-group-item"><fmt:message key="menu.profile.info"/></li>
        </a>
        <a href="${pageContext.request.contextPath}/orders.do?status=shipping">
            <li class="list-group-item"><fmt:message key="menu.shipping.orders"/></li>
        </a>
        <a href="${pageContext.request.contextPath}/orders.do?status=processing">
            <li class="list-group-item"><fmt:message key="menu.processing.orders"/></li>
        </a>
        <a href="${pageContext.request.contextPath}/orders.do?status=completed">
            <li class="list-group-item"><fmt:message key="menu.completed.orders"/></li>
        </a>
        <c:if test="${role.id == 3}">
            <a href="${pageContext.request.contextPath}/orders.do?status=all">
                <li class="list-group-item"><fmt:message key="menu.checkout.orders"/></li>
            </a>
            <a href="${pageContext.request.contextPath}/findUser.do">
                <li class="list-group-item"><fmt:message key="menu.work.users"/></li>
            </a>
            <a href="${pageContext.request.contextPath}/category.do">
                <li class="list-group-item"><fmt:message key="menu.add.category"/></li>
            </a>
            <a href="${pageContext.request.contextPath}/products.do">
                <li class="list-group-item"><fmt:message key="menu.add.product"/></li>
            </a>
        </c:if>
    </ul>

</div>

<div class="col-sm-10">

    <jsp:doBody/>

</div>



