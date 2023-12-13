package view;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.Icon; // interface used to manipulate images
import javax.swing.ImageIcon; // loads images
import control.GameController;


public class Player2Win {
    private JFrame frame;

    public Player2Win() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Victory");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // load image in background
            ImageIcon backgroundImage = new ImageIcon(getClass().getResource("Victory.png"));

            // create layered pane (just like plater 1) to add image (we will be adding a button)
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(backgroundImage.getIconWidth()/2, backgroundImage.getIconHeight()/2));
            //layeredPane.setPreferredSize(new Dimension(300, 533));
            // Create a label for the background image
            JLabel backgroundLabel = new JLabel(backgroundImage);
            backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth()/2, backgroundImage.getIconHeight()/2);
            layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
            // Create exit button
            JButton exitButton = new JButton("Exit");
            exitButton.setBounds(380, 450, 200, 60);
            exitButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });
            layeredPane.add(exitButton, JLayeredPane.PALETTE_LAYER);
            //JPanel to cover up 1 (this shows up as player 2)
            JPanel myPanel = new JPanel();
            myPanel.setPreferredSize(new Dimension(40, 80));
            myPanel.setBackground(Color.BLACK);
            JLabel two = new JLabel("2", SwingConstants.CENTER);
            two.setForeground(Color.WHITE); //making the text white
            two.setFont(new Font("Arial", Font.BOLD, 60)); //setting font and font size
            myPanel.add(two);
            myPanel.setBounds(480, 135, 40, 100);
            layeredPane.add(myPanel, JLayeredPane.PALETTE_LAYER);

            // set the frame's content pane to the layered pane
            frame.setContentPane(layeredPane);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}