package threads;

import util.GameController;

public class ImageRenderingThread extends ThreadBase{
    public ImageRenderingThread(boolean enabled) {
        super(enabled);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isEnabled()){
                GameController.getImageScaler().scaleImagesIfNeeded();
            }
        }
    }
}
