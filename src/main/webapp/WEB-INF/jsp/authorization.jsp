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

                <form class="form-horizontal" method="post" action='' name="login_form">


                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="login"><fmt:message key="login"/></label>
                            <div class="col-md-8">
                                <input id="login" name="login" type="text" placeholder="<fmt:message key="enter.login"/>" class="form-control input-md">
                            </div>
                        </div>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="passwd"><fmt:message key="pass"/></label>
                            <div class="col-md-8">
                                <input id="passwd" name="passwd" type="text" placeholder="<fmt:message key="enter.pass"/>" class="form-control input-md">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-4">
                                <button id ="signup" type="submit" class="btn btn-primary"><fmt:message key="sign.in"/></button>
                            </div>
                        </div>

                </form>

            </div>
            <div class="modal-footer">
                <a href="#" class="btn btn-primary"><fmt:message key="register"/></a>
            </div>
        </div>
    </div>
</div>
