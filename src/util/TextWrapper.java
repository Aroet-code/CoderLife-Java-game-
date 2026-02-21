package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TextWrapper {
    public static Image placeWrappedText(Image image, String text, Font font, Color color, int yPad, int xOffset, int yOffset, boolean offsetBothSides){
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resultImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.setFont(font);
        g.setColor(color);
        int allowedWidth = width - xOffset;
        if (offsetBothSides){
            allowedWidth -= xOffset;
        }
        FontMetrics metrics = g.getFontMetrics();

        List<String> lines = TextWrapper.wrapText(text, metrics, allowedWidth);

        int currentY = yOffset + metrics.getAscent();
        for (String line : lines){
            int x = xOffset;

            g.drawString(line, x, currentY);

            currentY += metrics.getHeight() + yPad;
        }
        g.dispose();
        return resultImage;
    }

    private static List<String> wrapText(String text, FontMetrics metrics, int maxWidth){
        List<String> lines = new ArrayList<>();
        String[] paragraphs = text.split("\n");

        for (String p : paragraphs){
            if (p.isEmpty()){
                lines.add("");
                continue;
            }
            String[] words = p.split(" ");
            StringBuilder currentLine = new StringBuilder();

            for (String word : words){
                String testLine = currentLine.isEmpty() ? word : currentLine.toString() + " " + word;
                int lineWidth = metrics.stringWidth(testLine);

                if (lineWidth <= maxWidth){
                    if (currentLine.isEmpty()){
                        currentLine.append(word);
                    } else {
                        currentLine.append(" ").append(word);
                    }
                } else {
                    if (currentLine.isEmpty()){
                        lines.add(currentLine.toString());
                        currentLine = new StringBuilder(word);
                    } else {
                        lines.add(currentLine.toString());
                        currentLine = new StringBuilder(word);
                    }
                }
            }
            if (!(currentLine.isEmpty())){
                lines.add(currentLine.toString());
            }
        }
        return lines;
    }
}
