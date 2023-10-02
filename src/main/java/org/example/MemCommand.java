package org.example;

import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "org.example.MemCommand",
        description = "It allows you to add text to a selected image and supports various options.",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        aliases = "mem"
)
class MemCommand implements Callable<Integer> {
    @CommandLine.Option(names = {"-f", "--filename"}, description = "The file you want to modify.", required = true)
    private String filename;

    @CommandLine.Option(names = {"-t", "--text"}, description = "The text you want to add to the picture.", required = true)
    private String text;

    @CommandLine.Option(names = {"-n", "--newFile"}, description = "New already modified picture.", required = true)
    private String newFile;

    @CommandLine.Option(names = {"-c", "--color"}, description = "Determines the color of the text.")
    private String color;

    @CommandLine.Option(names = {"-fs", "--fsize"}, description = "Determines the font size.")
    private float fSize = 10f;

    @CommandLine.Option(names = {"-ft", "--ftype"}, description = "Determines the font type.")
    private String fType;

    @CommandLine.Option(names = {"-p", "--pos"}, description = "Determines the position where the text will be (top, center, bottom).")
    private String position;

    @Override
    public Integer call() throws Exception {
        BufferedImage image = ImageIO.read(new File(filename));

        Graphics g = image.getGraphics();

        g.setColor(getColor(color));
        g.setFont(Font.decode(fType));

        g.setFont(g.getFont().deriveFont(Font.PLAIN, fSize));
        g.drawString(text, (image.getWidth() / 3), getPosition(position, image.getHeight()));
        g.dispose();
        ImageIO.write(image, "png", new File(newFile));

        return null;
    }

    private static int getPosition(String position, int imageHeight) {
        if("top".equals(position)) return imageHeight / 10;
        else if("bottom".equals(position)) return (imageHeight * 9) / 10;
        else return imageHeight / 2; // default == center
    }

    private static Color getColor(String color) {
        if (color == null) {
            return Color.black;
        }
        try {
            return Color.decode(color);
        } catch (NumberFormatException nfe) {
            try {
                final Field f = Color.class.getField(color);

                return (Color) f.get(null);
            } catch (Exception ce) {
                return Color.black;
            }
        }
    }
}