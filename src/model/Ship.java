package model;

public class Ship {
    private ShipType type;
    private int size;
    private int[][] position;  // Coordinates of ship cells
    private boolean isHorizontal;

    public Ship(ShipType type) {
        this.type = type;
        switch(type){
            case AIRCRAFT_CARRIER: //this is the enum, so there are 5 types of ships
                this.size = 5;
                break;
            case BATTLESHIP:
                this.size = 4;
                break;
            case SUBMARINE:
                this.size = 3;
                break;
            case CRUISER:
                this.size = 2;
                break;
            case DESTROYER:
                this.size = 3;
                break;
        }
        this.position = new int[size][2];
        this.isHorizontal = true; 
    }

    public void rotate() {
        //rotate the ship 90 degree (just setting the boolean)
        isHorizontal = !isHorizontal;
    }

    public void setPosition(int x, int y) {
        // Update the position array based on x and y coordinates
        position = new int[size][2]; // Reinitialize the position array
    
        if (isHorizontal) {
            for (int i = 0; i < size; i++) {
                position[i][0] = x + i;
                position[i][1] = y;
            }
        } else {
            for (int i = 0; i < size; i++) {
                position[i][0] = x;
                position[i][1] = y + i;
            }
        }
    }

    public int getSize(){
        return size;
    }

    public ShipType getType(){
        return type;
    }

    public boolean isHorizontal(){
        return isHorizontal;
    }

    public int[][] getPosition() {
        return position;
    }
}
