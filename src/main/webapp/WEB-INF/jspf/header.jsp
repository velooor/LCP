<header class="navbar navbar-default navbar-fixed-top header fadeInDown animated">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-sign-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand casino" href="${context}/index.jsp">Main</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-sign-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">

                <li class="dropdown">
                    <a href="#" class="head dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message key="label.lang"/><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li class="text-center"><a href="${context}/main?command=locale&language=en-US"><img src="${context}/uk_locale.jpg" alt="" height="30" width="40"></a></li>
                        <li class="text-center" src="rf-locale.jpg"><a href="${context}/main?command=locale&language=ru-RU"><img src="${context}/rf-locale.jpg" alt="" height="30" width="40"></a></li>
                    </ul>
                </li>
            </ul>
            <ttll:guest role="${visitor.role}">
                <form class="navbar-form navbar-right">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target=".sign-up-modal-lg"><fmt:message key="label.sign.up"/></button>
                </form>
                <form class="navbar-form navbar-right" id="signInForm" name="singin" action="/main" method="post">

                        <input id="signInEmailOrLogin" type="text" class="form-control" name="INlogin" placeholder="<fmt:message key="label.email.or.login"/>" value="${ttll:nullCheck(emailOrLogin)}">

                        <input id="signInPassword" type="password" class="form-control" name="INpassword" placeholder="<fmt:message key="label.password"/>" value="${ttll:nullCheck(signInPassword)}">

                        <button type="submit" class="btn btn-primary"><fmt:message key="label.sign.in"/></button>
                    <p class="message">
                            ${errorLoginPassMessage}
                            ${wrongAction}
                            ${nullPage}
                    </p>
                    <input type="hidden" name="command" value="login">

                </form>
            </ttll:guest>
            <ttll:notguest role="${visitor.role}">
                <ul class="nav navbar-nav navbar-right">
                    <li><a class="head" href="${context}/jsp/user/profile_settings.jsp">${ttll:nullCheck(account.name)} ${ttll:nullCheck(account.surname)}</a></li>
                    <li><a class="head" href="${context}/jsp/user/profile_settings.jsp"><img class="img-circle" height="28" width="28" src="${context}/main?command=loadImage&id=${account.accountId}&src=account" alt="Avatar"></a></li>
                    <li><a id="MoneyAmount" class="head" href="${context}/jsp/user/profile_settings.jsp">${ttll:nullCheck(creditBalance.moneyAmount)} $</a></li>
                    <li><a class="head" href="${context}/jsp/user/messages.jsp"><fmt:message key="label.messages"/></a></li>
                        <li><a class="head" href="/main?command=logout"><fmt:message key="label.sign.out"/></a></li>
                </ul>
                <ttll:admin role="${visitor.role}">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a class="head" href="${context}/jsp/admin/admin_settings.jsp"><fmt:message key="label.adminsettings"/></a></li>
                    </ul>
                </ttll:admin>
            </ttll:notguest>
        </div>
    </div>
</header>