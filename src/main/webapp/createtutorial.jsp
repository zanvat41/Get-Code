<%--
  Created by IntelliJ IDEA.
  User: tyler
  Date: 4/3/2017
  Time: 4:35 PM
  To change this template use File | Settings | File Templates.

  createtutorial is the page that allows users to create their own tutorial
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%-- Start HTML --%>
<html>

    <%-- include google api and local JS Scripts --%>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="./js/google.js"></script>
    <script src="./js/tutorialform.js"></script>

    <%-- Head --%>
    <head>

        <%-- link the style sheet and add the metadata for the google authentication --%>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
        <meta name="google-signin-scope" content="profile email">

    <%-- set the title --%>
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
        <div class="container" style="min-height: 80%;top:50px; position: relative; padding-bottom: 100px;">

            <%-- dynamic form for making the form --%>
            <form:form method="post" id="make-tutorial" action="/createproblems" modelAttribute="tutorialForm">
                <input type="hidden" value="0" id="textsCount">
                <input type="hidden" value="0" id="headingsCount">
                <input type="hidden" value="0" id="picturesCount">
                <input type="hidden" value="0" id="videosCount">
                <input type="hidden" value="0" id="count">
                <h2>Tutorial Name: <input type="text" name="name"></h2>
                <h3>Language: <select name="language">
                    <option value=3>Java 7</option>
                    <option value=43>Java 8</option>
                    <option value=5>Python 2</option>
                    <option value=30>Python 3</option>
                    <option value=1>C</option>
                    <option value=2>C++</option>
                    <option value=9>C#</option>
                    <option value=32>Objective-C</option>
                    <option value=20>Javascript</option>
                    <option value=7>PHP</option>
                    <option value=6>Perl</option>
                    <option value=8>Ruby</option>
                    <option value=50>Rust</option>
                    <option value=37>VB.NET</option>
                    <option value=14>Bash</option>
                    <option value=18>Lua</option>
                    <option value=15>Scala</option>
                    <option value=12>Haskell</option>
                    <option value=26>Common Lisp (SBCL)</option>
                    <option value=13>Clojure</option>
                    <option value=16>Erlang</option>
                    <option value=51>Swift</option>
                    <option value=21>Go</option>
                    <option value=33>F#</option>
                    <option value=22>D</option>
                    <option value=23>OCaml</option>
                    <option value=24>R</option>
                    <option value=10>MySQL</option>
                    <option value=11>Oracle</option>
                    <option value=42>T-SQL</option>
                    <option value=44>DB2</option>
                    <option value=48>XQuery</option>
                    <option value=31>Groovy</option>
                    <option value=36>COBOL</option>
                    <option value=54>Fortran</option>
                    <option value=25>Pascal</option>
                    <option value=39>Smalltalk</option>
                    <option value=40>Tcl</option>
                    <option value=49>Racket</option>
                    <option value=46>Octave</option>
                    <option value=41>Whitespace</option>
                    <option value=38>LOLCODE</option>
                </select></h3>
                <h3>Class Code (leave blank if public): <input type="text" name="classCode"></h3><br>
                <div id="tutorial">
                </div>
            </form:form>
            <br>
            <div style="bottom: 0; position: absolute; height: 100px">
                <button class="btn-default" onclick="event.preventDefault(); addTextArea();">Add Text</button>
                <button class="btn-default" onclick="event.preventDefault(); addHeading();">Add Heading</button>
                <button class="btn-default" onclick="event.preventDefault(); addImage();">Add Image</button>
                <button class="btn-default" onclick="event.preventDefault(); addVideo();">Add Video</button>
                <button class="btn-default" onclick="event.preventDefault(); preview();">Preview</button>
                <button style="font-size: 17px;" class="btn-primary" onclick="event.preventDefault(); problems();">Next</button>

                <%-- include the footer --%>
                <jsp:include page="/footer.jsp"/>
            </div>
        </div>
    </body>
</html>