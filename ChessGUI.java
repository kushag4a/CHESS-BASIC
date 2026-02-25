import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGUI {
    
    // UI Variables
    private JFrame window;
    private JPanel boardPanel;
    private JButton[][] squares = new JButton[8][8];
    private JPanel topPanel;
    private JPanel bottomPanel;

    // Game Logic Variables
    private int selectedRow = -1;
    private int selectedCol = -1;
    private Board backendBoard; 

    // --- MISSING AI VARIABLES ADDED HERE ---
    private Stockfish aiEngine;
    private boolean isAgainstAI; 
    private boolean userIsWhite; 
    private int aiDifficulty;    
    private boolean aiIsThinking = false; 

    public ChessGUI(Board board, boolean isAgainstAI, boolean userIsWhite, int aiDifficulty) {
        this.backendBoard = board; 
        this.isAgainstAI = isAgainstAI; 
        this.userIsWhite = userIsWhite;
        this.aiDifficulty = aiDifficulty;
        



        //Start the Stockfish engine if it's an AI match
        if (this.isAgainstAI) {
            aiEngine = new Stockfish();
            // Use your exact path to make sure Java finds it!
            if (!aiEngine.startEngine("C:\\programs made\\Java set\\CHESS uJAVA\\stockfish.exe")) {
                System.out.println("ERROR: Could not start Stockfish!");
            }
        }
        window = new JFrame("Java Chess");
        window.setSize(600, 700); 
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        window.setLayout(new BorderLayout());

        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setPreferredSize(new Dimension(600, 50)); 

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(Color.DARK_GRAY);
        bottomPanel.setPreferredSize(new Dimension(600, 50)); 

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton square = new JButton();
                square.setOpaque(true);
                square.setBorderPainted(false); 
                square.setFont(new Font("Arial", Font.BOLD, 40));
                
                final int finalRow = row;
                final int finalCol = col;

                // --- DRAG AND DROP LISTENER ---
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        // Prevent clicking if AI is thinking
                        if (aiIsThinking) return; 
                        handleSquareClick(finalRow, finalCol);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (aiIsThinking) return; 
                        
                        Point p = SwingUtilities.convertPoint(square, e.getPoint(), boardPanel);
                        Component target = boardPanel.getComponentAt(p);
                        
                        if (target instanceof JButton) {
                            for (int r = 0; r < 8; r++) {
                                for (int c = 0; c < 8; c++) {
                                    if (squares[r][c] == target) {
                                        if (selectedRow != -1 && (r != selectedRow || c != selectedCol)) {
                                            handleSquareClick(r, c);
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    }
                });
                
                squares[row][col] = square;
                boardPanel.add(square);
            }
        }
        
        window.add(topPanel, BorderLayout.NORTH);
        window.add(boardPanel, BorderLayout.CENTER);
        window.add(bottomPanel, BorderLayout.SOUTH);
        
        window.setVisible(true);
        updateBoardGUI();

        // If playing AI and user is Black, the AI must make the first move!
        if (this.isAgainstAI && !this.userIsWhite) {
            triggerAIMove();
        }
    }

    private void handleSquareClick(int row, int col) {
        // --- FIRST CLICK: Picking up a piece ---
        if (selectedRow == -1) { 
            Piece p = backendBoard.getPiece(row, col);
            
            if (p != null && p.isWhite() == backendBoard.isWhiteTurn()) {
                // Prevent touching AI's pieces!
                if (isAgainstAI && p.isWhite() != userIsWhite) {
                    return; 
                }
                
                selectedRow = row;
                selectedCol = col;
                squares[row][col].setBackground(Color.YELLOW); 
                
                // DRAW THE HINTS
                for (int r = 0; r < 8; r++) {
                    for (int c = 0; c < 8; c++) {
                        if (p.isValidMove(backendBoard, selectedRow, selectedCol, r, c)) {
                            if (backendBoard.getPiece(r, c) == null) {
                                squares[r][c].setText("●"); 
                                squares[r][c].setForeground(new Color(50, 150, 50)); 
                            } else {
                                squares[r][c].setBackground(new Color(255, 100, 100)); 
                            }
                        }
                    }
                }
            }
        } 
        
        // --- SECOND CLICK: Putting the piece down ---
        else {
            if (row == selectedRow && col == selectedCol) {
                selectedRow = -1;
                selectedCol = -1;
                updateBoardGUI(); 
                return; 
            }

            // Move the user's piece!
            backendBoard.movePiece(selectedRow, selectedCol, row, col);
            
            selectedRow = -1;
            selectedCol = -1;
            updateBoardGUI(); 

            // --- TRIGGER THE AI ---
            // If it's an AI match and it is now the AI's turn...
            if (isAgainstAI && backendBoard.isWhiteTurn() != userIsWhite) {
                triggerAIMove();
            }
        }
    }

    public void updateBoardGUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                
                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(new Color(240, 217, 181)); 
                } else {
                    squares[row][col].setBackground(new Color(181, 136, 99));  
                }

                Piece p = backendBoard.getPiece(row, col);
                if (p != null) {
                    squares[row][col].setText(""); 

                    String colorPrefix = p.isWhite() ? "W" : "B";
                    String pieceName = p.getClass().getSimpleName(); 
                    String filename = colorPrefix + pieceName + ".jpg"; 

                    try {
                        ImageIcon originalIcon = new ImageIcon(filename);
                        Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
                        squares[row][col].setIcon(new ImageIcon(scaledImage));
                    } catch (Exception e) {
                        System.out.println("Could not load image: " + filename);
                        squares[row][col].setText(p.getSymbol()); 
                    }
                    
                    if (p.getSymbol().equalsIgnoreCase("k")) {
                        if (backendBoard.isInCheck(p.isWhite())) {
                            squares[row][col].setBackground(Color.RED); 
                        }
                    }

                } else {
                    squares[row][col].setIcon(null); 
                    squares[row][col].setText("");
                }
            }
        }
        
        // DRAW THE GRAVEYARDS
        topPanel.removeAll();
        bottomPanel.removeAll();
        
        for (Piece p : backendBoard.getCapturedWhite()) {
            topPanel.add(createTinyPieceIcon(p));
        }
        for (Piece p : backendBoard.getCapturedBlack()) {
            bottomPanel.add(createTinyPieceIcon(p));
        }

        topPanel.revalidate();
        topPanel.repaint();
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    private void triggerAIMove() {
        aiIsThinking = true; 
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Give Stockfish thinking time based on difficulty level
                int thinkTime = aiDifficulty * 500; 
                
                String bestMove = aiEngine.getBestMove(backendBoard.getMoveHistory(), thinkTime);
                
                if (bestMove != null && bestMove.length() >= 4) {
                    int startY = bestMove.charAt(0) - 'a'; 
                    int startX = 8 - Character.getNumericValue(bestMove.charAt(1)); 
                    int endY = bestMove.charAt(2) - 'a'; 
                    int endX = 8 - Character.getNumericValue(bestMove.charAt(3)); 
                    
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            backendBoard.movePiece(startX, startY, endX, endY);
                            updateBoardGUI();
                            aiIsThinking = false; 
                        }
                    });
                } else {
                    aiIsThinking = false;
                }
            }
        }).start();
    }

    private JLabel createTinyPieceIcon(Piece p) {
        String colorPrefix = p.isWhite() ? "W" : "B";
        String filename = colorPrefix + p.getClass().getSimpleName() + ".jpg"; 
        JLabel label = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(filename);
            Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            label.setText(p.getSymbol());
            label.setForeground(Color.WHITE);
        }
        return label;
    }
}