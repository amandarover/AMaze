package Amaze;

import java.util.ArrayList;

public class Maze {

    private Position maze [][];
    private Robot robot;
    private ArrayList<Position> la = new ArrayList<>();
    private ArrayList<Position> lf = new ArrayList<>();
    
    public Maze () {
        prepareMaze();
        createObstacles();
        createEnergies();
        robot = new Robot(maze[0][0]);
    }
    
    private void prepareMaze() {
        maze = new Position [10][10];
        
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                maze[i][j] = new Position(false, 0, i, j);
            }
        }
    }

    private void createObstacles() {
        int numObstaculos = ((int)(Math.random() * 15)) + 10;
        
        int n = 0;
        while (n < numObstaculos) {
            int i = ((int)(Math.random() * 10));
            int j = ((int)(Math.random() * 10));
            if (!(maze[i][j].isObstacle() 
                    || (i == 0 && j == 0) 
                    || (i == 9 && j == 9))) {
                maze[i][j].setObstacle(true);
                maze[i][j].setStatus("[X]");
                n++;
            }
        }
    }

    private void createEnergies() {
        int num5Energias = 5;
        int num10Energias = 3;
        
        int n5 = 0;
        while (n5 < num5Energias) {
            int i = ((int)(Math.random() * 10));
            int j = ((int)(Math.random() * 10));
            
            if (!(maze[i][j].isObstacle()
                    || (maze[i][j].getEnergy() > 0)
                    || (i == 0 && j == 0) 
                    || (i == 9 && j == 9))) {
                maze[i][j].setEnergy(5);
                maze[i][j].setStatus("["+5+"]");
                n5++;
            }
        }
        
        int n10 = 0;
        while (n10 < num10Energias) {
            int i = ((int)(Math.random() * 10));
            int j = ((int)(Math.random() * 10));
            
            if (!(maze[i][j].isObstacle()
                    || (maze[i][j].getEnergy() > 0)
                    || (i == 0 && j == 0) 
                    || (i == 9 && j == 9))) {
                maze[i][j].setEnergy(10);
                maze[i][j].setStatus("["+10+"]");
                n10++;
            }
        }
    }
    
    private void printMaze () {
        System.out.println("\n\n\nEnergia: " + robot.getEnergy());
        
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                System.out.print(maze[i][j].getStatus() + " ");
            }
            System.out.println("");
        }
    }
    
    private void currentMazeSnapshot(Position ex) {
        ex.setStatus("[o]");
        printMaze();
    }
    
    private Position goToNextPosition () {
        Position ex = removeAlreadyPassedPosition();
        la.remove(0);
        lf.add(ex);
        return ex;
    }
    
    private Position removeAlreadyPassedPosition () {
        Position ex = la.get(0);
        while (!la.isEmpty() && lf.contains(ex)) {
            la.remove(0);
            ex = la.get(0);
        }
        return ex;
    }
    
    private boolean reachedTheEnd (ArrayList <Position> newPositions) {
        for (Position p : newPositions) {
            if ( p.equals(maze[9][9]) ) {
                robot.walk(p);
                currentMazeSnapshot(newPositions.get(0));
                System.out.println("SOLUÇAO ENCONTRADA <3");
                return true;
            }
        }
        return false;
    }
    
    public boolean amplitudeSearch () {
        la.add(robot.getCurrentPosition());
        printMaze();
        
        while (!la.isEmpty() && robot.getEnergy()> 0) {
            Position ex = goToNextPosition();
            if (robot.walk(ex)) {
                currentMazeSnapshot(ex);
                ArrayList<Position> newPositions = searchForNewPositionAmplitude(ex);
                if (newPositions.isEmpty()) {
                    robot.deadEnd();
                    return false;
                }
                if (reachedTheEnd(newPositions)) {
                    return true;
                } else {
                    la.addAll(newPositions);
                }
            }
        }
        robot.deadWithouEnergy();
        return false;
    }
    
    private ArrayList <Position> searchForNewPositionAmplitude (Position ex) {
        int i = ex.getI();
        int j = ex.getJ();
        ArrayList<Position> newPositions = new ArrayList<>();
        if ( i < 9 && !(maze[i+1][j].isObstacle()
                || lf.contains(maze[i+1][j]))) { //BAIXO
            newPositions.add(maze[i+1][j]);
        }
        if ( j < 9 && !(maze[i][j+1].isObstacle() //DIREITA
                || lf.contains(maze[i][j+1]))) {
            newPositions.add(maze[i][j+1]);
        }
        if ( i > 0 && !(maze[i-1][j].isObstacle() //CIMA
                || lf.contains(maze[i-1][j]))) {
            newPositions.add(maze[i-1][j]);
        }
        if ( j > 0 && !(maze[i][j-1].isObstacle() //ESQUERDA
                || lf.contains(maze[i][j-1]))) {
            newPositions.add(maze[i][j-1]);
        }
        return newPositions;
    }

    public boolean pathfinding() {
        la.add(robot.getCurrentPosition());
        printMaze();
        
        while (!la.isEmpty() && robot.getEnergy()> 0) {
            Position ex = goToNextPosition();
            if (robot.walk(ex)) {
                currentMazeSnapshot(ex);
                ArrayList<Position> newPositions = searchForNewPositionStarAlgorithm(ex);
                if (newPositions.isEmpty()) {
                    robot.deadEnd();
                    return false;
                }
                if (reachedTheEnd(newPositions)) {
                    return true;
                } else {
                    Position nextPosition = lowestHValuePosition(newPositions);
                    la.add(nextPosition);
                }
            }
        }
        robot.deadWithouEnergy();
        return false;
    }
    
    private ArrayList <Position> searchForNewPositionStarAlgorithm (Position ex) {
        int h;
        int i = ex.getI();
        int j = ex.getJ();
        ArrayList<Position> newPositions = new ArrayList<>();
        if ( i < 9 && !(maze[i+1][j].isObstacle()
                || lf.contains(maze[i+1][j]))) {
            h = (10 - (i+1)) + (10 - j);
            maze[i+1][j].setH(h);
            newPositions.add(maze[i+1][j]);
        }
        if ( j < 9 && !(maze[i][j+1].isObstacle()
                || lf.contains(maze[i][j+1]))) {
            h = (10 - i) + (10 - (j+1));
            maze[i][j+1].setH(h);
            newPositions.add(maze[i][j+1]);
        }
        if ( i > 0 && !(maze[i-1][j].isObstacle()
                || lf.contains(maze[i-1][j]))) {
            h = (10 - (i-1)) + (10 - j);
            maze[i-1][j].setH(h);
            newPositions.add(maze[i-1][j]);
        }
        if ( j > 0 && !(maze[i][j-1].isObstacle()
                || lf.contains(maze[i][j-1]))) {
            h = (10 - i) + (10 - (j-1));
            maze[i][j-1].setH(h);
            newPositions.add(maze[i][j-1]);
        }
        return newPositions;
    }
    
    private Position lowestHValuePosition (ArrayList<Position> newPositions) {
        int hMenor = 1000;
        Position nexPosotion = null;
        for (int i = 0; i < newPositions.size(); i++) {
            if (newPositions.get(i).getH() < hMenor) {
                hMenor = newPositions.get(i).getH();
                nexPosotion = newPositions.get(i);
            }
        }
        return nexPosotion;
    }
    
    
    // BUSCA POR RECURSIVIDADE
    
    public void amplitudeAlgorithmRecursivity () {
        start(robot.getCurrentPosition());
    }

    public boolean start (Position ex) {
        if (ex == null) {
            return false;
        } else if (arrived()) {
            return true;
        } else {
            ex.setStatus("[o]");
            lf.add(ex);
            robot.walk(ex);
            int i = ex.getI();
            int j = ex.getJ();
            if ( i < 9 && !(maze[i+1][j].isObstacle() //BAIXO
                    || lf.contains(maze[i+1][j]))) {
                printMaze();
                return start(maze[i+1][j]);
            }
            if ( j < 9 && !(maze[i][j+1].isObstacle() //DIREITA
                    || lf.contains(maze[i][j+1]))) {
                printMaze();
                return start(maze[i][j+1]);
            }
            if ( i > 0 && !(maze[i-1][j].isObstacle() //CIMA
                    || lf.contains(maze[i-1][j]))) {
                printMaze();
                return start(maze[i-1][j]);
            }
            if ( j > 0 && !(maze[i][j-1].isObstacle() //ESQUERDA
                    || lf.contains(maze[i][j-1]))) {
                printMaze();
                return start(maze[i][j-1]);
            }
            printMaze();
            return arrived();
        }
    }

    private boolean arrived () {
        for (Position p : lf) {
            if ( p.getI() == 9 && p.getJ() == 9 ) {
                System.out.println("p: i= " + p.getI() + " j= " + p.getJ());
                System.out.println("SOLUÇAO ENCONTRADA <3");
                return true;
            }
        }
        return false;
    }
}
