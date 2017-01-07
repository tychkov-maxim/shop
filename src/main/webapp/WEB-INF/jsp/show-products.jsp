<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="lang"/>

<t:template_page tittle="Online Store">
    <jsp:body>
        <div class="container-fluid" id="categories">
            <t:get_errors errors="${productsMessage}"/>
            <div class="row content">
                <div class="col-sm-2">
                    <ul class="list-group">
                        <c:forEach items="${categories}" var="category">
                            <a href="${pageContext.request.contextPath}/show.do?category=${category.name}">
                                <li class="list-group-item">${category.name}</li>
                            </a>
                        </c:forEach>
                    </ul>
                </div>



                <div class="container-fluid">

                    <c:forEach items="${products}" var="product">
                        <div class="col-sm-2">
                            <div class="panel panel-primary">
                                <div class="panel-heading">${product.name}</div>
                                <div class="panel-body"><img
                                        src="/image${product.imagePath}"
                                        class="img-responsive" style="width:100%" alt="Image"></div>
                                <div class="panel-footer">${product.price}</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>



        <ul class="pager">
            <c:if test="${not empty previousPage}">
                <li>
                    <a href="${pageContext.request.contextPath}/show.do?page=${previousPage}&category=${param.category}">Previous</a>
                </li>
            </c:if>
             <c:if test="${not empty nextPage}">
                <li><a href="${pageContext.request.contextPath}/show.do?page=${nextPage}&category=${param.category}">Next</a>
                </li>
            </c:if>
        </ul>

    </jsp:body>
</t:template_page>
