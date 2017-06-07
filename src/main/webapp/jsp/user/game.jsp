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
<body>
<main class="container">
    <c:choose>
        <c:when test="${visitor.role == 'GUEST'}">
            <jsp:forward page="/jsp/guest/welcome.jsp"/>
        </c:when>
    </c:choose>
    <%@include file="../../WEB-INF/jspf/header.jsp"%>
        <input type="hidden" id="playerRes" data-value="${singleGame.playerResults}" />
        <input type="hidden" id="casinoRes" data-value="${singleGame.casinoResults}" />
        <input type="hidden" id="steps" data-value="${singleGame.numOfCasinoSteps}" />
        <input type="hidden" id="minPoints" data-value="${minPoints}" />
        <input type="hidden" id="rate" data-value="${singleGame.rate}" />



        <div class="modal fade rate-settings-modal-lg" tabindex="-1" role="dialog" aria-labelledby="RateSettings">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h3 class="modal-title"><fmt:message key="label.make.your.bet"/></h3>
                    </div>
                    <div class="modal-body">
                        <form id="makeBetForm" name="makeBetForm" action="/main" method="post" onsubmit="return validateForm ( )">
                            <div class="row">
                                <div class="col-sm-3">
                                    <input type="number" name="bet" class="form-control"  value="" required min="${ttll:nullCheck(minRate)}" max="${ttll:nullCheck(creditBalance.moneyAmount)}">
                                </div>
                                <div class="col-sm-9">
                                    $
                                </div>
                            </div>
                            <fmt:message key="label.min.bet.is"/>${ttll:nullCheck(minRate)} $

                            <input type="hidden" name="command" value="play">

                            <hr>

                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.close"/></button>
                        <button type="submit" form="makeBetForm" class="btn btn-primary"><fmt:message key="label.submit"/></button>
                    </div>
                </div>

            </div>
        </div>


        <div class="panel panel-info col-lg-12">
            <div class="panel-body">
                <h1 class="text-center">${singleGame.rate} $</h1>
            </div>
        </div>
        <div class="row">
            <div class="panel panel-info col-lg-6">
                <div class="panel-body">
                    <h1 id="playerPoints" class="points text-center"><b>0</b></h1>
                    <h4 class="text-center"><img class="img-circle" height="70" width="70" src="${context}/main?command=loadImage&id=${account.accountId}&src=account" alt="Avatar"></h4>
                    <h3 class="text-center"><fmt:message key="label.you"/></h3>
                    <h4 id="playerEndResult" class="text-center"></h4>

                </div>
            </div>
            <div class="panel panel-info col-lg-6">
                <div class="panel-body">
                    <h1 id="casinoPoints" class="points text-center"><b>0</b></h1>
                    <h4 class="text-center"><img class="img-circle" height="70" width="70" src="${context}/images/casino.jpg" alt="Avatar"></h4>
                    <h3 class="casino text-center"><fmt:message key="label.casino"/></h3>
                    <h4 id="casinoEndResult" class="text-center"></h4>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-6">
                <button type="button" id="button" class="btn btn-primary col-lg-12" onclick="processStep()" ><fmt:message key="label.throw.die"/></button>
                <br>
                <button type="button" id="pass" class="btn col-lg-12" onclick="playerPass()" ><fmt:message key="label.pass"/></button>
            </div>
            <div class="col-lg-6">
            </div>
        </div>
        <div class="row">
            <button type="button" id="playAgain" class="btn btn-primary col-lg-12" data-toggle="modal" data-target=".rate-settings-modal-lg"><fmt:message key="label.play.again"/></button>
        </div>

        <br><br><br><br><br>

    <%@include file="../../WEB-INF/jspf/footer.jsp"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${context}/js/jquery-3.2.0.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${context}/js/jquery.form.min.js"></script>
<%--<script src="${context}/js/sign_manage.js"></script>--%>
<script src="${context}/js/bootstrap.min.js"></script>
        <script src="${context}js/jquery.session.js"></script>
        <script src="${context}js/singleGame.js"></script>
</body>
</html>