<%--
  Created by IntelliJ IDEA.
  User: tyler
  Date: 4/20/2017
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.

  class is a page that allows users to either create a class as a teacher or join a class as a student
--%>

<%-- Set the content type and language, and include all needed tags --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>               <%-- Page info --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>          <%-- spring tags --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>                 <%-- core c library --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>                <%-- FMT --%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>    <%-- Spring forms --%>

<%-- Start HTML --%>
<html>

    <%-- include scripts for bootstrap and needed webpage scripts --%>
    <script src="./js/google.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <script src="https://apis.google.com/js/platform.js" async defer></script>


    <%-- Head --%>
    <head>

        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <link href="./css/sb-admin.css" rel="stylesheet">
        <link href="./css/sb-admin-2.css" rel="stylesheet">
        <link href="./css/font-awesome.css" rel="stylesheet">
        <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
        <meta name="google-signin-scope" content="profile email">
        <title>Create or Join a Class</title>

    </head>

    <%-- Body --%>
    <body>

        <%-- Container for CSS --%>
        <div id="wrapper">

            <%-- Navigation Bar --%>
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">

                <%-- Collapsing Nav Bar --%>
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>

                    <%-- The main link to go back to the profile page --%>
                    <a class="navbar-brand" href="/profile">Get Code</a>
                </div>

                <%-- Top Bar --%>
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
        </div>

        <%-- Main Content --%>
        <div class="content-section-a" style="height:100%">

            <%-- Container for CSS --%>
            <div class="container">

                <%-- Separate the two forms side by side --%>
                <div class="row">
                    <div class="clearfix"></div>

                    <%-- Create a class form --%>
                    <div class="col-lg-5 col-sm-6">
                        <form:form method="post" action="/createclass">
                            <div class="row">
                                <h1>Create a Class</h1>
                                <p>Click here to create a class and generate a class code for your students to use in order to enter your class</p>
                                <br /><br />
                            </div>
                            <div class="row">
                                <label for="classname">Class Name</label>
                                <input type="text" name="classname" id="classname" maxlength="30" required />
                                <br /><br />
                                <input type="submit" value="Create Class" class="btn-default"/>
                            </div>
                        </form:form>
                    </div>

                    <%-- Join a class form --%>
                    <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                        <form:form method="post" action="/joinclass">
                            <h1>Join a Class</h1>
                            <p>To join a class, enter the class code given by your teacher</p>
                            <br /><br /><br />
                            <label for="classcode">Class Code</label>
                            <input type="text" name="classcode" id="classcode" maxlength="30" required />
                            <br /><br />
                            <input type="submit" value="Join Class" class="btn-default"/>
                        </form:form>
                    </div>

                </div>
            </div>

            <%-- Put the footer at the bottom of the page --%>
            <jsp:include page="/footer.jsp"/>

        </div>
    </body>
</html>
