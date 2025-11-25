package xml;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class XMLWriter {

    /**
     * Створення повного XML-файлу сформованого за статистикою певного атрибуту
     * @param directory
     * @param stats
     * @param attribute
     */
    public static void createXML(String directory, Map<String, Integer> stats, String attribute){
        checkAttribute(attribute);

        String fileName = directory + "statistics_by_" + attribute + ".xml";
        try {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(new FileWriter(fileName));

            writer.writeStartDocument("1.0");
            writer.writeStartElement("statistics");

            for(Map.Entry<String, Integer> entry : stats.entrySet()){
                createItem(writer, entry);
            }

            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
            writer.close();

            FormatXML.formatXmlFile(new File(fileName));

        } catch (XMLStreamException e) {
            System.out.println("XMLStreamException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }


    /**
     * Формування окремого елементу статистики
     *
     * Map.Entry<"Taylor Swift", 52> entry  =>     <item>
     *                                                  <value>Taylor Swift</value>
     *                                                  <count>52</count>
     *                                             </item>
     *
     * @param writer
     * @param entry
     * @throws XMLStreamException
     */
    private static void createItem(XMLStreamWriter writer, Map.Entry<String, Integer> entry) throws XMLStreamException {

        writer.writeStartElement("item");

        writer.writeStartElement("value");
        writer.writeCharacters(entry.getKey());
        writer.writeEndElement();

        writer.writeStartElement("count");
        writer.writeCharacters(entry.getValue().toString());
        writer.writeEndElement();

        writer.writeEndElement();
    }


    private static void checkAttribute(String attribute){
        if (attribute == null || attribute.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

}
