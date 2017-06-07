<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ttll" uri="http://example.com/elgrand" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<c:set var="context" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="label.adminsettings"/></title>

    <!-- Bootstrap -->
    <link href="${context}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${context}/css/style.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Chewy" rel="stylesheet">
    <link rel="shortcut icon" href="${context}/css/mainFavicon.png" type="image/png">
    <script type="text/javascript" src="${context}js/registration.js"></script>

    <link rel="stylesheet" media="all" href="${context}css/animate.css">
    <script src="${context}js/wow.min.js"></script>
    <script>new WOW().init();</script>
</head>
<body>
<c:choose>
    <c:when test="${visitor.role != 'ADMIN'}">
        <jsp:forward page="/jsp/guest/welcome.jsp"/>
    </c:when>
</c:choose>
<%@include file="../../WEB-INF/jspf/header.jsp"%>
<main class="container">
    <div id="adminPanel" class="panel panel-default">
        <div class="panel-heading">
            <h2 class="panel-title text-center"><fmt:message key="label.minnumofpoints.minrate"/></h2>
        </div>
        <div class="panel-body">
            <form id="adminForm" action="${context}/main" method="post">
                <div class="form-group-lg">
                    <label for="changeMinNumOfPoints"><fmt:message key="label.minnumofpoints"/></label>
                    <input id="changeMinNumOfPoints" type="number" class="form-control" name="minNumOfPoints" placeholder="<fmt:message key="label.minnumofpoints"/>" value="${ttll:nullCheck(minPoints)}"min="0" max="21">
                </div>
                <div class="form-group-lg">
                    <label for="changeMinRate"><fmt:message key="label.minrate"/></label>
                    <input id="changeMinRate" type="number" class="form-control" name="minRate" placeholder="<fmt:message key="label.minrate"/>" value="${ttll:nullCheck(minRate)}" min="0" max="999999">
                </div>
                <input type="hidden" name="command" value="changeAdmin">
            </form>
        </div>
        <div class="panel-footer clearfix">
            <button type="submit" form="adminForm" class="btn btn-primary pull-right"><fmt:message key="label.submit"/></button>
        </div>
    </div>



    <div class="clearfix pulse animated">
        <div class="page-header text-center"><h2><fmt:message key="label.active.users"/></h2></div>
        <c:forEach var="user" items="${activeUsers}">
            <div class="panel panel-info col-lg-12">
                <h4 class="panel-heading col-lg-12"><img class="img-circle" height="45" width="45" src="${context}/main?command=loadImage&id=${user.accountId}&src=user" alt="Avatar"> ${user.name} ${user.surname} (${user.login})</h4>
                <div class="panel-body">
                    ${user.email} ${user.numOfVictories} ${user.numOfGames}
                    <div class="col-lg-12">
                        <button type="button" onclick="window.location.href='${context}/main?command=updateUserStatus&userid=${user.accountId}&isActive=false'" class="btn btn-primary pull-right"><fmt:message key="label.ban.user"/></button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="clearfix pulse animated">
        <div class="page-header text-center"><h2><fmt:message key="label.banned.users"/></h2></div>
        <c:forEach var="user" items="${bannedUsers}">
            <div class="panel panel-danger col-lg-12">
                <h4 class="panel-heading col-lg-12"><img class="img-circle" height="45" width="45" src="${context}/main?command=loadImage&id=${user.accountId}&src=user" alt="Avatar"> ${user.name} ${user.surname} (${user.login})</h4>
                <div class="panel-body">
                        ${user.email} ${user.numOfVictories} ${user.numOfGames}
                    <div class="col-lg-12">
                        <button type="button" onclick="window.location.href='${context}/main?command=updateUserStatus&userid=${user.accountId}&isActive=true'" class="btn btn-primary pull-right"><fmt:message key="label.activate.user"/></button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>


</main>
<%@include file="../../WEB-INF/jspf/footer.jsp"%>
<script src="${context}/js/jquery-3.2.0.js"></script>
<script src="${context}/js/jquery.form.min.js"></script>
<script src="${context}/js/bootstrap.min.js"></script>
</body>
</html>
