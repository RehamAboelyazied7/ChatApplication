package com.iti.chat.util;

import com.iti.chat.model.ChatRoom;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XMLUtil {

    public static void saveRoomToXML(ChatRoom chatRoom, File file) {

        JAXBContext jaxbContext = null;

        try {

            //create transformer factory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            //make a stream source to make make an xslt transformer
            StreamSource xslt = new StreamSource(XMLUtil.class.getResource("/xml/Chat Session.xsl").toString());

            Transformer transformer = transformerFactory.newTransformer(xslt);

            // Source XML for the transformer
            jaxbContext = JAXBContext.newInstance(ChatRoom.class);
            JAXBSource source = new JAXBSource(jaxbContext, chatRoom);

            // Result
            StreamResult sink = new StreamResult(file);

            transformer.transform(source , sink);


        } catch (JAXBException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
