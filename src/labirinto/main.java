package labirinto;

import javax.swing.JOptionPane;

public class main {
    
    public static void main(String[] args) {
        Labirinto labirinto = new Labirinto();
        String mode = JOptionPane.showInputDialog("Escolha tipo de labirinto:\n"
                + "\nDigite 1 : busca em Profundidade"
                + "\nDigite 2 : busca em Recursividade"
                + "\nDigite 3 : busca em Amplitude (DEMO)");
        
        switch (mode) {
            case "1": labirinto.buscarPorLargura();
                break;
            case "2": labirinto.buscarPorLarguraRecursividade();
                break;
            case "3": 
                break;
        }
    }
}
