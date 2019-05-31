package com.beata;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

public class XmlGenerator {


    private FileOutputStream outputStream;


    public XmlGenerator() throws IOException {

        createXmlFile();
    }

    private void createXmlFile() throws IOException {

        File file = new File("out.xml");

        deleteFileIfExists(file);
        outputStream = new FileOutputStream(file, true);
     }

    private void deleteFileIfExists(File file) {

        if(file.exists()) {
            file.delete();
        }
    }

    public void createDocumentHeader() throws IOException {

         String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
         String openingTag = "<text>\n";
         outputStream.write(xmlHeader.getBytes());
         outputStream.write(openingTag.getBytes());
     }


     public void marshalSentence(Sentence sentence) throws JAXBException {

         Marshaller marshaller = createMarshaller();
         marshaller.marshal(sentence,outputStream );
     }

    private Marshaller createMarshaller() throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(Sentence.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }

    public void closeXmlDocument() throws IOException{

        String closingTag = "</text>\n";
        outputStream.write(closingTag.getBytes());
        outputStream.close();
    }

}


