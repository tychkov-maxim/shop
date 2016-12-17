<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>

<div class="modal fade" id="auth" role="dialog">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">x</button>
                <h3><fmt:message key="autorization"/></h3>
            </div>
            <div class="modal-body">
                <form method="post" action='' name="login_form">
                    <p><input type="text" class="span3" name="eid" id="login" placeholder="<fmt:message key="login"/>" oninvalid="this.setCustomValidity('your message')" required></p>
                    <p><input type="password" class="span3" name="passwd" placeholder=<fmt:message key="pass"/>></p>
                    <p>
                        <button type="submit" class="btn btn-primary"><fmt:message key="sign.in"/></button>
                    </p>
                </form>
            </div>
            <div class="modal-footer">
                <a href="#" class="btn btn-primary"><fmt:message key="register"/></a>
            </div>
        </div>
    </div>
</div>
