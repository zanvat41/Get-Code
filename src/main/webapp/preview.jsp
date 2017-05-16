<%--
  Created by IntelliJ IDEA.
  User: tyler
  Date: 4/3/2017
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="./js/google.js"></script>
    <script src="./js/tutorialform.js"></script>
    <head>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
        <meta name="google-signin-scope" content="profile email">
    <title>Profile Page</title>
    </head>
    <body>
    <%-- nav bar --%>
    <nav class="navbar navbar-inverse navbar-fixed-top navbar-left" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/profile">Get Code</a>
        </div>
        <ul class="nav navbar-nav">
            <li>
                <a href="/createtutorial">Create Tutorial</a>
            </li>
            <li>
                <a target="_blank" href="/help.jsp">Help</a>
            </li>
            <li>
                <div style="display:none" class="g-signin2"data-onsuccess="onSignIn"></div>
                <a href="/logout" onclick="signOut(false);">Sign out</a>
            </li>
        </ul>
    </nav>
        <div class="container" style="min-height: 80%;top:50px; position: relative; padding-bottom: 60px;">
            <div>
                ${preview}
            </div>
            <div>
                <form:form method="post" action="/problemsPage">
                    <input type="hidden" name="tutorialId" id="tutorialId" value="${tutorialId}">
                    <input type="submit" value="Try Practice Problems" class="btn-primary">
                </form:form>
            </div>
            <div style="bottom: 0; position: absolute; height: 60px">
                <jsp:include page="/footer.jsp"/>
            </div>
        </div>
    </body>
</html>