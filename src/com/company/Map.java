package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Map {
    private JFrame frame;

    public static void main(String[] args) {
        Random rnd = new Random();
        int seed = rnd.nextInt(18366437);
        new Map().createFrame(1920, 1080, 1, seed);
    }

    private void createFrame(int width, int height, int cellSize, long seed) {
        frame = new JFrame("Procedural Generation with Perlin Noise");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(new MyMap(width, height, cellSize, seed));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    private class MyMap extends JPanel {
        private int[][] map1;
        private int[][] map2;
        private int[][] map3;
        private int widthInCell;
        private int heightInCell;
        private int cellSize;
        private float value;

        MyMap(int width, int height, int cellSize, long seed) {
            setLayout(null);
            setPreferredSize(new Dimension(width, height));

            widthInCell = width / cellSize;
            heightInCell = height / cellSize;
            this.cellSize = cellSize;

            map1 = new int[widthInCell][heightInCell];
            map2 = new int[widthInCell][heightInCell];
            map3 = new int[widthInCell][heightInCell];

            PerlinNoise perlin = new PerlinNoise(seed);
            for(int x = 0; x < widthInCell; x++) {
                for(int y = 0; y < heightInCell; y++) {
                    value = perlin.getNoise(x / 90f, y / 90f, 16, 0.35f) + .5f;
                    //глубокие реки
                    if(value <= 0.5){
                        map1[x][y] = 14;
                        map2[x][y] = 7;
                        map3[x][y] = 94;
                        //мелководье
                    }
                    else if (value > 0.5 && value <= 0.55){
                        map1[x][y] = 37;
                        map2[x][y] = 27;
                        map3[x][y] = 148;
                        //песок
                    }else if(value > 0.55 && value <= 0.6){
                        map1[x][y] = 199;
                        map2[x][y] = 184;
                        map3[x][y] = 54;
                        //трава
                    }else if(value > 0.6 && value <= 0.75){
                        map1[x][y] = 50;
                        map2[x][y] = 189;
                        map3[x][y] = 47;
                        //горы
                    }else if (value > 0.75 && value <= 0.85){
                        map1[x][y] = 77;
                        map2[x][y] = 77;
                        map3[x][y] = 77;
                        //Снег
                    }else{
                        map1[x][y] = 255;
                        map2[x][y] = 255;
                        map3[x][y] = 255;
                    }
                }
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            for(int x = 0; x < widthInCell; ++x) {
                for(int y = 0; y < heightInCell; ++y) {
                    Rectangle2D cell = new Rectangle2D.Float(x * cellSize, y * cellSize, cellSize, cellSize);
                    g2d.setColor(new Color(map1[x][y], map2[x][y], map3[x][y]));
                    g2d.fill(cell);
                }
            }
        }
    }
}
