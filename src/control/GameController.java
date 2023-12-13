package control; // Adjust package as needed

import view.*;
import model.*;
import javax.swing.*;

import java.util.TimerTask;
import java.awt.*;  
import java.awt.event.*;  
//bugs: player 1 board- the hits on the bottom are also displayed on the top board. misses on the bottom are displayed as hits on top. Misses from player 2 are displayed on top board for player 1
//player 2 board: player 1's misses are sometimes displayed as hits on p2's top board
//ships overwrite hits
public class GameController implements CellClickListener { //providing implementation for CellClickListener
    private GUIView guiView; //right now, is player 1's attack board; red = player 2's ships they've downed
    private GUIView guiView2;
    private Ship selectedShip;
    Player p1 = new Player();
    Player p2 = new Player();
    boolean[][] p1hits = new boolean[10][10];  //ships on player 1's board that player 2 has hit
    boolean[][] p2hits = new boolean[10][10];
    private boolean isHorizontal;
    int p1hitCount = 0;
    int p2hitCount = 0;
    int iterations = 0;
    boolean isP1 = true;
    boolean turnActive = false;
    private Timer timer;

    public GameController(GUIView guiView, GUIView guiView2) {
        this.guiView = guiView; 
        guiView.setCellClickListener(this);
        this.guiView2 = guiView2; 
        guiView2.setCellClickListener(this);

        //schedule the timer to update the view every second
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //execute the updateView logic
                updateView(isP1 ? p1 : p2, isP1 ? guiView2 : guiView);

                //stop the timer after the updateView logic has been executed
                timer.stop();
            }
        });
    }
    //implementation, responds to a click on the player's hit board and updates the other player's ship board
    public void CellClick(int row, int col, JPanel cellPanel, GUIView currView) {
        //only listen for clicks once the game has started
        if (turnActive){
            //panel has been clicked, it's player 1's turn...
            if (isP1){
                guiView2.getFrame().setTitle("Player 1"); //changes name of frame to player 1
                //if they clicked the correct grid (bottom)
                if (row > 11){
                    //get the panel of player 2's upper board
                    row -= 12;
                    JPanel myPanel = guiView.getPanel(row, col);
                    //if that gridspace hasn't been hit yet...
                    if (!p2hits[row][col]){
                        //if clicked cell is an enemy (hit), turn red. if not, turn grey
                        if (p2.isOccupied(row, col)){
                            cellPanel.setBackground(Color.RED); //the panel clicked
                            p2hitCount++;
                            myPanel.setBackground(Color.RED);
                        }
                        else{
                            cellPanel.setBackground(Color.GRAY);
                            myPanel.setBackground(Color.GRAY);
                        }
                        p2hits[row][col] = true;
                        isP1 = false;
                    }
                }
            }
            else {
                guiView.getFrame().setTitle("Player 2");
                if (row > 11){
                    row -= 12;
                    JPanel myPanel = guiView2.getPanel(row, col);
                    if (!p1hits[row][col]){
                        if (p1.isOccupied(row, col)){
                            cellPanel.setBackground(Color.RED);
                            p1hitCount++;
                            myPanel.setBackground(Color.RED);
                        }
                        else{
                            cellPanel.setBackground(Color.GRAY);
                            myPanel.setBackground(Color.GRAY);
                        }
                        p1hits[row][col] = true;
                        isP1 = true;
                    }
                }
            }
            //update based on timer
            if (isGameOver()) {
                guiView.getFrame().dispose();
                guiView2.getFrame().dispose();
            if (p1hitCount == 17){ //hit count is for your own board (hits totaled), if you reach 17 you lose
                Player2Win victory = new Player2Win();
            }
            else {
                Player1Win victory = new Player1Win();
            }
            System.out.println("Game Over");
            } else {
                // Schedule the timer to update the view after 1 second
                timer.start();
            
                turnActive = false; 
            }
        }
    }

    private void placeShipsForPlayer(Player player, GUIView currentView) {
        for (ShipType shipType : ShipType.values()) {
            Ship ship = new Ship(shipType);

            getUserInput(player, ship);
            player.addShip(ship, ship.getPosition()[0][0], ship.getPosition()[0][1], ship.isHorizontal());
            updateView(player, currentView);
        }
    }

    private void updateView(Player currentPlayer, GUIView currentView) {
        displayShips(currentPlayer, currentView);
        updateBoard(currentPlayer, currentView);
        
        
        if (currentPlayer == p1 && isP1) {
            guiView2.show();
            guiView.hide();
        } else if (currentPlayer == p2 && !isP1) {
            guiView.show();
            guiView2.hide();
        }
    }

    private void displayShips(Player currentPlayer, GUIView currentView) {
        //top grid of currentView
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                JPanel cellPanel = currentView.getPanel(row, col); //top panel of currentView
    
                // Check if the current cell is occupied by the player's ship
                if (currentPlayer.isOccupied(row, col)) {
                    if (currentView == guiView2){ //player 1 board
                        // Check if the current cell has been hit by the opponent
                        if (cellPanel != null && p1hits[row][col]) {
                            // Display the hit cell on the top board
                            cellPanel.setBackground(Color.RED);
                        }
                        // Display the player's ship on their own board
                        else if (cellPanel != null) cellPanel.setBackground(Color.BLUE);
                    }
                    else{
                        // Check if the current cell has been hit by the opponent
                        if (cellPanel != null && p2hits[row][col]) {
                            // Display the hit cell on the top board
                            cellPanel.setBackground(Color.RED);
                        }
                        // Display the player's ship on their own board
                        else if (cellPanel != null) cellPanel.setBackground(Color.BLUE);
                    }
                }
            }
        }
    }

    private void updateBoard(Player currentPlayer, GUIView currentView) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (currentPlayer.isOccupied(row, col)) {
                    JPanel cellPanel = currentView.getPanel(row, col);
                    if (cellPanel != null){
                        if (currentView == guiView2 && !p1hits[row][col]) cellPanel.setBackground(Color.BLUE);
                        else if (currentView == guiView && !p2hits[row][col]) cellPanel.setBackground(Color.BLUE);
                    }
                }
            }
        }

        //turn logic
        playerTurn();
    }

    //function to ask for user input for ship placement
    private void getUserInput(Player player, Ship ship) {
    boolean validInput = false;

    while (!validInput) {
        try {
            JPanel panel = new JPanel(new GridLayout(0, 1));

            // Display ship information
            panel.add(new JLabel("Placing " + ship.getType() + " (" + ship.getSize() + " spaces)"));

            // add all components to Jlabel.
            JTextField xField = new JTextField();
            JTextField yField = new JTextField();
            panel.add(new JLabel("Enter starting X coordinate (0-9):"));
            panel.add(xField);
            panel.add(new JLabel("Enter starting Y coordinate (0-9):"));
            panel.add(yField);

            //use a Joption pane to diplay the different options
            String[] options = {"Vertical", "Horizontal"};
            int rotationChoice = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    "Rotation",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            boolean isHorizontal = (rotationChoice == 0);

            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());

            if (player.isPlacementValid(ship, x, y, isHorizontal)) {
                ship.rotate();
                ship.setPosition(x, y);
                validInput = true;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid placement. Please choose a different location.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
        }
    }
}
    public void startGame() {
        // place ships for both players
        // tjhis was complicated, since you need to update separately to not overlap the ships
        placeShipsForPlayer(p1, guiView2);

        guiView.hide();
        guiView2.show();

        placeShipsForPlayer(p2, guiView);

        playerTurn();
    }

    private void playerTurn() {
        turnActive = true;
    }

    private boolean isGameOver() {
        if (p1hitCount == 17 || p2hitCount == 17){
            return true;
        }
        return false;
    }

    void displayGameOver(){
        System.out.println("Game over");
    }

}