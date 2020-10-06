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
		<a href="<%out.write(request.getContextPath());%>/servleti/main">Main
			page</a>
	</p>

	<h1>Register</h1>
	<c:choose>
		<c:when test="${message!=null }">
			<p>
				<%
					out.print(request.getAttribute("message"));
							request.removeAttribute("message");
				%>
			</p>
		</c:when>
	</c:choose>
	<form action="register" method="post">
		<p>
			<span class="formLabel">First Name </span><input type="text"
				name="firstName" size="20">
		</p>
		<p>
			<span class="formLabel">Last Name </span><input type="text"
				name="lastName" size="20">
		</p>
		<p>
			<span class="formLabel">Email </span><input type="text" name="email"
				size="20">
		</p>
		<p>
			<span class="formLabel">Nickname </span><input type="text"
				name="nick" size="20">
		</p>
		<p>
			<span class="formLabel">Password </span><input type="text"
				name="password" size="20">
		</p>

		<button type="submit">Submit</button>
	</form>


	<a href="main">Main page</a>


</body>


</html>