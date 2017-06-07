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
    <title><fmt:message key="label.waiting"/></title>

    <!-- Bootstrap -->
    <link href="${context}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${context}/css/style.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Chewy" rel="stylesheet">
    <link rel="shortcut icon" href="${context}/css/mainFavicon.png" type="image/png">
    <script type="text/javascript" src="${context}js/registration.js"></script>
    <script type="text/javascript" src="${context}js/default.js"></script>

    <link rel="stylesheet" media="all" href="${context}css/animate.css">
    <script src="${context}js/wow.min.js"></script>
    <script>new WOW().init();</script>
</head>
<body>
<c:choose>
    <c:when test="${visitor.role == 'GUEST'}">
        <jsp:forward page="/jsp/guest/welcome.jsp"/>
    </c:when>
</c:choose>
<%@include file="../../WEB-INF/jspf/header.jsp"%>
<main class="container">
    <div class="visible-lg-inline-block">
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".new-multiGame-modal-lg"><fmt:message key="label.new.multiGame"/></button>
        <form action="${context}/jsp/user/waitForGame.jsp">
            <button type="submit" class="btn right"><fmt:message key="label.update"/></button>
        </form>
    </div>
    <div class="visible-lg-inline-block">
        <p class="message">
            ${multiGameMessage}
        </p>
    </div>

    <div class="modal fade new-multiGame-modal-lg" tabindex="-1" role="dialog" aria-labelledby="NewGame">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title"><fmt:message key="label.new.multiGame"/></h3>
                </div>
                <div class="modal-body">
                    <form id="newGameForm" name="newGameForm" action="/main" method="post">
                        <div class="row">
                            <div class="col-sm-3">
                                <input type="number" name="bet" class="form-control"  value="" placeholder="${ttll:nullCheck(creditBalance.moneyAmount)}" required min="${ttll:nullCheck(minRate)}" max="${ttll:nullCheck(creditBalance.moneyAmount)}">
                            </div>
                            <div class="col-sm-9">
                                $
                            </div>
                        </div>
                        <fmt:message key="label.min.bet.is"/>${ttll:nullCheck(minRate)} $

                        <input type="hidden" name="command" value="newgame">
                        <hr>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.close"/></button>
                    <button type="submit" form="newGameForm" class="btn btn-primary"><fmt:message key="label.create"/></button>
                </div>
            </div>

        </div>
    </div>


    <div class="clearfix pulse animated">
        <div class="page-header text-center"><h2><fmt:message key="label.active.games"/>:</h2></div>
        <c:forEach var="multiGame" items="${notFinished}">
            <div class="panel panel-info col-lg-12">
                <h4 class="panel-heading col-lg-7">#${multiGame.gameId}</h4>
                <div class="panel-heading col-lg-5">
                    <h4 class="panel-heading"><fmt:message key="label.title.creator"/> ${multiGame.creator.name} ${multiGame.creator.surname} (${multiGame.creator.login})</h4>
                    <h4 class="panel-heading"><fmt:message key="label.title.another.player"/>: ${multiGame.player.name} ${multiGame.player.surname} (${multiGame.player.login})</h4>
                </div>
                <div class="panel-body">
                    <div class="col-lg-7"><fmt:message key="label.rate"/>: ${multiGame.rate} $</div>
                    <div class="col-lg-3">
                        <label><fmt:message key="label.creating.time"/>:</label>
                        <div>${multiGame.time}</div>
                    </div>
                    <h5>${multiGame.creator.name} ${multiGame.creator.surname}: ${multiGame.creatorScore}(+${multiGame.lastCreatorResult})</h5>
                    <h5>${multiGame.player.name} ${multiGame.player.surname}: ${multiGame.playerScore}(+${multiGame.lastPlayerResult})</h5>
                    <div class="col-lg-2">
                        <button type="button" onclick="window.location.href='${context}/main?command=returnToGame&gameId=${multiGame.gameId}'" class="btn btn-default pull-right"><fmt:message key="label.return.game"/></button>
                    </div>

                </div>
            </div>
        </c:forEach>
    </div>

    <div class="clearfix pulse animated">
        <div class="page-header text-center"><h2><fmt:message key="label.waiting"/>(<fmt:message key="label.my.games"/>):</h2></div>
        <c:forEach var="multiGame" items="${myNotActive}">
            <div class="panel panel-info col-lg-12">
                <h4 class="panel-heading col-lg-7">#${multiGame.gameId}</h4>
                <h4 class="panel-heading col-lg-5"><fmt:message key="label.title.creator"/> ${multiGame.creator.name} ${multiGame.creator.surname} (${multiGame.creator.login})</h4>
                <div class="panel-body">
                    <div class="col-lg-7"><fmt:message key="label.rate"/>: ${multiGame.rate} $</div>
                    <div class="col-lg-3">
                        <label><fmt:message key="label.creating.time"/>:</label>
                        <div>${multiGame.time}</div>
                    </div>
                    <div class="col-lg-2">
                        <button type="button" onclick="window.location.href='${context}/main?command=deleteGame&gameId=${multiGame.gameId}&rate=${multiGame.rate}'" class="btn btn-default pull-right"><fmt:message key="label.delete.game"/></button>
                    </div>


                </div>
            </div>
        </c:forEach>
    </div>

    <div class="clearfix pulse animated">
        <div class="page-header text-center"><h2><fmt:message key="label.waiting"/>:</h2></div>
        <c:forEach var="multiGame" items="${notMyNotActive}">
            <div class="panel panel-info col-lg-12">
                <h4 class="panel-heading col-lg-7">#${multiGame.gameId}</h4>
                <h4 class="panel-heading col-lg-5"><fmt:message key="label.title.creator"/> ${multiGame.creator.name} ${multiGame.creator.surname} (${multiGame.creator.login})</h4>
                <div class="panel-body">
                    <div class="col-lg-7"><fmt:message key="label.rate"/>: ${multiGame.rate} $</div>
                    <div class="col-lg-3">
                        <label><fmt:message key="label.creating.time"/>:</label>
                        <div>${multiGame.time}</div>
                    </div>
                    <div class="col-lg-2">
                        <button type="button" onclick="window.location.href='${context}/main?command=startMultiGame&gameId=${multiGame.gameId}&rate=${multiGame.rate}'" class="btn btn-default pull-right"><fmt:message key="label.join"/></button>
                    </div>


                </div>
            </div>
        </c:forEach>
    </div>

    <div class="clearfix pulse animated">
        <div class="page-header text-center"><h2><fmt:message key="label.finished.games"/>:</h2></div>
        <c:forEach var="multiGame" items="${finished}">
            <div class="panel panel-info col-lg-12">
                <h4 class="panel-heading col-lg-7">#${multiGame.gameId}</h4>
                <div class="panel-heading col-lg-5">
                    <h4 class="panel-heading"><fmt:message key="label.title.creator"/> ${multiGame.creator.name} ${multiGame.creator.surname} (${multiGame.creator.login})</h4>
                    <h4 class="panel-heading"><fmt:message key="label.title.another.player"/>: ${multiGame.player.name} ${multiGame.player.surname} (${multiGame.player.login})</h4>
                </div>
                <div class="panel-body">
                    <div class="col-lg-7"><fmt:message key="label.rate"/>: ${multiGame.rate} $</div>
                    <div class="col-lg-3">
                        <label><fmt:message key="label.creating.time"/>:</label>
                        <div>${multiGame.time}</div>
                    </div>
                    <h5>${multiGame.creator.name} ${multiGame.creator.surname}: ${multiGame.creatorScore}</h5>
                    <h5>${multiGame.player.name} ${multiGame.player.surname}: ${multiGame.playerScore}</h5>
                    <div class="col-lg-2">
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
