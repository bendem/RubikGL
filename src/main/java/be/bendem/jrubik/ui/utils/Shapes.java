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

    public static float[] rect(Point p, float size, float[] out) {
        return rect(p, size, size, size, out);
    }

    public static float[] rect(Point p, float xSize, float ySize, float zSize) {
        return rect(p, xSize, ySize, zSize, new float[108]);
    }

    public static float[] rect(Point p, float xSize, float ySize, float zSize, float[] out) {
        // Left
        out[0  ] = p.x();
        out[1  ] = p.y();
        out[2  ] = p.z();
        out[3  ] = p.x();
        out[4  ] = p.y();
        out[5  ] = p.z() + zSize;
        out[6  ] = p.x();
        out[7  ] = p.y() + ySize;
        out[8  ] = p.z() + zSize;
        out[9  ] = p.x();
        out[10 ] = p.y();
        out[11 ] = p.z();
        out[12 ] = p.x();
        out[13 ] = p.y() + ySize;
        out[14 ] = p.z() + zSize;
        out[15 ] = p.x();
        out[16 ] = p.y() + ySize;
        out[17 ] = p.z();
        // Right
        out[18 ] = p.x() + xSize;
        out[19 ] = p.y() + ySize;
        out[20 ] = p.z() + zSize;
        out[21 ] = p.x() + xSize;
        out[22 ] = p.y();
        out[23 ] = p.z();
        out[24 ] = p.x() + xSize;
        out[25 ] = p.y() + ySize;
        out[26 ] = p.z();
        out[27 ] = p.x() + xSize;
        out[28 ] = p.y();
        out[29 ] = p.z();
        out[30 ] = p.x() + xSize;
        out[31 ] = p.y() + ySize;
        out[32 ] = p.z() + zSize;
        out[33 ] = p.x() + xSize;
        out[34 ] = p.y();
        out[35 ] = p.z() + zSize;
        // Top
        out[36 ] = p.x() + xSize;
        out[37 ] = p.y() + ySize;
        out[38 ] = p.z() + zSize;
        out[39 ] = p.x() + xSize;
        out[40 ] = p.y() + ySize;
        out[41 ] = p.z();
        out[42 ] = p.x();
        out[43 ] = p.y() + ySize;
        out[44 ] = p.z();
        out[45 ] = p.x() + xSize;
        out[46 ] = p.y() + ySize;
        out[47 ] = p.z() + zSize;
        out[48 ] = p.x();
        out[49 ] = p.y() + ySize;
        out[50 ] = p.z();
        out[51 ] = p.x();
        out[52 ] = p.y() + ySize;
        out[53 ] = p.z() + zSize;
        // Bottom
        out[54 ] = p.x() + xSize;
        out[55 ] = p.y();
        out[56 ] = p.z() + zSize;
        out[57 ] = p.x();
        out[58 ] = p.y();
        out[59 ] = p.z();
        out[60 ] = p.x() + xSize;
        out[61 ] = p.y();
        out[62 ] = p.z();
        out[63 ] = p.x() + xSize;
        out[64 ] = p.y();
        out[65 ] = p.z() + zSize;
        out[66 ] = p.x();
        out[67 ] = p.y();
        out[68 ] = p.z() + zSize;
        out[69 ] = p.x();
        out[70 ] = p.y();
        out[71 ] = p.z();
        // Front
        out[72 ] = p.x();
        out[73 ] = p.y() + ySize;
        out[74 ] = p.z() + zSize;
        out[75 ] = p.x();
        out[76 ] = p.y();
        out[77 ] = p.z() + zSize;
        out[78 ] = p.x() + xSize;
        out[79 ] = p.y();
        out[80 ] = p.z() + zSize;
        out[81 ] = p.x() + xSize;
        out[82 ] = p.y() + ySize;
        out[83 ] = p.z() + zSize;
        out[84 ] = p.x();
        out[85 ] = p.y() + ySize;
        out[86 ] = p.z() + zSize;
        out[87 ] = p.x() + xSize;
        out[88 ] = p.y();
        out[89 ] = p.z() + zSize;
        // Back
        out[90 ] = p.x() + xSize;
        out[91 ] = p.y() + ySize;
        out[92 ] = p.z();
        out[93 ] = p.x();
        out[94 ] = p.y();
        out[95 ] = p.z();
        out[96 ] = p.x();
        out[97 ] = p.y() + ySize;
        out[98 ] = p.z();
        out[99 ] = p.x() + xSize;
        out[100] = p.y() + ySize;
        out[101] = p.z();
        out[102] = p.x() + xSize;
        out[103] = p.y();
        out[104] = p.z();
        out[105] = p.x();
        out[106] = p.y();
        out[107] = p.z();
        return out;
    }
}
