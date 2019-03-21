package com.example.android.finalproject.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TestParser {
    public static void parseXML(String xml) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));

            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("anime");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Anime Name : "
                            + eElement.getAttribute("name"));

                    NodeList infos = eElement.getElementsByTagName("info");
                    //System.out.println("infos length:" + infos.getLength());
                    for(int temp2 = 0; temp2 < infos.getLength(); temp2++){
                        //System.out.println("In For loop");
                        Element element2 = (Element) infos.item(temp2);
                        //System.out.println("Attribute" + element2.getAttribute("type"));
                        if(element2.getAttribute("type").equals("Picture")) {
                            System.out.println("Info : "
                                    + element2
                                    .getAttribute("src"));
                        }
                        if(element2.getAttribute("type").equals("Genres")) {
                            System.out.println("Info : "
                                    + infos
                                    .item(temp2)
                                    .getTextContent());
                        }
                        if(element2.getAttribute("type").equals("Plot Summary")) {
                            System.out.println("Info : "
                                    + infos
                                    .item(temp2)
                                    .getTextContent());
                        }
                        if(element2.getAttribute("type").equals("Vintage")) {
                            System.out.println("Info : "
                                    + infos
                                    .item(temp2)
                                    .getTextContent());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
