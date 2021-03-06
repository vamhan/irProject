package controller;

import java.io.IOException;

import controller.lucene.search.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LuceneSearch {

	private static String FILEPATH = "/Users/vamhan/Downloads/apache-tomcat-7.0.52/webapps/ROOT/irProject/SearchEngine/WebContent/files/";

	/** Creates a new instance of Main */
	public LuceneSearch() {
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		System.out.print("Input Query: ");
		Scanner in = new Scanner(System.in);
		search(in.nextLine());
		in.close();
	}

	public static JSONArray search(String keyword) {
		try {

			// configure index properties
			EnglishAnalyzer analyzer = new EnglishAnalyzer(Version.LUCENE_41);
			Directory indexDir = new RAMDirectory();

			// build a lucene index
			// System.out.println("---rebuildIndexes");
			Indexer indexer = new Indexer(indexDir, analyzer, FILEPATH);
			indexer.rebuildIndex();
			// System.out.println("---rebuildIndexes done");

			// perform searches and retrieve the results
			SearchEngine searcher = new SearchEngine(indexDir, analyzer);
			return searchTeam(searcher, keyword);
		} catch (Exception e) {
			System.out.println("Exception caught.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// public static void search (SearchEngine searcher, String query)
	// throws IOException, ParseException {
	// System.out.println("---Query: " + query);
	// ScoreDoc[] hits = searcher.performSearch(query);
	// System.out.println("---Results found: " + hits.length);
	// for(int i=0;i<hits.length;++i) {
	// int docId = hits[i].doc;
	// Document doc = searcher.getDoc(docId);
	// System.out.println(doc.get("name") + ", " + doc.get("city") +
	// " - score: " + hits[i].score);
	// }
	// System.out.println("---end of query results");
	// }

	public static JSONArray searchTeam(SearchEngine searcher, String query)
			throws IOException, ParseException, JSONException {
		System.out.println("---Query: " + query);
		ScoreDoc[] hits = searcher.performSearch(query);
		System.out.println("---Results found: " + hits.length);
		
		JSONArray docList = new JSONArray();
		
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document doc = searcher.getDoc(docId);
			System.out.println(doc.get("title") + "\n" + doc.get("description") + " - score: " + hits[i].score);

			JSONObject jo = new JSONObject();
			jo.put("title", doc.get("title"));
			jo.put("description", doc.get("description"));
			jo.put("path", doc.get("fullpath"));
			docList.put(jo);
		}
		System.out.println("---end of query results");
		return docList;
	}

}
