package by.epamtc.iovchuk.parser;

import java.util.Iterator;

public interface CustomXMLParser<T> {

    void parse();

    Iterator<T> iterator();

}