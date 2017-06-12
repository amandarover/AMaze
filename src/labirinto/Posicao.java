package labirinto;

public class Posicao {
    
    private boolean obstaculo;
    private int energia;
    private int i;
    private int j;
    private String status;

    public Posicao(boolean obstaculo,int energia, int i, int j) {
        this.obstaculo = obstaculo;
        this.energia = energia;
        this.i = i;
        this.j = j;
        if (energia>0){
            this.status = "["+energia+"]";
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

    public boolean isObstaculo() {
        return obstaculo;
    }

    public void setObstaculo(boolean obstaculo) {
        this.obstaculo = obstaculo;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
