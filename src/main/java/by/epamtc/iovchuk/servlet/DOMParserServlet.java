package by.epamtc.iovchuk.servlet;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.parser.DOM.DeviceDomBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

public class DOMParserServlet extends XMLParserServlet {

    private static final long serialVersionUID = 222990763842377510L;

    private static final String XML_FILES_LOCATION =
            "M:/intellijIDEA_Projects/epam-course-project-5/web/resources/xml/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DeviceDomBuilder deviceDomBuilder = new DeviceDomBuilder();
        deviceDomBuilder.buildDevices(XML_FILES_LOCATION + "computer_devices.xml");
        Iterator<Device> devicesIterator = deviceDomBuilder.getDevicesIterator();
        while (devicesIterator.hasNext()) {
            Device device = devicesIterator.next();
            System.out.println(device);
        }
        String path = "/WEB-INF/parse_result/dom-parse-result.jsp";
        forward(request, response, path);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}