package org.mql.dao;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import org.mql.models.Entree;
import org.mql.utilities.Query;
import org.mql.utilities.Utilities;
import org.mql.utilities.XMLDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Repository
public class EntreeDAO<T> implements IDAO<Entree> {

	@Autowired
	private Parser parser;
	private Document document;

	private List<Entree> entrees;
	private Entree entree;
	private String path;

	public EntreeDAO() {
		entrees = new Vector<>();
	}

	public List<Entree> getAll() {
		cleanUp();
		document = parser.parser();
		NodeList entreesNodes = document.getElementsByTagName(XMLDescription.ENTREE.getDescription());
		uploadEntrees(entreesNodes);
		return Collections.unmodifiableList(entrees);
	}

	public void add(Entree entree) {
		document = parser.parser();
		path = String.format(Query.BY_SECTION_NAME.getQuery(), entree.getSection());
		Element entreeNode = parser.addElement(parser.getElement(path), XMLDescription.ENTREE.getDescription(), "");
		createEntree(entree, entreeNode);
		parser.outputAsXML(document);
	}

	public void delete(String entreeID) {
		document = parser.parser();
		path = String.format(Query.BY_ENTREE_ID.getQuery(), entreeID);
		Node childToRemove = parser.getElement(path);
		childToRemove.getParentNode().removeChild(childToRemove);
		parser.outputAsXML(document);
	}

	public void update(Entree entree) {

		document = parser.parser();
		path = String.format(Query.BY_ENTREE_ID.getQuery(), entree.getId());
		Node oldEntree = parser.getElement(path);
		if (entree.getSection().equals(
				Utilities.getStringNodeAttrValue(oldEntree.getParentNode(), XMLDescription.NAME.getDescription()))) {
			Element newEntree = parser.addElement(null, XMLDescription.ENTREE.getDescription(), "");
			createEntree(entree, newEntree);
			oldEntree.getParentNode().replaceChild(newEntree, oldEntree);
		} else {
			delete(entree.getId());
			add(entree);
		}

		parser.outputAsXML(document);

	}

	public Entree getById(String entreeID) {
		document = parser.parser();
		path = String.format(Query.BY_ENTREE_ID.getQuery(), entreeID);
		Element entreeNode = null;
		entreeNode = parser.getElement(path);
		if (entreeNode != null)
			uploadEntree(entreeNode);
		return entree;
	}

	public List<Entree> findBySection(String sectionName) {
		cleanUp();
		document = parser.parser();
		if (sectionName.equals(null) || sectionName.equals("")) {
			uploadEntrees(document.getElementsByTagName(XMLDescription.ENTREE.getDescription()));
		} else {
			path = String.format(Query.BY_SECTION_NAME.getQuery(), sectionName);
			Element section = null;
			section = parser.getElement(path);
			uploadEntrees(section.getElementsByTagName(XMLDescription.ENTREE.getDescription()));
		}
		return Collections.unmodifiableList(entrees);
	}

	private void uploadEntree(Node entreeNode) {
		entree = new Entree();
		entree.setName(Utilities.getStringNodeAttrValue(entreeNode, XMLDescription.NAME.getDescription()));
		entree.setPrice(Utilities.getDoubleNodeAttrValue(entreeNode, XMLDescription.PRICE.getDescription()));
		entree.setImg(Utilities.getStringNodeAttrValue(entreeNode, XMLDescription.IMG.getDescription()));
		entree.setSection(
				Utilities.getStringNodeAttrValue(entreeNode.getParentNode(), XMLDescription.NAME.getDescription()));
		entree.setId(Utilities.getStringNodeAttrValue(entreeNode, XMLDescription.ID.getDescription()));
		try {
			entree.setStatus(Utilities.getStringNodeAttrValue(entreeNode, XMLDescription.STATUS.getDescription()));
		} catch (Exception e) {
			entree.setStatus("");
		}
		NodeList items = entreeNode.getChildNodes();
		for (int j = 0; j < items.getLength(); j++) {
			if (items.item(j).getNodeName().equals(XMLDescription.ITEM.getDescription()))
				entree.addItem(Utilities.getStringNodeValue(items.item(j)));
		}
	}

	private void uploadEntrees(NodeList entreesNodes) {
		for (int i = 0; i < entreesNodes.getLength(); i++) {
			uploadEntree(entreesNodes.item(i));
			entrees.add(entree);
		}
	}

	private void createEntree(Entree entree, Element entreeNode) {
		entreeNode.setAttribute(XMLDescription.NAME.getDescription(), entree.getName());
		entreeNode.setAttribute(XMLDescription.PRICE.getDescription(), entree.getPrice() + "");
		entreeNode.setAttribute(XMLDescription.ID.getDescription(), UUID.randomUUID().toString());
		entreeNode.setAttribute(XMLDescription.IMG.getDescription(), entree.getImg());
		if (entree.getStatus() != null && !entree.getStatus().equals(""))
			entreeNode.setAttribute(XMLDescription.STATUS.getDescription(), "new");
		for (String item : entree.getItems()) {
			parser.addElement(entreeNode, XMLDescription.ITEM.getDescription(), item);
		}
	}

	private void cleanUp() {
		entrees.clear();
		entree = null;
	}

}
