package util;

import java.awt.*;

public class ScoreUtil {
    public static GridBagConstraints buildConstraints(int gridX, int gridY, int gridHeight,
            int gridWidth, int weightX, int weightY, int fill) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.gridheight = gridHeight;
        constraints.gridwidth = gridWidth;
        constraints.weightx = weightX;
        constraints.weighty = weightY;
        constraints.fill = fill;
        return constraints;
    }

    public static String getCurrentPath() {
        String path = ScoreUtil.class.getResource("/").getFile().toString();
        return path.substring(0, path.indexOf("DataModify"));
    }
}
