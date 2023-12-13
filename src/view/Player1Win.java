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


public class Player1Win {
    private JFrame frame;

    public Player1Win() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Victory");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // load image
            ImageIcon backgroundImage = new ImageIcon(getClass().getResource("Victory.png"));

            // create layerd pane to place image on
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(backgroundImage.getIconWidth()/2, backgroundImage.getIconHeight()/2));
            //layeredPane.setPreferredSize(new Dimension(300, 533));
            // create a label for the background image
            JLabel backgroundLabel = new JLabel(backgroundImage);
            backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth()/2, backgroundImage.getIconHeight()/2);
            layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
            // exit button so user can leave
            JButton exitButton = new JButton("Exit");
            exitButton.setBounds(380, 450, 200, 60);
            exitButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });
            layeredPane.add(exitButton, JLayeredPane.PALETTE_LAYER);

            // set content to the layerd pane just created
            frame.setContentPane(layeredPane);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}