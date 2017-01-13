<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<fmt:message key="tittle.find.user" var="tittle"/>
<t:template_page tittle="${tittle}">
    <jsp:body>
        <div class="container-fluid">
            <t:menu>
                <form class="form-horizontal" method="get" action='${pageContext.request.contextPath}/user.do'
                      name="login_form">

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="login"><fmt:message key="login"/></label>
                        <div class="col-md-4">
                            <input id="login" name="login" type="text" placeholder="<fmt:message key="enter.login"/>"
                                   class="form-control input-md" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-4 control-label" for="signup"></label>
                        <div class="col-md-4">
                            <button id="signup" type="submit" class="btn btn-primary"><fmt:message key="find"/></button>
                        </div>
                    </div>
                </form>
            </t:menu>
        </div>
    </jsp:body>
</t:template_page>
