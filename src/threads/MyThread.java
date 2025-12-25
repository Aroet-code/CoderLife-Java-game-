package threads;

import util.ObjCreator;

public class MyThread implements Runnable{
    private long tick = 0;
    @Override
    public void run() {
        while (true) {
            try {

                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tick % 4 == 0){
                ObjCreator.animationController.animate();
            }
            ObjCreator.camera.repaint();
            tick++;
        }
    }
}
