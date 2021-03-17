package com.company;

import javax.swing.*;
import java.awt.*;

public class DrawPanel extends JPanel {
    PerlinNoise pn;

    @Override
    public void paint(Graphics g) {
        pn.getNoise(10,10);
    }
}
