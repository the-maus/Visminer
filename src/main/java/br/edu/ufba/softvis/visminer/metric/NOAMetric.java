package br.edu.ufba.softvis.visminer.metric;

import java.util.List;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.annotations.MetricAnnotation;
import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.ClassOrInterfaceDeclaration;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;
import br.edu.ufba.softvis.visminer.constant.SoftwareUnitType;

@MetricAnnotation(
		name = "Number of Attributes",
		description = "The Number Of Attributes metric is used to count the average number of attributes for a class in the model.",
		acronym = "NOA")
public class NOAMetric implements IMetric {

	@Override
	public void calculate(AST ast, Document document) {

		int noa = calculate(ast);
		
		document.append("NOA", new Document("accumulated", new Integer(noa)));
		
	}
	
	public int calculate(AST ast){
		List<TypeDeclaration> tipos = ast.getDocument().getTypes();
		
		int noa = 0;
		ClassOrInterfaceDeclaration classe = null;
		
		for(TypeDeclaration tipo : tipos){
			if(SoftwareUnitType.CLASS_OR_INTERFACE.equals(tipo.getType())){
				classe =  (ClassOrInterfaceDeclaration) tipo;
				noa += classe.getFields()!=null ? classe.getFields().size() : 0;
			}
		}
		
		return noa;
	}

}
