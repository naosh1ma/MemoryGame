package view;

import javax.swing.*;

public class Frame extends JFrame{
    // Attributen

    PanelMenu panelMenu;
    PanelSettings panelSettings;
    PanelGame panelGame;
    PanelLogin panelLogin;
    PanelRangList panelRangList;

    // Konstruktor
    public Frame(){
        panelLogin = new PanelLogin();
        this.add(panelLogin);
        this.setBounds(100, 100, 430, 330);
        this.setTitle("Memory");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    // Methoden
    public void setPanelMenu(){panelMenu = new PanelMenu();}
    public void setPanelSettings(){panelSettings = new PanelSettings();}
    public void setPanelGame(){panelGame = new PanelGame();}
    public void setPanelRangList(){panelRangList = new PanelRangList();}
    public void setFrameGameBounds(){
        this.setBounds(100, 100, 750, 600);
    }

    public PanelGame getPanelGame() {return panelGame;}
    public PanelSettings getPanelSettings() {return panelSettings;}
    public PanelMenu getPanelMenu() {return panelMenu;}
    public PanelRangList getPanelRangList() {return panelRangList;}
    public PanelLogin getPanelLogin() {return panelLogin;}
}
