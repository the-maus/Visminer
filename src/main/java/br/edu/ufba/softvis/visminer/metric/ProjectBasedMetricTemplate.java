package br.edu.ufba.softvis.visminer.metric;

import java.util.List;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.PackageDeclaration;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;

public abstract class ProjectBasedMetricTemplate implements IMetric {

	@Override
	public void calculate(TypeDeclaration type, AST ast, Document document) {}
	
	public abstract void calculate(List<PackageDeclaration> packageDeclaration, Document document);

}