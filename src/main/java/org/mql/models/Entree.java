package org.mql.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Component;

@Component
public class Entree {

	private String id;
	private String name;
	private double price;
	private String status;
	private String section;
	private List<String> items;
	private String img;

	public Entree() {
		items = new ArrayList<>();
	}

	public Entree(String name, double price, String status, String section) {
		this();
		this.name = name;
		this.price = price;
		this.status = status;
		this.section = section;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public List<String> getItems() {
		return Collections.unmodifiableList(items);
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public void addItem(String item) {
		items.add(item);
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	@Override
	public String toString() {
		return "Entree [id=" + id + ", name=" + name + ", price=" + price + ", status=" + status + ", section="
				+ section + ", items=" + items + ", img=" + img + "]";
	}

}
