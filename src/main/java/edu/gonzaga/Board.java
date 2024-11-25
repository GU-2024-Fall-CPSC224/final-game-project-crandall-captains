package edu.gonzaga;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel {

//Board Attributes
private ArrayList<JButton> buttons;
private JLabel statusLabel;

public Board() {   
    
    this.setLayout(new BorderLayout());

    buttons = new ArrayList<>();
    statusLabel = new JLabel("Game On", SwingConstants.CENTER);

    this.add(statusLabel, BorderLayout.NORTH);
    this.add(createButtonPanel(), BorderLayout.CENTER);

}

private JPanel createButtonPanel() {

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(8,8));

    for(int i = 0; i <= 63; i++) {
        
        int row = i / 8;
        int col = i % 8;
        char collumnlabel = (char) ('A' + col);
        int rowlabel = 8 - row;
        JButton button = new JButton(collumnlabel + String.valueOf(rowlabel));
        button.addActionListener(new ButtonClickListener());
        buttons.add(button);
        buttonPanel.add(button);

        // Set alternating colors for a chessboard pattern
         if ((row + col) % 2 == 0) {
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE); // White text on black background
            } else {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK); // Black text on white background
            }
        }

        return buttonPanel;

    }


private class ButtonClickListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        statusLabel.setText("You Clicked: " + source.getText());
    }
}

}
    

