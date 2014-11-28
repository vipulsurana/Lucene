package pkg.updations;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import pkg.lucene.LuceneConstants;

public class DisplayAllUsersScore {

	public static void main(String[] args) throws IOException, ParseException {
		
	    Directory d2 = FSDirectory.open(new File(LuceneConstants.INDEX_DIR));	    
		DirectoryReader ireader = DirectoryReader.open(d2);	    
	    IndexSearcher isearcher = new IndexSearcher(ireader);
	    //MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[] {"firstname", "lastname"}, new StandardAnalyzer());
	    Query queryParsing = MultiFieldQueryParser.parse(new String[] {"Amy-Ivy", "Achziger"}, new String[] {"firstname", "lastname"},new StandardAnalyzer());
	    ScoreDoc[] hits = isearcher.search(queryParsing, null, 1000).scoreDocs;
	    
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = isearcher.doc(docId);
			System.out.println("UserId: " + d.get("userid") + ", Name: " +d.get("firstname") + " " + d.get("lastname"));
			System.out.println("Document ID: " + docId + ", Score: " + hits[i].score);
			//System.out.println("Explanation: " + isearcher.explain(queryParsing, docId));
		}
	}
}
