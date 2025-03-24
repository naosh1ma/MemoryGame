package controller;

import model.Model;
import view.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    Model model;
    Frame frame;

    public Controller(Model model, Frame frame) {
        this.model = model;
        this.frame = frame;

        frame.getPanelLogin().addLoginListener(new LoginNextListener());
        frame.getPanelLogin().addCancelListener(new LoginCancelListener());
    }

    private void restartGame() {
        model.newStart();
        frame.getPanelGame().setBackIcon(model.getCardsBack());
        frame.getPanelGame().enableButtons();
        frame.getPanelGame().revalidate();
        frame.getPanelGame().repaint();
    }

    private void endGame() {
        frame.remove(frame.getPanelGame());
        frame.getPanelMenu().setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    private void openSettings() {
        frame.setPanelSettings();
        frame.getPanelSettings().addThemesListener(new SettingsThemeListener());
        frame.getPanelSettings().addStartGameListener(new SettingsStartGameListener());
        frame.getPanelSettings().addBackListener(new SettingsBackListener());
        frame.getPanelMenu().setVisible(false);
        frame.add(frame.getPanelSettings());
        frame.getPanelSettings().setVisible(true);
    }

    private void checkForMatch() {
        int[] openedCards = model.getOpenedCards();
        if (model.checkMatch()) {
            frame.getPanelGame().disableButton(openedCards[0]);
            frame.getPanelGame().disableButton(openedCards[1]);
            if (model.isPairFound()) {
                Object[] options = {"Ja", "Nein"};
                int input = JOptionPane.showOptionDialog(null, "                      Du hast gewonnen!\n" +
                                "                     Willst du neustarten?", "Game Over",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                        options, options[0]);
                if (input == JOptionPane.YES_OPTION) {
                    restartGame();
                } else if (input == JOptionPane.NO_OPTION) {
                    endGame();
                }
            }
        } else {
            frame.getPanelGame().resetButtonIcon(openedCards[0], model.getCardsBack());
            frame.getPanelGame().resetButtonIcon(openedCards[1], model.getCardsBack());
        }
        model.resetOpenedCards();
    }

    public class LoginNextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setFrameGameBounds();
            frame.setPanelMenu();
            frame.add(frame.getPanelMenu());
            frame.getPanelLogin().setVisible(false);
            frame.setLocationRelativeTo(null);
            frame.getPanelMenu().addDifficultyListener(new MenuDifficultyListener());
            frame.getPanelMenu().addRangListListener(null);
            frame.getPanelMenu().addAdminListener(null);
        }
    }

    public static class LoginCancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public class MenuDifficultyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            openSettings();
            switch (button.getText()) {
                case "4 x 5":
                    model.createGame(4, 5);
                    break;
                case "6 x 6":
                    model.createGame(6, 6);
                    break;
                case "8 x 8":
                    model.createGame(8, 8);
                    break;
            }
        }
    }

    public class MenuRangListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class MenuAdminListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }


    public class SettingsThemeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checkBox = (JCheckBox) e.getSource();
            if (checkBox.isSelected()) {
                model.setThemes(checkBox.getText());
            } else {
                model.deleteTheme(checkBox.getText());
            }
        }
    }

    public class SettingsStartGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setPanelGame();
            frame.getPanelGame().addNewStartListener(new GameNewStartListener());
            frame.getPanelGame().addEndGameListener(new GameEndListener());
            try {
                model.initGame();
                model.createIcons();
                frame.getPanelSettings().setVisible(false);
                frame.add(frame.getPanelGame());
                frame.getPanelGame().createGameField(model.getRows(), model.getCols());
                frame.getPanelGame().setBackIcon(model.getCardsBack());
                frame.getPanelGame().setVisible(true);
                frame.getPanelGame().addButtonsGameListener(new GameCardsListener());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Es wurde noch keine Themen ausgew\u00E4hlt!");
            }
        }
    }

    public class SettingsBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.getPanelSettings().setVisible(false);
            frame.getPanelMenu().setVisible(true);
            frame.revalidate();
            frame.repaint();
        }
    }

    public class GameNewStartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] options = {"Ja", "Nein"};
            int input = JOptionPane.showOptionDialog(null, "          Willst du wirklich neustarten?", "=)",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    options, options[0]);
            if (input == JOptionPane.YES_OPTION) {
                restartGame();
            }
        }
    }


    public class GameEndListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object[] options = {"Ja", "Nein"};
            int input = JOptionPane.showOptionDialog(null, "        Willst du wirklich Spiel beenden?", "=)",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    options, options[0]);
            if (input == JOptionPane.YES_OPTION) {
                endGame();
            }
        }
    }

    public class GameCardsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            int index = frame.getPanelGame().getButtonIndex(button);
            if (button.getIcon() == model.getCardsBack()) {
                frame.getPanelGame().setScore(model.getScoreSingle());
                model.setOpenCard(index);
                frame.getPanelGame().setButtonBackground(index, 2);
                frame.getPanelGame().setButtonIcon(index, model.getIcon(index));
                if (model.areBothCardsOpen()) {
                    model.decreaseScore();
                    frame.getPanelGame().setScore(model.getScoreSingle());
                    if (model.getScoreSingle() == 0) {
                        Object[] options = {"Ja", "Nein"};
                        int input = JOptionPane.showOptionDialog(null, "                        Du hast verloren!\n" +
                                        "                     Willst du neustarten?", "Game Over",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                                options, options[0]);
                        if (input == JOptionPane.YES_OPTION) {
                            restartGame();
                        } else if (input == JOptionPane.NO_OPTION) {
                            endGame();
                        }
                    }
                    Timer timer = new Timer(1000, e1 -> checkForMatch());
                    timer.setRepeats(false);
                    timer.start();
                }
            }
            frame.repaint();
        }
    }
}
