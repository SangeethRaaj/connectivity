package mysql_remote_server;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Conn1 {

	public static void main(String[] args) throws Exception {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://sangeethraaj.com:3306/sangeethraaj_am","sangeethraaj_amu","arunkumar");  
			
			ArrayList<String> tableList = new ArrayList<String>();
			
//			tableList.add("userlist");
//			tableList.add("Studentslist");
//			tableList.add("slotslist");
			tableList.add("attendancelist");
//			tableList.add("classlist");
			
			
			ArrayList<String> FileList = new ArrayList<String>();
			
//			FileList.add("C:\\Users\\sange\\Desktop\\CodemMonger\\NCP\\Project\\JDBC\\Connectivity\\src\\xmls\\user\\userlist.xml");
//			FileList.add("C:\\Users\\sange\\Desktop\\CodemMonger\\NCP\\Project\\JDBC\\Connectivity\\src\\xmls\\student\\Studentslist.xml");
//			FileList.add("C:\\Users\\sange\\Desktop\\CodemMonger\\NCP\\Project\\JDBC\\Connectivity\\src\\xmls\\slots\\Slotslist.xml");
			FileList.add("C:\\Users\\sange\\Desktop\\CodemMonger\\NCP\\Project\\JDBC\\Connectivity\\src\\xmls\\attendance\\attendancelist.xml");
//			FileList.add("C:\\Users\\sange\\Desktop\\CodemMonger\\NCP\\Project\\JDBC\\Connectivity\\src\\xmls\\class\\classlist.xml");
			
			Statement stmt=con.createStatement();  
			ResultSet rs;
			
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			
			for(int i = 0;i<tableList.size();i++) {
				String t = tableList.get(i);
				String f = FileList.get(i);
				
				ArrayList<String> h = new ArrayList<String>();
				rs = stmt.executeQuery("select * from `" + t + "`");
				for(int j=1;j<=rs.getMetaData().getColumnCount();j++) {
					h.add(rs.getMetaData().getColumnName(j));
				}
				
				Document doc = docBuilder.parse(new File(f));
				doc.getDocumentElement().normalize();
				System.out.println("Reading from the " + doc.getDocumentElement().getNodeName() + " xml and writing to the table `"+t+"`");
				
				NodeList listt =doc.getDocumentElement().getChildNodes();
				
				for (int s = 0; s < listt.getLength(); s++) {
				       Node n = listt.item(s);
				       if (n.getNodeType() == Node.ELEMENT_NODE) {
				    	   
				    	   Element d = (Element) n, dd;
				    	   NodeList dl, dl1;
				    	   ArrayList<String> dt = new ArrayList<String>();
				    	   for(String st : h) {
				    		    dl = d.getElementsByTagName(st);
					    	    dd = (Element) dl.item(0);
					    	    dl1 = dd.getChildNodes();
					    	   if(st.contains("Time")) {
					    		   dt.add(((Node) dl1.item(0)).getNodeValue().trim().replace('T', ' '));
					    	   }
					    	   else
					    		   dt.add(((Node) dl1.item(0)).getNodeValue().trim());
				    	   }
				    	   
				    	   String qry = "INSERT INTO "+t+"(";
				    	   for(String st : h) {
				    		   qry+= "`" + st + "`,";
				    	   }
				    	   qry = qry.substring(0, qry.length()-1) + ')';
				    	   
				    	   qry += " VALUES(";
				    	   for(String st : dt) {
				    		   qry+= "'" + st + "',";
				    	   }
				    	   qry = qry.substring(0, qry.length()-1) + ')';
				    	   stmt.executeUpdate(qry);
				    	   System.out.println("Successfully executed '"+qry+"'");
				       }
				}
				
			}
			
			System.out.println("----------- After insertion -----------");
			
			for(int i = 0;i<tableList.size();i++) {
				String t = tableList.get(i);
				ArrayList<String> h = new ArrayList<String>();
				rs = stmt.executeQuery("select * from `" + t + "`");
				for(int j=1;j<=rs.getMetaData().getColumnCount();j++) {
					h.add(rs.getMetaData().getColumnLabel(j));
				}
				System.out.println("\n-------- Table : "+t+" -----------");
				while(rs.next()) {
					for(String ss : h) {
						System.out.print(ss + " : " + rs.getString(ss)+"\t| ");
					}
					System.out.println();
				}
				
			}
			
			con.close();   
	}

}
