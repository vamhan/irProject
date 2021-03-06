package controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

public class DBPediaRequest {

	static HttpClient client;
	
	public static JSONObject getResult(String keyword) {
		try {
			if (client == null) {
				client = new DefaultHttpClient();
			}
			String getURL = "http://lookup.dbpedia.org/api/search/KeywordSearch?QueryString=" + keyword;
			HttpGet get = new HttpGet(getURL);
			get.setHeader("Accept", "application/json");
			HttpResponse responseGet = client.execute(get);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					responseGet.getEntity().getContent()));
	
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
	
			JSONObject o = new JSONObject(result.toString());
			
			JSONArray resultList = o.getJSONArray("results");
			for (int i = 0; i < resultList.length(); i++) {
				JSONObject object = resultList.getJSONObject(i);
				String uri = object.getString("uri");
				int wikiPageID = getWikiPageID(uri);
				//System.out.println(wikiPageID);
				object.put("wikiPageID", wikiPageID);
			}
			
			rd.close();
			get.abort();
			
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	private static int getWikiPageID(String uri) {
		String s2 = "PREFIX  dbo: <http://dbpedia.org/ontology/>\n" +
                "\n" +
                "SELECT ?wikiid\n" +
                "WHERE\n" +
                "  { <" + uri + "> dbo:wikiPageID ?wikiid .\n" +
                "  }\n" +
                "";

        Query query = QueryFactory.create(s2); //s2 = the query above
        QueryExecution qExe = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", query );
        ResultSet results = qExe.execSelect();
        //System.out.println(results.nextSolution().get("wikiid").asLiteral().getInt());
        return results.nextSolution().get("wikiid").asLiteral().getInt();
        //ResultSetFormatter.out(System.out, results, query) ;
	}
}
