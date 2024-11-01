import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BattleShipGame {
    private final int ROW = 10;
    private final int COL = 10;

    private BattleShipBoard battleShipBoard;
    private BattleShipFrame battleShipFrame;

    public BattleShipGame() {
        battleShipBoard = new BattleShipBoard(ROW, COL);
        battleShipFrame = new BattleShipFrame(ROW, COL);
        initializeGame();
    }

    private void initializeGame() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                battleShipFrame.getTiles()[row][col].addActionListener((e) -> fireMissile(e));
            }
        }
        battleShipFrame.getPlayAgainButton().addActionListener(e -> playAgain());;
    }

    private void fireMissile (ActionEvent e) {
        BattleShipTile clickedTile = (BattleShipTile) e.getSource();
        int row = clickedTile.getRow();
        int col = clickedTile.getCol();

        boolean[] attackResult = battleShipBoard.processAttack(row, col);
        boolean hit = attackResult[0];
        boolean sunk = attackResult[1];

        if (hit) {
            battleShipFrame.updateTotalHitLabel(battleShipBoard.getTotalHitCounter());
            clickedTile.setText("X");
            clickedTile.setForeground(Color.RED);
            if (sunk) {
                battleShipFrame.showMessage("You've sunk a ship!");
            }
        } else {
            battleShipFrame.updateMissLabel(battleShipBoard.getMissCounter());
            battleShipFrame.updateTotalMissLabel(battleShipBoard.getTotalMissCounter());
            battleShipFrame.updateStrikeLabel(battleShipBoard.getStrikeCounter());
            clickedTile.setText("M");
            clickedTile.setForeground(Color.YELLOW);
        }

        if (battleShipBoard.isGameOver()) {
            if (battleShipBoard.getStrikeCounter() == 3) {
                battleShipFrame.showMessage("You lost the game!");
            } else {
                battleShipFrame.showMessage("You won!");
            }
            resetGame();
        }

        //clickedTile.setEnabled(false);
    }

    private void playAgain() {
        int result = JOptionPane.showConfirmDialog(battleShipFrame, "Are you sure?",
                "Play Again?", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            resetGame();  // Restart the game if player wants to play again
        }
    }

    private void resetGame() {
        battleShipBoard.resetBoard();
        battleShipFrame.updateMissLabel(0);
        battleShipFrame.updateStrikeLabel(0);
        battleShipFrame.updateTotalMissLabel(0);
        battleShipFrame.updateTotalHitLabel(0);
        battleShipFrame.resetTiles();
    }
}
