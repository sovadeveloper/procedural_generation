package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Map {
    private JFrame frame;

    public static void main(String[] args) {
        new Map().createFrame(1920, 1080, 1, Long.decode(args[0]));
    }

    private void createFrame(int width, int height, int cellSize, long seed) {
        frame = new JFrame("Perlin noise 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new MyMap(width, height, cellSize, seed));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    private class MyMap extends JPanel {
        private int[][] map;
        private int widthInCell;
        private int heightInCell;
        private int cellSize;

        MyMap(int width, int height, int cellSize, long seed) {
            setLayout(null);
            setPreferredSize(new Dimension(width, height));

            widthInCell = width / cellSize;
            heightInCell = height / cellSize;
            this.cellSize = cellSize;

            map = new int[widthInCell][heightInCell];

            PerlinNoise perlin = new PerlinNoise(seed);
            for(int x = 0; x < widthInCell; x++) {
                for(int y = 0; y < heightInCell; y++) {
                    float value = perlin.getNoise(x/100f,y/100f,8,0.5f);
                    map[x][y] = (int)(value * 255 + 128) & 255;
                }
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            for(int x = 0; x < widthInCell; ++x) {
                for(int y = 0; y < heightInCell; ++y) {
                    Rectangle2D cell = new Rectangle2D.Float(x * cellSize, y * cellSize,
                            cellSize, cellSize);
                    g2d.setColor(new Color(map[x][y], map[x][y], map[x][y]));
                    g2d.fill(cell);
                }
            }
        }
    }
}
