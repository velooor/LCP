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
    <title><fmt:message key="label.main.title"/></title>

    <link href="${context}/css/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="${context}/css/style.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Chewy" rel="stylesheet">
    <link rel="shortcut icon" href="${context}/css/mainFavicon.png" type="image/png">

    <script src="${context}https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="${context}https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

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
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".money-settings-modal-lg"><fmt:message key="label.top.up.account"/></button>
    </div>
    <div class="visible-lg-inline-block">
        <p class="message">
            ${topUpAccountMessage}
        </p>
    </div>
    <br>

    <div class="modal fade money-settings-modal-lg" tabindex="-1" role="dialog" aria-labelledby="MoneySettings">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title"><fmt:message key="label.top.up.account"/></h3>
                </div>
                <div class="modal-body">
                    <form id="topUpAccountForm" name="topUpAccountForm" action="/main" method="post" onsubmit="return validateForm ( )">
                        <label for="creditcard" class="control-label"><fmt:message key="label.creditcard"/>:</label>
                        <div class="row" id="creditcard">
                            <div class="col-sm-2">
                                <input type="text" name="creditcard1" class="form-control"  value="${ttll:nullCheck(cardFirst)}" placeholder="0000" required pattern="^[0-9]{4}$">
                            </div>
                            <div class="col-sm-2">
                                <input type="text" name="creditcard2" class="form-control"  value="${ttll:nullCheck(cardSecond)}" placeholder="0000" required pattern="^[0-9]{4}$">
                            </div>
                            <div class="col-sm-2">
                                <input type="text" name="creditcard3" class="form-control"  value="${ttll:nullCheck(cardThird)}" placeholder="0000" required pattern="^[0-9]{4}$">
                            </div>
                            <div class="col-sm-2">
                                <input type="text" name="creditcard4" class="form-control"  value="${ttll:nullCheck(cardFourth)}" placeholder="0000" required pattern="^[0-9]{4}$">
                            </div>
                            <div class="col-sm-4"></div>
                        </div>

                        <br>

                        <div class="row">
                            <div class="col-sm-2">
                                <label for="ccv" class="control-label"><fmt:message key="label.CCV"/></label>
                                <input type="number" id="ccv" name="ccv" class="form-control"  value="" placeholder="CCV" required pattern="^[0-9]{3}$">
                            </div>

                            <div class="col-sm-2"></div>

                            <div class="row col-sm-5">
                                <label for="valid" class="control-label"><fmt:message key="label.valid"/></label>
                                <div class="row" id="valid">
                                    <div class="col-sm-3">
                                        <input type="number" name="validMonth" class="form-control"  value="${ttll:nullCheck(cardMonth)}" placeholder="mm" required min="01" max="12" pattern="^[0-9]{2}$">
                                    </div>
                                    <div class="col-sm-1">
                                        /
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="number" name="validYear" class="form-control"  value="${ttll:nullCheck(cardYear)}" placeholder="yy" required min="17" pattern="^[0-9]{2}$">
                                    </div>
                                    <div class="col-sm-5"></div>
                                </div>
                            </div>
                            <div class="col-sm-3"></div>
                        </div>

                        <br>

                        <div class="row">
                            <div class="col-sm-3">
                                <input type="number" name="amount" class="form-control"  value="" placeholder="0" required min="1">
                            </div>
                            <div class="col-sm-9">
                                $
                            </div>
                        </div>

                        <input type="hidden" name="command" value="topupaccount">
                        <p class="message">
                            ${NewMessageMessage}
                        </p>

                        <hr>

                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.close"/></button>
                    <button type="submit" form="topUpAccountForm" class="btn btn-primary"><fmt:message key="label.submit"/></button>
                </div>
            </div>

        </div>
    </div>




    <div id="changeNamePanel" class="panel panel-default">
        <div class="panel-heading">
            <h2 class="panel-title text-center"><fmt:message key="label.first.name.and.second.name"/></h2>
        </div>
        <div class="panel-body">
            <form id="changeNameForm" action="${context}/main" method="post">
                <div class="form-group-lg">
                    <label for="changeFirstName"><fmt:message key="label.first.name"/></label>
                    <input id="changeFirstName" type="text" class="form-control" name="firstName" placeholder="<fmt:message key="label.first.name"/>" value="${ttll:nullCheck(account.name)}">
                </div>
                <div class="form-group-lg">
                    <label for="changeSecondName"><fmt:message key="label.second.name"/></label>
                    <input id="changeSecondName" type="text" class="form-control" name="secondName" placeholder="<fmt:message key="label.second.name"/>" value="${ttll:nullCheck(account.surname)}">
                </div>
                <input type="hidden" name="command" value="changeName">
            </form>
        </div>
        <div class="panel-footer clearfix">
            <button type="submit" form="changeNameForm" class="btn btn-primary pull-right"><fmt:message key="label.submit"/></button>
        </div>
    </div>
    <div id="changeAvatarPanel" class="panel panel-default">
        <div class="panel-heading">
            <h2 class="panel-title text-center"><fmt:message key="label.avatar"/></h2>
        </div>
        <div class="panel-body">
            <img class="img-circle text-center" height="100" width="100" src="${context}/main?command=loadImage&id=${account.accountId}&src=account" alt="Avatar">
            <form id="changeAvatarForm" action="${context}/main" method="post" enctype="multipart/form-data">
                <div class="form-group-lg">
                    <input id="changeAvatar" type="file" name="avatar">
                </div>
                <input type="hidden" name="command" value="changeAvatar">
            </form>
        </div>
        <div class="panel-footer clearfix">
            <form id="resetAvatarForm" action="${context}/main" method="post">
                <button type="submit" class="btn btn-default pull-left"  name="command" value="resetAvatar"><fmt:message key="label.reset"/></button>
            </form>
            <button type="submit" form="changeAvatarForm" class="btn btn-primary pull-right"><fmt:message key="label.submit"/></button>
        </div>
    </div>
    <div id="changePasswordPanel" class="panel panel-default">
        <div class="panel-heading">
            <h2 class="panel-title text-center"><fmt:message key="label.password"/></h2>
        </div>
        <div class="panel-body">
            <form id="changePasswordForm" action="${context}/main" method="post">
                <div class="form-group-lg">
                    <label for="changeOldPassword"><fmt:message key="label.old.password"/></label>
                    <input id="changeOldPassword" pattern="((?=.*\d)(?=.*[a-Z]).{6,})" type="password" class="form-control" name="oldPassword" placeholder="<fmt:message key="label.old.password"/>">
                </div>
                <div class="form-group-lg">
                    <label for="changeNewPassword"><fmt:message key="label.new.password"/></label>
                    <input id="changeNewPassword" pattern="((?=.*\d)(?=.*[a-Z]).{6,})" type="password" class="form-control" name="newPassword" placeholder="<fmt:message key="label.new.password"/>">
                </div>
                <div class="form-group-lg">
                    <label for="changeNewRepeatedPassword"><fmt:message key="label.repeat.new.password"/></label>
                    <input id="changeNewRepeatedPassword" pattern="((?=.*\d)(?=.*[A-z]).{6,})" type="password" class="form-control" name="repeatedNewPassword" placeholder="<fmt:message key="label.repeat.new.password"/>">
                </div>
                <input type="hidden" name="command" value="changePassword">
                <p class="alert-info">
                    ${errorChangePasswordMessage}
                </p>
            </form>
        </div>
        <div class="panel-footer clearfix">
            <button type="submit" form="changePasswordForm" class="btn btn-primary pull-right"><fmt:message key="label.submit"/></button>
        </div>
    </div>
</main>
<%@include file="../../WEB-INF/jspf/footer.jsp"%>
<script src="${context}/js/jquery-3.2.0.js"></script>
<script src="${context}/js/jquery.form.min.js"></script>
<script src="${context}/js/bootstrap.min.js"></script>
</body>
</html>
