
<html>
<body bgcolor="white">

<!-- Forward to a servlet -->
<jsp:forward page="/DBPediaRequests">
  <jsp:param name="keyword" value='<%= request.getParameter("keyword") %>' ></jsp:param>
</jsp:forward>

</html>