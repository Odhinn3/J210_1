package parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.*;
import models.Clients;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import repositories.ClientService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import models.Adresses;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import repositories.AdressService;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
public class DOMParser {
    
    private final File file;
    private final ClientService service;
    private final AdressService adrservice;


    public DOMParser(ClientService service, AdressService adrservice, File file) {
        this.service = service;
        this.adrservice = adrservice;
        this.file = file;       
    }
 
    public void createFile(List<Clients> clients){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element rootElement = document.createElement("clients");
            document.appendChild(rootElement);
            clients.forEach(client -> {
                Element clientElement = document.createElement("client");
                rootElement.appendChild(clientElement);
                String id = client.getClientid().toString();
                clientElement.setAttribute("id", id);
                clientElement.setAttribute("clientname", client.getClientname());
                clientElement.setAttribute("clienttype", client.getClienttype());
                String date = Long.toString(client.getRegdate().getTime());
                clientElement.setAttribute("regdate", date);
                List<Adresses> list = client.getAdressesList();
                if(!list.isEmpty()){
                    list.forEach(adress ->{
                        Element adressElement = document.createElement("adress");
                        clientElement.appendChild(adressElement);
                        adressElement.setAttribute("adressid", adress.getAdressid().toString());
                        adressElement.setAttribute("clientid", id);
                        adressElement.setAttribute("ip", adress.getIp());
                        adressElement.setAttribute("mac", adress.getMac());
                        adressElement.setAttribute("model", adress.getModel());
                        adressElement.setAttribute("loc", adress.getLocationadress());
                    });
                } 
            });
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource dOMSource = new DOMSource(document);
            
            StreamResult result = new StreamResult(file);
            
            transformer.transform(dOMSource, result);   
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Clients> readXml(){
        List<Clients> clientslist = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            NodeList clientsnodelist = document.getElementsByTagName("client");
            for(int i=0; i<clientsnodelist.getLength(); i++){
                Node clientnode = clientsnodelist.item(i);
                NamedNodeMap clientmap = clientnode.getAttributes();
                String idclientstring = clientmap.getNamedItem("id").getNodeValue();
                if(idclientstring.matches("[\\d]+")){
                    int clientid = Integer.parseInt(idclientstring);
                    Clients client = service.findById(clientid);
                    if(client==null){
                        client = new Clients();
                        client.setClientid(clientid);
                        System.out.println("new client id = " + clientid);
                    }
                    client.setClientname(clientmap.getNamedItem("clientname").getNodeValue());
                    client.setClienttype(clientmap.getNamedItem("clienttype").getNodeValue());
                    long l = Long.parseLong(clientmap.getNamedItem("regdate").getNodeValue());
                    Date date = new Date(l);
                    client.setRegdate(date);
                    System.out.println("new client from xml: " + clientid + ", " + client.getClientname());
                    List<Adresses> adresses = new ArrayList<>();
                    if(clientnode.hasChildNodes()){
                        NodeList adrnodelist = document.getElementsByTagName("adress");
                        for(int j=0; j<adrnodelist.getLength(); j++){
                            Node nodeadr = adrnodelist.item(j);
                            NamedNodeMap mapadr = nodeadr.getAttributes();
                            String adridstring = mapadr.getNamedItem("adressid").getNodeValue();                       
                            if(adridstring.matches("[\\d]+")){
                                int adrid = Integer.parseInt(adridstring);
                                Adresses adress = adrservice.findAdressById(adrid);
                                if(adress == null){
                                    adress = new Adresses();
                                    adress.setAdressid(adrid);
                                    System.out.println("adrid = " + adress.getAdressid());
                                }
                                adress.setClientid(client);
                                System.out.println("adress " + adress.getAdressid() + " clientid = " + adress.getClientid().getClientid());
                                adress.setIp(mapadr.getNamedItem("ip").getNodeValue());
                                adress.setLocationadress(mapadr.getNamedItem("loc").getNodeValue());
                                adress.setMac(mapadr.getNamedItem("mac").getNodeValue());
                                adress.setModel(mapadr.getNamedItem("model").getNodeValue());
                                adrservice.saveAdress(adress);
                                adresses.add(adress);
                            }
                        }
                    }                   
                    client.setAdressesList(adresses);
                    service.saveClient(client);
                    System.out.println("client " + client.getClientid() + " successfully saved");
                    clientslist.add(client);
                }       
            }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(DOMParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(DOMParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DOMParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientslist;
    }
}
