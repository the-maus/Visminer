package br.edu.ufba.softvis.visminer.metric;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.annotations.MetricAnnotation;
import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.MethodDeclaration;
import br.edu.ufba.softvis.visminer.ast.Statement;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;

@MetricAnnotation(name = "Method Lines of Code", description = "Method Lines of Code is a software metric used to indicate "
		+ "the number of lines inside method bodies", acronym = "MLOC")
public class MLOCMetric extends MethodBasedMetricTemplate {
	
	private List<Document> methodsDoc;

	@Override
	public void calculate(TypeDeclaration type, List<MethodDeclaration> methods, AST ast, Document document) {
		
		methodsDoc = new ArrayList<Document>();
		
		for(MethodDeclaration method : methods){
			int mloc = calculate(method);
			methodsDoc.add(new Document("method", method.getName()).append("value", new Integer(mloc)));
		}
		
		document.append("MLOC", new Document("methods", methodsDoc));
	}
	
	
	public int calculate(MethodDeclaration method){
		List<Statement> statements = method.getStatements();
		
		return statements.size(); //TODO review the calculation
	}

}
