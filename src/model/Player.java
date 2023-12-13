package model;

public class Player{
    boolean[][] ships;
    boolean [][] shipPlacements; //ti track placed ships
    public Player(){
        this.ships = new boolean[10][10];
        this.shipPlacements = new boolean[10][10];
    }
    public boolean isOccupied(int x, int y){
        if (ships[x][y]) return true;
        return false;
    }
    public void addShip(Ship ship, int x, int y, boolean isHorizontal) {
        if (isPlacementValid(ship, x, y, isHorizontal)) {
            // update ship placement
            updateShipPlacements(ship, x, y, isHorizontal);
    
            // add ships
            int size = ship.getSize();
            for (int i = 0; i < size; i++) {
                if (isHorizontal) {
                    ships[x][y + i] = true;
                    ship.getPosition()[i] = new int[]{x, y + i};
                } else {
                    ships[x + i][y] = true;
                    ship.getPosition()[i] = new int[]{x + i, y};
                }
            }
        }
    }

    public boolean isPlacementValid(Ship ship, int x, int y, boolean isHorizontal) {
        int size = ship.getSize();
    
        // check if the placement is within the board boundaries
        if (x < 0 || y < 0 || (isHorizontal && y + size > 10) || (!isHorizontal && x + size > 10)) {
            System.out.println("Placement out of bounds");
            return false;
        }
    
        // check if the cells are already occupied
        for (int i = 0; i < size; i++) {
            if (isHorizontal && ships[x][y + i]) {
                System.out.println("Occupied horizontally");
                return false;
            } else if (!isHorizontal && ships[x + i][y]) {
                System.out.println("Occupied vertically");
                return false;
            }
        }
    
        return true;
    }

    private void updateShipPlacements(Ship ship, int x, int y, boolean isHorizontal) {
        // check size and update the ship placement
        int size = ship.getSize();

        for (int i = 0; i < size; i++) {
            if (isHorizontal) {
                shipPlacements[x][y + i] = true;
            } else {
                shipPlacements[x + i][y] = true;
            }
        }
    }
}