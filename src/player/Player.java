package player;

import board.Board;
import utils.Position;
import java.util.Scanner;

public class Player {
    private String color;

    public Player(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}