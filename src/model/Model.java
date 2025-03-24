package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Model {

    Random random;

    private int rows;
    private int cols;
    private int diff;
    private int scoreSingle;
    private int scoreMulti_1;
    private int scoreMulti_2;
    private int openCount;
    private int pairsFound;
    private int[] openedCards = {-1, -1};
    private ImageIcon cardsBack;
    private ArrayList<String> themes;
    private ArrayList<ImageIcon> cardsFront;

    private final String BACK_PATH = "MemoryGame/icons/back.png";
    private final String FRONT_PATH = "MemoryGame/icons/";

    public void createGame(int rows, int cols) {
        themes = new ArrayList<>();
        this.rows = rows;
        this.cols = cols;
        this.diff = rows * cols;
        System.out.println(this.rows + " " + this.cols + " " + this.diff);
    }

    public void initGame() {
        random = new Random();
        scoreSingle = 100;
        openCount = 0;
        pairsFound = 0;
        cardsBack = new ImageIcon(new ImageIcon(BACK_PATH).getImage().getScaledInstance(
                getBackWidth(), getBackHeight(), Image.SCALE_SMOOTH));
    }

    public void createIcons() {
        cardsFront = new ArrayList<>();
        for (int i = 0; i < getDiff() / 2; i++) {
            ImageIcon image = new ImageIcon(new ImageIcon(FRONT_PATH +
                    themes.get(random.nextInt(themes.size())) + "/" + (i + 1) + ".png")
                    .getImage().getScaledInstance(getFrontWidth(), getFrontHeight(), Image.SCALE_SMOOTH));
            cardsFront.add(image);
            cardsFront.add(image);
        }
        Collections.shuffle(cardsFront);
    }

    public void newStart() {
        this.scoreSingle = 100;
        Collections.shuffle(cardsFront);
    }

    public void setOpenCard(int index) {
        if (openCount < 2) {
            openedCards[openCount] = index;
            openCount++;
        }
    }

    public void resetOpenedCards() {
        openedCards[0] = -1;
        openedCards[1] = -1;
        openCount = 0;
    }

    public boolean isPairFound() {
        pairsFound++;
        return pairsFound == cardsFront.size() / 2;
    }

    public void decreaseScore() {
        if (getDiff() == 20) {
            scoreSingle = scoreSingle - 5;}
        if (getDiff() == 36) {
            scoreSingle = scoreSingle - 3;}
        if (getDiff() == 64) {
            scoreSingle = scoreSingle - 1;}
    }

    public int getFrontWidth() {
        int size = 0;
        if (this.diff == 20) {size = 140;}
        if (this.diff == 36) {size = 100;}
        if (this.diff == 64) {size = 80;}
        return size;
    }

    public int getFrontHeight() {
        int size = 0;
        if (this.diff == 20) {size = 120;}
        if (this.diff == 36) {size = 80;}
        if (this.diff == 64) {size = 60;}
        return size;
    }

    public int getBackWidth() {
        int size = 0;
        if (this.diff == 20) {size = 145;}
        if (this.diff == 36) {size = 120;}
        if (this.diff == 64) {size = 90;}
        return size;
    }

    public int getBackHeight() {
        int size = 0;
        if (this.diff == 20) {size = 125;}
        if (this.diff == 36) {size = 80;}
        if (this.diff == 64) {size = 60;}
        return size;
    }

    public void setThemes(String theme) {themes.add(theme);}
    public ImageIcon getIcon(int index) {return cardsFront.get(index);}
    public ImageIcon getCardsBack() {return cardsBack;}
    public int getCols() {return cols;}
    public int getRows() {return rows;}
    public int getDiff() {return diff;}
    public int getScoreSingle() {return scoreSingle;}
    public int[] getOpenedCards() {return openedCards;}
    public void deleteTheme(String theme) {themes.remove(theme);}
    public boolean checkMatch() {return cardsFront.get(openedCards[0]).equals(cardsFront.get(openedCards[1]));}
    public boolean areBothCardsOpen() {return openCount == 2;}

}
