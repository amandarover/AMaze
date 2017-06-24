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
    
    public boolean anda(Posicao ex) {
        this.energia--;
        absorveEnergia(ex);
        this.posicaoAtual = ex;
        return true;
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
