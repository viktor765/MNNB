package message;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Message {
    private final Color color;
    private final String text;
    private final String sender;
    private boolean disconnect;
    
    public String getText() {
        return text;
    }
    
    public String getSender() {
        return sender;
    }
    
    public Color getColor() {
        return color;
    }

    public boolean isDisconnect() {
        return disconnect;
    }
    
    public String toXML() {
        if(disconnect) {
            return "<message sender=\"" + sender + "\"><disconnect/></message>";
        }

        return "<message sender=\"" + sender + "\">"
            + "<text color=\"" + color.getRGB() + "\">" 
            + text.replaceAll(">", "&gt;")
                  .replaceAll("<", "&lt;")
            + "</text></message>";
    }
    
    public Message(String text, String sender, Color color) {
        this.text = text.replaceAll("<\\/?(kursiv|fetstil).*?>", "")
                        .replaceAll("<(?!(message|text)).*?>"//clears all incompatible tags in text
                            + ".*" 
                            + "<(?!(\\/(message|text))).*?>", "")
                        .replaceAll("&gt;", ">").replaceAll("&lt;", "<");
        this.sender = sender;
        this.color = color;
    }
    
    public Message(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        //File file = new File("test.xml");
        builder.setErrorHandler(null);
        
        xml = xml.replaceAll("<\\/?(kursiv|fetstil).*?>", "")
                 .replaceAll("<(?!(message|text|disconnect)).*?>"//clears all incompatible tags
                            + ".*" 
                            + "<(?!(\\/(message|text|disconnect))).*?>", "");
        
        Document doc = builder.parse(new ByteArrayInputStream(
                xml.getBytes(StandardCharsets.UTF_8.name())));

        this.sender = doc.getDocumentElement().getAttribute("sender");

        if(doc.getDocumentElement().getElementsByTagName("disconnect").getLength() != 0) {
            disconnect = true;
            text = "";
            color = Color.GRAY;
            return;
        }

        disconnect = false;

        this.text = doc.getDocumentElement().getTextContent()
                .replaceAll("&gt;", ">").replaceAll("&lt;", "<");

        this.color = new Color(Integer.parseInt(
                doc.getElementsByTagName("text").item(0)
                .getAttributes().getNamedItem("color")
                .getTextContent()));
    }

    @Override
    public String toString() {
        return "message.Message{" +
                "color=" + color +
                ", text='" + text + '\'' +
                ", sender='" + sender + '\'' +
                ", disconnect=" + disconnect +
                '}';
    }
}
