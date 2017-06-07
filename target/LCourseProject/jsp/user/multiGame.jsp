<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ttll" uri="http://example.com/elgrand" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<html>
<head>
    <title><fmt:message key="welcome.title"/></title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link rel="shortcut icon" href="${context}/css/mainFavicon.png" type="image/png">
    <!-- Bootstrap -->
    <link href="${context}/css/style.css" rel="stylesheet">
    <link href="${context}/css/bootstrap/bootstrap.min.css" rel="stylesheet">

    <link href="https://fonts.googleapis.com/css?family=Chewy" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <link rel="stylesheet" media="all" href="${context}css/animate.css">
    <script src="${context}js/wow.min.js"></script>
    <script>new WOW().init();</script>
</head>
<body onload="init()">
<main class="container">
    <c:choose>
    <c:when test="${visitor.role == 'GUEST'}">
        <jsp:forward page="/jsp/guest/welcome.jsp"/>
    </c:when>
    </c:choose>
    <%@include file="../../WEB-INF/jspf/header.jsp"%>


    <input type="hidden" id="gameId" data-value="${multiGameParams.gameId}" />
        <input type="hidden" id="rate" data-value="${multiGameParams.rate}" />
        <input type="hidden" id="creatorScore" data-value="${multiGameParams.creatorScore}" />
        <input type="hidden" id="playerScore" data-value="${multiGameParams.playerScore}" />
        <input type="hidden" id="lastPlayerResult" data-value="${multiGameParams.lastPlayerResult}" />
        <input type="hidden" id="lastCreatorResult" data-value="${multiGameParams.lastCreatorResult}" />
        <input type="hidden" id="playerMoveFlag" data-value="${multiGameParams.playerMove}" />
        <input type="hidden" id="finished" data-value="${multiGameParams.finished}" />
        <input type="hidden" id="playerId" data-value="${multiGameParams.player.accountId}" />
        <input type="hidden" id="creatorId" data-value="${multiGameParams.creator.accountId}" />

        <input type="hidden" id="minPoints" data-value="${minPoints}" />

        <input type="hidden" id="currentAccountId" data-value="${account.accountId}"/>





    <div class="panel panel-info col-lg-12">
        <div class="panel-body">
            <h4 class="text-center">#${multiGameParams.gameId}</h4>
        </div>
    </div>
    <div class="panel panel-info col-lg-12">
        <div class="panel-body">
            <h1 class="text-center">${multiGameParams.rate} $</h1>
        </div>
    </div>
    <div class="row">
        <div class="panel panel-info col-lg-6">
            <div class="panel-body">
                <h1 id="playerPoints" class="points text-center"><b>0</b></h1>
                <h4 class="text-center"><img class="img-circle" height="70" width="70" src="${context}/main?command=loadImage&id=${multiGameParams.player.accountId}&src=multiGame" alt="Avatar"></h4>
                <h3 class="text-center">${multiGameParams.player.name} ${multiGameParams.player.surname}</h3>
                <h4 id="playerEndResult" class="text-center"></h4>

            </div>
        </div>
        <div class="panel panel-info col-lg-6">
            <div class="panel-body">
                <h1 id="creatorPoints" class="points text-center"><b>0</b></h1>
                <h4 class="text-center"><img class="img-circle" height="70" width="70" src="${context}/main?command=loadImage&id=${multiGameParams.creator.accountId}&src=multiGame" alt="Avatar"></h4>
                <h3 class="text-center">${multiGameParams.creator.name} ${multiGameParams.creator.surname}</h3>
                <h4 id="creatorEndResult" class="text-center"></h4>
            </div>
        </div>
    </div>


    <button type="button" id="playerMove" class="btn btn-primary" onclick="step()" ><fmt:message key="label.throw.die"/></button>
    <br><br>
    <button type="button" id="playerPass" class="btn" onclick="pass()" ><fmt:message key="label.pass"/></button>


    <button type="button" id="creatorMove" class="btn btn-primary" onclick="step()" ><fmt:message key="label.throw.die"/></button>
    <br><br>
    <button type="button" id="creatorPass" class="btn" onclick="pass()" ><fmt:message key="label.pass"/></button>

    <form id="toWaitingGame" action="${context}/jsp/user/waitForGame.jsp">
        <button type="submit" class="btn btn-primary col-lg-12"><fmt:message key="label.play.again"/></button>
    </form>

    <br><br><br><br><br>

    <%@include file="../../WEB-INF/jspf/footer.jsp"%>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${context}/js/jquery-3.2.0.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${context}/js/jquery.form.min.js"></script>
    <%--<script src="${context}/js/sign_manage.js"></script>--%>
    <script src="${context}/js/bootstrap.min.js"></script>
    <script src="${context}js/jquery.session.js"></script>
    <script src="${context}js/multiGame.js"></script>
</body>
</html>