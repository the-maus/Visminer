package br.edu.ufba.softvis.visminer.codesmell;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;

public interface ICodeSmell {
	
	public void detect(TypeDeclaration type, AST ast, Document document);
}
