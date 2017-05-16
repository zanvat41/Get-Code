<%--
  Created by IntelliJ IDEA.
  User: tyler
  Date: 4/3/2017
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.

  hey! is an alert page that alerts a user with a message from the server
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%-- Start HTML --%>
<html>

    <%-- Scripts for google api and local stuff --%>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="./js/google.js"></script>

    <%-- Head --%>
    <head>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
        <meta name="google-signin-scope" content="profile email">
    <title>Hey!</title>
    </head>

    <%-- Body --%>
    <body>

        <%-- container for CSS --%>
        <div class="container">
            <%-- Navbar --%>
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
            <%-- Message --%>
                <div style="top:50px; position: relative; padding-bottom: 100px;">
                    <h1>Hey! ${message}</h1>
                </div>
        </div>

        <%-- include footer, and fixed it to bottom --%>
        <div style="bottom: 0; position: absolute; height: 100px; text-align: center">
            <jsp:include page="/footer.jsp"/>
        </div>
    </body>
</html>