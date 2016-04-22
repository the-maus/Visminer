package br.edu.ufba.softvis.visminer.antipattern;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.annotations.AntiPatternAnnotation;
import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.ClassOrInterfaceDeclaration;
import br.edu.ufba.softvis.visminer.ast.MethodDeclaration;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;
import br.edu.ufba.softvis.visminer.constant.SoftwareUnitType;
import br.edu.ufba.softvis.visminer.metric.SLOCMetric;
import br.edu.ufba.softvis.visminer.metric.TCCMetric;
import br.edu.ufba.softvis.visminer.metric.WMCMetric;

@AntiPatternAnnotation(name = "Brain Class", description = "A Brain Class is a class that tends to be complex and centralize the functionality of the system. "
						+ "Contrary to God Classes, Brain Classes do not use much data from foreign classes and are slightly more cohesive")
public class BrainClass implements IAntiPattern {

	@Override
	public void detect(TypeDeclaration type, AST ast, Document document) {
		if (type.getType() == SoftwareUnitType.CLASS_OR_INTERFACE) {
			ClassOrInterfaceDeclaration cls = (ClassOrInterfaceDeclaration) type;
		
			boolean brainClass = detect(ast, type, cls);
			document.append("name", new String("Brain Class")).append("value", new Boolean(brainClass));
		}
	}
	
	public boolean detect(AST ast, TypeDeclaration type, ClassOrInterfaceDeclaration cls){
		boolean brainClass  = false;
		
		WMCMetric wmcMetric = new WMCMetric();
		GodClass godClass = new GodClass();
		BrainMethod brainMethod = new BrainMethod();
		TCCMetric tccMetric = new TCCMetric();
		SLOCMetric slocMetric = new SLOCMetric();
		
		int wmc = wmcMetric.calculate(cls.getMethods());
		boolean isGodClass = godClass.detect(type, cls);
		int nbm = 0; //number of brain methods
		float tcc = tccMetric.calculate(type, cls.getMethods());
		int sloc = slocMetric.calculate(ast.getSourceCode());
	
		for(MethodDeclaration method : cls.getMethods()){
			if(brainMethod.detect(method, ast))
				nbm++;
		}
		
		brainClass = isGodClass && (wmc >=47 && tcc < 0.5) && ((nbm > 1 && sloc >= 197) || (nbm == 1 && sloc >= (2*197) && wmc >= (2*47) ));
		
		return brainClass;
	}
	

}
