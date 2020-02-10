/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectfour;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Connect Four 
 * January 18, 2018 
 * Jessica Zheng
 *
 */
public class NoGravityMode extends JPanel {
    
    //constants for grid layout values
    private static final int GRID_HEIGHT = 7;
    private static final int GRID_WIDTH = 7;

    //constants for cell status
    private static final int NO_CELL = 0;
    private static final int RED_CELL = 1;
    private static final int YELLOW_CELL = 2;

    JButton menuButton = new JButton("Menu");
    JButton modeButton = new JButton("Modes");
    JButton restartButton = new JButton("Restart");
    JLabel winLabel = new JLabel();
    
    private static JFrame window;
    private int pressed;
    
    JPanel[][] panel = null;
    CustomPanel[][] panels = null;
    private boolean setDisabled = false;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        window = new JFrame("Connect Four - No Gravity");
        NoGravityMode content = new NoGravityMode();
        window.setContentPane(content);
        window.pack();  // Set size of window to preferred size of its contents.
        window.setResizable(false);  // User can't change the window's size.
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    public NoGravityMode() {
        //buttons are hidden until game ends
        menuButton.setVisible(false);
        modeButton.setVisible(false);
        restartButton.setVisible(false);

        setBackground(new Color(255, 204, 204));
        setLayout(new GridLayout(GRID_HEIGHT, GRID_WIDTH));
        setBorder(BorderFactory.createLineBorder(new Color(130, 50, 40), 3));
        
        //initializes panel and panels
        panel = new JPanel[GRID_HEIGHT][GRID_WIDTH];
        panels = new CustomPanel[GRID_HEIGHT][GRID_WIDTH];
        //sets the layout, 6 by 7 grid for buttons and an empty strip at the bottom
        //to place menuButton, modeButton, restartButton and winLabel
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {

                if (i < 6 && j < 7) {
                    panels[i][j] = (CustomPanel) new CustomPanel();
                    add(panels[i][j]);
                } else {
                    panel[i][j] = new JPanel();
                    add(panel[i][j]);
                }

            }

        }
        //menu button action listener
        menuButton.addActionListener(new ActionListener() {
            @Override
            //when menu button is pressed it does the actions below
            public void actionPerformed(ActionEvent e) {
                //opens menu page
                JFrame MainPage = new MainPage();
                MainPage.setVisible(true);
                //disposes this window
                window.dispose();

            }
        });
        //mode button action listener
        modeButton.addActionListener(new ActionListener() {
            @Override
            //when mode button is pressed it does the actions below
            public void actionPerformed(ActionEvent e) {
                //opens mode page
                JFrame modePage = new ModesMenu();
                modePage.setVisible(true);
                //disposes this window
                window.dispose();

            }
        });
        //restart button action listener
        restartButton.addActionListener(new ActionListener() {
            @Override
            //when restart button is pressed it does the actions below
            public void actionPerformed(ActionEvent e) {
                //disposes this window
                window.dispose();
                //reopens a two player no gravity connect four page
                NoGravityMode.main(null);

            }
        });
        //location of where the buttons and labels are placed
        panel[6][2].add(restartButton);

        panel[6][4].add(modeButton);

        panel[6][6].add(menuButton);

        panel[6][0].add(winLabel);
    }
    
    //sets text to who won and what colour
    private void setText(String colour, String win) {
        winLabel.setText(colour + " won " + win + ".");
    }
    
    //when yellow wins it shows a game over pop up and allows the user to recognize
    //that yellow won
    private void yellowWins(String position) {
        JOptionPane.showMessageDialog(this,
                "Yellow wins " + position + "!", "Game Over", JOptionPane.ERROR_MESSAGE);
        //displays the menu, mode and restart button
        menuButton.setVisible(true);
        modeButton.setVisible(true);
        restartButton.setVisible(true);
    }
    
    //when red wins it shows a game over pop up and allows the user to recognize 
    //that red won
    private void redWins(String position) {
        JOptionPane.showMessageDialog(this,
                "Red wins " + position + "!", "Game Over", JOptionPane.ERROR_MESSAGE);
        //displays the menu, mode and restart button
        menuButton.setVisible(true);
        modeButton.setVisible(true);
        restartButton.setVisible(true);
    }
    
    //checks if the game is a tie, if all buttons are filled and none have four
    //horizontally, vertically or diagonally it is a tie
    private boolean checkTieGame() {
        for (int i = 0; i < GRID_HEIGHT - 1; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                if (panels[i][j].getStatus() == NO_CELL) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //displays tie message when there is a tie
    private void tieGameMessage() {
        if (!checkTieGame()) {
            JOptionPane.showMessageDialog(this,
                    "Tie!", "Game Over", JOptionPane.ERROR_MESSAGE);
            //sets it to true therefore it doesn't allow the user to play anything else
            setDisabled = true;
            //displays the menu, mode and restart button
            menuButton.setVisible(true);
            modeButton.setVisible(true);
            restartButton.setVisible(true);
        }
    }
    
    //a function to check who won the game
    private void checkWin() {

        //horizontal check 
        for (int i = 0; i < GRID_HEIGHT - 1; i++) {
            for (int j = 0; j < GRID_WIDTH - 3; j++) {
                if (panels[i][j].getStatus() == RED_CELL && panels[i][j + 1].getStatus() == RED_CELL &&
                        panels[i][j + 2].getStatus() == RED_CELL && panels[i][j + 3].getStatus() == RED_CELL &&
                        setDisabled == false) {
                    //finds four red buttons horizontally lined up, red winss
                    redWins("horizontally");
                    //winLabel displays that red won horizontally
                    setText("Red", "horizontally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == YELLOW_CELL && panels[i][j + 1].getStatus() == YELLOW_CELL &&
                        panels[i][j + 2].getStatus() == YELLOW_CELL && panels[i][j + 3].getStatus() == YELLOW_CELL &&
                        setDisabled == false) {
                    //finds four yellow buttons horizontally lined up, yellow wins
                    yellowWins("horizontally");
                    //winLabel displays that yellow won horizontally
                    setText("Yellow", "horizontally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;

                }
            }
        }
        //vertical check
        for (int i = 0; i < GRID_HEIGHT - 4; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                if (panels[i][j].getStatus() == RED_CELL && panels[i + 1][j].getStatus() == RED_CELL && 
                        panels[i + 2][j].getStatus() == RED_CELL && panels[i + 3][j].getStatus() == RED_CELL &&
                        setDisabled == false) {
                    //finds four red vertically lined up, red wins
                    redWins("vertically");
                    //winLabel displays that red won vertically
                    setText("Red", "vertically");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == YELLOW_CELL && panels[i + 1][j].getStatus() == YELLOW_CELL &&
                        panels[i + 2][j].getStatus() == YELLOW_CELL && panels[i + 3][j].getStatus() == YELLOW_CELL && 
                        setDisabled == false) {
                    //finds four yellow vertically lined up, yellow wins
                    yellowWins("vertically");
                    //winLabel displays that yellow won vertically
                    setText("Yellow", "vertically");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                }
            }
        }
        //bottom left to top right diagonal check 
        for (int i = 3; i < GRID_HEIGHT - 1; i++) {
            for (int j = 0; j < GRID_WIDTH - 3; j++) {
                if (panels[i][j].getStatus() == RED_CELL && panels[i - 1][j + 1].getStatus() == RED_CELL &&
                        panels[i - 2][j + 2].getStatus() == RED_CELL && panels[i - 3][j + 3].getStatus() == RED_CELL &&
                        setDisabled == false) {
                    //finds four red diagonally lined up from bottom left to top right, red wins
                    redWins("diagonally");
                    //winLabel displays that red won diagonally
                    setText("Red", "diagonally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == YELLOW_CELL && panels[i - 1][j + 1].getStatus() == YELLOW_CELL &&
                        panels[i - 2][j + 2].getStatus() == YELLOW_CELL && panels[i - 3][j + 3].getStatus() == YELLOW_CELL &&
                        setDisabled == false) {
                    //finds four yellow diagonally lined up from bottom left to top right, yellow wins
                    yellowWins("diagonally");
                    //winLabel displays that yellow won diagonally
                    setText("Yellow", "diagonally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                }
            }
        }
        //bottom right to top left diagonal check
        for (int i = 3; i < GRID_HEIGHT - 1; i++) {
            for (int j = 3; j < GRID_WIDTH; j++) {
                if (panels[i][j].getStatus() == RED_CELL && panels[i - 1][j - 1].getStatus() == RED_CELL &&
                        panels[i - 2][j - 2].getStatus() == RED_CELL && panels[i - 3][j - 3].getStatus() == RED_CELL &&
                        setDisabled == false) {
                    //finds four red diagonally lined up from bottom right to top left, red wins
                    redWins("diagonally");
                    //winLabel displays that red won diagonally
                    setText("Red", "diagonally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == YELLOW_CELL && panels[i - 1][j - 1].getStatus() == YELLOW_CELL &&
                        panels[i - 2][j - 2].getStatus() == YELLOW_CELL && panels[i - 3][j - 3].getStatus() == YELLOW_CELL &&
                        setDisabled == false) {
                    //finds four yellow diagonally lined up from bottom right to top left, yellow wins
                    yellowWins("diagonally");
                    //winLabel displays that yellow won diagonally
                    setText("Yellow", "diagonally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                }
            }
        }
        //checks if it is a time game
        checkTieGame();
        //if it is a tie game, displays tie game message
        tieGameMessage();

    }
    
    //private custom panel for each JButton
    private class CustomPanel extends JPanel {

        private int status;
        JButton button;

        public CustomPanel() {
            pressed = 0;
            status = NO_CELL;
            button = new JButton();
            
            //places an empty cell image
            //cells are blank when game starts
            try {
                Image emptyCell = ImageIO.read(getClass().getResource("emptyCell.png"));
                button.setIcon(new ImageIcon(emptyCell));

            } catch (Exception ex) {
                System.out.println(ex);
            }
            //adds the JButton
            add(button);
            //button action listener
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //when the value of pressed is even, it'll play red
                    if (pressed % 2 == 0) {
                        try {
                        //checks if it is setDisabled is true of false
                        //if true, means that the game is over and someone has won
                        //or all spots are filled
                            if (!setDisabled) {
                                //pressed increases by 1
                                pressed++;
                                //sets the image and status to red cell
                                Image redCell = ImageIO.read(getClass().getResource("redDot.png"));
                                button.setIcon(new ImageIcon(redCell));
                                button.removeActionListener(this);
                                status = RED_CELL;
                            }
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    } else {
                        try {
                            if (!setDisabled) {
                                //pressed increase by 1
                                pressed++;
                                //sets the image and status to yellow cell
                                Image yellowCell = ImageIO.read(getClass().getResource("yellowDot.png"));
                                button.setIcon(new ImageIcon(yellowCell));
                                button.removeActionListener(this);
                                status = YELLOW_CELL;
                            }
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                    }
                    //if game is not over it'll check for a win
                    if (!setDisabled) {
                        checkWin();
                    }

                }

            });
        }
        
        //gets status for the buttons
        public int getStatus() {
            return status;
        }
        //returns the button
        public JButton getButton() {
            return button;
        }

    }

}
