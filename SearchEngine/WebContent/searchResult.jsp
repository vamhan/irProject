<%@ page import="org.json.*" %>
<html>
<body bgcolor="white">

<% 
JSONArray resultList = ((JSONObject)request.getAttribute("resultList")).getJSONArray("results");
for (int i = 0; i < resultList.length(); i++) {
	JSONObject result = resultList.getJSONObject(i);
	out.print("<div><b>");
	out.println(result.getString("label"));
	out.print("</b></div>");
	out.print("<div>");
	out.println(result.getString("description"));
	out.print("</div>");
	out.print("<div><a href='" + result.getString("uri") + "'>");
	out.println(result.getString("uri"));
	out.print("</a></div>");
	out.print("<div>&nbsp;</div>");
}

%>


</html>