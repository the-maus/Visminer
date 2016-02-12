package br.edu.ufba.softvis.visminer.metric;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.annotations.MetricAnnotation;
import br.edu.ufba.softvis.visminer.ast.AST;

@MetricAnnotation(
		name = "Source Lines of Code",
		description = "Source lines of code (SLOC), also known as lines of code (LOC), is a software metric"
				+ " used to measure the size of a computer program by counting the number of lines in the text of"
				+ " the program's source code.",
				acronym = "SLOC")
public class SLOCMetric implements IMetric{

	private Pattern pattern;

	public SLOCMetric(){
		pattern = Pattern.compile("(\r\n)|(\r)|(\n)");
	}

	@Override
	public void calculate(AST ast, Document document){
		int sloc = calculate(ast.getSourceCode());
		
		document.append("SLOC", new Document("accumulated", new Integer(sloc)));
	}
	
	
	
	
	//private int count(String source){
	public int calculate(String source){
		if(source == null || source.length() == 0)
			return 0;

		Matcher m = pattern.matcher(source);

		int i = 0;

		while(m.find())
			i++;

		return i;
	}

}