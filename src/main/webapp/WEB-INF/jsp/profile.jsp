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

            <t:menu>


                <div class="col-sm-3"></div>
                <div class="col-sm-4">

                    <c:if test="${empty profileMessages}">
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
                            <c:if test="${not empty requestScope.user}">
                                <div class="panel-footer" align="center">
                                    <a href="${pageContext.request.contextPath}/user.do?login=${user.login}&admin=true" class="btn btn-success" role="button">Повысить до одмена</a>
                                    <br/>
                                    <br/>

                                    <form class="form-inline" method="get" action="${pageContext.request.contextPath}/user.do">
                                        <button type="submit" class="btn btn-primary">Изменить счет</button>
                                        <input type="number" class="form-control mb-2 mr-sm-2 mb-sm-0" id="inlineFormInput" name="money">
                                        <input type="hidden" name="login" value="${user.login}">
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </c:if>
                </div>


            </t:menu>


        </div>
    </jsp:body>
</t:template_page>
