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
 * Connect Four January 18, 2018 Jessica Zheng
 *
 */
public class ConnectFourThreePlayer extends JPanel {

    //constants for grid layout values
    private static final int GRID_HEIGHT = 8;
    private static final int GRID_WIDTH = 9;

    //constants for cell status
    private static final int NO_CELL = 0;
    private static final int RED_CELL = 1;
    private static final int YELLOW_CELL = 2;
    private static final int BLUE_CELL = 3;

    private static JFrame window;
    //variable to see who's turn it is
    int playerTurn = 0;

    JButton menuButton = new JButton("Menu");
    JButton modeButton = new JButton("Modes");
    JButton restartButton = new JButton("Restart");
    JLabel winLabel = new JLabel();

    JPanel[][] panel = null;
    CustomPanel[][] panels = null;
    private boolean setDisabled = false;

    public static void main(String[] args) {
        window = new JFrame("Connect Four - Three Player");
        ConnectFourThreePlayer content = new ConnectFourThreePlayer();
        window.setContentPane(content);
        window.pack();  // Set size of window to preferred size of its contents.
        window.setResizable(false);  // User can't change the window's size.
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    public ConnectFourThreePlayer() {
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
        //sets the layout, 7 by 9 grid for buttons and an empty strip at the bottom
        //to place menuButton, modeButton, restartButton and winLabel
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {

                if (i < 7 && j < 9) {
                    panels[i][j] = (CustomPanel) new CustomPanel(i, j);
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
                //reopens a three player connect four page
                ConnectFourThreePlayer.main(null);

            }
        });
        //location of where the buttons and labels are placed
        panel[7][2].add(restartButton);

        panel[7][4].add(modeButton);

        panel[7][6].add(menuButton);

        panel[7][0].add(winLabel);

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
        menuButton.setVisible(true);
        modeButton.setVisible(true);
        restartButton.setVisible(true);
    }

    //when red wins it shows a game over pop up and allows the user to recognize
    //that red won
    private void redWins(String position) {
        JOptionPane.showMessageDialog(this,
                "Red wins " + position + "!", "Game Over", JOptionPane.ERROR_MESSAGE);
        menuButton.setVisible(true);
        modeButton.setVisible(true);
        restartButton.setVisible(true);
    }

    //when blue wins it shows a game over pop up and allows the user to recognize
    //that blue won
    private void blueWins(String position) {
        JOptionPane.showMessageDialog(this,
                "Blue wins " + position + "!", "Game Over", JOptionPane.ERROR_MESSAGE);
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
                if (panels[i][j].getStatus() == RED_CELL && panels[i][j + 1].getStatus() == RED_CELL
                        && panels[i][j + 2].getStatus() == RED_CELL && panels[i][j + 3].getStatus() == RED_CELL
                        && setDisabled == false) {
                    //finds four red buttons horizontally lined up, red wins
                    redWins("horizontally");
                    //winLabel displays that red won horizontally
                    setText("Red", "horizontally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == YELLOW_CELL && panels[i][j + 1].getStatus() == YELLOW_CELL
                        && panels[i][j + 2].getStatus() == YELLOW_CELL && panels[i][j + 3].getStatus() == YELLOW_CELL
                        && setDisabled == false) {
                    //finds four yellow buttons horizontally lined up, yellow wins
                    yellowWins("horizontally");
                    //winLabel displays that yellow won horizontally
                    setText("Yellow", "horizontally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;

                } else if (panels[i][j].getStatus() == BLUE_CELL && panels[i][j + 1].getStatus() == BLUE_CELL
                        && panels[i][j + 2].getStatus() == BLUE_CELL && panels[i][j + 3].getStatus() == BLUE_CELL
                        && setDisabled == false) {
                    //finds four blue buttons horizontally lined up, blue wins
                    blueWins("horizontally");
                    //winLabel displays that blue won horizontally
                    setText("Blue", "horizontally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                }
            }
        }
        //vertical check
        for (int i = 0; i < GRID_HEIGHT - 4; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                if (panels[i][j].getStatus() == RED_CELL && panels[i + 1][j].getStatus() == RED_CELL
                        && panels[i + 2][j].getStatus() == RED_CELL && panels[i + 3][j].getStatus() == RED_CELL
                        && setDisabled == false) {
                    //finds four red vertically lined up, red wins
                    redWins("vertically");
                    //winLabel displays that red won vertically
                    setText("Red", "vertically");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == YELLOW_CELL && panels[i + 1][j].getStatus() == YELLOW_CELL
                        && panels[i + 2][j].getStatus() == YELLOW_CELL && panels[i + 3][j].getStatus() == YELLOW_CELL
                        && setDisabled == false) {
                    //finds four yellow vertically lined up, yellow wins 
                    yellowWins("vertically");
                    //winLabel displays that yellow won vertically
                    setText("Yellow", "vertically");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == BLUE_CELL && panels[i + 1][j].getStatus() == BLUE_CELL
                        && panels[i + 2][j].getStatus() == BLUE_CELL && panels[i + 3][j].getStatus() == BLUE_CELL
                        && setDisabled == false) {
                    //finds four blue vertically lined up, blue wins
                    blueWins("vertically");
                    //winLabel displays that blue won vertically
                    setText("Blue", "vertically");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                }
            }
        }
        //bottom left to top right diagonal check 
        for (int i = 3; i < GRID_HEIGHT - 1; i++) {
            for (int j = 0; j < GRID_WIDTH - 3; j++) {
                if (panels[i][j].getStatus() == RED_CELL && panels[i - 1][j + 1].getStatus() == RED_CELL
                        && panels[i - 2][j + 2].getStatus() == RED_CELL && panels[i - 3][j + 3].getStatus() == RED_CELL
                        && setDisabled == false) {
                    //finds four red diagonally lined up from bottom left to top right, red wins
                    redWins("diagonally");
                    //winLabel displays that red won diagonally
                    setText("Red", "diagonally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == YELLOW_CELL && panels[i - 1][j + 1].getStatus() == YELLOW_CELL
                        && panels[i - 2][j + 2].getStatus() == YELLOW_CELL && panels[i - 3][j + 3].getStatus() == YELLOW_CELL
                        && setDisabled == false) {
                    //finds four yellow diagonally lined up from bottom left to top right, yellow wins
                    yellowWins("diagonally");
                    //winLabel displays that yellow won diagonally
                    setText("Yellow", "diagonally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == BLUE_CELL && panels[i - 1][j + 1].getStatus() == BLUE_CELL
                        && panels[i - 2][j + 2].getStatus() == BLUE_CELL && panels[i - 3][j + 3].getStatus() == BLUE_CELL
                        && setDisabled == false) {
                    //finds four blue diagonally lined up from bottom left to top right, blue wins
                    blueWins("diagonally");
                    //winLabel displays that blue won diagonally
                    setText("Blue", "diagonally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                }
            }
        }
        //bottom right to top left diagonal check
        for (int i = 3; i < GRID_HEIGHT - 1; i++) {
            for (int j = 3; j < GRID_WIDTH; j++) {
                if (panels[i][j].getStatus() == RED_CELL && panels[i - 1][j - 1].getStatus() == RED_CELL
                        && panels[i - 2][j - 2].getStatus() == RED_CELL && panels[i - 3][j - 3].getStatus() == RED_CELL
                        && setDisabled == false) {
                    //finds four red diagonally lined up from bottom right to top left, red wins
                    redWins("diagonally");
                    //winLabel displays that red won diagonally
                    setText("Red", "diagonally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == YELLOW_CELL && panels[i - 1][j - 1].getStatus() == YELLOW_CELL
                        && panels[i - 2][j - 2].getStatus() == YELLOW_CELL && panels[i - 3][j - 3].getStatus() == YELLOW_CELL
                        && setDisabled == false) {
                    //finds four yellow diagonally lined up from bottom right to top left, yellow wins
                    yellowWins("diagonally");
                    //winLabel displays that yellow won diagonally
                    setText("Yellow", "diagonally");
                    //set to true therefore the user won't be able to play anymore
                    setDisabled = true;
                } else if (panels[i][j].getStatus() == BLUE_CELL && panels[i - 1][j - 1].getStatus() == BLUE_CELL
                        && panels[i - 2][j - 2].getStatus() == BLUE_CELL && panels[i - 3][j - 3].getStatus() == BLUE_CELL
                        && setDisabled == false) {
                    //finds four blue diagonally lined up from bottom right to top left, blue wins
                    blueWins("diagonally");
                    //winLabel displays that blue won diagonally
                    setText("Blue", "diagonally");
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

    //checks the amount of empty cells in that column
    private int checkColumn(int j) {
        int emptyCells = 0;

        for (int i = 0; i < GRID_HEIGHT - 1; i++) {
            if (panels[i][j].getStatus() == NO_CELL) {
                emptyCells++;
            }
        }
        //returns the number of empty cells
        return emptyCells;
    }

    //private custom panel for each JButton
    private class CustomPanel extends JPanel {

        private int status;
        int colNum;
        int rowNum;

        JButton button;

        public CustomPanel(int rowNum1, int colNum1) {
            //receives the passed through column and row int
            colNum = colNum1;
            rowNum = rowNum1;
            //playerTurn is set to 0
            playerTurn = 0;
            //all JButtons are set to NO_CELL
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
                    if (!setDisabled) {
                        //depending on the value of playerTurn it'll play either red, yellow or blue
                        switch (playerTurn) {
                            case 0:
                                if (checkColumn(colNum) >= 1) {
                                    //sets status to 1 and playerTurn is set to 1
                                    //next turn is yellow
                                    setStatusRed(checkColumn(colNum) - 1, colNum);
                                    playerTurn = 1;

                                }
                                break;
                            case 1:
                                if (checkColumn(colNum) >= 1) {
                                    //sets status to 2 and playerTurn is set to 2
                                    //next turn is blue
                                    setStatusYellow(checkColumn(colNum) - 1, colNum);
                                    playerTurn = 2;
                                }
                                break;
                            case 2:
                                if (checkColumn(colNum) >= 1) {
                                    //sets status to 3 and playerTurn is set to 0
                                    //next turn is red
                                    setStatusBlue(checkColumn(colNum) - 1, colNum);
                                    playerTurn = 0;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    //if game is not over it'll check for a win
                    if (!setDisabled) {
                        checkWin();
                    }
                }

            });
        }

        //sets status of the buttons
        public void setStatus(int status) {
            this.status = status;
        }

        //gets status for the buttons
        public int getStatus() {
            return status;
        }

        //returns the button
        public JButton getButton() {
            return button;
        }

        //function to set button to red circle
        private void setStatusRed(int i, int j) {
            try {
                //sets the image to a red circle
                Image redCell = ImageIO.read(getClass().getResource("redDot.png"));
                panels[i][j].getButton().setIcon(new ImageIcon(redCell));
                //sets status to 1
                panels[i][j].setStatus(RED_CELL);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

        //function to set button to yellow circle
        private void setStatusYellow(int i, int j) {
            try {
                //sets the image to a yellow circle
                Image yellowCell = ImageIO.read(getClass().getResource("yellowDot.png"));
                panels[i][j].getButton().setIcon(new ImageIcon(yellowCell));
                //sets status to 2
                panels[i][j].setStatus(YELLOW_CELL);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

        //function to set button to blue circle
        private void setStatusBlue(int i, int j) {
            try {
                //sets the image to a blue circle
                Image blueCell = ImageIO.read(getClass().getResource("blueDot.png"));
                panels[i][j].getButton().setIcon(new ImageIcon(blueCell));
                //sets status to 3
                panels[i][j].setStatus(BLUE_CELL);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

    }

}
