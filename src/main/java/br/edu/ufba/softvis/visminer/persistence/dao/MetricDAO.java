package br.edu.ufba.softvis.visminer.persistence.dao;

import java.util.List;

import org.bson.Document;

import br.edu.ufba.softvis.visminer.metric.IMetric;

public interface MetricDAO {

	public void save(Document document);
	
	public void saveProjectMetric(Document document);
	
	public List<IMetric> findByFile(String uid);
	
}
