package com.commons;

import com.models.buildingblocks.ListBuildingBlock;
import javafx.scene.paint.Color;

public class Globals {
    public static ListBuildingBlock listBuildingBlock = new ListBuildingBlock();
    public static Color[] listColors = {Color.RED, Color.YELLOW, Color.LIGHTGREEN, Color.CYAN, Color.BROWN, Color.BLUE, Color.PURPLE, Color.DARKGRAY, Color.PINK, Color.DARKGREEN};
    public static int DEFAULT_WIDTH;
    public static int DEFAULT_HEIGHT;

    public static void setDefaultResolution(int width, int height) {
        DEFAULT_WIDTH = width;
        DEFAULT_HEIGHT = height;
    }

    public static Color getRandomColor() {
        return listColors[(int) (Math.random() * listColors.length)];
    }

    public static Color getDarkColor(Color color) {
        return color.darker();
    }
}
