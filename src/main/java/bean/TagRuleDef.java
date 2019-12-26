package bean;

public class TagRuleDef {

    private String id;
    private String ruleCode;
    private String ruleDescription;
    private Double ruleWeight;
    private String ruleParentId;
    private String scoreExp;
    private Integer version;
    private int level;
    private String sortCode;
    private String displayName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public Double getRuleWeight() {
        return ruleWeight;
    }

    public void setRuleWeight(Double ruleWeight) {
        this.ruleWeight = ruleWeight;
    }

    public String getRuleParentId() {
        return ruleParentId;
    }

    public void setRuleParentId(String ruleParentId) {
        this.ruleParentId = ruleParentId;
    }

    public String getScoreExp() {
        return scoreExp;
    }

    public void setScoreExp(String scoreExp) {
        this.scoreExp = scoreExp;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}