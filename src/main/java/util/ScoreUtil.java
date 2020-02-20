package util;

import bean.TagRuleDef;
import bean.TagRuleInstance;

import java.awt.GridBagConstraints;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

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

    public static TagRuleInstance buildTree(List<TagRuleInstance> instanceList) {
        Map<Long, List<TagRuleInstance>> childMapGroup = instanceList.stream().collect(Collectors.groupingBy(TagRuleInstance::getParentInstanceId));
        System.out.println(childMapGroup.toString());
        return null;
    }

    public static void main(String[] args) {

    }
}
