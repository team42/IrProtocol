package GUI;

import java.awt.Color;
import java.awt.Graphics;

// @author Nicolai

public class TaxiMenu {

    int options = 1;
    int currentSelected = 1;
    int currentMenu = 1;

    public TaxiMenu() {
    }

    public void draw(Graphics g) {



        for (int i = 0; i < options; i++) {
            g.setColor(Color.red);
            g.fillRect(10, 10 + (i * 45), 175, 35);
        }

        g.setColor(Color.green);
        g.fillRect(10, 10 + ((currentSelected - 1) * 45), 175, 35);

        for (int i = 0; i < options; i++) {
            g.setColor(Color.black);
            g.drawRect(10, 10 + (i * 45), 175, 35);
            g.drawString(menuName(i+1), 20, 32 + (i * 45));
        }
    }

    public void OK() {
        switch (currentMenu) {
            case 1:
                switch (currentSelected) {
                    case 1:
                        //Start Trip
                        currentMenu = 3;
                        options = 2;
                        break;
                }
                break;
            case 2:
                switch (currentSelected) {
                    case 1:
                        //Accept Tripe
                        currentMenu = 3;
                        options = 2;
                        break;
                    case 2:
                        //Decline Trip
                        currentMenu = 1;
                        options = 1;
                        break;
                }
                break;
            case 3:
                switch (currentSelected) {
                    case 1:
                        //Pause Trip
                        currentMenu = 3;
                        options = 2;
                        break;
                    case 2:
                        //Finish Trip
                        currentMenu = 4;
                        options = 1;
                        break;
                }
                break;
            case 4:
                switch (currentSelected) {
                    case 1:
                        //Finish Trip
                        currentMenu = 1;
                        options = 1;
                        break;
                }
                break;
        }
        currentSelected = 1;
    }

    public void Up() {
        currentSelected = currentSelected-1;
        if (currentSelected < 1) {
            currentSelected = options;
        }
    }

    public void Down() {
        currentSelected = currentSelected+1;
        if (currentSelected > options) {
            currentSelected = 1;
        }
    }

    private String menuName(int optionsNo) {
        String optionsName = "not Found!";

        switch (currentMenu) {
            case 1:
                switch (optionsNo) {
                    case 1:
                        optionsName = "Start Trip";
                        break;
                }
                break;
            case 2:
                switch (optionsNo) {
                    case 1:
                        optionsName = "Accept Tripe";
                        break;
                    case 2:
                        optionsName = "Decline Trip";
                        break;
                }
                break;
            case 3:
                switch (optionsNo) {
                    case 1:
                        optionsName = "Pause Trip";
                        break;
                    case 2:
                        optionsName = "Finish Trip";
                        break;
                }
                break;
            case 4:
                switch (optionsNo) {
                    case 1:
                        optionsName = "Finish Trip";
                        break;
                }
                break;
        }

        return optionsName;
    }
}
