/*
 * Indexer.java
 *
 * Created on 6 March 2006, by John Cho
 * Adapted on 27 February 2013, by Ver?nika Peralta
 *
 */

package controller.lucene.search;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.apache.lucene.index.CorruptIndexException;

import controller.lucene.business.*;

import java.io.*;

public class Indexer {

	private Analyzer analyzer;
	private Directory indexDir;
	private IndexWriter indexWriter = null;
	private String filePath;
	private String fileContent; // temporary storer of all the text parsed from
								// doc and pdf

	/** Creates a new instance of Indexer */
	public Indexer(Directory indexDir, Analyzer analyzer, String filePath) {
		this.analyzer = analyzer;
		this.indexDir = indexDir;
		this.filePath = filePath;
	}

	/** Gets index writer */
	public IndexWriter getIndexWriter() throws IOException {
		if (indexWriter == null) {
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41,
					analyzer);
			indexWriter = new IndexWriter(indexDir, config);
		}
		return indexWriter;
	}

	/** Closes index writer */
	public void closeIndexWriter() throws IOException {
		if (indexWriter != null) {
			indexWriter.close();
		}
	}

	/** Deletes all documents in the index */
	public void deleteIndex() throws IOException {
		if (indexWriter != null) {
			indexWriter.deleteAll();
		}
	}

	/** Adds a document (a hotel) to the index */
	/*
	 * public void indexHotel(Hotel hotel) throws IOException {
	 * System.out.println("Indexing hotel: " + hotel); IndexWriter writer =
	 * getIndexWriter(); Document doc = new Document(); doc.add(new
	 * StoredField("id", hotel.getId())); doc.add(new TextField("name",
	 * hotel.getName(), Field.Store.YES)); doc.add(new StringField("city",
	 * hotel.getCity(), Field.Store.YES)); doc.add(new TextField("description",
	 * hotel.getDescription(), Field.Store.YES)); String fullSearchableText =
	 * hotel.getName() + " " + hotel.getCity() + " " + hotel.getDescription();
	 * doc.add(new TextField("content", fullSearchableText, Field.Store.NO));
	 * writer.addDocument(doc); }
	 */

	/** Adds a document (a team) to the index */
	/*
	 * public void indexTeam(String fileDirectory) throws IOException{
	 * 
	 * IndexWriter writer = getIndexWriter(); File dir = new
	 * File(fileDirectory); File[] files = dir.listFiles(); for (File file :
	 * files) { System.out.println("Indexing file: "+ file.getName()); Document
	 * document = new Document();
	 * 
	 * String path = file.getCanonicalPath(); document.add(new
	 * StringField("path", path, Field.Store.YES));
	 * 
	 * Reader reader = new FileReader(file); document.add(new
	 * TextField("content", reader));
	 * 
	 * writer.addDocument(document); }
	 * 
	 * }
	 */

	/** Rebuilds index */
	public void rebuildIndex() throws IOException {
		// Erase existing index
		deleteIndex();

		// Index all Team file
		indexFile();

		// Don't forget to close the index writer when done
		closeIndexWriter();
	}

	private void indexFile() {

		File dir = new File(filePath);
		System.out.print(dir.getAbsolutePath());
		File[] filesToIndex = dir.listFiles();
		for (File file : filesToIndex) {
			try {
				// to check whether the file is a readable file or not.
				if (!file.isDirectory() && !file.isHidden() && file.exists()
						&& file.canRead() && file.length() > 0.0
						&& file.isFile()) {
					if (file.getName().endsWith(".txt")) {
						indexTextFiles(file);// if the file text file no need to
												// parse text.
						// System.out.println("File Indexed: " +
						// file.getName());
					} else if (file.getName().endsWith(".doc")
							|| file.getName().endsWith(".pdf")) {
						// different method for indexing doc and pdf file.
						StartIndex(file);
					}
				}
			} catch (Exception e) {
				System.out.println("Sorry cannot index "
						+ file.getAbsolutePath());
			}
		}
	}

	private void indexTextFiles(File file) throws CorruptIndexException,
			IOException {
		IndexWriter writer = getIndexWriter();
		Document doc = new Document();
		doc.add(new TextField("content", new FileReader(file)));
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));

			int line = 0;
			for (String x = in.readLine(); x != null && line < 5; x = in.readLine()) {
				line++;
				switch (line) {
					case 1 : doc.add(new StringField("title", x, Field.Store.YES));break;
					case 4 : doc.add(new StringField("description", x, Field.Store.YES));break;
					default : break;
				}
			}
			in.close();
		} catch (IOException e) {
			System.out.println("File I/O error!");
		}
		doc.add(new StringField("filename", file.getName(), Field.Store.YES));
		doc.add(new StringField("fullpath", "/SearchEngine/files/"
				+ file.getName(), Field.Store.YES));
		if (doc != null) {
			writer.addDocument(doc);
		}
	}

	public void StartIndex(File file) throws FileNotFoundException,
			CorruptIndexException, IOException {
		fileContent = null;
		try {
			IndexWriter writer = getIndexWriter();
			Document doc = new Document();
			if (file.getName().endsWith(".doc")) {
				// call the doc file parser and get the content of doc file in
				// txt format
				fileContent = new DocFileParser().DocFileContentParser(file
						.getAbsolutePath());
			}
			if (file.getName().endsWith(".pdf")) {
				// call the pdf file parser and get the content of pdf file in
				// txt format
				fileContent = new PdfFileParser().PdfFileContentParser(file
						.getAbsolutePath());
				System.out.println(fileContent);
			}
			String[] lines = fileContent.split("\n");
			doc.add(new StringField("title", lines[0], Field.Store.YES));
			doc.add(new StringField("description", lines[3], Field.Store.YES));
			doc.add(new TextField("content", fileContent, Field.Store.YES));
			doc.add(new StringField("filename", file.getName(), Field.Store.YES));
			doc.add(new StringField("fullpath", "/SearchEngine/files/"
					+ file.getName(), Field.Store.YES));
			if (doc != null) {
				writer.addDocument(doc);
			}
			// System.out.println("File Indexed: " + file.getName());
		} catch (Exception e) {
			System.out.println("error in indexing" + (file.getAbsolutePath()));
		}
	}

}
