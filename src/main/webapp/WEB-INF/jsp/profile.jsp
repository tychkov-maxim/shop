<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>
<c:set value="${sessionScope.user.role}" var="role"/>

<t:template_page tittle="Online Store">
    <jsp:body>
        <div class="container-fluid">
            <t:get_messages messages="${profileMessages}"/>
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
                        <a href="#">
                            <li class="list-group-item">Обработать заказы</li>
                        </a>
                        <a href="#">
                            <li class="list-group-item">Повысить пользователя до админа</li>
                        </a>
                        <a href="#">
                            <li class="list-group-item">Пополнить счет пользователя</li>
                        </a>
                    </c:if>

                </ul>
            </div>

            <div class="col-sm-10">
                <div class="col-sm-3"></div>
                <div class="col-sm-4">

                    <div class="panel panel-primary">
                        <div class="panel-heading">Info</div>
                        <h3>
                        <div class="panel-body text-center">
                            <div> Name: ${user.firstName}</div>
                        </div>
                        <div class="panel-body text-center">
                            <div> Last name: ${user.lastName}</div>
                        </div>
                        <div class="panel-body text-center">
                            <div> Login: ${user.login}</div>
                        </div>
                        <div class="panel-body text-center">
                            <div> Role: ${user.role.name}</div>
                        </div>
                        <div class="panel-body text-center">
                            <div> Account: ${user.account}</div>
                        </div>
                        <div class="panel-body text-center">
                            <div> Address: ${user.address}</div>
                        </div>
                        </h3>
                    </div>

                </div>
            </div>

        </div>
    </jsp:body>
</t:template_page>
