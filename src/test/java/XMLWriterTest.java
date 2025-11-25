import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xml.XMLWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class XMLWriterTest {

    @Test
    @DisplayName("Створення XML")
    void createsXMLFile() {
        Map<String, Integer> stats = Map.of("pop", 10);

        XMLWriter.createXML("src/test/resources/xml/", stats, "genre");

        File file = new File("src/test/resources/xml/statistics_by_genre.xml");
        assertTrue(file.exists());

    }

    @Test
    @DisplayName("Перевірка структури XML")
    void xmlHasCorrectStructure() throws Exception {
        Map<String, Integer> stats = Map.of("rock", 5, "pop", 10);

        XMLWriter.createXML("src/test/resources/xml/", stats, "genre");

        File file = new File("src/test/resources/xml/statistics_by_genre.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        NodeList items = doc.getElementsByTagName("item");
        assertEquals(2, items.getLength());

        Node firstItem = items.item(0);
        Node valueNode = ((Element) firstItem).getElementsByTagName("value").item(0);
        Node countNode = ((Element) firstItem).getElementsByTagName("count").item(0);

        assertNotNull(valueNode.getTextContent());
        assertNotNull(countNode.getTextContent());
    }


    @Test
    @DisplayName("Обробка некоректного атрибуту")
    void nullAttributeTest() {
        Map<String, Integer> stats = Map.of("rock", 5);
        assertThrows(IllegalArgumentException.class, () -> XMLWriter.createXML("src/test/resources/xml/", stats, null));
    }

    @Test
    @DisplayName("Перевірка форматування")
    void xmlIsFormatted() throws Exception {
        Map<String, Integer> stats = Map.of("rock", 1);

        XMLWriter.createXML("src/test/resources/xml/", stats, "genre");

        File file = new File("src/test/resources/xml/statistics_by_genre.xml");
        String content = Files.readString(file.toPath());

        assertTrue(content.contains("\n"));
    }


}
