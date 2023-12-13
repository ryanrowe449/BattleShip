package view;

import model.Ship;
import control.GameController;
import model.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;

public class GUIView  {
    private static final int GRID_SIZE = 10; // grid size is 10

    private JFrame frame;
    // private Ship selectedShip;
    // private Player currentPlayer;
    //should be 15 enemy spaces
    //array of the board, determines which panels are enemies
    boolean[][] enemies = new boolean[10][10];
    private CellClickListener cellClickListener;
    private JPanel[][] cellPanels = new JPanel[21][10]; //holds reference to cellPanels so I can change the state of any cellPanel

    public void setCellClickListener(CellClickListener listener) {
        this.cellClickListener = listener;
    }

    public GUIView() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Battleship Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            //rules feature
            JLabel rules = new JLabel("Click here to see the rules", SwingConstants.CENTER);
            //setting border
            //int topThickness = 10;
            //int bottomThickness = 10;
            boolean isBottom = false;
            //rules.setBorder(BorderFactory.createMatteBorder(topThickness, 0, bottomThickness, 0, Color.BLACK));
            //when clicked, take the user to a rules page
            rules.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //rules webpage
                openWebpage("https://www.hasbro.com/common/instruct/battleship.pdf");
            }
            });
            frame.add(rules, BorderLayout.NORTH);

            JPanel gridPanel = new JPanel(new GridLayout(22, GRID_SIZE));
            //creating the top board (player's board, shows your ships)
            for (int row = 0; row < 22; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (row != 11 && row != 0){
                        //row 12 is a blank set of JPanels used to divide the boards
                        JPanel cellPanel = new JPanel();
                        cellPanel.setPreferredSize(new Dimension(40, 25));
                        // make black and green to look like a radar grid 
                        cellPanel.setBackground(Color.BLACK);
                        cellPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                        cellPanels[row-1][col] = cellPanel; //store all JPanels except for the ones that represent the dividers between the boards

                        // adding label
                        if (row > 11){
                            row -= 12;
                            isBottom = true;
                        }
                        else{
                            row -= 1;
                            isBottom = false;
                        }
                        JLabel label = new JLabel(String.format("[%d, %d]", row, col), SwingConstants.CENTER);
                        label.setForeground(Color.WHITE);
                        cellPanel.add(label);
                        if (isBottom) row += 12;
                        else row += 1;
                        //adding the gridpanel and mouselistener
                        cellPanel.addMouseListener(new CellMouseListener(row, col, this));
                        gridPanel.add(cellPanel);
                    }
                    else {
                        //create the bottom label "Your Hits"
                        if (row == 11){
                        if (col == 4){
                            JPanel cellPanel = new JPanel();
                            cellPanel.setPreferredSize(new Dimension(30, 20));
                            cellPanel.setBackground(Color.BLACK);
                            JLabel your = new JLabel("YOUR", SwingConstants.CENTER);
                            your.setBackground(Color.BLACK);
                            your.setForeground(Color.GREEN);
                            cellPanel.add(your);
                            gridPanel.add(cellPanel);
                        }
                        else if (col == 5){
                            JPanel cellPanel = new JPanel();
                            cellPanel.setPreferredSize(new Dimension(30, 20));
                            cellPanel.setBackground(Color.BLACK);
                            JLabel hits = new JLabel("HITS", SwingConstants.CENTER);
                            hits.setBackground(Color.BLACK);
                            hits.setForeground(Color.GREEN);
                            cellPanel.add(hits);
                            gridPanel.add(cellPanel);
                        }
                        else{
                            JPanel cellPanel = new JPanel();
                            cellPanel.setPreferredSize(new Dimension(30, 20));
                            cellPanel.setBackground(Color.BLACK);
                            gridPanel.add(cellPanel);
                        }
                        }
                        else {
                            //if row == 0, create the top label for player board
                            if (col == 4){
                                JPanel cellPanel = new JPanel();
                                cellPanel.setPreferredSize(new Dimension(30, 20));
                                cellPanel.setBackground(Color.BLACK);
                                JLabel your = new JLabel("YOUR", SwingConstants.CENTER);
                                your.setBackground(Color.BLACK);
                                your.setForeground(Color.GREEN);
                                cellPanel.add(your);
                                gridPanel.add(cellPanel);
                            }
                            else if (col == 5){
                                JPanel cellPanel = new JPanel();
                                cellPanel.setPreferredSize(new Dimension(30, 20));
                                cellPanel.setBackground(Color.BLACK);
                                JLabel Ships = new JLabel("SHIPS", SwingConstants.CENTER);
                                Ships.setBackground(Color.BLACK);
                                Ships.setForeground(Color.GREEN);
                                cellPanel.add(Ships);
                                gridPanel.add(cellPanel);
                            }
                            else{
                                JPanel cellPanel = new JPanel();
                                cellPanel.setPreferredSize(new Dimension(30, 20));
                                cellPanel.setBackground(Color.BLACK);
                                gridPanel.add(cellPanel);
                            }
                        }
                    }
                }
            }

            frame.add(gridPanel, BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null); // center frame
            frame.setVisible(true);
        });
    }

    public JFrame getFrame(){
        return frame;
    }
    
    public void show(){
        if (frame != null) frame.setVisible(true);
    }
    public void hide(){
        if (frame != null) frame.setVisible(false);
    }

    public JPanel getPanel(int row, int col){
        return cellPanels[row][col];
    }

    // mouseListener for cell panels
    private class CellMouseListener extends MouseAdapter {
        private final int row;
        private final int col;
        private GUIView currView;

        public CellMouseListener(int row, int col, GUIView currView) {
            this.row = row;
            this.col = col;
            this.currView = currView;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (cellClickListener != null) {
                cellClickListener.CellClick(row, col, (JPanel) e.getSource(), currView);
            }
        }
    }
    //rules feature
    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url)); //opening URL (obtained from URI object) in default web browser on user's desktop
        } catch (IOException | URISyntaxException er) {
            er.printStackTrace(); //must be in a try catch block to be exucuted
        }
    }
}