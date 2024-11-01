import java.util.ArrayList;

public class BattleShip {
    private int length;
    private int hits;
    private ArrayList<int[]> positions;

    public BattleShip(int length) {
        this.length = length;
        this.hits = 0;
        this.positions = new ArrayList<>();
    }

    public void addPosition(int row, int col) {
        positions.add(new int[] {row, col});
    }

    public void hit() {
        hits++;
    }

    public boolean isSunk() {
        return hits >= length;
    }

    public int getLength() {
        return length;
    }

    public ArrayList<int[]> getPositions() {
        return positions;
    }
}
