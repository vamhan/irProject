<%@ page import="org.json.*" %>
<html>
<body bgcolor="white">

<% 
JSONArray resultList = ((JSONObject)request.getAttribute("resultList")).getJSONArray("results");
for (int i = 0; i < resultList.length(); i++) {
	JSONObject result = resultList.getJSONObject(i);
	out.print("<div><a href='" + "http://en.wikipedia.org/wiki?curid=" + result.getInt("wikiPageID") + "'><b>");
	out.println(result.getString("label"));
	out.print("</b></a></div>");
	out.print("<div>");
	out.println(result.getString("description"));
	out.print("</div>");
	out.print("<div>&nbsp;</div>");
}
//out.print(request.getAttribute("resultList"));

JSONArray localReports = (JSONArray)request.getAttribute("localReports");
for (int i = 0; i < localReports.length(); i++) {
	JSONObject result = localReports.getJSONObject(i);
	out.print("<div><b>");
	out.println(result.getString("title"));
	out.print("</b></div>");
	out.print("<div>");
	out.println(result.getString("description"));
	out.print("</div>");
	out.print("<div><a href='" + result.getString("path") + "'>");
	out.println(result.getString("path"));
	out.print("</a></div>");
	out.print("<div>&nbsp;</div>");
}

%>


</html>