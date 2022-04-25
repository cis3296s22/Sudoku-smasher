package music;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Music extends JFrame {

    public JButton play, wavfile;
    private String file;
    public static Music instance;

    public static Frame setup() {
        Music window = new Music();
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        return window;
    }
    public Music() {
        this.setSize(300, 100);
        this.setTitle("Audio Player");

        Container pane = this.getContentPane();
        pane.setLayout(new FlowLayout());
        pane.setBackground(Color.BLUE);

        this.wavfile = new JButton("files");
        this.play = new JButton("play");
        pane.add(wavfile);
        pane.add(play);
        this.setListeners();
    }

    public void setListeners() {
        this.wavfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(instance);
                fd.setMode(FileDialog.LOAD);
                fd.setVisible(true);
                String soubor = fd.getDirectory() + fd.getFile();
                if (soubor != null) {
                    file = soubor;
                }
            }
        });

        this.play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Player.getSound(file);
            }
        });
    }


}