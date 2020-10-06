<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  session="true" %>


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


	<h1>OS Usage</h1>
	<p>Here are the results of OS usage in survey that we completed.</p>
	<p><img src="reportImage" width="500" height="333"></p>
	
	


</body>


</html>