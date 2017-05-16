<%--
  Created by IntelliJ IDEA.
  User: Tyler
  Date: 3/1/2017
  Time: 6:11 PM
  To change this template use File | Settings | File Templates.

  index is the landing page that will give users the link to sign in and go to the main site
--%>

<%-- set the content type and language, and set the tag library for spring forms --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%-- Set the document type --%>
<!DOCTYPE html>

<%-- Start HTML --%>
<html>

    <%-- import all needed scripts for google login, button rendering, jquery, and bootstrap --%>
    <script src="https://apis.google.com/js/platform.js" async defer></script>
    <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
    <script src="./js/google.js"></script>
    <script src="./js/jquery.js"></script>
    <script src="./js/bootstrap.min.js"></script>

    <%-- Head --%>
    <head>

        <%-- import style sheets and set the google authentication metadata --%>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="./css/landing-page.css">
        <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
        <meta name="google-signin-scope" content="profile email">

        <%-- set the title --%>
        <title>Get Code</title>
    </head>

    <%-- Body --%>
    <body>

        <%-- The header --%>
        <div class="intro-header">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">

                        <%-- Get Code --%>
                        <div class="intro-message">
                            <h1>Get Code</h1>
                            <hr class="intro-divider">

                            <%-- List of control buttons --%>
                            <ul class="list-inline intro-social-buttons">

                                <%-- Sign in with google --%>
                                <li>
                                    <div class="down-10">
                                        <form:form method = "post" id="login_form" action = "/profile">
                                            <input type="hidden" class="text" id="firstName" name="firstName" value="">
                                            <input type="hidden" class="text" id="lastName" name="lastName" value="">
                                            <input type="hidden" class="text" id="email" name="email" value="">
                                            <div class="g-signin2" data-margin-top="20px" data-width="150px" data-height="30px" data-onsuccess="onSignIn"></div>
                                        </form:form>
                                    </div>
                                </li>

                                <%-- Admin login --%>
                                <li>
                                    <form:form method = "GET" action = "/adminlogin">
                                        <input type = "submit" value = "Admin Login" class="btn-default"/>
                                    </form:form>
                                </li>

                                <%-- Admin registration --%>
                                <li>
                                    <form:form method = "GET" action = "/adminregistration">
                                        <input type = "submit" value = "Admin Registration" class="btn-default"/>
                                    </form:form>
                                </li>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <%-- Services --%>
        <a  name="services"></a>
        <div class="content-section-a">
            <div class="container">
                <div class="row">
                    <div class="col-lg-5 col-sm-6">
                        <hr class="section-heading-spacer">
                        <div class="clearfix"></div>
                        <h2 class="section-heading">Get Code offers free and unlimited access to...</h2>
                        <ul>
                            <li>Tutorials on various beginner topics such as...</li>
                            <ul>
                                <li>Data Types</li>
                                <li>Programming Basics</li>
                                <li>Good Programming Practices</li>
                                <li>And more!</li>
                            </ul>
                            <li>Practice Problems Training Both Programming Skill and Critical Thinking</li>
                            <li>Progress Tracking</li>
                            <li>Classes and Custom Tutorials</li>
                        </ul>
                    </div>
                    <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                        <img class="img-responsive" src="img/side-image.jpeg" alt="">
                    </div>
                </div>
            </div>
        </div>

        <%-- Footer --%>
        <jsp:include page="/footer.jsp"/>
    </body>
</html>