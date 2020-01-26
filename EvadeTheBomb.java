import java.awt.*;
import javax.swing.*;
import javax.swing.JPanel.*;
import java.util.Random;
import java.awt.event.*;

/**
 * Evade the bomb game for assignment 3.
 *
 * @author Frankie Cleary
 * @version 12/03/2019
 */
public class EvadeTheBomb extends JFrame
{
    // the variables for the gridpanel
    private final int ROWS = 2;
    private final int COLS = 5;
    private final int GAP = 2;
    private final int NUM = ROWS * COLS;
    
    //the 3 different panels within the frame
    private JPanel gridpanel = new JPanel(new GridLayout(ROWS,COLS, GAP,GAP));
    private JPanel playPanel = new JPanel();   
    private JPanel difficultyPanel = new JPanel();
    
    //the panels that fit within the gridpanel for the game to be played on
    private JPanel [] panel = new JPanel[NUM];
    
    //variables for game functionality
    private int winAmount = 7;
    private JLabel difficulty = new JLabel("Intermediate", SwingConstants.CENTER);
    private JLabel gameState = new JLabel("Game in progress");
    private int clickCounter = 0;
    private boolean gameOver = false;
    private int bombsquare;
    
    /**
     * Create the game board, used on startup to make the panels and add mouselisteners to each
     */
    public void setBoard()
    {
        for(int x = 0; x < NUM; x++) {
            panel[x] = new JPanel();
            gridpanel.add(panel[x]);
            panel[x].setBackground(Color.YELLOW);
            panel[x].setName(Integer.toString(x));
            panel[x].addMouseListener(new MouseAdapter() {
                //assign a mouse listener to each box in the grid
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    int count = mouseEvent.getClickCount();
                    String sourcePanel = ((Component)mouseEvent.getSource()).getName();
                    if (count == 1 && gameOver == false && ((Component)mouseEvent.getSource()).getBackground() != Color.GREEN) {
                        
                        if (Integer.parseInt(sourcePanel) == bombsquare) {
                            panel[Integer.parseInt(sourcePanel)].setBackground(Color.RED);
                            loss();
                        } 
                        else {
                            panel[Integer.parseInt(sourcePanel)].setBackground(Color.GREEN);
                            clickCounter += 1;
                            if (clickCounter == winAmount)
                                win();
                        }
                    }
                }
            });
        }
    }
    
    /**
     * reset function in order to restart the game and make it playable after a loss/win
     */
    public void reset()
    {
        Random rand = new Random(); 
        bombsquare = rand.nextInt(9);
        clickCounter = 0;
        gameOver = false;
        gameState.setForeground(Color.WHITE);
        gameState.setText("Game in progress");
        for(int x = 0; x < NUM; x++) {
            panel[x].setBackground(Color.YELLOW);
        }
    }
    
    /**
     * Constructor of the frame for class EvadeTheBomb
     */
    public EvadeTheBomb()
    {
        super("Evade The Bomb");
        setSize(600,200);
        setBoard();
        makeFrame();
        setVisible(true);
    }

    /**
     * Add the items to the container which is placed on the frame and place them appropriately.
     * contains the lambda functions for the buttons and their functionality
     */
    public void makeFrame()
    {
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout());
        
        JButton playBT = new JButton("Play A Game");
        playBT.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton exitBT = new JButton("Exit");
        exitBT.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton easyBT = new JButton("Easy");
        easyBT.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton mediumBT = new JButton("Intermediate");
        mediumBT.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton hardBT = new JButton("Hard");
        hardBT.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(gridpanel);

        playPanel.setLayout(new BoxLayout(playPanel, BoxLayout.Y_AXIS));
        add(playPanel, BorderLayout.CENTER);
        playPanel.add(Box.createRigidArea(new Dimension(0,5)));
        playPanel.add(playBT);
        playPanel.add(Box.createRigidArea(new Dimension(0,5)));
        playPanel.add(exitBT);
        playPanel.setBackground(Color.BLUE);
        gameState.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameState.setForeground(Color.WHITE);
        playPanel.add(gameState);
        
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
        add(difficultyPanel, BorderLayout.CENTER); 
        difficultyPanel.add(Box.createRigidArea(new Dimension(0,5)));
        difficultyPanel.add(easyBT, Component.CENTER_ALIGNMENT);
        difficultyPanel.add(Box.createRigidArea(new Dimension(0,5)));
        difficultyPanel.add(mediumBT, Component.CENTER_ALIGNMENT);
        difficultyPanel.add(Box.createRigidArea(new Dimension(0,5)));
        difficultyPanel.add(hardBT, Component.CENTER_ALIGNMENT);
        difficultyPanel.add(Box.createRigidArea(new Dimension(0,20)));
        difficulty.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyPanel.add(difficulty);
        difficultyPanel.setBackground(Color.GREEN);

        playBT.addActionListener(source -> {
            reset();
        });
        exitBT.addActionListener(source -> {
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            System.exit(0); 
        });
        
        easyBT.addActionListener(source -> {
            winAmount = 5;
            difficulty.setText("Easy");
        });
        mediumBT.addActionListener(source -> {
            winAmount = 7;
            difficulty.setText("Intermediate");
        });
        hardBT.addActionListener(source -> {
            winAmount = 9;
            difficulty.setText("Hard");
        });
    }
    
    /**
     * When the player has won the game stop further input and tell them they have won
     */
    public void win() 
    {
        gameOver = true;
        gameState.setForeground(Color.GREEN);
        gameState.setText("You have won!");
    }
    
    /**
     * When the player has lost the game stop further input and tell them they have lost
     */
    public void loss() 
    {
        gameOver = true;
        gameState.setForeground(Color.RED);
        gameState.setText("You have lost, you got " + clickCounter + " points!");
    }
    
}
