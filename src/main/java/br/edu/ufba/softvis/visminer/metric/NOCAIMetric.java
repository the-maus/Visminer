package br.edu.ufba.softvis.visminer.metric;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.annotations.MetricAnnotation;
import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.PackageDeclaration;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;
import br.edu.ufba.softvis.visminer.constant.SoftwareUnitType;

@MetricAnnotation(name = "Number of Classes and Interfaces", description = "Number of Classes and Interfaces is a software metric used to measure "
				  + "the size of a computer program  by counting the concrete and abstract classes", acronym = "NOCAI")
public class NOCAIMetric extends PackageBasedMetricTemplate {
	
	@Override
	public void calculate(PackageDeclaration packageDeclaration, Document document) {
		int nocai = calculate(packageDeclaration);
		document.append("NOCAI", new Document("value", new Integer(nocai)));
		
	}
	
	public int calculate(PackageDeclaration packageDeclaration){
		int nocai = 0;
		for (AST ast : packageDeclaration.getAstList()) {
			for(TypeDeclaration type : ast.getDocument().getTypes()){
				if(SoftwareUnitType.CLASS_OR_INTERFACE.equals(type.getType()))
					nocai += ast.getDocument().getTypes().size();
			}
		}
		
		return nocai;
	}

}