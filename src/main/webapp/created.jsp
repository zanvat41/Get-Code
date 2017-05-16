<%--
  Created by IntelliJ IDEA.
  User: tyler
  Date: 4/3/2017
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.

  created is a the page that shows the user the practice problems page they just created
--%>

<%-- Set the content type and language, and include all needed tags --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>               <%-- Page info --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>          <%-- spring tags --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>                 <%-- core c library --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>                <%-- FMT --%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>    <%-- Spring forms --%>

<%-- Start HTML --%>
<html>

    <%-- include the JQuery Script, the google API script, and the local scripts --%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="./js/google.js"></script>
    <script src="./js/tutorialform.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="./ace-builds/src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>

    <script>
        var tutID = '${tutID}';
        var language = '${languageName}';
    </script>

    <%-- Head --%>
    <head>

        <style type="text/css" media="screen">
            .editor {
                width: 1000px;
                height: 200px;
            }
        </style>

        <%-- link the stylesheet and add the metadata for the google authentication --%>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
        <meta name="google-signin-scope" content="profile email">

    <%-- Set the title --%>
    <title>Profile Page</title>
    </head>

    <%-- body --%>
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

    <form:form method="post" id="check-ans" action="/check-ans" modelAttribute="answersForm"></form:form>

        <%-- container for CSS and all of the content created by the server --%>
        <div class="container" style="min-height: 80%;top:50px; position: relative; padding-bottom: 60px;">
            <input hidden id="language" value=${language} />
            <h2><h2 style="display: inline">Practice Problems </h2><h4 style="display: inline">[${languageName}]</h4></h2><br><br>
            <div>
                ${created}
            </div>

            <button style="font-size: 17px;" class="btn-primary" onclick="event.preventDefault(); answers();">Submit</button>

            <%-- include the footer --%>
            <div style="bottom: 0; position: absolute; height: 60px">
                <jsp:include page="/footer.jsp"/>
            </div>

        </div>
    </body>
</html>