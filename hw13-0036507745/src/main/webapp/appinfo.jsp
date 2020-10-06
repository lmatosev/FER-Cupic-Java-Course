<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>


<html>
<head>
</head>

<body
	bgcolor=<%String pickedBgCol = (String) session.getAttribute("pickedBgCol");
			if (pickedBgCol == null) {
				out.print("white");
			} else {
				out.print(pickedBgCol);
			}%>>


	<p>
		<%
			ServletContext sc = request.getServletContext();
			long currentTime = System.currentTimeMillis();
			long storedTime = (long) sc.getAttribute("time");
			long time = currentTime - storedTime;
			long seconds = time / 1000;
			long minutes = seconds / 60;
			long hours = minutes / 60;
			long days = hours / 24;
			StringBuilder sb = new StringBuilder();
			sb.append("Application running for: ");
			sb.append(days).append(" days ").append(hours).append(" hours ").append(minutes).append(" minutes ")
					.append(seconds).append(" seconds and ").append(time).append(" miliseconds");
			out.write(sb.toString());
		%>
	</p>





</body>


</html>