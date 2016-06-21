package be.bendem.jrubik.ui.utils;

public class Shapes {

    public static float[] triangle(Point p1, Point p2, Point p3) {
        return new float[] {
            p1.x(), p1.y(), p1.z(),
            p2.x(), p2.y(), p2.z(),
            p3.x(), p3.y(), p3.z(),
        };
    }

    public static float[] rect(Point p1, Point p2) {
        return new float[] {
            p1.x(), p1.y(), p1.z(),
            p1.x(), p1.y(), p2.z(),
            p1.x(), p2.y(), p2.z(),
            p2.x(), p2.y(), p1.z(),
            p1.x(), p1.y(), p1.z(),
            p1.x(), p2.y(), p1.z(),
        };
    }

    public static float[] cube(Point p, float size) {
        return new float[] {
            p.x()       , p.y()       , p.z()       ,
            p.x()       , p.y()       , p.z() + size,
            p.x()       , p.y() + size, p.z() + size,
            p.x() + size, p.y() + size, p.z()       ,
            p.x()       , p.y()       , p.z()       ,
            p.x()       , p.y() + size, p.z()       ,
            p.x() + size, p.y()       , p.z() + size,
            p.x()       , p.y()       , p.z()       ,
            p.x() + size, p.y()       , p.z()       ,
            p.x() + size, p.y() + size, p.z()       ,
            p.x() + size, p.y()       , p.z()       ,
            p.x()       , p.y()       , p.z()       ,
            p.x()       , p.y()       , p.z()       ,
            p.x()       , p.y() + size, p.z() + size,
            p.x()       , p.y() + size, p.z()       ,
            p.x() + size, p.y()       , p.z() + size,
            p.x()       , p.y()       , p.z() + size,
            p.x()       , p.y()       , p.z()       ,
            p.x()       , p.y() + size, p.z() + size,
            p.x()       , p.y()       , p.z() + size,
            p.x() + size, p.y()       , p.z() + size,
            p.x() + size, p.y() + size, p.z() + size,
            p.x() + size, p.y()       , p.z()       ,
            p.x() + size, p.y() + size, p.z()       ,
            p.x() + size, p.y()       , p.z()       ,
            p.x() + size, p.y() + size, p.z() + size,
            p.x() + size, p.y()       , p.z() + size,
            p.x() + size, p.y() + size, p.z() + size,
            p.x() + size, p.y() + size, p.z()       ,
            p.x()       , p.y() + size, p.z()       ,
            p.x() + size, p.y() + size, p.z() + size,
            p.x()       , p.y() + size, p.z()       ,
            p.x()       , p.y() + size, p.z() + size,
            p.x() + size, p.y() + size, p.z() + size,
            p.x()       , p.y() + size, p.z() + size,
            p.x() + size, p.y()       , p.z() + size,
        };
    }
}
