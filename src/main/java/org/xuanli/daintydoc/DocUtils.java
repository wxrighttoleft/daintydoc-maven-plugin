package org.xuanli.daintydoc;

import com.sun.org.apache.xerces.internal.dom.DocumentTypeImpl;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import javax.swing.text.html.HTMLDocument;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DocUtils {
    private String outputDirection;

    public DocUtils(String outputDirection) {
        this.outputDirection = outputDirection;
    }


    public void out(DocPage page) throws IOException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        File outDir = new File(outputDirection);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File file = new File(outDir.getPath() + "/" + page.getFileName() + ".html");
        file.createNewFile();
        OutputStream outs  = new FileOutputStream(file);
        transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        DOMSource source = new DOMSource();
        source.setNode(createDocument(page));
        StreamResult result = new StreamResult();
        result.setOutputStream(outs);
        transformer.transform(source, result);
        outs.flush();
    }

    private Document createDocument(DocPage page) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = builder.newDocument();
        Element titleElement = document.createElement("h4");
        titleElement.setTextContent(page.getName());
        document.appendChild(titleElement);
        return document;
    }

    public static void main(String[] args) {
        try {
            DocPage page = new DocPage();
            page.setFileName("test");
            page.setName("测试");
            new DocUtils("/home/java-h5/temp/").out(page);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
