import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    
    public static void main(String[] args) {
        showMainMenu();
    }

    public static void showMainMenu() {
        JFrame menuWindow = new JFrame("Java Chess Launcher");
        menuWindow.setSize(400, 400);
        menuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuWindow.setLocationRelativeTo(null); 
        
        menuWindow.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("JAVA CHESS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        menuWindow.add(title);

        JButton btnPvP = new JButton("User vs User");
        btnPvP.setFont(new Font("Arial", Font.BOLD, 20));
        btnPvP.setBackground(new Color(100, 200, 100)); 
        
        // PvP Button launches normal game
        btnPvP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuWindow.dispose(); 
                // againstAI = false, userIsWhite = true, difficulty = 1 (doesn't matter for PvP)
                startGame(false, true, 1); 
            }
        });

        JButton btnPvAI = new JButton("User vs AI");
        btnPvAI.setFont(new Font("Arial", Font.BOLD, 20));
        btnPvAI.setBackground(new Color(100, 150, 255)); 
        
        btnPvAI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel aiSetupPanel = new JPanel(new GridLayout(3, 1, 10, 10));
                
                String[] colors = {"Play as White", "Play as Black"};
                JComboBox<String> colorBox = new JComboBox<>(colors);
                aiSetupPanel.add(new JLabel("Select your color:"));
                aiSetupPanel.add(colorBox);

                // Stockfish Difficulty Levels
                String[] levels = {"Level 1 (Fast)", "Level 2 (Medium)", "Level 3 (Hard)"};
                JComboBox<String> levelBox = new JComboBox<>(levels);
                aiSetupPanel.add(new JLabel("Select AI Difficulty:"));
                aiSetupPanel.add(levelBox);

                int result = JOptionPane.showConfirmDialog(menuWindow, aiSetupPanel, 
                        "AI Match Setup", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    menuWindow.dispose(); 
                    boolean userIsWhite = colorBox.getSelectedIndex() == 0;
                    int aiDifficulty = levelBox.getSelectedIndex() + 1; 
                    
                    // Launch the game with the chosen settings!
                    startGame(true, userIsWhite, aiDifficulty); 
                }
            }
        });

        JButton btnPvOnline = new JButton("User vs Online (Not Available)");
        btnPvOnline.setFont(new Font("Arial", Font.ITALIC, 16));
        btnPvOnline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(menuWindow, "Coming Soon!", "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menuWindow.add(btnPvP);
        menuWindow.add(btnPvAI);
        menuWindow.add(btnPvOnline);

        menuWindow.setVisible(true);
    }

    // THE FIX: startGame now accepts the AI settings from the menu!
    public static void startGame(boolean againstAI, boolean userIsWhite, int aiDifficulty) {
        System.out.println("Starting Game Backend...");
        Board myBoard = new Board();
        
        // --- BLACK PIECES (Top) ---
        myBoard.setPiece(0, 0, new Rook(false));
        myBoard.setPiece(0, 1, new Knight(false));
        myBoard.setPiece(0, 2, new Bishop(false));
        myBoard.setPiece(0, 3, new Queen(false));
        myBoard.setPiece(0, 4, new King(false));
        myBoard.setPiece(0, 5, new Bishop(false));
        myBoard.setPiece(0, 6, new Knight(false));
        myBoard.setPiece(0, 7, new Rook(false));
        for (int i = 0; i < 8; i++) myBoard.setPiece(1, i, new Pawn(false)); 

        // --- WHITE PIECES (Bottom) ---
        myBoard.setPiece(7, 0, new Rook(true));
        myBoard.setPiece(7, 1, new Knight(true));
        myBoard.setPiece(7, 2, new Bishop(true));
        myBoard.setPiece(7, 3, new Queen(true));
        myBoard.setPiece(7, 4, new King(true));
        myBoard.setPiece(7, 5, new Bishop(true));
        myBoard.setPiece(7, 6, new Knight(true));
        myBoard.setPiece(7, 7, new Rook(true));
        for (int i = 0; i < 8; i++) myBoard.setPiece(6, i, new Pawn(true)); 

        System.out.println("Launching Game Window...");
        
        // THE FIX: Passes all the variables into the GUI!
        new ChessGUI(myBoard, againstAI, userIsWhite, aiDifficulty);
    }
}