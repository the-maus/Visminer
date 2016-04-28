package br.edu.ufba.softvis.visminer.codesmell;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.annotations.CodeSmellAnnotation;
import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.ClassOrInterfaceDeclaration;
import br.edu.ufba.softvis.visminer.ast.MethodDeclaration;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;
import br.edu.ufba.softvis.visminer.constant.SoftwareUnitType;
import br.edu.ufba.softvis.visminer.metric.CCMetric;
import br.edu.ufba.softvis.visminer.metric.MLOCMetric;
import br.edu.ufba.softvis.visminer.metric.MaxNestingMetric;
import br.edu.ufba.softvis.visminer.metric.NOAVMetric;

@CodeSmellAnnotation(name = "Brain Method", description = "A Brain Method tends to centralize the functionality of a class.")
public class BrainMethod implements ICodeSmell {
	
	private List<Document> methodsDoc;
	private int mlocThreshold = 65;
	private float ccMlocThreshold = 0.24f;
	private int maxNestingThreshold = 5;
	private int noavThreshold = 5;
	
	public BrainMethod() {}
	
	public BrainMethod(int mlocThreshold, float ccMlocThreshold, int maxNestingThreshold, int noavThreshold) {
		this.mlocThreshold = mlocThreshold;
		this.ccMlocThreshold = ccMlocThreshold;
		this.maxNestingThreshold = maxNestingThreshold;
		this.noavThreshold = noavThreshold;
	}

	@Override
	public void detect(TypeDeclaration type, AST ast, Document document) {
		if (type.getType() == SoftwareUnitType.CLASS_OR_INTERFACE) {
			ClassOrInterfaceDeclaration cls = (ClassOrInterfaceDeclaration) type;
			
			methodsDoc = new ArrayList<Document>();

			for(MethodDeclaration method : cls.getMethods()){
				boolean brainMethod = detect(method, ast);
				methodsDoc.add(new Document("method", method.getName()).append("value", new Boolean(brainMethod)));
			}

			document.append("name", new String("Brain Method")).append("methods", methodsDoc);
		}
	}
	
	public boolean detect(MethodDeclaration method, AST ast){
		boolean brainMethod = false;
		
		MLOCMetric mlocMetric = new MLOCMetric();
		CCMetric ccMetric = new CCMetric();
		NOAVMetric noavMetric = new NOAVMetric();
		MaxNestingMetric maxNestingMetric = new MaxNestingMetric();
		
		int cc = ccMetric.calculate(method);
		int mloc = mlocMetric.calculate(method, ast);
		int noav = noavMetric.calculate(method);
		int maxNesting = maxNestingMetric.calculate(method);
		
		brainMethod = (mloc>mlocThreshold) && (cc/mloc >= ccMlocThreshold) && (maxNesting >= maxNestingThreshold && noav > noavThreshold);
		
		return brainMethod;
	}

}
