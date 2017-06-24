package labirinto;

import javax.swing.JOptionPane;

public class main {
    
    public static void main(String[] args) {
        Labirinto labirinto = new Labirinto();
        String mode = JOptionPane.showInputDialog("Escolha tipo de labirinto:\n"
                + "\nDigite 1 : busca em Amplitude"
                + "\nDigite 2 : busca em A*");
        
        switch (mode) {
            case "1": labirinto.buscarPorLargura();
                break;
            case "2": labirinto.algoritmoEstrela();
                break;
        }
    }
}
