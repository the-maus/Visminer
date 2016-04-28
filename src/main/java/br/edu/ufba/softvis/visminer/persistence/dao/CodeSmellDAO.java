package br.edu.ufba.softvis.visminer.persistence.dao;

import java.util.List;

import br.edu.ufba.softvis.visminer.model.CodeSmell;

public interface CodeSmellDAO {
	
	public List<CodeSmell> findAll();

}
