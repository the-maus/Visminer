package br.edu.ufba.softvis.visminer.ast;

import java.util.ArrayList;
import java.util.List;

public class PackageDeclaration {

	private int id;
	private String name;
	
	// auxiliary attributes
	private List<AST> astList = new ArrayList<AST>();
	private String commit;
	
	public PackageDeclaration() {}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	// getters and setters for auxiliary attributes
	
	public PackageDeclaration(String name, List<AST> astList, String commit) {
		this.name = name;
		this.astList = astList;
		this.commit = commit;
	}
	
	public List<AST> getAstList() {
		return astList;
	}

	public void setAstList(List<AST> astList) {
		this.astList = astList;
	}
	
	public String getCommit() {
		return commit;
	}
	
	public void setCommit(String commit) {
		this.commit = commit;
	}
	
	public void addAST(AST ast){
		astList.add(ast);
	}
	
}
