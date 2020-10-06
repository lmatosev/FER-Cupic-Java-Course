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
	
	
	<h1>Main</h1>

	<p>Login</p>

	<c:choose>
		<c:when test="${sessionScope.message != null}">
			<p>
				<%
					out.print(session.getAttribute("message"));
							session.removeAttribute("message");
				%>
			</p>
		</c:when>
	</c:choose>

	<form action="login" method="post">
		<p>
			<span class="formLabel">Nickname </span><input type="text"
				name="nick" size="20"
				value=<%if (session.getAttribute("username") != null && session.getAttribute("current.user.nick") == null) {
				out.print(session.getAttribute("username"));
			} else {
				out.print("");
			}%>>

		</p>
		<p>
			<span class="formLabel">Password </span><input type="text"
				name="password" size="20">
		</p>

		<button type="submit">Submit</button>


	</form>

	<a href="register">Register</a>


	<p>Authors</p>

	<c:forEach items="${users}" var="user">

		<li><a href="author/${user.getNick()}">${user.getNick()}</a></li>


	</c:forEach>
</body>


</html>