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


	<p><a href="setcolor?color=white">WHITE</a></p>
	<p><a href="setcolor?color=red">RED</a></p>
	<p><a href="setcolor?color=green">GREEN</a></p>
	<p><a href="setcolor?color=cyan">CYAN</a></p>

	


</body>


</html>