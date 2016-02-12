package br.edu.ufba.softvis.visminer.antipattern;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.ast.AST;

public interface IAntiPattern {

	
	public void detect(AST ast, Document doc);
}
