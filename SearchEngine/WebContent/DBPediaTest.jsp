<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
<script type="text/javascript" src="jquery/jquery-1.7.1.min.js"></script>
</head>
<body>
	<form action="/SearchEngine/Search" method="post">
		keyword: <input id="keyword" type="text" name="keyword" /> <input type="submit" value="Search"/>
		<div id="results"></div>
	</form>
</body>


</html>