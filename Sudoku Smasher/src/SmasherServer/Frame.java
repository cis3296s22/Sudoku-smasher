package SmasherServer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Frame {
    public static void createAndShowGUI() {
        final Border fieldBorder = BorderFactory.createLineBorder(Color.BLACK);

        final JPanel grid = new JPanel(new GridLayout(9, 0));
        for (int i = 0; i < 81; ++i) {
            final JTextField field = new JTextField(2);
            field.setHorizontalAlignment(JTextField.CENTER); //Center text horizontally in the text field.
            field.setBorder(fieldBorder); //Add the colored border.
            grid.add(field);
        }

        final JPanel centeredGrid = new JPanel(new GridBagLayout());
        centeredGrid.add(grid);

        final JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(centeredGrid);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}
