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
    
    public void anda(Posicao proximaPosicao) {
        this.posicaoAtual = proximaPosicao;
        energia--;
    }

    public int getEnergia() {
        return energia;
    }

    public void ganhaEnergia(int energia) {
        this.energia += energia;
    }
}
