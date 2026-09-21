package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelSettings extends JPanel {
    JLabel lblSettings;
    JLabel lblPlayers;
    JLabel lblThemes;
    JCheckBox chckbxTheme1;
    JCheckBox chckbxTheme2;
    JCheckBox chckbxTheme3;
    JCheckBox chckbxTheme4;
    JComboBox comboBoxPlayer;
    JButton btnStart;
    JButton btnBack;

    PanelSettings() {
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBounds(0, 0, 734, 561);


        lblSettings = new JLabel("Einstellungen");
        lblPlayers = new JLabel("Modus:");
        lblThemes = new JLabel("Themen:");
        chckbxTheme1 = new JCheckBox("Autos");
        chckbxTheme2 = new JCheckBox("Sport");
        chckbxTheme3 = new JCheckBox("Flaggen");
        chckbxTheme4 = new JCheckBox("Tiere");
        btnStart = new JButton("Start");
        btnBack = new JButton("Zurück");
        comboBoxPlayer = new JComboBox();

        comboBoxPlayer.setModel(new DefaultComboBoxModel(new String[] { "Singleplayer", "1 vs Bot", "1 vs 1" }));
        comboBoxPlayer.setSelectedIndex(0);

        lblSettings.setFont(new Font("Arial", Font.PLAIN, 30));
        lblPlayers.setFont(new Font("Arial", Font.PLAIN, 25));
        lblThemes.setFont(new Font("Arial", Font.PLAIN, 25));
        chckbxTheme1.setFont(new Font("Arial", Font.PLAIN, 20));
        chckbxTheme2.setFont(new Font("Arial", Font.PLAIN, 20));
        chckbxTheme3.setFont(new Font("Arial", Font.PLAIN, 20));
        chckbxTheme4.setFont(new Font("Arial", Font.PLAIN, 20));
        comboBoxPlayer.setFont(new Font("Arial", Font.PLAIN, 20));
        btnStart.setFont(new Font("Arial", Font.PLAIN, 20));
        btnBack.setFont(new Font("Arial", Font.PLAIN, 20));

        lblSettings.setHorizontalAlignment(SwingConstants.CENTER);
        lblSettings.setBounds(0, 50, 734, 60);
        lblPlayers.setBounds(160, 150, 150, 60);
        lblThemes.setBounds(480, 150, 150, 60);
        chckbxTheme1.setBounds(480, 220, 110, 30);
        chckbxTheme2.setBounds(480, 260, 110, 30);
        chckbxTheme3.setBounds(480, 300, 110, 30);
        chckbxTheme4.setBounds(480, 340, 110, 30);
        comboBoxPlayer.setBounds(110, 220, 190, 30);
        btnStart.setBounds(490, 500, 120, 40);
        btnBack.setBounds(125, 500, 120, 40);

        this.add(lblSettings);
        this.add(lblPlayers);
        this.add(lblThemes);
        this.add(chckbxTheme1);
        this.add(chckbxTheme2);
        this.add(chckbxTheme3);
        this.add(chckbxTheme4);
        this.add(comboBoxPlayer);
        this.add(btnStart);
        this.add(btnBack);

    }

    public boolean isPlayerVsPlayer(){return "1 vs 1".equals(comboBoxPlayer.getSelectedItem());}
    public void addBackListener(ActionListener listener){btnBack.addActionListener(listener);}
    public void addStartGameListener(ActionListener listener){btnStart.addActionListener(listener);}
    public void addThemesListener(ActionListener listener){
        chckbxTheme1.addActionListener(listener);
        chckbxTheme2.addActionListener(listener);
        chckbxTheme3.addActionListener(listener);
        chckbxTheme4.addActionListener(listener);
    }
}
