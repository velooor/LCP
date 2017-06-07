<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ttll" uri="http://example.com/elgrand" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<c:set var="context" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><fmt:message key="label.error.title"/></title>

    <!-- Bootstrap -->
    <link href="${context}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${context}/css/style.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Bungee" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
    <%@include file="../../WEB-INF/jspf/header.jsp"%>

<main class="container">
    <div class="jumbotron">
        <h3 class="text-center"><fmt:message key="label.error.title"/></h3>
        <h1 class="text-center">${pageContext.errorData.statusCode}</h1>
        <div class="text-center"><a href="${context}/index.jsp"><h2><fmt:message key="label.error.to.main"/></h2></a></div>
    </div>
</main>

    <%@include file="../../WEB-INF/jspf/footer.jsp"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${context}/js/jquery-3.2.0.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${context}/js/jquery.form.min.js"></script>
<script src="${context}/js/bootstrap.min.js"></script>
</body>
</html>
