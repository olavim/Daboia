package daboia.algorithm;

import java.util.*;
import daboia.domain.Piece;
import daboia.DaboiaLogic;
import java.awt.Point;
 
public class Berserk extends DaboiaLogic {
    
    private final ArrayList<String> moves = new ArrayList<>();
    
    @Override
    public void onLaunch() {
    }
 
    @Override
    public String getMove() { 
        
        Piece apple = getGameInstance().getApple();
        
        // Koitetaan syödä apple
        String move = nextMove(apple.x, apple.y);
            
        if (!moveIsOk(move)) {

            // Jos häntäänkään ei ole reittiä, kupataan ja tehdään passelia siksakkia kunnes reitti löytyy.
            move = stall();

            if (move == null) {
                // kuppaa() on rajoitettu niin, ettei sen takia liikuta yhteen directionan liikaa.
                // Liikaa on esimerkiksi se, että mato liikkuu alueen ylälaidasta alalaitaan, ja siten asettaa itsensä ansaan.
                // Jos kuitenkin on mahdotonta tehdä move näillä rajoituksilla, laitetaan movehistoriaan "fyllinki",
                // jonka jälkeen tämä rajoitus käytännössä poistuu.
                moves.add("fyllinki");
                move = stall();                    
            }
        }
        
        // Peli on hävitty
        if (move == null) {
            return "UP";
        }
        
        moves.add(move);
        return move;
        
    }
    
    // Tarkastaa, että ruutuun saa liikkua, ja että siinä on liikkumavaraa yli (madon koko + 20) ruutua.
    // Luku 20 on valittu, sillä se vain on hyväksi todettu.
    private boolean moveIsOk(String move) {
        if (move == null) {
            return false;
        }
        
        int[][] board = getGameInstance().getBoard();
        Piece snakeHead = getPlayer().getSnake().getHead();
        
        int x = 0;
        int y = 0;
        
        for (int i = 0; i < 4; i++) {
            if (move.equals(SnakeLogicUtils.direction(i))) {
                x = SnakeLogicUtils.placeX(snakeHead.x, i);
                y = SnakeLogicUtils.placeY(snakeHead.y, i);
                break;
            }
        }
        
        boolean canMove = SnakeLogicUtils.canMove(board, x, y);
        int possibilities = SnakeLogicUtils.possibilities(board, x, y, new ArrayList<>());
        
        return canMove && possibilities > getPlayer().getSnake().getLength() + 20;
    }
    
    // Seuraava best move A* -algoritmilla
    public String nextMove(int goalX, int goalY) {
        int[][] board = getGameInstance().getBoard();
        
        PriorityQueue<Location> queue = new PriorityQueue<>();        
        ArrayList<Location> visited = new ArrayList<>();
        Location location;
{
        int headX = getPlayer().getSnake().getHead().x;
        int headY = getPlayer().getSnake().getHead().y;
        int score = SnakeLogicUtils.calculateScore(headX, goalX, headY, goalY);
        location = new Location(headX, headY, score, new ArrayList<>());
}        
        queue.add(location);
        
        while (!queue.isEmpty()) {
            location = queue.poll();
            
            if (location.x() == goalX && location.y() == goalY) {
                return location.route().get(0);
            }
            
            visited.add(location);            
            
            for (int i = 0; i < 4; i++) {
                int x = SnakeLogicUtils.placeX(location.x(), i);
                int y = SnakeLogicUtils.placeY(location.y(), i);
                
                if (SnakeLogicUtils.canMove(board, x, y)) {
                    ArrayList<String> route = new ArrayList<>(location.route());
                    route.add(SnakeLogicUtils.direction(i));
                    
                    int score = SnakeLogicUtils.calculateScore(x, goalX, y, goalY) + route.size();
                    
                    Location k = new Location(x, y, score, route);
                    
                    if (!visited.contains(k) && !queue.contains(k)) {
                        queue.add(k);
                    }
                }
            }
        }
        
        return null;
    }
    
    // Valitsee siirron, josta seuraa mahdollisimman paljon liikkumavaraa
    private String stall() {
        int[][] board = getGameInstance().getBoard();
        
        Piece snakeHead = getPlayer().getSnake().getHead();
        
        int max = 0;
        int bestIndex = -1;
        
        for (int i = 0; i < 4; i++) {
            int x = SnakeLogicUtils.placeX(snakeHead.x, i);
            int y = SnakeLogicUtils.placeY(snakeHead.y, i);
            
            int possibilities = SnakeLogicUtils.possibilities(board, x, y, new ArrayList<>());
            int numSame = SnakeLogicUtils.numSame(moves, SnakeLogicUtils.direction(i));
            
            if (possibilities > max && numSame <= 4) {
                max = possibilities;
                bestIndex = i;
            }
        }
        
        if (bestIndex == -1) {
            return null;
        }
        
        return SnakeLogicUtils.direction(bestIndex);
    }
   
}

class Location implements Comparable {
    private final int x;
    private final int y;
    private final int score;
    private final ArrayList<String> route;
     
    public Location(int x, int y, int score, ArrayList<String> route) {
        this.x = x;
        this.y = y;
        this.score = score;
        this.route = route;
    }
     
    public int x() {
        return this.x;
    }
     
    public int y() {
        return this.y;
    }
    
    public int score() {
        return this.score;
    }
    
    public ArrayList<String> route() {
        return this.route;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof Location)) {
            return false;
        }
        
        Location k = (Location) o;
        
        return this.hashCode() == k.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.x;
        hash = 29 * hash + this.y;
        return hash;
    }
    
    @Override
    public int compareTo(Object o) {
        Location k = (Location) o;
        return Integer.compare(this.score(), k.score());
    }
}

class SnakeLogicUtils {

    public final static int[][] directionMatrix = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
    public final static String[] directions = new String[] { "UP", "RIGHT", "DOWN", "LEFT" };

    // Kuinka mones peräjälkeinen sama siirto tämä siirto on
    public static int numSame(ArrayList<String> moves, String move) {
        int hits = 0;
        for (int i = moves.size() - 1; i >= 0; i--) {
            if (moves.get(i).equals(move)) {
                hits++;
            } else {
                break;
            }
        }
        
        return hits;
    }
    
    // Rekursiivinen dfs; kuinka moneen ruutuun *tästä* ruudusta voi päästä
    public static int possibilities(int[][] board, int x, int y, ArrayList<Point> visited) {
        if (!canMove(board, x, y) || visited.contains(new Point(x, y))) {
            return 0;
        }
        
        visited.add(new Point(x, y));
        
        int sum = 1;
        
        for (int i = 0; i < 4; i++) {
            sum += possibilities(board, placeX(x, i), placeY(y, i), visited);
        }
        
        return sum;
    }
    
    // Tarkastaa onko ruutu tyhjä tai omena
    public static boolean canMove(int[][] board, int x, int y) {
        if (x < 0 || x >= board[0].length || y < 0 || y >= board.length) {
            return false;
        }
        
        return board[y][x] == 0 || board[y][x] == 8;
    }
    
    // Manhattan distance
    public static int calculateScore(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    
    public static int[][] copyArr(int[][] arr) {
        int[][] arr2 = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr2.length; i++) {
            System.arraycopy(arr[i], 0, arr2[i], 0, arr2[i].length);
        }
        return arr2;
    }
    
    public static String direction(int i) {
        return directions[i];
    }
    
    public static int placeX(int x, int i) {
        return x + directionMatrix[i][1];
    }
    
    public static int placeY(int y, int i) {
        return y + directionMatrix[i][0];
    }
    
}