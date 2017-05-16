<%--
  Created by IntelliJ IDEA.
  User: ZheLin
  Date: 5/15/2017
  Time: 19:33
  Description: This is a page with the users' instructions.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <%-- Link to needed stylesheets and set the title --%>
        <link rel="stylesheet" type="text/css" href="./css/bootstrap.css">
        <link href="./css/sb-admin.css" rel="stylesheet">
        <link href="./css/sb-admin-2.css" rel="stylesheet">
        <link href="./css/font-awesome.css" rel="stylesheet">
        <meta name="google-signin-client_id" content="378509392544-vqjohdm5a5k5276qrfelb0rhkk4m8ofe.apps.googleusercontent.com">
        <meta name="google-signin-scope" content="profile email">
    <title>Help and Instructions</title>

</head>
<body>

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

    <%-- Instructions --%>
    <div class="content-section-a">
        <div class="container">
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">General</h2>
                    <ul>
                        <li>Sign in: You need a google account to sign in. On the login page, click the “Sign In” button. Then you will be able to sign in with your google account, or create a new google account.</li>
                        <li>Sign out: After you signed in, you can sign out by clicking the “sign out” button on the top toolbar on any page.</li>
                        <li>Return to profile page: After you signed in, you can return to the profile page by clicking the “Get Code” button on the top toolbar on any page.</li>
                        <li>Search tutorial: On profile page, you can use the search bar on the left to search the for tutorials.</li>
                        <li>Filter tutorial: On the process tracker/search result table on the profile page, there is a language filter. You can use it to get the tutorials with a given programming language.</li>
                    </ul>
                    <h2 class="section-heading">Teachers' Functions</h2>
                    <ul>
                        <li>Create Class: On profile page, click the “Create Or Join a Class” button on the left. Then in the page that shown up, type in the class name on the input bar under the “Create a Class” section, and click “Create Class”. The class code for your new class will be generated.</li>
                        <li>Create Tutorial: On the top toolbar on any page, click “Create Tutorial”. In the new page that shown up, you can add your contents there. For example, if you want to add some text material, click “Add Text” and type in your contents. (To add videos or images, you need to provide the url in the input bar.) </br> If you want this tutorial be a public tutorial, then type none in the “class code” section. Otherwise, if you want this new tutorial to be a part of your class, put the class code of your class in the “class code” section. After finished inputting the material, click “Next” to add practice problems or click “Preview” to see how your tutorial looks. </br> If you clicked “Next”, you would be able to add practice problems. You can add multiple choices problems with 2 to 8 answers. Also, you can add coding challenges by providing codes and test cases. After you finished adding practice problems, click “submit” and the tutorial would be generated. </li>
                        <li>View classes you are teaching: On the profile page, there is a “Classes Teaching” table, you can view all the classes that you are teaching there.</li>
                        <li>Lock/Unlock class: Click on a class you are teaching, then in the page that shown up, click lock/unlock button to lock/unlock your class. If your class is locked, students cannot join it.</li>
                        <li>Delete class: Click on a class you are teaching, then in the page that shown up, click delete button to delete your class. </li>
                        <li>Manage your tutorial: On the profile page, click “Manage My Tutorials”. And in the page that shown up, you can click edit/delete to edit/delete your tutorials.</li>
                    </ul>
                    <h2 class="section-heading">Students' Functions</h2>
                    <ul>
                        <li>Join a class: On profile page, click the “Create Or Join a Class” button on the left. Then in the page that shown up, type in the class code on the input bar under the “Join a Class” section, and click “Join Class”. </li>
                        <li>View classes you are attending: On the profile page, there is a “Classes Enrolled” table, you can view all the classes that you are attending there. </li>
                        <li>View tutorials of a class you are attending: Click on a class, then in the page that shown up, click on the tutorial. </li>
                        <li>Leave a class: On a “Classes Enrolled” table, you can click “Leave Class” to leave a class that you are attending.</li>
                    </ul>
        </div>
    </div>

    <%-- Footer --%>
    <jsp:include page="/footer.jsp"/>

</div>
</body>
</html>