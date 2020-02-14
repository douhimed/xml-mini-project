package org.mql.utilities;

public enum Query {

	BY_SECTION_NAME("//section[@name='%s']"), BY_ENTREE_ID("//entree[@id='%s']");
	
	private String query;
	
	private Query(String query) {
		this.query = query;
	}
	
	public String getQuery() {
		return query;
	}
	
}
