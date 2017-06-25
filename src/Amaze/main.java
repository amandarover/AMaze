package Amaze;
import javax.swing.JOptionPane;

public class main {
    
    public static void main(String[] args) {
        Maze maze = new Maze();
        String mode = JOptionPane.showInputDialog("Escolha tipo de labirinto:\n"
                + "\nDigite 1 : busca em Amplitude"
                + "\nDigite 2 : busca em A*");
        
        switch (mode) {
            case "1": maze.amplitudeSearch();
                break;
            case "2": maze.pathfinding();
                break;
        }
    }
}
