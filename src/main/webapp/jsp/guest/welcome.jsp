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
    <title><fmt:message key="welcome.title"/></title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

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

<%@include file="../../WEB-INF/jspf/header.jsp"%>

<main class="container">
    <div class="modal fade sign-up-modal-lg" tabindex="-1" role="dialog" aria-labelledby="SignUp">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title"><fmt:message key="label.sign.up"/></h3>
                </div>
                <div class="modal-body">
                    <form id="signUpForm" name="singup" action="/main" method="post" onsubmit="return validateForm ( )">
                        <div class="form-group-lg">
                            <input type="text" name="name" class="form-control" placeholder="<fmt:message key="registration.name"/>*" required pattern="^[A-Za-z]{1,}$"/>
                        </div>
                        <div class="form-group-lg">
                            <input type="text" name="surname" class="form-control" placeholder="<fmt:message key="registration.surname"/>*"required pattern="^[A-Za-z]{1,}$"/>
                        </div>
                        <div class="form-group-lg">
                                <input id="date" name="birthdate" class="form-control input" placeholder="<fmt:message key="registration.birthdate"/>*" type="date" min="1850-01-01" max="2013-01-01" required>
                        </div>
                        <div class="form-group-lg">
                            <input type="text" name="login" class="form-control" placeholder="<fmt:message key="registration.login"/>*" required  pattern="^[a-zA-Z](.[a-zA-Z0-9_-]*)$"/>
                        </div>
                        <div class="form-group-lg">
                            <input type="email" name="email" class="form-control"  placeholder="<fmt:message key="registration.email"/>*" required pattern="^.+@.+[.].+$"/>
                        </div>
                        <div class="form-group-lg">
                            <input type="password" name="password" class="form-control" placeholder="<fmt:message key="registration.password"/>*" required pattern="((?=.*\d)(?=.*[A-z]).{6,})"/>
                        </div>
                        <div class="form-group-lg">
                            <input type="password" name="password2" class="form-control" placeholder="<fmt:message key="registration.reppassword"/>*" required pattern="((?=.*\d)(?=.*[A-z]).{6,})"/>
                        </div>
                        <p class="message">
                                ${errorSingUpPassMessage}
                            </p>
                        <input type="hidden" name="command" value="singup">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.close"/></button>
                    <button type="submit" form="signUpForm" class="btn btn-primary"><fmt:message key="label.sign.up"/></button>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../../WEB-INF/jspf/content.jsp"%>
</main>


<%@include file="../../WEB-INF/jspf/footer.jsp"%>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${context}/js/jquery-3.2.0.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${context}/js/jquery.form.min.js"></script>
<%--<script src="${context}/js/sign_manage.js"></script>--%>
<script src="${context}/js/bootstrap.min.js"></script>
</body>
</html>
