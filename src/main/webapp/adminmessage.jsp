<%--
  Created by IntelliJ IDEA.
  User: tyler
  Date: 4/6/2017
  Time: 5:51 PM
  To change this template use File | Settings | File Templates.

  adminmessage is used to message someone who attempted to login or register properly in the admin pages
--%>

<%-- set content type, language, and include the tag library for spring forms --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>

<%-- Start HTML --%>
<html>

    <%-- Head --%>
    <head>

        <%-- set the stylesheet and set the title to the message returned from the server --%>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <title>${adminmessage}</title>
    </head>

    <%-- Body --%>
    <body class="bodyBackground">

        <%-- Container for CSS --%>
        <div class="container">

            <%-- show the message returned from the server adn provide a link back to the index page --%>
            <div>
                <h1>${adminmessage}</h1>
                <h3>Please click here to go back to the index page</h3>
                <form:form method = "GET" action = "/">
                    <input type="submit" value="Go Back" class="btn-default">
                </form:form>
            </div>

        </div>
    </body>
</html>