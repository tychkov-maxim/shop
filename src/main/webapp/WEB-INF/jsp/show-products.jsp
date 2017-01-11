<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>
<c:set var="productsInRow" value="4"/>

<t:template_page tittle="Online Store">
    <jsp:body>
        <div class="container-fluid">
            <t:get_messages messages="${productsMessage}"/>
            <div class="col-sm-2">
                    <ul class="list-group">
                        <c:forEach items="${categories}" var="category">
                            <a href="${pageContext.request.contextPath}/show.do?category=${category.name}">
                                <c:if test="${chosenCategory == category.name}">
                                    <li class="list-group-item active">${category.name}</li>
                                </c:if>
                                <c:if test="${chosenCategory != category.name}">
                                    <li class="list-group-item">${category.name}</li>
                                </c:if>
                            </a>
                        </c:forEach>
                    </ul>
            </div>

            <div class="col-sm-10">
                    <c:set var="i" value="0"/>
                    <c:forEach items="${products}" var="product">
                        <%--&lt;%&ndash;each 4 product need to add new block&ndash;%&gt;--%>
                        <c:if test="${i % productsInRow == 0}">
                        <div class="container-fluid">

                        </c:if>
                        <c:set var="i" value="${i + 1}"/>
                        <a href="product.do?id=${product.id}">
                            <div class="col-sm-3">
                                <div class="panel panel-primary">
                                    <div class="panel-heading">${product.name}</div>
                                    <div class="panel-body">
                                        <img src="${pageContext.request.contextPath}/image${product.imagePath}"
                                                class="img-responsive" alt="Image" style="width: 100%"></div>
                                    <div class="panel-footer">${product.price}</div>
                                </div>
                            </div>
                        </a>
                        <c:if test="${i % productsInRow == 0}"></div></c:if>
                    </c:forEach>
            </div>

            <ul class="pager">
                <c:if test="${not empty previousPage}">
                    <li>
                        <a href="${pageContext.request.contextPath}/show.do?page=${previousPage}&category=${param.category}">Previous</a>
                    </li>
                </c:if>
                <c:if test="${not empty nextPage}">
                    <li>
                        <a href="${pageContext.request.contextPath}/show.do?page=${nextPage}&category=${param.category}">Next</a>
                    </li>
                </c:if>
            </ul>


        </div>
    </jsp:body>
</t:template_page>
