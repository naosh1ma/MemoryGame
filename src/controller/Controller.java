package controller;

import model.Model;
import model.Ranking;
import view.Frame;
import view.PanelRangList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UncheckedIOException;
import java.util.List;

public class Controller {
    Model model;
    Frame frame;
    Ranking ranking;

    public Controller(Model model, Frame frame) {
        this.model = model;
        this.frame = frame;
        this.ranking = new Ranking();

        frame.getPanelLogin().addLoginListener(new LoginNextListener());
        frame.getPanelLogin().addCancelListener(e -> System.exit(0));
    }

    private void restartGame() {
        model.newStart();
        frame.getPanelGame().setBackIcon(model.getCardsBack());
        frame.getPanelGame().enableButtons();
        updateLabels();
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

    private void openRanking() {
        frame.setPanelRangList();
        PanelRangList panel = frame.getPanelRangList();
        panel.addDifficultyListener(e -> showRanking());
        panel.addBackListener(e -> {
            frame.remove(panel);
            frame.getPanelMenu().setVisible(true);
            frame.revalidate();
            frame.repaint();
        });
        showRanking();
        frame.getPanelMenu().setVisible(false);
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void showRanking() {
        PanelRangList panel = frame.getPanelRangList();
        List<Ranking.Entry> top = ranking.getTop(cardsFor(panel.getSelectedDifficulty()), 10);
        Object[][] rows = new Object[top.size()][];
        for (int i = 0; i < top.size(); i++) {
            rows[i] = new Object[] {i + 1, top.get(i).name(), top.get(i).score()};
        }
        panel.setRows(rows);
    }

    private static int cardsFor(String difficulty) {
        return switch (difficulty) {
            case "4 x 5" -> 20;
            case "6 x 6" -> 36;
            default -> 64;
        };
    }

    private void updateLabels() {
        if (model.isMultiplayer()) {
            frame.getPanelGame().setScoreText(model.getPlayerName() + "  " + model.getScoreMulti1()
                    + " : " + model.getScoreMulti2() + "  " + model.getPlayerName2());
            frame.getPanelGame().setInfoText("Am Zug: " + model.getCurrentPlayerName());
        } else {
            frame.getPanelGame().setScoreText("Score: " + model.getScoreSingle());
            frame.getPanelGame().setInfoText("Spieler: " + model.getPlayerName());
        }
    }

    private void askRestart(String message) {
        Object[] options = {"Ja", "Nein"};
        int input = JOptionPane.showOptionDialog(frame, message + "\nWillst du neustarten?", "Game Over",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                options, options[0]);
        if (input == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            endGame();
        }
    }

    private void finishGame() {
        if (model.isMultiplayer()) {
            String winner = model.getWinnerName();
            askRestart(winner == null ? "Unentschieden!" : winner + " hat gewonnen!");
            return;
        }
        try {
            ranking.add(model.getPlayerName(), model.getDiff(), model.getScoreSingle());
        } catch (UncheckedIOException ex) {
            JOptionPane.showMessageDialog(frame, "Rangliste konnte nicht gespeichert werden.");
        }
        askRestart("Du hast gewonnen! Score: " + model.getScoreSingle());
    }

    private void checkForMatch() {
        int[] openedCards = model.getOpenedCards();
        if (model.checkMatch()) {
            frame.getPanelGame().disableButton(openedCards[0]);
            frame.getPanelGame().disableButton(openedCards[1]);
            if (model.isMultiplayer()) {
                model.addPairToCurrentPlayer();
            }
            updateLabels();
            if (model.isPairFound()) {
                model.resetOpenedCards();
                finishGame();
                return;
            }
        } else {
            frame.getPanelGame().resetButtonIcon(openedCards[0], model.getCardsBack());
            frame.getPanelGame().resetButtonIcon(openedCards[1], model.getCardsBack());
            if (model.isMultiplayer()) {
                model.switchPlayer();
                updateLabels();
            }
        }
        model.resetOpenedCards();
    }

    public class LoginNextListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = frame.getPanelLogin().getPlayerName();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Bitte gib einen Spielernamen ein!");
                return;
            }
            model.setPlayerName(name);
            frame.showMenu();
            frame.getPanelMenu().addDifficultyListener(new MenuDifficultyListener());
            frame.getPanelMenu().addRangListListener(new MenuRangListListener());
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
            openRanking();
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
            if (!model.hasThemes()) {
                JOptionPane.showMessageDialog(frame, "Es wurde noch keine Themen ausgewählt!");
                return;
            }
            String player2 = null;
            if (frame.getPanelSettings().isPlayerVsPlayer()) {
                player2 = JOptionPane.showInputDialog(frame, "Name von Spieler 2:");
                if (player2 == null) {
                    return;
                }
                player2 = player2.trim();
                if (player2.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Bitte gib einen Spielernamen ein!");
                    return;
                }
            }
            model.setPlayerName2(player2);

            frame.setPanelGame();
            frame.getPanelGame().addNewStartListener(new GameNewStartListener());
            frame.getPanelGame().addEndGameListener(new GameEndListener());
            model.initGame();
            model.createIcons();
            frame.getPanelSettings().setVisible(false);
            frame.add(frame.getPanelGame());
            frame.getPanelGame().createGameField(model.getRows(), model.getCols());
            frame.getPanelGame().setBackIcon(model.getCardsBack());
            updateLabels();
            frame.getPanelGame().setVisible(true);
            frame.getPanelGame().addButtonsGameListener(new GameCardsListener());
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
            if (model.areBothCardsOpen()) {
                return;
            }
            JButton button = (JButton) e.getSource();
            int index = frame.getPanelGame().getButtonIndex(button);
            if (button.getIcon() == model.getCardsBack()) {
                model.setOpenCard(index);
                frame.getPanelGame().setButtonBackground(index, model.isMultiplayer() ? model.getCurrentPlayer() : 2);
                frame.getPanelGame().setButtonIcon(index, model.getIcon(index));
                if (model.areBothCardsOpen()) {
                    if (!model.isMultiplayer()) {
                        model.decreaseScore();
                        updateLabels();
                        if (model.getScoreSingle() == 0) {
                            askRestart("Du hast verloren!");
                            return;
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
