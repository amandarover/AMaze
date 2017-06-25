package Amaze;

public class Robot {
    private int energy = 50;
    private Position currentPosition;

    public Robot (Position startingPosition) {
        this.currentPosition = startingPosition;
    }
    
    public Position getCurrentPosition() {
        return currentPosition;
    }
    
    public boolean walk(Position ex) {
        this.energy --;
        absorbsEnergy(ex);
        this.currentPosition = ex;
        return true;
    }

    public void absorbsEnergy(Position ex) {
        this.energy += ex.getEnergy();
    }
    
    public void deadWithouEnergy () {
        System.out.println("MORRI DE CANSAÇO!!");
    }
    
    public void deadEnd () {
        System.out.println("SEM SAÍDA!!");
    }
    
    public int getEnergy() {
        return energy;
    }
}
