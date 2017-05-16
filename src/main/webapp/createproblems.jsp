<%--
  Created by IntelliJ IDEA.
  User: tyler
  Date: 4/3/2017
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.

  createproblems is the form page that allows users to create their own practice problem forms
--%>

<%-- Set the content type and language, and include all needed tags --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>               <%-- Page info --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>          <%-- spring tags --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>                 <%-- core c library --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>                <%-- FMT --%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>    <%-- Spring forms --%>

<%-- Start HTML --%>
<html>

    <%-- include the google api scripts as well as the local scripts --%>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="./js/google.js"></script>
    <script src="./js/tutorialform.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="./ace-builds/src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>

    <script>
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

        <%-- link the stylesheet and set the google authentication metadata --%>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
        <meta name="google-signin-scope" content="profile email">

    <%-- set the title appropriately --%>
    <title>Create Your Tutorial</title>

    </head>

    <%-- Body --%>
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

        <%-- container for CSS --%>
        <div class="container" style="top:50px; position: relative;">

            <%-- dynamic form for creating the practice problems form --%>
            <form:form method="post" id="make-tutorial" action="/created" modelAttribute="problemsForm">
                <input type="hidden" value=${language} name="language">
                <input type="hidden" value="0" id="mcsCount">
                <input type="hidden" value="0" id="codesCount">
                <input type="hidden" value="0" id="count">
                <h2><h2 style="display: inline">Practice Problems </h2><h4 style="display: inline">[${languageName}]</h4></h2>
                <div id="problems">
                </div>
            </form:form>
            <br>
            <button class="btn-default" onclick="event.preventDefault(); addMC();">Add Question</button>
            <button class="btn-default" onclick="event.preventDefault(); addCode();">Add Coding Challenge</button>
            <button style="font-size: 17px;" class="btn-primary" onclick="event.preventDefault(); submit();">Submit</button>
        </div>

        <%-- include the footer --%>
        <div style="top:50px; position: relative;">
            <jsp:include page="/footer.jsp"/>
        </div>

    </body>
</html>