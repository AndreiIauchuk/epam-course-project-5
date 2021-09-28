package by.epamtc.iovchuk.servlet;

import by.epamtc.iovchuk.entity.Device;
import by.epamtc.iovchuk.exception.XMLParserBuilderException;
import by.epamtc.iovchuk.parser.DeviceParserBuilder;
import by.epamtc.iovchuk.resource_manager.DeviceResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/main")
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 3120299103766816668L;

    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    private static final String DOM_PARSE_RESULT_URL =
            "/WEB-INF/parse_result/dom-parse-result.jsp";
    private static final String SAX_PARSE_RESULT_URL =
            "/WEB-INF/parse_result/sax-parse-result.jsp";
    private static final String STAX_PARSE_RESULT_URL =
            "/WEB-INF/parse_result/stax-parse-result.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("doGet()");

        String url;
        String parser = request.getParameter("parser");
        switch (parser) {
            case "dom": {
                url = DOM_PARSE_RESULT_URL;
                break;
            }
            case "sax": {
                url = SAX_PARSE_RESULT_URL;
                break;
            }
            case "stax": {
                url = STAX_PARSE_RESULT_URL;
                break;
            }
            default:
                return;
        }

        Set<Device> devices = parseXML(parser);
        request.setAttribute("devices", devices);
        forward(request, response, url);
    }

    private Set<Device> parseXML(String parser) {
        DeviceResourceManager resourceManager = DeviceResourceManager.getInstance();
        String filepath = resourceManager.getString("path");
        DeviceParserBuilder deviceParserBuilder = new DeviceParserBuilder(filepath);
        try {
            switch (parser) {
                case "dom": {
                    deviceParserBuilder = deviceParserBuilder.dom();
                    break;
                }
                case "sax": {
                    deviceParserBuilder = deviceParserBuilder.sax();
                    break;
                }
                case "stax": {
                    deviceParserBuilder = deviceParserBuilder.stax();
                    break;
                }
            }
            return deviceParserBuilder.parse();
        } catch (XMLParserBuilderException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    protected void forward
            (HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

}