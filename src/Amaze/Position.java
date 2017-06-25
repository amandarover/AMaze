package Amaze;

public class Position {
    
    private boolean obstacle;
    private int energy;
    private int i;
    private int j;
    private String status;
    private int h;

    public Position(boolean obstacle,int energy, int i, int j) {
        this.obstacle = obstacle;
        this.energy = energy;
        this.i = i;
        this.j = j;
        if (energy > 0){
            this.status = "["+energy+"]";
        } else {
            this.status = "[ ]";
        }
    }
    
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public void setObstacle(boolean obstaculo) {
        this.obstacle = obstaculo;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
}
