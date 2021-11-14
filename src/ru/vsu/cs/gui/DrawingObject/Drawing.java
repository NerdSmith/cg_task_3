package ru.vsu.cs.gui.DrawingObject;

import ru.vsu.cs.gui.ScreenConverter;

import java.awt.*;

public interface Drawing {
    void draw(Graphics2D graphics2D, ScreenConverter screenConverter);
}
