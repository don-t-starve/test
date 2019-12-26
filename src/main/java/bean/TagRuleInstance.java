package bean;

import java.util.List;

public class TagRuleInstance {
    private Long id;
    private Long parentInstanceId;
    private String scoreRuleDefId;
    private Double autoScoreValue;
    private String valueSource;
    private Double manualScore;
    private String manualScoreReason;

    private transient List<TagRuleInstance> children;
    private transient TagRuleInstance parent;

    private transient TagRuleDef definition;

    private Boolean isSuit;

    private int level;

    private int row;

    public TagRuleDef getDefinition() {
        return definition;
    }

    public void setDefinition(TagRuleDef definition) {
        this.definition = definition;
    }

    public List<TagRuleInstance> getChildren() {
        return children;
    }

    public void setChildren(List<TagRuleInstance> children) {
        this.children = children;
    }

    public TagRuleInstance getParent() {
        return parent;
    }

    public void setParent(TagRuleInstance parent) {
        this.parent = parent;
    }

    public Boolean getSuit() {
        return isSuit;
    }

    public void setSuit(Boolean suit) {
        isSuit = suit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentInstanceId() {
        return parentInstanceId;
    }

    public void setParentInstanceId(Long parentInstanceId) {
        this.parentInstanceId = parentInstanceId;
    }

    public String getScoreRuleDefId() {
        return scoreRuleDefId;
    }

    public void setScoreRuleDefId(String scoreRuleDefId) {
        this.scoreRuleDefId = scoreRuleDefId;
    }

    public Double getAutoScoreValue() {
        return autoScoreValue;
    }

    public void setAutoScoreValue(Double autoScoreValue) {
        this.autoScoreValue = autoScoreValue;
    }

    public String getValueSource() {
        return valueSource;
    }

    public void setValueSource(String valueSource) {
        this.valueSource = valueSource;
    }

    public Double getManualScore() {
        return manualScore;
    }

    public void setManualScore(Double manualScore) {
        this.manualScore = manualScore;
    }

    public String getManualScoreReason() {
        return manualScoreReason;
    }

    public void setManualScoreReason(String manualScoreReason) {
        this.manualScoreReason = manualScoreReason;
    }
}
