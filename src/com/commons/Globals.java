package com.commons;

import com.models.components.BuildingBlock;
import com.models.components.ListBuildingBlock;
import javafx.scene.paint.Color;

import java.util.Vector;

public class Globals {
    public static ListBuildingBlock listBuildingBlock = new ListBuildingBlock();
    public static Color[] listColors = {Color.RED, Color.YELLOW, Color.LIGHTGREEN, Color.CYAN, Color.BROWN, Color.BLUE, Color.PURPLE, Color.DARKGRAY, Color.PINK, Color.DARKGREEN};
    public static Vector<BuildingBlock> buildingBlocks = new Vector<>();
    public static int DEFAULT_WIDTH;
    public static int DEFAULT_HEIGHT;
    public static App app = new App();

    public static void setDefaultResolution(int width, int height) {
        DEFAULT_WIDTH = width;
        DEFAULT_HEIGHT = height;
    }

    public static Color getRandomColor() {
        return listColors[(int) (Math.random() * listColors.length)];
    }
}
