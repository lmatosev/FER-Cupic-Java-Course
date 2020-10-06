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


	<h1>Create/edit entry</h1>

	<c:choose>

		<c:when test="${empty entry}">
			<form action="" method="post">
				<p>
					<span class="formLabel">Title </span><input type="text"
						name="title" size="20">
				</p>
				<p>
					<span class="formLabel">Text </span><input type="text" name="text"
						size="20">
				</p>

				<button type="submit">Submit</button>
			</form>

		</c:when>


		<c:otherwise>

			<form action="" method="post">
				<input type="hidden" name="id" value="${entry.getId()}">
				<p>
					<span class="formLabel">Title </span><input type="text"
						name="title" size="20" value="${entry.getTitle()}">
				</p>
				<p>
					<span class="formLabel">Text </span><input type="text" name="text"
						size="20" value="${entry.getText() }">
				</p>

				<button type="submit">Submit</button>
			</form>

		</c:otherwise>

	</c:choose>


</body>


</html>