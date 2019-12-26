package main;

import bean.TagRuleDef;
import bean.TagRuleInstance;
import org.springframework.util.CollectionUtils;
import util.DBUtil;

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

public class RuleInstanceService {
    public TagRuleInstance getRuleInstance(Integer index, String phaseNo, String idProjApply) {
        Connection conn = null;
        List<TagRuleInstance> instances = new ArrayList<TagRuleInstance>();
        try {
            Properties properties = new Properties();
            FileInputStream inputStream = new FileInputStream(new File("src\\main\\java\\config\\sql.properties"));
            properties.load(inputStream);
            String sql = properties.getProperty("sql");
            conn = DBUtil.getConn();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, index);
            preparedStatement.setString(2, idProjApply);
            preparedStatement.setString(3, phaseNo);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TagRuleInstance tagRuleInstance = new TagRuleInstance();
                tagRuleInstance.setId(resultSet.getLong(1));
                tagRuleInstance.setScoreRuleDefId(resultSet.getString(2));
                tagRuleInstance.setAutoScoreValue(resultSet.getDouble(3));
                tagRuleInstance.setParentInstanceId(resultSet.getLong(4));
                tagRuleInstance.setValueSource(resultSet.getString(5));
                tagRuleInstance.setSuit(resultSet.getBoolean(8));
                tagRuleInstance.setLevel(resultSet.getInt(9));

                TagRuleDef tagRuleDef = new TagRuleDef();
                tagRuleDef.setId(resultSet.getString(10));
                tagRuleDef.setRuleCode(resultSet.getString(11));
                tagRuleDef.setRuleDescription(resultSet.getString(12));
                tagRuleDef.setRuleParentId(resultSet.getString(13));
                tagRuleDef.setRuleWeight(resultSet.getDouble(14));
                tagRuleDef.setDisplayName(resultSet.getString(20));

                tagRuleInstance.setDefinition(tagRuleDef);
                instances.add(tagRuleInstance);
            }
            return dealRuleInstance(instances);
        } catch (Exception e) {
            System.err.println("ruleInstanceService error:" + e.getMessage());
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("conn close error!" + e.getMessage());
                }
            }
        }
        return null;
    }

    private TagRuleInstance dealRuleInstance(List<TagRuleInstance> instances) {
        if (CollectionUtils.isEmpty(instances)) {
            return null;
        }
        Map<Integer, List<TagRuleInstance>> levelListMap = instances.stream().collect(Collectors.groupingBy(TagRuleInstance::getLevel));
        TagRuleInstance instance = levelListMap.get(1).get(0);
        List<TagRuleInstance> level2 = levelListMap.get(2);
        List<TagRuleInstance> level3 = levelListMap.get(3);
        for (final TagRuleInstance instance2 : level2) {
            instance2.setParent(instance);
            final List<TagRuleInstance> child = new ArrayList<TagRuleInstance>();
            if (!CollectionUtils.isEmpty(level3)) {
                level3.forEach(instance3 -> {
                    if (instance2.getId().equals(instance3.getParentInstanceId())) {
                        child.add(instance3);
                        instance3.setParent(instance2);
                    }
                });
            }
            instance2.setChildren(child);
        }
        instance.setChildren(level2);
        instance.setRow(level3 == null ? level2.size() : level2.size() * level3.size());
        return instance;
    }
}
