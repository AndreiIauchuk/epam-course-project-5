package by.epamtc.iovchuk.servlet;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.parser.DeviceParserBuilder;
import by.epamtc.iovchuk.parser.StAX.DeviceStAXParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class StAXParserServlet extends XMLParserServlet {

    private static final long serialVersionUID = -7002933372621067362L;

    private static final String DEVICES_XML_PATH =
            "M:/intellijIDEA_Projects/epam-course-project-5/web/resources/xml/computer_devices.xml";
    String jspPath = "/WEB-INF/parse_result/stax-parse-result.jsp";

    private Set<Device> devices;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        parseXML();
        forward(request, response, jspPath);
    }

    private void parseXML() {
        DeviceParserBuilder deviceParserBuilder = new DeviceParserBuilder();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse re)
            throws ServletException, IOException {

    }

}