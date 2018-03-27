package com.cinquepalmi.java.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class TestXML {

	public static void main(String[] args) {
		System.out.println(TestXML.validateXMLSchema("src/main/resources/xsd/ExchangeRates.xsd", "src/main/resources/xml/xml.xml"));
	}

	public static boolean validateXMLSchema(String xsdPath, String xmlPath){

		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdPath));
			Validator validator = schema.newValidator();
			
			Path path = Paths.get(xmlPath);
			byte[] array = Files.readAllBytes(path);
			System.out.println("Lo zio");
			ByteArrayInputStream input = new ByteArrayInputStream(array);
			validator.validate(new StreamSource(input));
		} catch (IOException | SAXException e) {
			System.out.println("Exception: "+e.getMessage());
			return false;
		}
		return true;
	}

}
