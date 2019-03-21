package com.example.android.finalproject.utils;

import com.example.android.finalproject.data.SingleSearchResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class AnimeXMLParser {
    public static ArrayList<SingleSearchResult> parseXML(String xml) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));

            Document doc = dBuilder.parse(is);

            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("anime");     //NodeList of anime tags. Different search results
            //System.out.println("----------------------------");
            ArrayList<SingleSearchResult> allResults = new ArrayList<>();
            String seriesName = "";
            String imageURL = "";
            String genres = "";
            String summary = "";
            String airDate = "";

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);              //Single node for single anime tag, a single search result
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                seriesName = "";
                imageURL = "";
                genres = "";
                summary = "";
                airDate = "";
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;     //Single anime tag, as an Element
                    //System.out.println("Anime Name : "+ eElement.getAttribute("name"));       //String for name
                    seriesName = eElement.getAttribute("name");         //Set name

                    NodeList infos = eElement.getElementsByTagName("info");         //NodeList of the info tags
                    //System.out.println("infos length:" + infos.getLength());
                    for(int temp2 = 0; temp2 < infos.getLength(); temp2++){
                        //System.out.println("In For loop");
                        Element infoInstance = (Element) infos.item(temp2);         //Single "instance" of the info tag
                        //System.out.println("Attribute" + infoInstance.getAttribute("type"));
                        if(infoInstance.getAttribute("type").equals("Picture")) {
                            //System.out.println(infoInstance.getAttribute("src"));      //String for image link
                            imageURL = infoInstance.getAttribute("src");        //Set image URL
                        }
                        if(infoInstance.getAttribute("type").equals("Genres")) {
                            //System.out.println(infos.item(temp2).getTextContent());                 //String for genres
                            if(genres.equals("")){
                                genres = infos.item(temp2).getTextContent();
                            }
                            else{       //Append to string if it wasn't empty
                                genres = genres + ", " + infos.item(temp2).getTextContent();
                            }
                        }
                        if(infoInstance.getAttribute("type").equals("Plot Summary")) {
                            //System.out.println(infos.item(temp2).getTextContent());                 //String for plot summary
                            summary = infos.item(temp2).getTextContent();
                        }
                        if(infoInstance.getAttribute("type").equals("Vintage")) {
                            //System.out.println(infos.item(temp2).getTextContent());                 //Date aired
                            if(airDate.equals("")) {            //Use first air date
                                airDate = infos.item(temp2).getTextContent();
                            }
                        }
                    }   //Note - end of info for loop
                }
                SingleSearchResult searchResult = new SingleSearchResult();
                searchResult.name = seriesName;
                searchResult.image = imageURL;
                searchResult.genres = genres;
                searchResult.summary = summary;
                searchResult.airDate = airDate;
                allResults.add(searchResult);
            }   //Note - end of anime for loop
            System.out.println(allResults);
            return allResults;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
