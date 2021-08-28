package by.epamtc.iovchuk.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StAXParserServlet extends ParserServlet {

    private static final long serialVersionUID = -7002933372621067362L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = "/WEB-INF/parse_result/stax-parse-result.jsp";
        forward(request, response, path);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse re)
            throws ServletException, IOException {

    }

}