<%--
  Created by IntelliJ IDEA.
  User: tyler
  Date: 4/20/2017
  Time: 6:55 PM
  To change this template use File | Settings | File Templates.

  classprofile is a page students use to access their class
--%>

<%-- Set the content type and language, and include all needed tags --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>               <%-- Page info --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>          <%-- spring tags --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>                 <%-- core c library --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>                <%-- FMT --%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>    <%-- Spring forms --%>

<%-- Start HTML --%>
<html>

<%-- get the google API script, the scrips we made, and the scripts for CSS --%>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<script src="./js/google.js"></script>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>

<%-- Head --%>
<head>

    <%-- Link the style sheets --%>
    <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
    <link href="./css/sb-admin.css" rel="stylesheet">
    <link href="./css/sb-admin-2.css" rel="stylesheet">
    <link href="./css/font-awesome.css" rel="stylesheet">

    <%-- google signin information for google app engine --%>
    <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
    <meta name="google-signin-scope" content="profile email">

    <%-- Set the title to the class name that was clicked on --%>
    <title>${className}</title>

</head>

<%-- Body --%>
<body>

<%-- Container for CSS --%>
<div id="wrapper">

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
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav side-nav">
                <li class="sidebar-search">

                    <%-- Search bar --%>
                    <form:form method="post" action="/search">
                        <div class="input-group custom-search-form">
                            <input name="query" id="query" type="text" class="form-control" placeholder="Search...">
                            <span class="input-group-btn" style="height:34px">
                                        <button class="btn btn-remove" type="submit" style="height:34px">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </span>
                        </div>
                    </form:form>

                </li>
                <li>
                    <a href="/profilepublicgeneral">All General Public Tutorials</a>
                </li>
                <li>
                    <a href="/mytutorials">Manage My Tutorials</a>
                </li>
                <li>
                    <a href="/class">Create or Join a Class</a>
                </li>
            </ul>
        </div>
    </nav>

    <%-- Main Page --%>
    <div>

        <%-- Fluid container so items can shift around based on window size --%>
        <div class="container-fluid">

            <%-- Top bar with name --%>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Hello, ${fullName}</h1>
                    <ol class="breadcrumb">
                        <li class="active">
                            <i class="fa fa-dashboard"></i> Teacher: ${teacherName}
                        </li>
                    </ol>
                </div>
            </div>

            <%-- Table with the class name and all of the tutorials for that class --%>
            <div class="row">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="fa fa-money fa-fw"></i> ${className}</h3>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>Tutorial</th>
                                    <th>Language</th>
                                    <th>Creator</th>
                                    <th>Class Code</th>
                                    <th>Highest Score</th>
                                    <th>Attempts</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="element" items="${listElements}">
                                    <tr>
                                        <td>
                                            <a href="/tutorialPage?tutorialId=${element.tutorialId}">${element.tutorialName}</a>
                                        </td>
                                        <td>${element.tutorialLanguage}</td>
                                        <td>${element.tutorialCreator}</td>
                                        <td>${element.tutorialClassCode}</td>
                                        <td>${element.userScore}%</td>
                                        <td>${element.userAttempts}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <%-- Put the footer at the bottom of the page --%>
            <div>
                <jsp:include page="/footer.jsp"/>
            </div>

        </div>
    </div>
</div>
</body>
</html>