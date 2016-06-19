package be.bendem.jrubik.utils;

public interface AutoCloseable extends java.lang.AutoCloseable {

    @Override
    void close();

}
