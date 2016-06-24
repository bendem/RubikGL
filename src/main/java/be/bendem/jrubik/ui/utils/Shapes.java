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

    public static float[] rect(Point p, float size) {
        return rect(p, size, size, size);
    }

    public static float[] rect(Point p, float xSize, float ySize, float zSize) {
        return new float[] {
            // Left
            p.x()        , p.y()        , p.z()        ,
            p.x()        , p.y()        , p.z() + zSize,
            p.x()        , p.y() + ySize, p.z() + zSize,
            p.x()        , p.y()        , p.z()        ,
            p.x()        , p.y() + ySize, p.z() + zSize,
            p.x()        , p.y() + ySize, p.z()        ,
            // Right
            p.x() + xSize, p.y() + ySize, p.z() + zSize,
            p.x() + xSize, p.y()        , p.z()        ,
            p.x() + xSize, p.y() + ySize, p.z()        ,
            p.x() + xSize, p.y()        , p.z()        ,
            p.x() + xSize, p.y() + ySize, p.z() + zSize,
            p.x() + xSize, p.y()        , p.z() + zSize,
            // Top
            p.x() + xSize, p.y() + ySize, p.z() + zSize,
            p.x() + xSize, p.y() + ySize, p.z()        ,
            p.x()        , p.y() + ySize, p.z()        ,
            p.x() + xSize, p.y() + ySize, p.z() + zSize,
            p.x()        , p.y() + ySize, p.z()        ,
            p.x()        , p.y() + ySize, p.z() + zSize,
            // Bottom
            p.x() + xSize, p.y()        , p.z() + zSize,
            p.x()        , p.y()        , p.z()        ,
            p.x() + xSize, p.y()        , p.z()        ,
            p.x() + xSize, p.y()        , p.z() + zSize,
            p.x()        , p.y()        , p.z() + zSize,
            p.x()        , p.y()        , p.z()        ,
            // Front
            p.x()        , p.y() + ySize, p.z() + zSize,
            p.x()        , p.y()        , p.z() + zSize,
            p.x() + xSize, p.y()        , p.z() + zSize,
            p.x() + xSize, p.y() + ySize, p.z() + zSize,
            p.x()        , p.y() + ySize, p.z() + zSize,
            p.x() + xSize, p.y()        , p.z() + zSize,
            // Back
            p.x() + xSize, p.y() + ySize, p.z()        ,
            p.x()        , p.y()        , p.z()        ,
            p.x()        , p.y() + ySize, p.z()        ,
            p.x() + xSize, p.y() + ySize, p.z()        ,
            p.x() + xSize, p.y()        , p.z()        ,
            p.x()        , p.y()        , p.z()        ,
        };
    }
}
