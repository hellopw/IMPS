
package Encryption;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.interfaces.RSAPublicKey;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XmlImpl { 
    private static Document document;
 
    public void init() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }
 
    public void createXml(String fileName, String account, RSAPublicKey pbk) {
        Element root = this.document.createElement("User"); 
        this.document.appendChild(root); 
        Element UserInfo = this.document.createElement("UserInfo"); 
        Element Account = this.document.createElement("Account"); 
        Account.appendChild(this.document.createTextNode(account)); 
        UserInfo.appendChild(Account); 
        Element Publickey = this.document.createElement("Publickey"); 
        Publickey.appendChild(this.document.createTextNode(Base64.encode(pbk.getEncoded()))); 
        UserInfo.appendChild(Publickey); 
        root.appendChild(UserInfo); 
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.println("生成XML文件成功!");
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createXml_2(String fileName, String account,String pbk) {
        Element root = document.createElement("User"); 
        document.appendChild(root); 
        Element UserInfo = document.createElement("UserInfo"); 
        Element Account = document.createElement("Account"); 
        Account.appendChild(document.createTextNode(account)); 
        UserInfo.appendChild(Account); 
        Element Publickey = document.createElement("Publickey"); 
        Publickey.appendChild(document.createTextNode(pbk)); 
        UserInfo.appendChild(Publickey); 
        root.appendChild(UserInfo); 
        TransformerFactory tf = TransformerFactory.newInstance();
        try {
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
            System.out.println("生成XML文件成功!");
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (TransformerException e) {
            System.out.println(e.getMessage());
        }
    }
 
    public void parserXml(String fileName, String account, RSAPublicKey pbk) throws DOMException, Exception {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(fileName);
             
            NodeList UserInfos = document.getChildNodes();
            for (int i = 0; i < UserInfos.getLength(); i++) {
                Node UserInfo = UserInfos.item(i);
                NodeList UserInfoList = UserInfo.getChildNodes();
                for (int j = 0; j < UserInfoList.getLength(); j++) {
                	System.out.println(UserInfoList.item(j).getNodeName()
                            + ":" + UserInfoList.item(j).getTextContent());
                	if(UserInfoList.item(j).getNodeName().equals("Account"))
                		account =  UserInfoList.item(j).getTextContent();
                	else if(UserInfoList.item(j).getNodeName().equals("Publickey"))
                		pbk = RSAcrypt.loadPublicKeyByStr(UserInfoList.item(j).getTextContent());
                }
            }
            System.out.println("解析完毕");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    
    
    public static void main(String args[]){
        XmlImpl dd = new XmlImpl();
        String  filepath = "E:/tmp.xml";
        String account = "yyb";
        
        dd.init();
        dd.createXml_2(filepath,"123","123");    //创建xml
       // dd.parserXml(str);    //读取xml
    }


}
