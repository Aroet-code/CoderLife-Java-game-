package threads;

import util.ObjCreator;

public class MyThread implements Runnable{
    private long tick = 0;
    private boolean enabled = false;
    @Override
    public void run() {
        while (enabled) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (tick % 4 == 0){
                ObjCreator.animationController.animate();
            }
            ObjCreator.mainMenuBackground.repaint();
            tick++;
        }
    }

    public void enable(){
        this.enabled = true;
    }

    public void disable(){
        this.enabled = false;
    }
}
