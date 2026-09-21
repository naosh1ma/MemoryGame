package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelLogin extends JPanel {
    JTextField textFieldUserName;
    JLabel lblUserName;
    JButton btnLogin;
    JButton btnCancel;


    PanelLogin(){
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBounds(0, 0, 430, 280);

        textFieldUserName = new JTextField();
        lblUserName = new JLabel("Spielername :");
        btnLogin = new JButton("Weiter");
        btnCancel = new JButton("Abbrechen");

        textFieldUserName.setFont(new Font("Arial", Font.PLAIN, 15));
        lblUserName.setFont(new Font("Arial", Font.PLAIN, 15));
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 15));
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 15));

        lblUserName.setBounds(50, 50, 200, 25);
        textFieldUserName.setBounds(50, 85, 320, 35);
        btnLogin.setBounds(50, 160, 120, 35);
        btnCancel.setBounds(250, 160, 120, 35);

        textFieldUserName.setColumns(10);

        add(textFieldUserName);
        add(lblUserName);
        add(btnLogin);
        add(btnCancel);
    }

    public String getPlayerName(){return textFieldUserName.getText().trim();}

    public void addLoginListener(ActionListener listener){
        btnLogin.addActionListener(listener);
        textFieldUserName.addActionListener(listener);
    }
    public void addCancelListener(ActionListener listener){btnCancel.addActionListener(listener);}

}
