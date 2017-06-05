<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="srv" uri="http://example.com/elgrand" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<html>
    <head>
        <title><fmt:message key="main.welcome"/></title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <link rel="shortcut icon" href="${context}/css/mainFavicon.png" type="image/png">
        <!-- Bootstrap -->
        <link href="${context}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
        <link href="${context}/css/style.css" rel="stylesheet">
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
        <div class="text-center">
            <h3><fmt:message key="main.welcome"/></h3>
        </div>

        <div class="row">
            <button type="button" class="btn btn-primary col-lg-12" data-toggle="modal" data-target=".rate-settings-modal-lg"><fmt:message key="label.play"/></button>
        </div>
        <br>
        <form action="${context}/jsp/user/waitForGame.jsp">
            <button type="submit" class="btn btn-primary col-lg-12"><fmt:message key="label.multi.multiGame"/></button>
        </form>
        <br><br>

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
                                    <input type="number" name="bet" class="form-control"  value="" placeholder="${srv:nullCheck(creditBalance.moneyAmount)}" required min="${srv:nullCheck(minRate)}" max="${srv:nullCheck(creditBalance.moneyAmount)}">
                                </div>
                                <div class="col-sm-9">
                                    $
                                </div>
                            </div>
                            <fmt:message key="label.min.bet.is"/>${srv:nullCheck(minRate)} $


                            <%--<div class="text-center">
                                <label class="radio"><input type="radio" name="vs" value="casino" checked><fmt:message key="label.vs.casino" /></label>
                                <label class="radio"><input type="radio" name="vs" value="man"><fmt:message key="label.vs.man"/></label>
                            </div>--%>

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
        <%@include file="../../WEB-INF/jspf/content.jsp"%>
    </main>



        <%--<%@include file="../../WEB-INF/jspf/about_content.jsp"%>--%>


        <%@include file="../../WEB-INF/jspf/footer.jsp"%>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="${context}/js/jquery-3.2.0.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="${context}/js/jquery.form.min.js"></script>
        <%--<script src="${context}/js/sign_manage.js"></script>--%>
        <script src="${context}/js/bootstrap.min.js"></script>
    </body>
</html>