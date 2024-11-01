import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BattleShipFrame extends JFrame {
    private int row = 10;
    private int col = 10;

    private JPanel mainPnl;
    private JPanel statusPanel;
    private JPanel boardPnl;
    private JPanel controlPnl;

    private JLabel missLabel, strikeLabel, totalMissLabel, totalHitLabel;
    private BattleShipTile[][] tiles;
    private JButton playAgainButton, quitButton;

    public BattleShipFrame(int row, int col) {
        this.row = row;
        this.col = col;

        setTitle("Battle Ship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);

        // get screen dimensions
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        // center frame in screen
        setLocation((screenWidth - getWidth()) / 2, (screenHeight - getHeight()) / 2);

        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createStatusPanel();
        mainPnl.add(statusPanel, BorderLayout.NORTH);

        createBoardPanel();
        mainPnl.add(boardPnl, BorderLayout.CENTER);

        createControlPanel();
        mainPnl.add(controlPnl, BorderLayout.SOUTH);

        add(mainPnl);

        setVisible(true);
    }

    public BattleShipTile[][] getTiles() {
        return tiles;
    }

    public void resetTiles() {
        for(int row = 0; row < this.row; row++) {
            for(int col = 0; col < this.col; col++) {
                tiles[row][col].setText("~");
                tiles[row][col].setEnabled(true);
                tiles[row][col].setForeground(new Color(35, 168, 199));
            }
        }
    }

    private void createStatusPanel() {statusPanel = new JPanel(new GridLayout(2, 2));
        missLabel = new JLabel("Misses: 0");
        strikeLabel = new JLabel("Strikes: 0");
        totalMissLabel = new JLabel("Total Misses: 0");
        totalHitLabel = new JLabel("Total Hits: 0");

        statusPanel.add(missLabel);
        statusPanel.add(strikeLabel);
        statusPanel.add(totalMissLabel);
        statusPanel.add(totalHitLabel);
    }

    private void createBoardPanel() {
        boardPnl = new JPanel();
        boardPnl.setLayout(new GridLayout(row, col));
        tiles = new BattleShipTile[row][col];
        for (int row = 0; row < this.row; row++) {
            for (int col = 0; col < this.col; col++) {
                tiles[row][col] = new BattleShipTile(row, col);
                tiles[row][col].setFont(new Font("Arial", Font.BOLD, 40));
                tiles[row][col].setText("~");
                tiles[row][col].setForeground(new Color(35, 168, 199));
                boardPnl.add(tiles[row][col]);
            }
        }
    }

    private void createControlPanel() {
        controlPnl = new JPanel();

        quitButton = new JButton("Quit!");
        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));

        playAgainButton = new JButton("Play Again");

        controlPnl.add(playAgainButton);
        controlPnl.add(quitButton);
    }

    public void updateMissLabel(int value) {
        missLabel.setText("Misses: " + value);
    }

    public void updateStrikeLabel(int value) {
        strikeLabel.setText("Strikes: " + value);
    }

    public void updateTotalMissLabel(int value) {
        totalMissLabel.setText("Total Misses: " + value);
    }

    public void updateTotalHitLabel(int value) {
        totalHitLabel.setText("Total Hits: " + value);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public JButton getPlayAgainButton() {
        return playAgainButton;
    }
}
