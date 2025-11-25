package xml;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.*;

public class FormatXML {

    /**
     * Форматування готового XML-файлу зі статистикою
     * @param file
     * @throws Exception
     */
    public static void formatXmlFile(File file) throws Exception {
        String xml = new String(java.nio.file.Files.readAllBytes(file.toPath()));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        StreamSource source = new StreamSource(new StringReader(xml));
        StreamResult result = new StreamResult(file);

        transformer.transform(source, result);
    }
}

