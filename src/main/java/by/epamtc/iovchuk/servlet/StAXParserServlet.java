package by.epamtc.iovchuk.servlet;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.exception.XMLParserBuilderException;
import by.epamtc.iovchuk.parser.DeviceParserBuilder;
import by.epamtc.iovchuk.resource_manager.DeviceResourceManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class StAXParserServlet extends XMLParserServlet {

    private static final long serialVersionUID = -7002933372621067362L;

   // private Logger logger = LogManager.getLogger();

    private Set<Device> devices;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        parseXML();
        request.setAttribute("devices", devices);

        String jspPath = "/WEB-INF/parse_result/stax-parse-result.jsp";
        forward(request, response, jspPath);
    }

    private void parseXML() {
        DeviceResourceManager resourceManager = DeviceResourceManager.getInstance();
        String filepath = resourceManager.getString("path");

        DeviceParserBuilder deviceParserBuilder = new DeviceParserBuilder(filepath);
        try {
            devices =
                    deviceParserBuilder
                            .stax()
                            .parse();
        } catch (XMLParserBuilderException e) {
           // logger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse re) {

    }

}