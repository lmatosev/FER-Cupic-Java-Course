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


	<h1>${entry.getTitle()}</h1>

	<p>${entry.getText()}</p>



	<c:if test="${sessionScope['current.user.id'] != null}">

		<c:if test="${sessionScope['current.user.nick'] == author.getNick()}">


			<a
				href="edit?nick=${entry.getCreator().getNick()}&id=${entry.getId()}">>Edit
				Entry</a>


		</c:if>


	</c:if>



	<c:forEach items="${entry.getComments()}" var="comment">

		<p>${comment.getPostedOn()}-${comment.getUsersEMail()}:
			${comment.getMessage() }</p>

	</c:forEach>


	<c:choose>

		<c:when test="${sessionScope['current.user.nick']==null}">

			<form action="comments?entryId=${entry.getId()}" method="post">
				<p>
					<span class="formLabel">Write a comment: </span> <input type="text"
						name="message" size="50">
				</p>

				<p>
					<span class="formLabel">Email </span><input type="text"
						name="email" size="20">
				</p>

				<button type="submit">Submit</button>


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
			</form>

		</c:when>


		<c:otherwise>
			<form
				action="comments?nick=${sessionScope['current.user.nick']}&entryId=${entry.getId()}"
				method="post">
				<p>
					<span class="formLabel">Write a comment: </span><input type="text"
						name="message" size="50">
				</p>

				<button type="submit">Submit</button>
			</form>
		</c:otherwise>
	</c:choose>
</body>


</html>