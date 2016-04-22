package br.edu.ufba.softvis.visminer.metric;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.annotations.MetricAnnotation;
import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.MethodDeclaration;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;

@MetricAnnotation(name = "Maximum Nesting Level", description = "This metric represents the maximum nesting level of control structures within"
					+ "a method", acronym = "MAXNESTING")
public class MaxNestingMetric extends MethodBasedMetricTemplate {
	
	private List<Document> methodsDoc;

	@Override
	public void calculate(TypeDeclaration type, List<MethodDeclaration> methods, AST ast, Document document) {
		
		methodsDoc = new ArrayList<Document>();
		
		for(MethodDeclaration method : methods){
			int maxNesting = calculate(method); 
			methodsDoc.add(new Document("method", method.getName()).append("value", new Integer(maxNesting)));
		}

		document.append("MAXNESTING", new Document("methods", methodsDoc));

	}
	
	public int calculate(MethodDeclaration method){
		return method.getMaxNesting(); //FIXME solve inconsistency of the values found
	}

}
