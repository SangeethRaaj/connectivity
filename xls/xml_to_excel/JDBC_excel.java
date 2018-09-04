package xml_to_excel;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class JDBC_excel {
	
	 public static void main(String[] args) throws Exception {
	   Fillo fillo = new Fillo();
	   com.codoid.products.fillo.Connection connection = fillo.getConnection("C:\\Users\\sange\\Desktop\\CodemMonger\\NCP\\Project\\JDBC\\Connectivity\\src\\DB.xls");
	   String strQuery = "Select * from Sheet1";
	   
	   Recordset recordset = connection.executeQuery(strQuery);
	   System.out.println(recordset.getFieldNames());
	   
	   DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	   DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	   
	   Document doc = docBuilder.parse(new File("C:\\Users\\sange\\Desktop\\CodemMonger\\NCP\\Project\\JDBC\\Connectivity\\src\\xmls\\attd.xml"));
	   doc.getDocumentElement().normalize(); 
	   System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName()); 
	   
	   NodeList listt = doc.getElementsByTagName("attd_stat");
	   for (int s = 0; s < listt.getLength(); s++) {
	       Node n = listt.item(s);
	       if (n.getNodeType() == Node.ELEMENT_NODE) {
	    	   Element d = (Element) n;
	    	   
	    	   NodeList dl = d.getElementsByTagName("date");
	    	   Element dd = (Element) dl.item(0);
	    	   NodeList dl1 = dd.getChildNodes();
	    	   String date = ((Node) dl1.item(0)).getNodeValue().trim();
	    	   
	    	   dl = d.getElementsByTagName("hour");
	    	   dd = (Element) dl.item(0);
	    	   dl1 = dd.getChildNodes();
	    	   String hour = ((Node) dl1.item(0)).getNodeValue().trim();
	    	   
	    	   dl = d.getElementsByTagName("rollno");
	    	   dd = (Element) dl.item(0);
	    	   dl1 = dd.getChildNodes();
	    	   String rollno = ((Node) dl1.item(0)).getNodeValue().trim();
	    	   
	    	   dl = d.getElementsByTagName("status");
	    	    dd = (Element) dl.item(0);
	    	    dl1 = dd.getChildNodes();
	    	   String status = ((Node) dl1.item(0)).getNodeValue().trim();
	    	   connection.executeUpdate("insert into Sheet1(`date`,`hour`,`rollno`,`status`) values('"+date+"','"+hour+"','"+rollno+"','"+status+"')");
//	    	   int i = connection.executeUpdate("INSERT into `Sheet1` () VALUES('today','2','a','y')");
	    	   //int i = connection.executeUpdate("INSERT INTO Sheet1(date,hour,rollno,status) VALUES('"+date+"','"+hour+"','"+rollno+"','"+status+"')");
	       }
	   }
	   while (recordset.next()) {
	        System.out.println("Date: " + recordset.getField("date") + " Hour: "+recordset.getField("hour")+ " Rollno: "+recordset.getField("rollno")+ " Status: "+recordset.getField("status"));
	   }
	   recordset.close();
	   connection.close();
	}
}