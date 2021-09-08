package by.epamtc.iovchuk.servlet;

import by.epamtc.iovchuk.parser.SAX.DeviceHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXParserServlet extends XMLParserServlet {

    private static final long serialVersionUID = 222990763842377510L;

    private static final String XML_FILES_LOCATION =
            "M:/intellijIDEA_Projects/epam-course-project-5/web/resources/xml/";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        parseXML();

        String path = "/WEB-INF/parse_result/sax-parse-result.jsp";
        forward(request, response, path);
    }

    //ИСКЛЮЧЕНИЯ КИДАТЬ В ЛОГГЕР
    private void parseXML() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(new DeviceHandler());
            // reader.setErrorHandler(new CustomErrorHandler());
            reader.parse(XML_FILES_LOCATION + "computer_devices.xml");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}