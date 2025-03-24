package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelLogin extends JPanel {
    JTextField textFieldUserName;
    JTextField textFieldPassword;
    JLabel lblUserName;
    JLabel lblPassword;
    JButton btnLogin;
    JButton btnCancel;


    PanelLogin(){
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBounds(0, 0, 450, 350);

        textFieldUserName = new JTextField();
        textFieldPassword = new JTextField();
        lblUserName = new JLabel("Username :");
        lblPassword = new JLabel("Passwort :");
        btnLogin = new JButton("Login");
        btnCancel = new JButton("Abbrechen");

        textFieldUserName.setFont(new Font("Arial", Font.PLAIN, 15));
        textFieldPassword.setFont(new Font("Arial", Font.PLAIN, 15));
        lblUserName.setFont(new Font("Arial", Font.PLAIN, 15));
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 15));
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 15));
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 15));

        textFieldUserName.setBounds(50, 70, 310, 35);
        textFieldPassword.setBounds(50, 170, 310, 35);
        lblUserName.setBounds(50, 35, 131, 25);
        lblPassword.setBounds(50, 135, 131, 25);
        btnLogin.setBounds(50, 245, 120, 35);
        btnCancel.setBounds(250, 245, 120, 35);

        textFieldUserName.setColumns(10);
        textFieldPassword.setColumns(10);

        add(textFieldUserName);
        add(textFieldPassword);
        add(lblUserName);
        add(lblPassword);
        add(btnLogin);
        add(btnCancel);
    }

    public void addLoginListener(ActionListener listener){btnLogin.addActionListener(listener);}
    public void addCancelListener(ActionListener listener){btnCancel.addActionListener(listener);}

}
