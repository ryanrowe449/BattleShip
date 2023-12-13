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


public class StartScreen {
    private JFrame frame;

    public StartScreen() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Battleship Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // load image
            ImageIcon backgroundImage = new ImageIcon(getClass().getResource("Battleship-WMS.png"));

            // crreate layered pane
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(backgroundImage.getIconWidth(), backgroundImage.getIconHeight()));

            // Create a label for the background image
            JLabel backgroundLabel = new JLabel(backgroundImage);
            backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
            layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

            // create Play button (starts up the game)
            JButton playButton = new JButton("Play");
            playButton.setBounds(50, 380, 80, 40);
            playButton.addActionListener(this::playButtonClicked);
            layeredPane.add(playButton, JLayeredPane.PALETTE_LAYER);

            // rules button sends user to webpage
            JButton rulesButton = new JButton("Rules");
            rulesButton.setBounds(150, 380, 80, 40);
            rulesButton.addActionListener(this::rulesButtonClicked);
            layeredPane.add(rulesButton, JLayeredPane.PALETTE_LAYER);

            // set the frame's content pane to the layered pane
            frame.setContentPane(layeredPane);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private void playButtonClicked(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
        frame.dispose(); // close start screen

        GUIView guiView = new GUIView();
        GUIView guiView2 = new GUIView();
        GameController gameController = new GameController(guiView, guiView2);

        // ensure visibility changes happen after GUIs are fully initialized
        SwingUtilities.invokeLater(() -> {
            guiView2.show(); // Show the first GUIView
            guiView.hide(); // Hide the second GUIView
        });

        gameController.startGame(); //start the game
    });
    }

    private void rulesButtonClicked(ActionEvent e) {
        openWebpage("https://www.hasbro.com/common/instruct/battleship.pdf");
    }

    // Rules feature
    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException er) {
            er.printStackTrace();
        }
    }
}