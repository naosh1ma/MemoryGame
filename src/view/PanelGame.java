package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PanelGame extends JPanel {
    // Attributen
    private JPanel panelGameField;
    private JButton btnGameNewStart;
    private JButton btnGameEnd;
    private ArrayList<JButton> buttonsGame;
    private JLabel lblScore;
    private JLabel lblTry;

    // Konstruktoren
    PanelGame() {
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBounds(0, 0, 734, 561);


        btnGameEnd = new JButton("Beenden");
        btnGameNewStart = new JButton("Neustarten");
        lblScore = new JLabel("Score: ");
        lblTry = new JLabel("Try: ");

        btnGameEnd.setFont(new Font("Arial", Font.PLAIN, 15));
        btnGameNewStart.setFont(new Font("Arial", Font.PLAIN, 15));
        lblScore.setFont(new Font("Arial", Font.PLAIN, 15));
        lblTry.setFont(new Font("Arial", Font.PLAIN, 15));

        btnGameEnd.setBounds(607, 515, 105, 35);
        btnGameNewStart.setBounds(479, 515, 105, 35);
        lblScore.setBounds(25, 520, 105, 25);
        lblTry.setBounds(180, 520, 105, 25);

        this.add(btnGameEnd);
        this.add(btnGameNewStart);
        this.add(lblScore);
        this.add(lblTry);
    }

    // Methoden
    public int getButtonIndex(JButton button) {return buttonsGame.indexOf(button);}
    public void setScore(int score) {lblScore.setText("Score: " + score);}
    public void setButtonIcon(int index, ImageIcon icon) {buttonsGame.get(index).setIcon(icon);}
    public void addEndGameListener(ActionListener listener) {btnGameEnd.addActionListener(listener);}
    public void addNewStartListener(ActionListener listener) {btnGameNewStart.addActionListener(listener);}
    public void disableButton(int index) {buttonsGame.get(index).setEnabled(false);}
    public void resetButtonIcon(int index, ImageIcon icon) {buttonsGame.get(index).setIcon(icon);}

    public void addButtonsGameListener(ActionListener listener) {
        for (JButton button : buttonsGame) {
            button.addActionListener(listener);
        }
    }

    public void createGameField(int row_size, int col_size) {
        buttonsGame = new ArrayList<>();
        panelGameField = new JPanel();
        panelGameField.setLayout(new GridLayout(row_size, col_size));
        panelGameField.setBounds(0, 1, 734, 503);
        addButtons(row_size, col_size);
        this.add(panelGameField,0);
    }

    private void addButtons(int row_size, int col_size) {
        for (int i = 0; i < row_size * col_size; i++) {
            JButton button = new JButton();
            button.setEnabled(true);
            buttonsGame.add(button);
            panelGameField.add(buttonsGame.get(i));
        }
    }

    public void setBackIcon(ImageIcon icon){
        for (JButton button : buttonsGame) {
            button.setIcon(icon);
        }
    }

    public void enableButtons(){
        for (JButton button : buttonsGame) {
            button.setEnabled(true);
        }
    }

    public void setButtonBackground(int index, int player){
        if (player == 1){
            buttonsGame.get(index).setBackground(new Color(0,255,127));
        }
        else if (player == 2){
            buttonsGame.get(index).setBackground(new Color(238,130,238));
        }

    }

}
