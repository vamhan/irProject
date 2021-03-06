/*
 * SearchEngine.java
 *
 * Created on 6 March 2006, by John Cho
 * Adapted on 27 February 2013, by Ver?nika Peralta
 *
 */

package controller.lucene.search;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;



public class SearchEngine {

    private IndexSearcher searcher = null;
    private QueryParser parser = null;
    int hitsPerPage = 10;
    
    /** Creates a new instance of SearchEngine */
    public SearchEngine(Directory indexDir, Analyzer analyzer) throws IOException {
        IndexReader reader = DirectoryReader.open(indexDir);
        searcher = new IndexSearcher(reader);
        parser = new QueryParser(Version.LUCENE_41, "content", analyzer);
    }
    
    /** Performs a search */
    public ScoreDoc[] performSearch(String queryString)
    throws IOException, ParseException {
        Query query = parser.parse(queryString);        
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
    	searcher.search(query, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;
        return hits;
    }
    
    /** Gets a document given its id */
    public Document getDoc (int docId)
        throws IOException {
        Document doc = searcher.doc(docId);
        return doc;
    }

}
