import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class MusicPlayer implements LineListener {
    private Clip clip;
    private static FloatControl volumeControl;

    public MusicPlayer() {
        try {
            File audioFile = new File("src/bensound-smile_2.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.addLineListener(this);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Error loading audio file: " + e.getMessage());
        }
    }

    public void play() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public  void decreaseVolume() {
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            float newVolume = currentVolume - 10f;
            if (newVolume < volumeControl.getMinimum()) {
                newVolume = volumeControl.getMinimum();
            }
            volumeControl.setValue(newVolume);
        }
    }

    public  void increaseVolume() {
        if (volumeControl != null) {
            float currentVolume = volumeControl.getValue();
            float newVolume = currentVolume + 10f;
            if (newVolume > volumeControl.getMaximum()) {
                newVolume = volumeControl.getMaximum();
            }
            volumeControl.setValue(newVolume);
        }
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            clip.setFramePosition(0);
        }
    }
}