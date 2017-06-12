package labirinto;

public class Robo {
    private int energia = 50;
    private Posicao posicaoAtual;

    public Robo (Posicao posicaoInicial) {
        this.posicaoAtual = posicaoInicial;
    }
    
    public Posicao getPosicaoAtual() {
        return posicaoAtual;
    }
    
    public void anda(Posicao ex) {
        this.posicaoAtual = ex;
        this.energia--;
        if (ex.getEnergia() > 0) {
            absorveEnergia(ex);
        }
        if (this.energia == 0) {
            morre();
        }
    }

    public void absorveEnergia(Posicao ex) {
        this.energia += ex.getEnergia();
    }
    
    public void morre () {
        System.out.println("MORRI DE CANSAÇO!!");
    }
    
    public int getEnergia() {
        return energia;
    }
}
