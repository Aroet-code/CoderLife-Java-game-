package util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AsyncImageLoader {
    public static final Map<String, CompletableFuture<Image>> loadingCache = new HashMap<>();
    public static final Map<String, Image> loadedCache = new HashMap<>();

    public static CompletableFuture<Image> loadImageAsync(String path){
        if (loadedCache.containsKey(path)){
            return CompletableFuture.completedFuture(loadedCache.get(path));
        }

        CompletableFuture<Image> future = CompletableFuture.supplyAsync(() -> {
            try {
                ImageIcon icon = new ImageIcon(path);

                while (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                    Thread.sleep(10);
                }

                Image image = icon.getImage();

                MediaTracker tracker = new MediaTracker(new Panel());
                tracker.addImage(image, 0);
                tracker.waitForAll();

                loadedCache.put(path, image);
                return image;
            } catch (Exception e){
                System.out.println("Failed to load image at path: " + path);
                e.printStackTrace();
                return new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            }
        });

        loadingCache.put(path, future);
        future.thenRun(() -> loadingCache.remove(path));

        return future;
    }

    public static Image createPlaceholder(int width, int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawString("...",10, height/2);
        g.dispose();
        return image;
    }

    public static Image createErrorImage(int width, int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawString("ERROR",10, height/2);
        g.dispose();
        return image;
    }
}
