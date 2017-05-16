<%--
  Created by IntelliJ IDEA.
  User: ZheLin
  Date: 4/4/2017
  Time: 21:49
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
    <nav class="navbar navbar-inverse ">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="/profile">Get Code</a>
            </div>
            <div id="navbar">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active">
                        <a href="/createtutorial">Create Tutorial</a>
                    </li>
                    <li>
                        <a target="_blank" href="/help.jsp">Help</a>
                    </li>
                    <li class="active">
                        <div style="display:none" class="g-signin2"data-onsuccess="onSignIn"></div>
                        <a href="/logout" onclick="signOut(false);">Sign out</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
        <div class="container">
            <h1>${tutorial.name}</h1>
            <div class="container-content">
                The tutorial content goes here!!!
            </div>
        </div>
        <jsp:include page="/footer.jsp"/>
    </body>
</html>