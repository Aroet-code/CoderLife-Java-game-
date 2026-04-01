package threads;

import camera.GamePanel;

public class GamePanelUpdateThread extends ThreadBase{
    private GamePanel gamePanel;
    public GamePanelUpdateThread(GamePanel gamePanel) {
        super(true);
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (isEnabled()){
                gamePanel.repaint();
            }
        }
    }
}
