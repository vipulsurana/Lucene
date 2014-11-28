package pkg.updations;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import pkg.lucene.LuceneConstants;

public class AddSingleDocument {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Begin");
		
	    // To store an index on disk, use this instead:
		Directory d2 = FSDirectory.open(new File(LuceneConstants.INDEX_DIR));
	    
	    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StandardAnalyzer());
	    IndexWriter iwriter = new IndexWriter(d2, config);
	    
	    Document doc = new Document();
		doc.add(new StringField("userid","100001", Store.YES));
		doc.add(new TextField("firstname","Vipul", Store.YES));
	    doc.add(new TextField("lastname","Surana", Store.YES));
	    iwriter.addDocument(doc);
	    
	    iwriter.commit();
	    iwriter.close();
	    System.out.println("Finish");
	}

}