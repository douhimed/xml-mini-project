package org.mql.dao;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.mql.utilities.Utilities;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Component
public class Parser {
	
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private StreamResult streamResult;
	private TransformerFactory transformaerFactory;
	private Transformer transformer;
	private XPathFactory xFactory;
	private XPath xPath;
	private XPathExpression exp;
	private Document document;

	public Document parser() {

		factory = DocumentBuilderFactory.newInstance();
		factory.setAttribute(Utilities.JAXP_SCHEMA_SOURCE, Utilities.MENU_XML_XSD);
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(Utilities.MENU_XML);
		} catch (Exception e) {
			System.err.println("went bad at DOMparser : " + e.getMessage());
		}

		xFactory = XPathFactory.newInstance();
		xPath = xFactory.newXPath();
		
		return document;

	}

	public void outputAsXML(Document document) {
		DOMSource domSource = new DOMSource(document);
		streamResult = new StreamResult(Utilities.MENU_XML);
		try {
			ctreateTransformer();
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			System.err.println("something went bad at transforming the file : " + e.getMessage());
		}
	}

	public void ctreateTransformer() throws Exception {
		transformaerFactory = TransformerFactory.newInstance();
		transformer = transformaerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	}
	
	public Element addElement(Node root, String elementName, String textValue) {
		Element childElement = document.createElement(elementName);
		childElement.setTextContent(textValue);
		if (root != null)
			root.appendChild(childElement);
		return childElement;
	}
	
	public Element getElement(String path) {
		Element element = null;
		try {
			exp = xPath.compile(path);
			element = (Element) exp.evaluate(document, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			System.err.println("went bad at xpath: " + e.getMessage());
		}
		return element;
	}


}
