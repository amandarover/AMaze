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
        
        for (int i = 0; i < labirinto.length; i++) {
            for (int j = 0; j < labirinto.length; j++) {
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
    
    private void mostraLabirinto () {
        System.out.println("\n\n\nEnergia: " + robo.getEnergia());
        
        for (int i = 0; i < labirinto.length; i++) {
            for (int j = 0; j < labirinto.length; j++) {
                System.out.print(labirinto[i][j].getStatus() + " ");
            }
            System.out.println("");
        }
    }
    
    private void currentMazeSnapshot(Posicao ex) {
        ex.setStatus("[o]");
        mostraLabirinto();
    }
    
    private Posicao goToNextPosition () {
        Posicao ex = removeAlreadyPassedPosition();
        la.remove(0);
        lf.add(ex);
        return ex;
    }
    
    private Posicao removeAlreadyPassedPosition () {
        Posicao ex = la.get(0);
        while (!la.isEmpty() && lf.contains(ex)) {
            la.remove(0);
            ex = la.get(0);
        }
        return ex;
    }
    
    private boolean reachedTheEnd (ArrayList <Posicao> posicoesNovas) {
        for (Posicao p : posicoesNovas) {
            if ( p.equals(labirinto[9][9]) ) {
                robo.anda(p);
                currentMazeSnapshot(posicoesNovas.get(0));
                System.out.println("SOLUÇAO ENCONTRADA <3");
                return true;
            }
        }
        return false;
    }
    
    public boolean amplitudeSearch () {
        la.add(robo.getPosicaoAtual());
        
        while (!la.isEmpty() && robo.getEnergia() > 0) {
            Posicao ex = goToNextPosition();
            if (robo.anda(ex)) {
                currentMazeSnapshot(ex);
                ArrayList<Posicao> posicoesNovas = searchForNewPositionAmplitude(ex);
                if (posicoesNovas.isEmpty()) {
                    robo.deadEnd();
                    return false;
                }
                if (reachedTheEnd(posicoesNovas)) {
                    return true;
                } else {
                    la.addAll(posicoesNovas);
                }
            }
        }
        robo.deadWithouEnergy();
        return false;
    }
    
    private ArrayList <Posicao> searchForNewPositionAmplitude (Posicao ex) {
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
        return posicoesNovas;
    }

    boolean pathfinding() {
        la.add(robo.getPosicaoAtual());
        mostraLabirinto();
        
        while (!la.isEmpty() && robo.getEnergia() > 0) {
            Posicao ex = goToNextPosition();
            if (robo.anda(ex)) {
                currentMazeSnapshot(ex);
                ArrayList<Posicao> posicoesNovas = searchForNewPositionStarAlgorithm(ex);
                if (posicoesNovas.isEmpty()) {
                    robo.deadEnd();
                    return false;
                }
                if (reachedTheEnd(posicoesNovas)) {
                    return true;
                } else {
                    Posicao proximaPosicao = posicaoMenorH(posicoesNovas);
                    la.add(proximaPosicao);
                }
            }
        }
        robo.deadWithouEnergy();
        return false;
    }
    
    private ArrayList <Posicao> searchForNewPositionStarAlgorithm (Posicao ex) {
        int h;
        int i = ex.getI();
        int j = ex.getJ();
        ArrayList<Posicao> posicoesNovas = new ArrayList<>();
        if ( i < 9 && !(labirinto[i+1][j].isObstaculo()
                || lf.contains(labirinto[i+1][j]))) {
            h = (10 - (i+1)) + (10 - j);
            labirinto[i+1][j].setH(h);
            posicoesNovas.add(labirinto[i+1][j]);
        }
        if ( j < 9 && !(labirinto[i][j+1].isObstaculo()
                || lf.contains(labirinto[i][j+1]))) {
            h = (10 - i) + (10 - (j+1));
            labirinto[i][j+1].setH(h);
            posicoesNovas.add(labirinto[i][j+1]);
        }
        if ( i > 0 && !(labirinto[i-1][j].isObstaculo()
                || lf.contains(labirinto[i-1][j]))) {
            h = (10 - (i-1)) + (10 - j);
            labirinto[i-1][j].setH(h);
            posicoesNovas.add(labirinto[i-1][j]);
        }
        if ( j > 0 && !(labirinto[i][j-1].isObstaculo()
                || lf.contains(labirinto[i][j-1]))) {
            h = (10 - i) + (10 - (j-1));
            labirinto[i][j-1].setH(h);
            posicoesNovas.add(labirinto[i][j-1]);
        }
        return posicoesNovas;
    }
    
    private Posicao posicaoMenorH (ArrayList<Posicao> posicoesNovas) {
        int hMenor = 1000;
        Posicao proxPosicao = null;
        for (int i = 0; i < posicoesNovas.size(); i++) {
            if (posicoesNovas.get(i).getH() < hMenor) {
                hMenor = posicoesNovas.get(i).getH();
                proxPosicao = posicoesNovas.get(i);
            }
        }
        return proxPosicao;
    }
    
    
    
    
    
    // BUSCA POR RECURSIVIDADE
    
    public void amplitudeAlgorithmRecursivity () {
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
            robo.anda(ex);
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
}
