package labirinto;

import java.util.ArrayList;

public class Labirinto {

    private Posicao labirinto [][];
    private Robo robo;
    private ArrayList<Posicao> la = new ArrayList<>();
    private ArrayList<Posicao> lf = new ArrayList<>();
    
    public Labirinto () {
        preparaLabirinto();
        criaObstaculos();
        criaEnergias();
        robo = new Robo(labirinto[0][0]);
    }
    
    private void preparaLabirinto() {
        labirinto = new Posicao [10][10];
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                labirinto[i][j] = new Posicao(false, 0, i, j);
            }
        }
    }

    private void criaObstaculos() {
        int numObstaculos = ((int)(Math.random() * 15)) + 10;
        
        int n = 0;
        while (n < numObstaculos) {
            int i = ((int)(Math.random() * 10));
            int j = ((int)(Math.random() * 10));
            if (!(labirinto[i][j].isObstaculo() 
                    || (i == 0 && j == 0) 
                    || (i == 9 && j == 9))) {
                labirinto[i][j].setObstaculo(true);
                labirinto[i][j].setStatus("[X]");
                n++;
            }
        }
    }

    private void criaEnergias() {
        int num5Energias = 5;
        int num10Energias = 3;
        
        int n5 = 0;
        while (n5 < num5Energias) {
            int i = ((int)(Math.random() * 10));
            int j = ((int)(Math.random() * 10));
            
            if (!(labirinto[i][j].isObstaculo()
                    || (labirinto[i][j].getEnergia() > 0)
                    || (i == 0 && j == 0) 
                    || (i == 9 && j == 9))) {
                labirinto[i][j].setEnergia(5);
                labirinto[i][j].setStatus("["+5+"]");
                n5++;
            }
        }
        
        int n10 = 0;
        while (n10 < num10Energias) {
            int i = ((int)(Math.random() * 10));
            int j = ((int)(Math.random() * 10));
            
            if (!(labirinto[i][j].isObstaculo()
                    || (labirinto[i][j].getEnergia() > 0)
                    || (i == 0 && j == 0) 
                    || (i == 9 && j == 9))) {
                labirinto[i][j].setEnergia(10);
                labirinto[i][j].setStatus("["+10+"]");
                n10++;
            }
        }
    }
    
    public boolean buscarPorLargura () {
        ArrayList<Posicao> la = new ArrayList<>();
        ArrayList<Posicao> lf = new ArrayList<>();
        la.add(robo.getPosicaoAtual());
        
        while (!la.isEmpty()) {
            Posicao ex = la.get(0);
            while (!la.isEmpty() && lf.contains(ex)) {
                la.remove(0);
                ex = la.get(0);
            }
            la.remove(0);
            ex.setStatus("[o]");
            lf.add(ex);
//            robo.anda(ex);
            
            int i = ex.getI();
            int j = ex.getJ();
            ArrayList<Posicao> posicoesNovas = new ArrayList<>();
            if ( i < 9 && !(labirinto[i+1][j].isObstaculo()
                    || lf.contains(labirinto[i+1][j]))) { //BAIXO
                posicoesNovas.add(labirinto[i+1][j]);
            }
            if ( j < 9 && !(labirinto[i][j+1].isObstaculo() //DIREITA
                    || lf.contains(labirinto[i][j+1]))) {
                posicoesNovas.add(labirinto[i][j+1]);
            }
            if ( i > 0 && !(labirinto[i-1][j].isObstaculo() //CIMA
                    || lf.contains(labirinto[i-1][j]))) {
                posicoesNovas.add(labirinto[i-1][j]);
            }
            if ( j > 0 && !(labirinto[i][j-1].isObstaculo() //ESQUERDA
                    || lf.contains(labirinto[i][j-1]))) {
                posicoesNovas.add(labirinto[i][j-1]);
            }
            for (Posicao p : posicoesNovas) {
                if ( p.equals(labirinto[9][9]) ) {
                    System.out.println("p: i= " + p.getI() + " j= " + p.getJ());
                    System.out.println("SOLUÇAO ENCONTRADA <3");
                    posicoesNovas.get(0).setStatus("[o]");
                    return true;
                }
            }
            if (posicoesNovas.isEmpty()) {
                System.out.println("MORREU!");
                return false;
            }
            posicoesNovas.get(0).setStatus("[o]");
            la.addAll(0, posicoesNovas); //BUSCA POR PROFUNDIDADE (insere no inicio)
            mostraLabirinto();
        }
        return false;
    }
    
    public void buscarPorLarguraRecursividade () {
        start(robo.getPosicaoAtual());
    }

    public boolean start (Posicao ex) {
        if (ex == null) {
            return false;
        } else if (verificaChegada()) {
            return true;
        } else {
            ex.setStatus("[o]");
            lf.add(ex);
    //        robo.anda(ex);
            int i = ex.getI();
            int j = ex.getJ();
            if ( i < 9 && !(labirinto[i+1][j].isObstaculo() //BAIXO
                    || lf.contains(labirinto[i+1][j]))) {
                mostraLabirinto();
                return start(labirinto[i+1][j]);
            }
            if ( j < 9 && !(labirinto[i][j+1].isObstaculo() //DIREITA
                    || lf.contains(labirinto[i][j+1]))) {
                mostraLabirinto();
                return start(labirinto[i][j+1]);
            }
            if ( i > 0 && !(labirinto[i-1][j].isObstaculo() //CIMA
                    || lf.contains(labirinto[i-1][j]))) {
                mostraLabirinto();
                return start(labirinto[i-1][j]);
            }
            if ( j > 0 && !(labirinto[i][j-1].isObstaculo() //ESQUERDA
                    || lf.contains(labirinto[i][j-1]))) {
                mostraLabirinto();
                return start(labirinto[i][j-1]);
            }
            mostraLabirinto();
            return verificaChegada();
        }
    }

    private boolean verificaChegada () {
        for (Posicao p : lf) {
            if ( p.getI() == 9 && p.getJ() == 9 ) {
                System.out.println("p: i= " + p.getI() + " j= " + p.getJ());
                System.out.println("SOLUÇAO ENCONTRADA <3");
                return true;
            }
        }
        return false;
    }
    
    private void mostraLabirinto () {
        String lfResultado = "lf: ";
        for ( Posicao p : lf ) {
            lfResultado += "[" + p.getI() + ", " + p.getJ() + "]; ";
        }
        System.out.println(lfResultado);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(labirinto[i][j].getStatus() + " ");
            }
            System.out.println("");
        }
    }
}
