package SmasherClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class UpLoadButton{
    private JButton uploadCsvButton;
    private final String MEDIA_ROOT = "data/";
    private final String MEDIA_INPUT_ROOT = MEDIA_ROOT + "input/";
    private final String MEDIA_OUTPUT_ROOT = MEDIA_ROOT + "output/";

    public UpLoadButton(ArrayList<JTextField> boxes){
        uploadCsvButton = new JButton("Upload CSV");
        uploadCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String input_filename = JOptionPane.showInputDialog("Enter the filename (Ensure your file is uploaded to the data/input folder");
                File file = new File(MEDIA_INPUT_ROOT + input_filename);

                System.out.println(file.getAbsolutePath());

                if(file.exists()){
                    // Continue with file input stream
                    System.out.println("File exists!");

                    try {
                        FileReader fr = new FileReader(file);
                        BufferedReader br =  new BufferedReader(fr);
                        int i = 0;
                        String tmp_line;
                        while((tmp_line = br.readLine()) != null ){

                            // Split the first line on commas
                            String[] lineList = tmp_line.split(",");

                            if(lineList.length != 9){
                                throw new IllegalArgumentException("CSV is mal-formatted");
                            }

                            // Iterate through columns
                            for(int j = 0; j < lineList.length; j++){
                                JTextField jtf = boxes.get((i*9)+j);
                                jtf.setText(lineList[j]);
                            }
                            // Increment row
                            i = i + 1;

                        }

                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {

                    System.out.println("File does not exist");
                }

            }
        });
    }

    public JButton getUploadCsvButton() {
        return uploadCsvButton;
    }
}
