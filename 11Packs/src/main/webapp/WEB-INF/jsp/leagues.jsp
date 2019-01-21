<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<html>
<head>
<title>Show Leagues</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	      
	<h3 style="color: red;">Show All Leagues</h3>
	<ul>
		<c:forEach var="league" items="${leagues}">
			<li>${league.league} Entry Fee: ${league.entryFee} League Size: ${league.size}</li>
		</c:forEach>
	</ul>
	<a href="/createTeam/${matchId}">Create Team</a>
</body>
</html>