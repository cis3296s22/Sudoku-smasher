package music;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player {
    private static Player sound = new Player();
    private static Clip clip;

    public static Player getSound(String path){
        try {
            if ( clip != null && clip.isOpen() )
                clip.close();
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path).toURL()));
            clip.loop(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sound;
    }

}
