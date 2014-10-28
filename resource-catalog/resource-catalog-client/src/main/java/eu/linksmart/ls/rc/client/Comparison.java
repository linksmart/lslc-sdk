package eu.linksmart.ls.rc.client;

public enum Comparison {
	
	EQUALS("equals"), 
	
	PREFIX("prefix"), 
	
	SUFFIX("suffix"), 
	
	CONTAINS("contains");
	
	private String criteria;
	 
	private Comparison(String criteria) {
		this.criteria = criteria;
	}
 
	public String getCriteria() {
		return criteria;
	}

}
