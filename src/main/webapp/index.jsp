<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<c:choose>
    <c:when test="${visitor.role == 'GUEST'}">
        <jsp:forward page="/jsp/guest/welcome.jsp"/>
    </c:when>

    <c:when test="${visitor.role == 'USER'}">
        <jsp:forward page="/jsp/user/main.jsp"/>
    </c:when>
    <c:when test="${visitor.role == 'ADMIN'}">
        <jsp:forward page="/jsp/user/main.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="/jsp/guest/welcome.jsp"/>
    </c:otherwise>
</c:choose>
</body>
</html>
