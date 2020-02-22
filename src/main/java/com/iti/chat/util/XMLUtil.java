package com.iti.chat.util;

import com.iti.chat.model.ChatRoom;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XMLUtil {

    public static void saveRoomToXML(ChatRoom chatRoom , File file) {


        JAXBContext jaxbContext = null;

        try {

            jaxbContext = JAXBContext.newInstance(ChatRoom.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            jaxbMarshaller.marshal(chatRoom , file);


        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
