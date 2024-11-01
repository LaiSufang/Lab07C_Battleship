import java.util.ArrayList;
import java.util.Random;

public class BattleShipBoard {
    private int row;
    private int col;
    private int[][] board;
    private ArrayList<BattleShip> battleShips;
    private int missCounter;
    private int strikeCounter;
    private int totalMissCounter;
    private int totalHitCounter;
    private int totalBattleShipLength;

    public BattleShipBoard(int row, int col) {
        this.row = row;
        this.col = col;
        battleShips = new ArrayList<>();
        resetBoard();
    }

    public void resetBoard() {
        board = new int[row][col];
        battleShips.clear();
        placeShipsRandomly();
        missCounter = 0;
        strikeCounter = 0;
        totalMissCounter = 0;
        totalHitCounter = 0;
    }

    public int getMissCounter() {
        return missCounter;
    }

    public int getStrikeCounter() {
        return strikeCounter;
    }

    public int getTotalMissCounter() {
        return totalMissCounter;
    }

    public int getTotalHitCounter() {
        return totalHitCounter;
    }

    public boolean isGameOver() {
        return strikeCounter == 3 || totalHitCounter == totalBattleShipLength;
    }

    public boolean[] processAttack(int row, int col) {
        boolean hit = false;
        boolean sunk = false;

        if (board[row][col] == 1) {  // 1 indicates a part of a ship
            board[row][col] = 2;      // 2 indicates a hit ship part
            hit = true;
            totalHitCounter++;
            missCounter = 0;

            // Check if this hit sinks a ship
            BattleShip hitShip = findShipByPosition(row, col);
            if (hitShip != null) {
                hitShip.hit();
                if (hitShip.isSunk()) {
                    sunk = true;
                }
            }
        } else {
            board[row][col] = -1; // Mark as a miss
            missCounter++;
            totalMissCounter++;
            if (missCounter == 5) {
                strikeCounter++;
                missCounter = 0;
            }
        }

        return new boolean[] { hit, sunk };
    }

    private BattleShip findShipByPosition(int row, int col) {
        for (BattleShip battleShip : battleShips) {
            for (int[] position : battleShip.getPositions()) {
                if (position[0] == row && position[1] == col) {
                    return battleShip;
                }
            }
        }
        return null;
    }

    private void placeShipsRandomly() {
        Random rand = new Random();
        int[] shipSizes = {2, 3, 3, 4, 5};

        for (int size : shipSizes) {
            boolean placed = false;
            BattleShip ship = new BattleShip(size);
            totalBattleShipLength += size;

            while (!placed) {
                int x = rand.nextInt(row);
                int y = rand.nextInt(col);
                boolean horizontal = rand.nextBoolean();

                if (canPlaceShip(x, y, size, horizontal)) {
                    placeShip(ship, x, y, size, horizontal);
                    battleShips.add(ship);
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                int x = row + i;
                if (x >= this.row || board[x][col] != 0) {
                    return false;
                }
            } else {
                int y = col + i;
                if (y >= this.col || board[row][y] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeShip(BattleShip ship, int row, int col, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                int x = row + i;
                board[x][col] = 1;
                ship.addPosition(x, col);
            } else {
                int y = col + i;
                board[row][y] = 1;
                ship.addPosition(row, y);
            }
        }
    }
}
