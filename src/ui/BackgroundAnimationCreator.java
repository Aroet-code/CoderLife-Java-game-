package ui;

import camera.Screen;
import util.GameController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class BackgroundAnimationCreator {
    private static final List<AnimationObject> objects = new ArrayList<>();
    private static final List<AnimationObject> objects1 = new ArrayList<>();
    private static final Screen screen = GameController.getScreen();

    public static void create(){
        BackgroundAnimationCreator.addObjects(objects);
        BackgroundAnimationCreator.addObjects1(objects1);
    }

    public static BackgroundAnimation additionalAnimation = new BackgroundAnimation(new Callable<Image>() {
        @Override
        public Image call() throws Exception {
            int width, height;
            width = screen.getWidth();
            height = screen.getHeight();

            BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = finalImage.createGraphics();
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, width, height);

            synchronized (objects1){
                for (AnimationObject animationObject : objects1){
                    if (animationObject instanceof AnimationTickTimer){
                        AnimationTickTimer timer = (AnimationTickTimer) animationObject;
                        timer.increaseTick();
                        switch (timer.id){
                            case 0 -> {
                                if (!(timer.isTickReached())){
                                    g.setColor(Color.WHITE);
                                    int tick = timer.getTick();
                                    int ovalWidth = (int) (tick * (width / 100) * timer.getCompletionState());
                                    int ovalHeight = (int) (tick * (height / 100) * timer.getCompletionState());
                                    g.fillOval(width / 2 - ovalWidth / 2, height / 2 - ovalHeight / 2, ovalWidth, ovalHeight);
                                } else {
                                    GameController.getSceneManager().changeScene(GameController.getScreen(), "MAIN_GAME");
                                    timer.reset();
                                    timer.disable();
                                    GameController.getScreen().getGamePanel().setAnimation(null);
                                }
                            }
                        }
                    }
                }
            }

            return finalImage;
        }
    }, objects1);

    public static BackgroundAnimation mainMenuAnimation =  new BackgroundAnimation(new Callable<Image>() {
        @Override
        public Image call() throws Exception {
            int width, height;
            //  if (screen != null) {
            width = screen.getWidth();
            height = screen.getHeight();
            Random random = new Random();
            int maxWidth = 10;
 /*           } else {
                width = 1000;
                height = 1000;
            }*/

            BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = finalImage.createGraphics();
            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, width, height);

            List<AnimationObject> toRemove = new ArrayList<>();
            List<AnimationObject> toAdd = new ArrayList<>();

            synchronized (objects) {
                for (AnimationObject object : objects) {
                    if (object instanceof Star) {
                        Star star = (Star) object;
                        int returnValue = star.elevate((int) (object.getWidth() * 0.8f));
//                        int returnValue = star.elevate(speed);

                        object.setColor(new Color(object.getColor().getRed() / 255f, object.getColor().getGreen() / 255f,
                                object.getColor().getBlue() / 255f,
                                Math.clamp((object.getColor().getAlpha() / 255f) - (0.007f * object.getWidth() / 5), 0f, 1f)));
                        g.setColor(object.getColor());
                        g.fillRect((int)star.getX() - (star.getWidth() / 2),
                                (int)star.getY() - (star.getHeight() / 2),
                                star.getWidth(),
                                star.getHeight());

                        if (returnValue == -1) {
                            toRemove.add(star);
                            int newWidth = Math.max(3, random.nextInt(maxWidth));
                            toAdd.add(new Star(random.nextInt(width),
                                    height + newWidth,
                                    newWidth,
                                    newWidth));
                        }
                    }
                    if (object instanceof RectangledFire){
                        RectangledFire fire = (RectangledFire) object;
                        int returnValue = fire.burst();

                        object.setColor(new Color(object.getColor().getRed() / 255f, object.getColor().getGreen() / 255f,
                                object.getColor().getBlue() / 255f, Math.clamp((fire.getAlpha()), 0f, 1f)));
                        fire.setAlpha(fire.getAlpha() - 0.005f);
                        g.setColor(object.getColor());
                        g.fillRect(0, screen.getHeight() - object.getHeight(), screen.getWidth(), object.getHeight());
                        if (returnValue == -1) {
                            toRemove.add(fire);
//                            int newWidth = Math.max(1, random.nextInt(maxWidth));
                            toAdd.add(new RectangledFire(0, screen.getHeight(), screen.getWidth(), 1f));
                        }
                    }
                    if (object instanceof AnimatedLabel) {
                        AnimatedLabel label = (AnimatedLabel) object;
                        int returnValue = label.getToCoordinate((width - label.getWidth()) / 2, 600, 0.015f);
                        g.setColor(Color.RED);
//                        g.fillRect((int)label.getX(), (int)label.getY(), label.getImage().getWidth(), label.getImage().getHeight());
                        g.setColor(new Color(0.30f, 0.65f , 1.00f, 1f));
                        g.setFont(new Font("Comic Sans", Font.ITALIC, 48));
//                        g.drawString("TOUCH TO START", (int)label.getX(), (int)label.getY());

                        if (label.getImage() == null){
                            System.out.println("The image is null.");
                        }
                        g.drawImage(label.getImage(), (int)label.getX(), (int)label.getY(), null);
//                        System.out.println(label.getX() + " " + label.getY());
                    }
                    if (object instanceof LightLine){
                        LightLine line = (LightLine) object;
                        int returnValue = line.decay();
                        g.setColor(new Color((float) line.getColor().getRed() / 255, (float) line.getColor().getGreen() / 255,
                                (float) line.getColor().getBlue() / 255, line.getAlpha()));
                        g.fillRect((int) line.getX(), (int) line.getY(), line.getWidth(), line.getHeight());
                        if (returnValue == -1){
                            toRemove.add(line);
                            toAdd.add(new LightLine(10 + random.nextInt(screen.getWidth() - 10 - line.getWidth()), 5, screen.getHeight(), 0.02f, g.getColor()));
                        }
                    }
                }

                objects.removeAll(toRemove);
                objects.addAll(toAdd);
            }

            g.dispose();
            return finalImage;
        }
    }, objects);

    public static void addObjects(List<AnimationObject> objects){
        synchronized (objects) {
            for (int i = 0; i < 100; i++) {
                objects.add(new Star(0, 0 + (i * 30), 10, 10));
            }
            for (int i = 0; i < 15; i++){
                objects.add(new RectangledFire(0, screen.getHeight(), screen.getWidth(), 1 + (0.08f * i)));
            }
            objects.add(new AnimatedLabel(500, 2800, 500, 50, "НАЖМИ <ENTER> ДЛЯ НАЧАЛА", new Font("Arial", Font.ITALIC, 32), new Color(0.30f, 0.65f , 1.00f, 1f)));
            objects.add(new LightLine(100, 5, screen.getHeight(), 0.02f, new Color(0.30f, 0.65f , 1.00f, 1f)));
//            objects.add(new LightLine(500, 10, screen.getHeight(), 0.015f, new Color(0.30f, 0.65f , 1.00f, 1f)));
        }
    }

    public static void addObjects1(List<AnimationObject> objects1){
        synchronized (objects1) {
            objects1.add(new AnimationTickTimer(240, 0));
        }
    }
}
