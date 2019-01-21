<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Log in with your credentials</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>

<body>

<div class="container">

    <form method="POST" action="/identity/registration" class="form-signon">
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            
            <input name="referralCode" type="text" class="form-control" placeholder="Referral Code (Optional)"
                   autofocus="true"/> 
                   <br>           
            <input name="uniqueNumber" type="text" class="form-control" placeholder="Mobile Number"
                   autofocus="true"/>
                   <br>
            <input name="email" type="text" class="form-control" placeholder="Email"
                   autofocus="true"/>  
                   <br>  
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <br>
            <input name="firstname" type="text" class="form-control" placeholder="First Name"
                   autofocus="true"/>
                   <br>
            <input name="lastName" type="text" class="form-control" placeholder="Last Name"
                   autofocus="true"/>         
            <span>${errorMsg}</span>
				   <br>
            <button class="btn btn-lg btn-primary btn-block" type="submit">SignOn</button>
        </div>

    </form> 

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script></body>
</html>
