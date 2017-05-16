<%--
  Created by IntelliJ IDEA.
  User: tyler
  Date: 4/3/2017
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.

  profile is the main page that allows users to view their classes, tutorials, and so on
--%>

<%-- Set the content type and language, and include all needed tags --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>               <%-- Page info --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>          <%-- spring tags --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>                 <%-- core c library --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>                <%-- FMT --%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>    <%-- Spring forms --%>

<%-- Start HTML --%>
<html>

    <%-- import scripts --%>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <script src="./js/google.js"></script>
    <script src="./js/ajax.js"></script>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <%-- Head --%>
    <head>

        <%-- import stylesheets and add meta data for google authentication --%>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <link href="./css/sb-admin.css" rel="stylesheet">
        <link href="./css/sb-admin-2.css" rel="stylesheet">
        <link href="./css/font-awesome.css" rel="stylesheet">
        <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
        <meta name="google-signin-scope" content="profile email">

        <%-- Set the title --%>
        <title>Profile Page</title>
    </head>

    <%-- Body --%>
    <body>

        <%-- Wrapper for CSS --%>
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

            <%-- wrapper for CSS --%>
            <div>
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">Hello, ${fullName}</h1>
                            <ol class="breadcrumb">
                                <li class="active">
                                    <i class="fa fa-dashboard"></i> Dashboard
                                </li>
                            </ol>
                        </div>
                    </div>
                    <div class="row">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-dashboard fa-fw"></i> ${query}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Language: <select id="langFilter" name="langFilter">
                                    <option value="nf" selected="selected">All</option>
                                    <option value="Java 7">Java 7</option>
                                    <option value="Java 8">Java 8</option>
                                    <option value="Python 2">Python 2</option>
                                    <option value="Python 3">Python 3</option>
                                    <option value="C">C</option>
                                    <option value="C++">C++</option>
                                    <option value="C#">C#</option>
                                    <option value="Objective-C">Objective-C</option>
                                    <option value="Javascript">Javascript</option>
                                    <option value="PHP">PHP</option>
                                    <option value="Perl">Perl</option>
                                    <option value="Ruby">Ruby</option>
                                    <option value="VB.NET">VB.NET</option>
                                    <option value="Bash">Bash</option>
                                    <option value="Lua">Lua</option>
                                    <option value="Scala">Scala</option>
                                    <option value="Haskell">Haskell</option>
                                    <option value="Common Lisp">Common Lisp (SBCL)</option>
                                    <option value="Clojure">Clojure</option>
                                    <option value="Erlang">Erlang</option>
                                    <option value="Swift">Swift</option>
                                    <option value="Go">Go</option>
                                    <option value="F#">F#</option>
                                    <option value="D">D</option>
                                    <option value="OCaml">OCaml</option>
                                    <option value="R">R</option>
                                    <option value="MySQL">MySQL</option>
                                    <option value="Oracle">Oracle</option>
                                    <option value="T-SQL">T-SQL</option>
                                    <option value="DB2">DB2</option>
                                    <option value="XQuery">XQuery</option>
                                    <option value="Groovy">Groovy</option>
                                    <option value="COBOL">COBOL</option>
                                    <option value="Fortran">Fortran</option>
                                    <option value="Pascal">Pascal</option>
                                    <option value="Smalltalk">Smalltalk</option>
                                    <option value="Tcl">Tcl</option>
                                    <option value="Racket">Racket</option>
                                    <option value="Rust">Rust</option>
                                    <option value="Octave">Octave</option>
                                    <option value="Whitespace">Whitespace</option>
                                    <option value="LOLCODE">LOLCODE</option>
                                </select></h3>
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">

                                    <%-- Table of the public tutorials --%>
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

                                        <tbody id="tbody1">
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
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-book fa-fw"></i> Classes Enrolled</h3>
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">

                                    <%-- Table for the classes you are in --%>
                                    <table class="table table-bordered table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th>Class Name</th>
                                            <th>Class Code</th>
                                            <th>Teacher</th>
                                            <th>Actions</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="class" items="${classes}">
                                            <tr>
                                                <td>
                                                    <a href="/classprofile?classCode=${class.classCode}&className=${class.className}&classTeacher=${class.teacher}">${class.className}</a>
                                                </td>
                                                <td>${class.classCode}</td>
                                                <td>${class.teacher}</td>
                                                <td>
                                                <form:form method="post" action="/leaveclass">
                                                    <input type="hidden" id="classcode" name="classcode" value="${class.classCode}">
                                                    <input type="submit" value="Leave Class" class="btn-default">
                                                </form:form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title"><i class="fa fa-globe fa-fw"></i> Classes Teaching</h3>
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">

                                    <%-- table for the class you teach --%>
                                    <table class="table table-bordered table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th>Class Name</th>
                                            <th>Class Code</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="classteaching" items="${classesTeaching}">
                                            <tr>
                                                <td>
                                                    <a href="/roster?classCode=${classteaching.classCode}&className=${classteaching.className}">${classteaching.className}</a>
                                                </td>
                                                <td>${classteaching.classCode}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>

                    <%-- include footer --%>
                    <div>
                        <jsp:include page="/footer.jsp"/>
                    </div>
                </div>
            </div>
                <ul class="nav navbar-nav side-nav navbar-right">
                </ul>
        </div>
    </body>
</html>