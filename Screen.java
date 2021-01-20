import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import javax.sound.midi.*;

public class Screen {
 
    private static JFrame frame = new JFrame("Battleships");
    private static JPanel topPanel = new JPanel();
    private static JPanel middlePanel = new JPanel();
    private static JPanel bottomPanel = new JPanel();
    private static JButton button;
    private static JLabel text;
    private static ArrayList<JButton> listOfButtons = new ArrayList<JButton>();
    private static JButton resetButton = new JButton("Reset");
    public static String[][] grid = {
        {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9"},
        {"B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9"},
        {"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9"},
        {"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9"},
        {"E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9"},
        {"F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9"},
        {"G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9"},
        {"H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9"},
        {"I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "I9"},
    }; 
    private static Battleship battleshipOne, battleshipTwo, battleshipThree;
    private static ArrayList<String> battleshipOneHits = new ArrayList<String>();
    private static ArrayList<String> battleshipTwoHits = new ArrayList<String>();
    private static ArrayList<String> battleshipThreeHits = new ArrayList<String>();
    private static int guesses = 1;
    private static Screen screen;

    public static void main(String[] args) {
        screen = new Screen();
        screen.setTopPanel();
        screen.setMiddlePanel();
        screen.setBottomButtons();
        screen.setFrame();
        screen.getBattleships();

        if( (battleshipOneHits.size() == battleshipOne.getLength()) && (battleshipTwoHits.size() == battleshipTwo.getLength()) && (battleshipThreeHits.size() == battleshipThree.getLength()) ) {
            text.setText("You Won in " + guesses + " Gos!");
        }
    }

    public void playHitSound() {
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            Sequence sequence = new Sequence(Sequence.PPQ, 3);
            Track t = sequence.createTrack();

            ShortMessage message = new ShortMessage();
            message.setMessage(144, 1, 70, 122);
            MidiEvent hitEvent = new MidiEvent(message, 1);
            t.add(hitEvent);
            player.setSequence(sequence);

            player.start();
        } catch(Exception ex) {

        }
    }

    public void missHitSound() {
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            Sequence sequence = new Sequence(Sequence.PPQ, 3);
            Track t = sequence.createTrack();

            ShortMessage message = new ShortMessage();
            message.setMessage(144, 1, 10, 100);
            MidiEvent hitEvent = new MidiEvent(message, 1);
            t.add(hitEvent);
            player.setSequence(sequence);

            player.start();
        } catch(Exception ex) {

        }
    }

    public void getBattleships() {
        battleshipOne = null;
        battleshipTwo = null;
        battleshipThree = null;

        battleshipOne = new Battleship(2);
        battleshipOne.placeBattleship();

        battleshipTwo = new Battleship(3);
        battleshipTwo.placeBattleship();

        battleshipThree = new Battleship(5);
        battleshipThree.placeBattleship();

        for(String x : battleshipOne.getStartingPos()) {
            if((battleshipTwo.getStartingPos().contains(x)) || (battleshipThree.getStartingPos().contains(x))) {
                getBattleships();
            }
        }
        for(String x : battleshipTwo.getStartingPos()) {
            if(battleshipThree.getStartingPos().contains(x)) {
                getBattleships();
            }
        }
    }

    private void setFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void setTopPanel() {
        text = new JLabel("Welcome to Battleships!");
        text.setForeground(Color.WHITE);
        topPanel.add(text);
        topPanel.setBackground(Color.BLACK);
        frame.add(topPanel, BorderLayout.NORTH);
    }

    private void setMiddlePanel() {
        Border emptyBorder = BorderFactory.createEmptyBorder();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                button = new JButton(grid[i][j]);
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
                button.setFont(new Font("Arial", Font.PLAIN, 30));
                button.setBorder(emptyBorder);
                listOfButtons.add(button);
            }
        }
        for(JButton btn : listOfButtons) {
            btn.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if( (battleshipOneHits.size() == battleshipOne.getLength()) && (battleshipTwoHits.size() == battleshipTwo.getLength()) && (battleshipThreeHits.size() == battleshipThree.getLength()) ) {
                        text.setText("You Won in " + guesses + " Gos!");
                    } else if( (battleshipOne.getStartingPos().contains(btn.getText())) ) {
                        screen.playHitSound();
                        battleshipOneHits.add(btn.getText());
                        text.setText("You Hit " + btn.getText() + "!            Guesses: " + guesses);
                        btn.setBackground(Color.RED);
                        btn.setText("X");
                        btn.setForeground(Color.BLACK);
                        guesses++;
                        if( (battleshipOneHits.contains(battleshipOne.getStartingPos().get(0))) && (battleshipOneHits.contains(battleshipOne.getStartingPos().get(1))) ) {
                            text.setText("You Sank Battleship One!");
                            if( (battleshipOneHits.size() == battleshipOne.getLength()) && (battleshipTwoHits.size() == battleshipTwo.getLength()) && (battleshipThreeHits.size() == battleshipThree.getLength()) ) {
                                text.setText("You Won in " + guesses + " Gos!");
                            } 
                        }
                    } else if(battleshipTwo.getStartingPos().contains(btn.getText())) {
                        screen.playHitSound();
                        battleshipTwoHits.add(btn.getText());
                        text.setText("You Hit " + btn.getText() + "!            Guesses: " + guesses);
                        btn.setBackground(Color.RED);
                        btn.setText("X");
                        btn.setForeground(Color.BLACK);
                        guesses++;
                        if( (battleshipTwoHits.contains(battleshipTwo.getStartingPos().get(0))) && (battleshipTwoHits.contains(battleshipTwo.getStartingPos().get(1))) && 
                        (battleshipTwoHits.contains(battleshipTwo.getStartingPos().get(2))) ) {
                            text.setText("You Sank Battleship Two!");
                            if( (battleshipOneHits.size() == battleshipOne.getLength()) && (battleshipTwoHits.size() == battleshipTwo.getLength()) && (battleshipThreeHits.size() == battleshipThree.getLength()) ) {
                                text.setText("You Won in " + guesses + " Gos!");
                            } 
                        }
                    } else if(battleshipThree.getStartingPos().contains(btn.getText())) {
                        screen.playHitSound();
                        battleshipThreeHits.add(btn.getText());
                        text.setText("You Hit " + btn.getText() + "!            Guesses: " + guesses);
                        btn.setBackground(Color.RED);
                        btn.setText("X");
                        btn.setForeground(Color.BLACK);
                        guesses++;
                        if( (battleshipThreeHits.contains(battleshipThree.getStartingPos().get(0))) && (battleshipThreeHits.contains(battleshipThree.getStartingPos().get(1))) && 
                        (battleshipThreeHits.contains(battleshipThree.getStartingPos().get(2))) && (battleshipThreeHits.contains(battleshipThree.getStartingPos().get(3))) && 
                        (battleshipThreeHits.contains(battleshipThree.getStartingPos().get(4))) ) {
                            text.setText("You Sank Battleship Three!");
                            if( (battleshipOneHits.size() == battleshipOne.getLength()) && (battleshipTwoHits.size() == battleshipTwo.getLength()) && (battleshipThreeHits.size() == battleshipThree.getLength()) ) {
                                text.setText("You Won in " + guesses + " Gos!");
                            } 
                        }
                    } else if (btn.getText().equals("X")) {
                        btn.setText("X");
                    }  else if (btn.getText().equals("O")) {
                        btn.setText("O");
                    } else {
                        screen.missHitSound();
                        btn.setBackground(Color.WHITE);
                        btn.setText("O");
                        btn.setForeground(Color.BLACK);
                        text.setText("You Missed!           Guesses: " + guesses);
                        guesses++;
                    }
                }

            });
            middlePanel.add(btn);
        }
        middlePanel.setLayout(new GridLayout(9, 9)); 
        frame.add(middlePanel, BorderLayout.CENTER);
    }

    private void setBottomButtons() {
        resetButton.setPreferredSize(new Dimension(125, 40));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guesses = 1;
                battleshipOneHits.clear();
                battleshipTwoHits.clear();
                battleshipThreeHits.clear();
                for(JButton btn : listOfButtons) {
                    middlePanel.remove(btn);
                }
                listOfButtons.clear();
                screen.getBattleships();
                text.setText("Welcome to Battleships!");

                screen.getBattleships();
                screen.setMiddlePanel();
            }
        });
        bottomPanel.add(resetButton);
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }
}