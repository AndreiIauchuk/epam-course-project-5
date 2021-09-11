package by.epamtc.iovchuk.servlet;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.parser.DOM.DeviceDOMParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class DOMParserServlet extends XMLParserServlet {

    private static final long serialVersionUID = 222990763842377510L;

    private static final String DEVICES_XML_PATH =
            "M:/intellijIDEA_Projects/epam-course-project-5/web/resources/xml/computer_devices.xml";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DeviceDOMParser deviceDomParser = new DeviceDOMParser();
        deviceDomParser.buildDevices(XML_FILES_LOCATION + "computer_devices.xml");
        Iterator<Device> devicesIterator = deviceDomParser.getDevicesIterator();
        while (devicesIterator.hasNext()) {
            System.out.println(devicesIterator.next());
        }
        String path = "/WEB-INF/parse_result/dom-parse-result.jsp";
        forward(request, response, path);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}