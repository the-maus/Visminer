package br.edu.ufba.softvis.visminer.antipattern;

import java.util.List;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.annotations.AntiPatternAnnotation;
import br.edu.ufba.softvis.visminer.ast.AST;
import br.edu.ufba.softvis.visminer.ast.ClassOrInterfaceDeclaration;
import br.edu.ufba.softvis.visminer.ast.MethodDeclaration;
import br.edu.ufba.softvis.visminer.ast.TypeDeclaration;
import br.edu.ufba.softvis.visminer.constant.SoftwareUnitType;
import br.edu.ufba.softvis.visminer.metric.ATFDMetric;
import br.edu.ufba.softvis.visminer.metric.NOAMetric;
import br.edu.ufba.softvis.visminer.metric.TCCMetric;
import br.edu.ufba.softvis.visminer.metric.WMCMetric;

@AntiPatternAnnotation(name="God Class", 
					   description="A God Class is an object that controls way too many other objects in the system "+
						   			"and has grown beyond all logic to become The Class That Does Everything")
public class GodClass implements IAntiPattern{
	
	//TODO colocar limiares no construtor, ex: ATFDThreshold
	
	@Override
	public void detect(AST ast, Document doc) {
		if (ast.getDocument().getTypes() != null) {
			for (TypeDeclaration type : ast.getDocument().getTypes()) {
				ClassOrInterfaceDeclaration cls = null;
				if (type.getType() == SoftwareUnitType.CLASS_OR_INTERFACE) {
					cls = (ClassOrInterfaceDeclaration) type;
				} else {
					continue;
				}

				if (cls.getMethods() == null) {
					continue;
				}

				boolean godClass = detect(ast, cls.getMethods(), doc);
				
				doc.append("GodClass", new Boolean(godClass));
			}
		}
		
	}
	
	public boolean detect(AST ast, List<MethodDeclaration> methods, Document doc){
		boolean godClass = false;
		
		ATFDMetric atfdMetric = new ATFDMetric();
		WMCMetric wmcMetric = new WMCMetric();
		TCCMetric tccMetric = new TCCMetric();
		NOAMetric noaMetric = new NOAMetric();
		
		int atfd = atfdMetric.calculate(methods);
		int wmc = wmcMetric.calculate(methods);
		float tcc = tccMetric.calculate(methods);
		int noa = noaMetric.calculate(ast);
		
		godClass = atfd > 40 && wmc > 75 || (tcc < 0.2 && noa > 20); 
		
		return godClass;
	}
	

}
