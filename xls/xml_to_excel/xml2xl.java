package xml_to_excel;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class xml2xl {
	
	Fillo fillo;
	com.codoid.products.fillo.Connection connection;
	xml2xl(String xpath) throws FilloException{
		Fillo fillo = new Fillo();
		connection = fillo.getConnection(xpath);
		System.out.println();
	}
	
	boolean writeXML(String sheet, String xpath) throws Exception {
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	   
		Document doc = docBuilder.parse(new File(xpath));
		doc.getDocumentElement().normalize(); 
		
		System.out.println("Root element of the xml doc to be imported into excel is " + doc.getDocumentElement().getNodeName()); 
		System.out.println("The Sheet to be written into is "+sheet);
		return true;
	}

}
