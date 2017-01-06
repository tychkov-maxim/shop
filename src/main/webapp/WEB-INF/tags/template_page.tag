<%@tag description="Tag gererates template page" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<%@attribute name="tittle" required="true"%>

<c:set var="role" value="${sessionScope.user.role}"/>
<c:set var="currentURL" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<html>
<head>
    <title>${tittle}</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
</head>
<body>


<div class="jumbotron">
    <a href="${pageContext.request.contextPath}/lang.do?language=ru&referrer=${currentURL}">ru</a>
    <a href="${pageContext.request.contextPath}/lang.do?language=en&referrer=${currentURL}">en</a>
    <div class="container text-center">
        <h1>Online Store</h1>
    </div>
</div>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Logo</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li><a href="#">Home</a></li>
                <li><a href="#">Products</a></li>
                <li><a href="#">Contact</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${empty role}">
                <li><a href="" data-toggle="modal" data-target="#auth"><span class="glyphicon glyphicon-user"></span>
                    Your Account</a></li>
                </c:if>
                <c:if test="${not empty role}">
                    <li><a href="${pageContext.request.contextPath}/logout.do"><span class="glyphicon glyphicon-user"></span>
                    ${sessionScope.user.firstName}</a></li>
                </c:if>
                <li><a href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Cart <span
                        class="badge">5</span></a></li>
            </ul>
        </div>
    </div>
</nav>

<jsp:doBody/>


<jsp:include page="/WEB-INF/jsp/authorization.jsp"/>

<footer class="container-fluid text-center">
    <p>Online Store Copyright</p>
</footer>

</body>
</html>
