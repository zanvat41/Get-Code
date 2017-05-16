<%--
  Created by IntelliJ IDEA.
  User: MAVIRI 3
  Date: 2/5/2017
  Time: 2:45 PM
  To change this template use File | Settings | File Templates.

  adminlogin is the page that is used by admins of the site to login to the admin page
--%>

<%-- Set the content type and language, as well as include the tag library for spring forms --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%-- Start of HTML --%>
<html>

    <%-- Head --%>
    <head>

        <%-- Add the style sheet for the page as well as set the title --%>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <title>Admin Login</title>

    </head>

    <%-- Body --%>
    <body class="bodyBackground">

        <%-- Container for CSS --%>
        <div class="container">

            <%-- Get the admin login message from the server using EL and prompt the user for their username and password --%>
            <div>
                <h1>${adminloginmessage}</h1>
                <h3>Please enter your admin username and password</h3>
            </div>
            <br />

            <%-- Form for getting the username and password in order to verify the login --%>
            <form:form method="post" action="/adminlogin">
                <div>
                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" maxlength="30" required />
                    <br /><br />
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" required />
                    <br /><br />
                    <input type="submit" value="Login" class="btn-default">
                </div>
            </form:form>

        </div>
    </body>
</html>