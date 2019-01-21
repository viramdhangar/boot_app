<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<html>
<head>
<title>Show Employees</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	      
	<h3 style="color: red;">Show All Matches</h3>
	<ul>
		<c:forEach var="match" items="${matches}">
			<li><a href="/leagues/${match.id}">${match.team1} vs ${match.team2} on ${match.matchStartTime}</a></li>
		</c:forEach>
	</ul>
</body>
</html>