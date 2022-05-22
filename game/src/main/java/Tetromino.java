import java.awt.*;

public class Tetromino {

    // для каждой фигуры есть 4 набора координат для каждого поворота
    // для каждого поворота есть общая точка - Point(1, 1)
    static final Point[][][] Shapes = {

            // квадрат со стороной 2
            {
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) }
            },

            // полоса длиной 4
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3) }
            },

            // фигура Т длиной 3
            {
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
            },

            // фигура L длиной 3
            {
                    { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 2) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) }
            },

            // фигура Г длиной 3
            {
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
                    { new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 0) },
                    { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
                    { new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) }
            },

            // фигура Z длиной 3
            {
                    { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
                    { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
                    { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) }
            },

            // фигура S длиной 3
            {
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
                    { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
                    { new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) }
            }
    };

    // цвета фигур
    static final Color[] Colors = {
            new Color(156, 34, 93), new Color(219, 48, 130),
            new Color(255, 8, 127), new Color(255, 56, 152),
            new Color(255, 84, 167), new Color(255, 113, 181),
            new Color(228, 35, 157), new Color(232, 64, 170)
    };
}
