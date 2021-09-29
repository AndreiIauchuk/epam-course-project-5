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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/main")
@MultipartConfig
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 3120299103766816668L;

    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    private static final String MAIN_PAGE_URL = "/main.jsp";
    private static final String DOM_PARSE_RESULT_URL =
            "/WEB-INF/parse_result/dom-parse-result.jsp";
    private static final String SAX_PARSE_RESULT_URL =
            "/WEB-INF/parse_result/sax-parse-result.jsp";
    private static final String STAX_PARSE_RESULT_URL =
            "/WEB-INF/parse_result/stax-parse-result.jsp";

    String filepath;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String parser = request.getParameter("parser");
        if (parser == null) {
            response.sendRedirect(MAIN_PAGE_URL);
            return;
        }
        String url = getUrl(parser);
        filepath = (String) request.getAttribute("filepath");
        if (filepath == null) {
            initFilepathFromResource();
        }
        Set<Device> devices = parseXML(parser);
        request.setAttribute("devices", devices);
        forward(request, response, url);
    }

    private String getUrl(String parser) {
        String url = null;
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
        }
        return url;
    }

    private void initFilepathFromResource() {
        DeviceResourceManager resourceManager = DeviceResourceManager.getInstance();
        filepath = resourceManager.getString("path");
    }

    private Set<Device> parseXML(String parser) {
        DeviceResourceManager resourceManager = DeviceResourceManager.getInstance();
        filepath = resourceManager.getString("path");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String contentType = request.getContentType();
        if (!contentType.contains("multipart/form-data")) {
            return;
        }

        Part filePart = request.getPart("file");
        filepath = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        request.getSession().setAttribute("filepath", filepath);
        forward(request, response, MAIN_PAGE_URL);
    }

    protected void forward
            (HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

}