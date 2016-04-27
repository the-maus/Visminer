package br.edu.ufba.softvis.visminer.metric;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.annotations.MetricAnnotation;
import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.PackageDeclaration;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;
import br.edu.ufba.softvis.visminer.constant.SoftwareUnitType;

@MetricAnnotation(name = "Number of Classes and Interfaces", description = "Number of Classes and Interfaces is a software metric used to measure "
				  + "the size of a computer program  by counting the concrete and abstract classes", acronym = "NOCAI")
public class NOCAIMetric extends ProjectBasedMetricTemplate {
	
	private List<Document> packagesDoc;
	
	@Override
	public void calculate(List<PackageDeclaration> packages, Document document) {
		
		packagesDoc = new ArrayList<Document>();
		
		int nocai = calculate(packages);
		document.append("NOCAI", new Document("value", new Integer(nocai)).append("packages", packagesDoc));
		
	}
	
	public int calculate(List<PackageDeclaration> packages){
		
		int nocai = 0;
		for(PackageDeclaration packageDeclaration : packages){
			nocai += calculate(packageDeclaration);
		}
		
		return nocai;
	}
	
	public int calculate(PackageDeclaration packageDeclaration){
		int nocai = 0;
		for (AST ast : packageDeclaration.getAstList()) {
			for(TypeDeclaration type : ast.getDocument().getTypes()){
				if(SoftwareUnitType.CLASS_OR_INTERFACE.equals(type.getType()))
					nocai += ast.getDocument().getTypes().size();
			}
		}
		
		packagesDoc.add(new Document("package", packageDeclaration.getName()).append("value", new Integer(nocai)));
		
		return nocai;
	}

}