<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<div class="modal fade" id="cart" role="dialog">
    <div class="modal-dialog modal-xs">
        <div class="modal-body">
            <div class="container">
                <div class="row">
                    <div class="col-xs-8">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <div class="panel-title">
                                    <div class="row">
                                        <div class="col-xs-6">
                                            <h5><span class="glyphicon glyphicon-shopping-cart"></span> <fmt:message key="cart.shopping"/>
                                            </h5>
                                        </div>
                                        <div class="col-xs-6">
                                            <button type="button" class="btn btn-primary btn-sm btn-block" data-dismiss="modal">
                                                <span class="glyphicon glyphicon-share-alt"></span> <fmt:message key="cart.continue"/>
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-body">
                                <c:if test="${(empty cart) || (cart.size ==0)}"><h1 align="center"><fmt:message key="cart.empty"/></h1></c:if>
                                <c:if test="${(not empty cart) && (cart.size > 0)}">
                                    <c:forEach items="${cart.cart}" var="cartEntrySet">
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
                                                <div class="col-xs-2">
                                                    <h6><strong>X<span
                                                            class="text-muted"></span></strong>
                                                    </h6>
                                                </div>
                                                <div class="col-xs-2">
                                                    <h6><strong>${quantity}<span
                                                            class="text-muted"></span></strong>
                                                    </h6>
                                                </div>
                                                <div class="col-xs-2">
                                                    <a href="${pageContext.request.contextPath}/cart.do?delete=${product.id}">
                                                        <span class="glyphicon glyphicon-trash"> </span>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                        <hr>
                                    </c:forEach>
                                    <div class="panel-footer">
                                        <div class="row text-center">
                                            <div class="col-xs-9">
                                                <h4 class="text-right"><fmt:message key="cart.total"/> ${cart.allCost}</h4>
                                            </div>
                                            <div class="col-xs-3">
                                                <a href="${pageContext.request.contextPath}/showCheckout.do" class="btn btn-success btn-block" role="button">
                                                    <fmt:message key="cart.checkout"/>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

