package my.pan.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XML_Relsover {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, JDOMException {
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream in = XML_Relsover.class.getResourceAsStream("/my/pan/xml/test.xml");
		//xml_jdom(in);
//		Document doc = builder.parse(in);
//		Element e = doc.getDocumentElement();
//		System.out.println(e.getNodeName());
	}
	
	public static void xml_jdom(InputStream in,String parentNodeName,String name) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		org.jdom.Document doc = builder.build(in);
		org.jdom.Element root = doc.getRootElement();
		List<org.jdom.Element> childrens = root.getChildren();
		for(org.jdom.Element each:childrens){
			String thename = each.getName();
			System.out.println(thename);
			
		}
	}
}
