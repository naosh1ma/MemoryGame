package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelMenu extends JPanel{

    private JLabel lblWelcome;
    private JButton btnGameEasy;
    private JButton btnGameNorm;
    private JButton btnGameHard;
    private JButton btnRangList;
    private JButton btnAdmin;

    PanelMenu() {
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBounds(0, 0, 734, 561);

        lblWelcome = new JLabel("Willkommen zu Memory");
        btnGameEasy = new JButton("4 x 5");
        btnGameNorm = new JButton("6 x 6");
        btnGameHard = new JButton("8 x 8");
        btnRangList = new JButton("Rang");
        btnAdmin = new JButton("");

        lblWelcome.setFont(new Font("Arial", Font.PLAIN, 30));
        btnGameEasy.setFont(new Font("Arial", Font.PLAIN, 20));
        btnGameNorm.setFont(new Font("Arial", Font.PLAIN, 20));
        btnGameHard.setFont(new Font("Arial", Font.PLAIN, 20));
        btnRangList.setFont(new Font("Arial", Font.PLAIN, 20));

        lblWelcome.setBounds(180, 75, 400, 80);
        btnGameEasy.setBounds(125, 300, 120, 40);
        btnGameNorm.setBounds(125, 370, 120, 40);
        btnGameHard.setBounds(125, 440, 120, 40);
        btnRangList.setBounds(300, 480, 120, 40);
        btnAdmin.setBounds(620,10,100,60);

        // make button invisible but clickable
        btnAdmin.setOpaque(false);
        btnAdmin.setContentAreaFilled(false);
        btnAdmin.setBorderPainted(false);

        this.add(lblWelcome);
        this.add(btnGameEasy);
        this.add(btnGameNorm);
        this.add(btnGameHard);
        this.add(btnRangList);
        this.add(btnAdmin);
    }

    public void addDifficultyListener(ActionListener listener){
        btnGameEasy.addActionListener(listener);
        btnGameNorm.addActionListener(listener);
        btnGameHard.addActionListener(listener);
    }

    public void addRangListListener(ActionListener listener){btnRangList.addActionListener(listener);}
    public void addAdminListener(ActionListener listener){btnAdmin.addActionListener(listener);}

}
