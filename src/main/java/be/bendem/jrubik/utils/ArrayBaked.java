package be.bendem.jrubik.utils;

public interface ArrayBaked<array> {

    array weakArray();

    array array();

    array array(array array, int offset);

    default array array(array array) {
        return array(array, 0);
    }

}
