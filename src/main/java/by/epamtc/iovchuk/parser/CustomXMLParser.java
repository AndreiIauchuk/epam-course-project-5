package by.epamtc.iovchuk.parser;

import by.epamtc.iovchuk.exception.XMLParserException;

import java.util.Iterator;

public interface CustomXMLParser<T> {

    void parse() throws XMLParserException;

    Iterator<T> iterator();

}