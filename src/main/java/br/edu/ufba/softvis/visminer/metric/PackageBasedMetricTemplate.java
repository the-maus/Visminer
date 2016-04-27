package br.edu.ufba.softvis.visminer.metric;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.PackageDeclaration;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;

public abstract class PackageBasedMetricTemplate implements IMetric {

	@Override
	public void calculate(TypeDeclaration type, AST ast, Document document) {}
	
	public abstract void calculate(PackageDeclaration packageDeclaration, Document document);

}