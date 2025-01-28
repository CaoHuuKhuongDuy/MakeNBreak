package com.commons;

import com.models.User;
import com.models.components.BuildingBlock;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Vector;

public class Globals {
    public static Color[] listColors = {Color.RED, Color.YELLOW, Color.LIGHTGREEN, Color.CYAN, Color.BROWN, Color.BLUE, Color.PURPLE, Color.DARKGRAY, Color.PINK, Color.DARKGREEN};
    public static Vector<BuildingBlock> buildingBlocks = new Vector<>();
    public static int DEFAULT_WIDTH;
    public static int DEFAULT_HEIGHT;
    public static App app = new App();

    public static void init() {
        Coordinate cell00 = new Coordinate(0, 0);
        Coordinate cell01 = new Coordinate(0, 1);
        Coordinate cell02 = new Coordinate(0, 2);
        Coordinate cell10 = new Coordinate(1, 0);
        Coordinate cell11 = new Coordinate(1, 1);
        Coordinate cell12 = new Coordinate(1, 2);
        Coordinate cell20 = new Coordinate(2, 0);
        Coordinate cell21 = new Coordinate(2, 1);
        Coordinate cell22 = new Coordinate(2, 2);

        BuildingBlock tBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell11, cell20, cell21, cell22)));
        BuildingBlock smallZBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell11, cell21)));
        BuildingBlock oBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell10, cell11, cell20, cell21)));
        BuildingBlock bigLBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell20, cell21)));
        BuildingBlock plusBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell01, cell10, cell11, cell12, cell21)));
        BuildingBlock qBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell11, cell20, cell21)));
        BuildingBlock smallLBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell11, cell20, cell21)));
        BuildingBlock wBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell11, cell21, cell22)));
        BuildingBlock caretBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell01, cell02, cell10, cell20)));
        BuildingBlock bigZBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell10, cell11, cell12, cell22)));
        BuildingBlock IBlock = new BuildingBlock(new Vector<>(Arrays.asList(cell00, cell01, cell02)));
        buildingBlocks.add(tBlock);
        buildingBlocks.add(smallZBlock);
        buildingBlocks.add(oBlock);
        buildingBlocks.add(bigLBlock);
        buildingBlocks.add(plusBlock);
        buildingBlocks.add(qBlock);
        buildingBlocks.add(smallLBlock);
        buildingBlocks.add(wBlock);
        buildingBlocks.add(caretBlock);
        buildingBlocks.add(bigZBlock);
        buildingBlocks.add(IBlock);
    }

    public static void setDefaultResolution(int width, int height) {
        DEFAULT_WIDTH = width;
        DEFAULT_HEIGHT = height;
    }

    public static Color getRandomColor() {
        return listColors[(int) (Math.random() * listColors.length)];
    }

    public static User getUser(int userID) {
        return app.getUsers().get(userID);
    }

    public static String getResource(String path) {
        var url = Globals.class.getResource(path);
        if (url == null) return "";
        return url.toExternalForm();
    }
}
