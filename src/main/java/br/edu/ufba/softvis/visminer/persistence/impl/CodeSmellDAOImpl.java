package br.edu.ufba.softvis.visminer.persistence.impl;

import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import br.edu.ufba.softvis.visminer.model.CodeSmell;
import br.edu.ufba.softvis.visminer.persistence.Database;
import br.edu.ufba.softvis.visminer.persistence.dao.CodeSmellDAO;

public class CodeSmellDAOImpl implements CodeSmellDAO {

	@Override
	public List<CodeSmell> findAll() {

		MongoCollection<Document> coll =  Database.getInstance().getDbCollection("types");
		
		//{ "antipatterns.value": true}
		FindIterable<Document> iterable = coll.find(new Document("codesmells.value", true));
		
		iterable.forEach(new Block<Document>() {
			
			@Override
			public void apply(Document doc) {
				System.out.println(doc);
			}
		});
		
		return null;
	}
	
	
	

}
