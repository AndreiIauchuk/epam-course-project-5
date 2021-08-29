package by.epamtc.iovchuk.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DOMParserServlet extends XMLParserServlet {

    private static final long serialVersionUID = 222990763842377510L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = "/WEB-INF/parse_result/dom-parse-result.jsp";
        forward(request, response, path);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}