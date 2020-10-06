<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
</head>

<body>

	<p>
		<c:choose>

			<c:when test="${sessionScope['current.user.nick'] ==null}">

				<p>Not logged in</p>

			</c:when>

			<c:otherwise>

				<p>First Name: ${sessionScope['current.user.firstName'] }</p>
				<p>Last Name: ${sessionScope['current.user.lastName']}</p>

				<p>
					<a href="<%out.write(request.getContextPath());%>/servleti/logout">Logout</a>
				</p>
			</c:otherwise>

		</c:choose>
	</p>
	
	<p>
		<a href="<%out.write(request.getContextPath());%>/servleti/main">Main page</a>
	</p>


	<h1>Blog Entries</h1>

	<p>Author: ${author.getNick()}</p>


	<c:forEach items="${entries}" var="entry">

		<li><a href="${author.getNick()}/${entry.getId()}">${entry.getTitle()}</a></li>

	</c:forEach>

	<c:if test="${sessionScope['current.user.id'] != null}">

		<c:if test="${sessionScope['current.user.nick'] == author.getNick()}">

			<li><a href=" 	${author.getNick()}/new?nick=${author.getNick()}">New
					Entry</a></li>


		</c:if>


	</c:if>





</body>


</html>