package org.mql.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Node;

public class Utilities {

	public static final String MENU_XML = System.getProperty("user.dir") + "/src/main/resources/db_xml/menu_db.xml";
	public static final String MENU_XML_XSD = System.getProperty("user.dir") + "/src/main/resources/db_xml/menu_db.xsd";
	public static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	public static final String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/";

	public static String getStringNodeValue(Node node) {
		return node.getFirstChild().getNodeValue();
	}

	public static double getDoubleNodeValue(Node node) {
		return Double.parseDouble(node.getFirstChild().getNodeValue());
	}

	public static int getIntNodeValue(Node node) {
		return Integer.parseInt(node.getFirstChild().getNodeValue());
	}

	public static int getIntNodeAttrValue(Node node, String attribute) {
		return Integer.parseInt(node.getAttributes().getNamedItem(attribute).getNodeValue());
	}

	public static String getStringNodeAttrValue(Node node, String attribute) {
		return node.getAttributes().getNamedItem(attribute).getNodeValue();
	}

	public static Double getDoubleNodeAttrValue(Node node, String attribute) {
		return Double.parseDouble(node.getAttributes().getNamedItem(attribute).getNodeValue());
	}

	public static String upload(String path, MultipartFile file) {
		String name = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(uploadDir + path, name);
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
	}

}
