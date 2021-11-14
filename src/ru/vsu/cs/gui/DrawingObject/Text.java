package ru.vsu.cs.gui.DrawingObject;

import ru.vsu.cs.function_interpreter.util.DrawUtils;
import ru.vsu.cs.gui.RealPoint;
import ru.vsu.cs.gui.ScreenConverter;
import ru.vsu.cs.gui.ScreenPoint;

import java.awt.*;

public class Text implements Drawing {
    private final String text;
    private final RealPoint realCoordinates;
    private final Font font;

    public Text(String text, Font font, RealPoint realCoordinates) {
        this.text = text;
        this.realCoordinates = realCoordinates;
        this.font = font;
    }

    @Override
    public void draw(Graphics2D graphics2D, ScreenConverter screenConverter) {
        DrawUtils.drawWithFont(graphics2D, this.font, () -> {
            ScreenPoint screenCoordinates = screenConverter.convertRealToScreen(this.realCoordinates);
            graphics2D.drawString(this.text, screenCoordinates.getX(), screenCoordinates.getY());
        });
    }
}
