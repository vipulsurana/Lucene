package pkg.updations;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import pkg.lucene.LuceneConstants;

public class SearchFromIndex {

	public static void main(String[] args) throws IOException, ParseException {
		
		// To store an index on disk, use this instead:
	    Directory d2 = FSDirectory.open(new File(LuceneConstants.INDEX_DIR));
	    
		DirectoryReader ireader = DirectoryReader.open(d2);
	    
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    
	    QueryParser parser = new QueryParser("firstname", new StandardAnalyzer());
	    Query queryParsing = parser.parse("Amy");
	    
	    //comment above 2 lines and uncomment below 2 lines, see the difference in Console score
	    //QueryParser parser = new QueryParser("userid", new StandardAnalyzer());
	    //Query queryParsing = parser.parse("2");
	    
	    //uncomment this line and see that it explains difference b/w default similarity and bm25similarity
	    isearcher.setSimilarity(new BM25Similarity());
	    
	    ScoreDoc[] hits = isearcher.search(queryParsing, null, 1000).scoreDocs;
	    System.out.println("Total hits: " + hits.length);
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = isearcher.doc(docId);
			System.out.println("UserId: " + d.get("userid") + ", Name: " +d.get("firstname") + " " + d.get("lastname"));
			System.out.println("Document ID: " + docId + ", Score: " + hits[i].score);
			//System.out.println("Explanation: " + isearcher.explain(queryParsing, docId));
		}
	}
}
