package tank;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private static Clip start;
    private static Clip move;
    Thread t = new Thread(){
        public void run(){
      playBackground();
        }
    };

    static {
        File bgMusicStartFile = new File("images/start.wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bgMusicStartFile);
            start = AudioSystem.getClip();
            start.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public static void playBackground(){
        move.setFramePosition(0);
        move.loop(Clip.LOOP_CONTINUOUSLY);
    }
    }

