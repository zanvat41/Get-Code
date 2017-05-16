<%--
  Created by IntelliJ IDEA.
  User: MAVIRI 3
  Date: 2/5/2017
  Time: 2:45 PM
  To change this template use File | Settings | File Templates.

  adminregistration is a page that allows users to register as admins for the site
--%>

<%-- set the content type, language, and include the tag library for spring forms --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%-- Start HTML --%>
<html>

    <%-- Head --%>
    <head>

        <%-- set the style sheet and set the title appropriately --%>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <title>Admin Registration</title>
    </head>

    <%-- Body --%>
    <body class="bodyBackground">

        <%-- Container for CSS --%>
        <div class="container">

            <%-- Get the admin registration message and prompt the user for their desired name and password --%>
            <div>
                <h1>${adminregistrationmessage}</h1>
                <h3>Please enter your admin username and password</h3>
            </div>
            <br />

            <%-- Get the username and password from the user --%>
            <form:form method="post" action="/adminregistration">
                <div>
                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" maxlength="30" required />
                    <br /><br />
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" required />
                    <br /><br />
                    <input type="submit" value="Register" class="btn-default">
                </div>
            </form:form>

        </div>
    </body>
</html>