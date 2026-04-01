package threads;

import util.GameController;
////import util.ObjCreator;
//
//@Deprecated
//public class MainThread extends ThreadBase{
//    private long tick = 0;
//
//    public MainThread(boolean enabled) {
//        super(enabled);
//    }
//
//    @Override
//    public void run() {
//        while (this.isEnabled()) {
//            try {
//                Thread.sleep(16);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (tick % 4 == 0){
//                GameController.getAnimationController().animate();
//            }
////            ObjCreator.mainMenuBackground.repaint();
//            tick++;
//        }
//    }
//}
