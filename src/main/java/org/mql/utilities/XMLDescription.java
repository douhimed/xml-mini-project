package org.mql.utilities;

public enum XMLDescription {

	ID("id"), IMG("img"), ITEM("item"), ITEMS("items"),
	ENTREE("entree"), STATUS("status"), PRICE("price"), NAME("name"), SECTION("section"), MENU("menu");
	
	private String description;
	
	private XMLDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}

