package sounds;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdvancedAudioManager {
    private static AdvancedAudioManager instance;
    private final ExecutorService audioExecutor;
    private Clip backgroundMusic;
    private final Map<String, byte[]> soundCache;
    private float masterVolume = 1.0f;
    private Random random;

    public AdvancedAudioManager(){
        this.audioExecutor = Executors.newSingleThreadExecutor();
        this.soundCache = new ConcurrentHashMap<>();
    }

    public static AdvancedAudioManager getInstance(){
        if (instance == null){
            instance = new AdvancedAudioManager();
        }
        return instance;
    }

    public void preloadSound(String key, String filepath){
        audioExecutor.submit(() ->{
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                        new File(filepath).getAbsoluteFile()
                );

                byte[] audioData = audioStream.readAllBytes();
                soundCache.put(key, audioData);
                audioStream.close();
            }
            catch (Exception e) {
                System.err.println("Error while handling preloading sounds: " + e.getMessage());
            }
        });
    }

    public void playCachedSound(String key){
        audioExecutor.submit(() -> {
            byte[] audioData = soundCache.get(key);
            if (audioData != null){
                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioData));
                    Clip clip = AudioSystem.getClip();
                    clip.open();
                    setClipVolume(clip, masterVolume);

                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });

                    clip.start();
                } catch (Exception e) {
                    System.err.println("Error while playing cached sounds: " + e.getMessage());
                }
            }
        });
    }

    public void pauseBackgroundMusic(){
        audioExecutor.submit(() -> {
            if (backgroundMusic != null && backgroundMusic.isRunning()){
                backgroundMusic.stop();
            }
        });
    }

    public void resumeBackgroundMusic(){
        audioExecutor.submit(() -> {
            if (backgroundMusic != null && backgroundMusic.isRunning()){
                backgroundMusic.stop();
            }
        });
    }

    public void stopBackgroundMusic(){
        audioExecutor.submit(() -> {
            if (backgroundMusic != null && backgroundMusic.isRunning()){
                backgroundMusic.stop();
                backgroundMusic.close();
            }
        });
    }

    public void playBackgroundMusic(String filepath){
        audioExecutor.submit(() -> {
            try {
                stopBackgroundMusic();

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioStream);
                setClipVolume(backgroundMusic, masterVolume);
                backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                System.err.println("Error while handling playing background music: " + e.getMessage());
            }
        });
    }

    public void playBackgroundMusic(String[] filepaths){
        audioExecutor.submit(() -> {
            try {
                stopBackgroundMusic();
                AudioInputStream[] audioInputStreams = new AudioInputStream[filepaths.length];
                for (int i = 0; i < filepaths.length; i++) {
                    String filepath = filepaths[i];
                    audioInputStreams[i] = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
                }
                backgroundMusic = AudioSystem.getClip();
                backgroundMusic.open(audioInputStreams[random.nextInt(filepaths.length)]);
                setClipVolume(backgroundMusic, masterVolume);
                backgroundMusic.start();
            } catch (Exception e) {
                System.err.println("Error while handling playing background music: " + e.getMessage());
            }
        });
    }

    private void setClipVolume(Clip clip, float volume){
        if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)){
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20);
            gainControl.setValue(dB);
        }
    }

    public void shutdown() {
        if (backgroundMusic != null){
            backgroundMusic.stop();
            backgroundMusic.close();
        }
        audioExecutor.shutdown();
    }
}
