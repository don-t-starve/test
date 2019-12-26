package main;

import bean.TagRuleInstance;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import util.ScoreUtil;

import javax.swing.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private JPanel northJPanel;
    private JSplitPane centerPane;
    private JPanel leftJPanel;
    private JPanel rightJPanel;

    private ScrollPane leftScrollPane;
    private ScrollPane rightScrollPane;

    private JPanel southJPanel;

    private GridBagLayout northGridBagLayout = new GridBagLayout();
    private GridBagLayout centerGridBagLayout = new GridBagLayout();

    private JTextField idProjApply;
    private JList<Integer> mainRuleCode;
    private JScrollPane mainRuleCodeScrollPane;
    private JTextField phaseNo;
    private JTextField otherPhaseNo;
    private JButton selectFirst;
    private JButton selectOther;

    private JButton submit;

    private TagRuleInstance firstInstance = null;
    private TagRuleInstance otherInstance = null;

    private RuleInstanceService ruleInstanceService = new RuleInstanceService();

    private static List<String> ruleCodeProcessor = new ArrayList<String>();

    static {
        ruleCodeProcessor.add("1.4.6");
        ruleCodeProcessor.add("4.5.6");
        ruleCodeProcessor.add("B_1.4.9");
        ruleCodeProcessor.add("B_4.5.9");
        ruleCodeProcessor.add("C_1.4.9");
        ruleCodeProcessor.add("C_2.5.9");
        ruleCodeProcessor.add("D_1.4.4");
        ruleCodeProcessor.add("D_4.6.4");

        ruleCodeProcessor.add("8.3.2");
        ruleCodeProcessor.add("B_8.3.2");
        ruleCodeProcessor.add("C_6.3.2");
        ruleCodeProcessor.add("D_8.3.2");
    }

    public MainFrame() {

        northJPanel = new JPanel(northGridBagLayout);
        JLabel idProjApplyText = new JLabel("项目申请号:");
        northGridBagLayout.addLayoutComponent(idProjApplyText,
                ScoreUtil.buildConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.BOTH));
        northJPanel.add(idProjApplyText);

        idProjApply = new JTextField("HIS11010217");
        northGridBagLayout.addLayoutComponent(idProjApply,
                ScoreUtil.buildConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.BOTH));
        northJPanel.add(idProjApply);

        JLabel mainRuleCodeText = new JLabel("主标签ruleCode:");
        northGridBagLayout.addLayoutComponent(mainRuleCodeText,
                ScoreUtil.buildConstraints(2, 0, 1, 1, 1, 0, GridBagConstraints.BOTH));

        Integer[] ruleCode = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
        mainRuleCode = new JList<Integer>(ruleCode);
        mainRuleCode.setVisibleRowCount(2);
        mainRuleCode.setSelectedIndex(0);
        mainRuleCodeScrollPane = new JScrollPane(mainRuleCode);
        northGridBagLayout.addLayoutComponent(mainRuleCodeScrollPane,
                ScoreUtil.buildConstraints(3, 0, 1, 1, 1, 0, GridBagConstraints.BOTH));
        northJPanel.add(mainRuleCodeScrollPane);

        JLabel phaseNoText = new JLabel("流程编号:");
        northGridBagLayout.addLayoutComponent(phaseNoText,
                ScoreUtil.buildConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.BOTH));
        northJPanel.add(phaseNoText);

        phaseNo = new JTextField("0010");
        northGridBagLayout.addLayoutComponent(phaseNo,
                ScoreUtil.buildConstraints(1, 1, 1, 1, 2, 0, GridBagConstraints.BOTH));
        northJPanel.add(phaseNo);

        selectFirst = new JButton("find");
        northGridBagLayout.addLayoutComponent(selectFirst,
                ScoreUtil.buildConstraints(2, 1, 1, GridBagConstraints.REMAINDER, 1, 0, GridBagConstraints.BOTH));
        northJPanel.add(selectFirst);

        JLabel otherPhaseNoText = new JLabel("其他岗位:");
        northGridBagLayout.addLayoutComponent(otherPhaseNoText,
                ScoreUtil.buildConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.BOTH));
        northJPanel.add(otherPhaseNoText);

        otherPhaseNo = new JTextField("0020");
        northGridBagLayout.addLayoutComponent(otherPhaseNo,
                ScoreUtil.buildConstraints(1, 2, 1, 1, 2, 0, GridBagConstraints.HORIZONTAL));
        northJPanel.add(otherPhaseNo);

        selectOther = new JButton("find");
        northGridBagLayout.addLayoutComponent(selectOther,
                ScoreUtil.buildConstraints(2, 2, 1, 2, 1, 0, GridBagConstraints.BOTH));
        northJPanel.add(selectOther);
        northJPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));

        leftJPanel = new JPanel();
        rightJPanel = new JPanel();

        leftScrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        rightScrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        leftScrollPane.add(leftJPanel);
        rightScrollPane.add(rightJPanel);

        centerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScrollPane, rightScrollPane);
        centerPane.setDividerLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2);

        southJPanel = new JPanel();
        submit = new JButton("submit");
        southJPanel.add(submit);

        this.setLayout(new BorderLayout());
        this.add(northJPanel, "North");
        this.add(centerPane, "Center");
        this.add(southJPanel, "South");
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setBounds(0, 0, width, height - 50);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        selectFirst.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Integer index = MainFrame.this.mainRuleCode.getSelectedValue();
                String phaseNo = MainFrame.this.phaseNo.getText();
                String idProjApply = MainFrame.this.idProjApply.getText();
                if (null == index || StringUtils.isEmpty(phaseNo) || StringUtils.isEmpty(idProjApply)) {
                    JOptionPane.showConfirmDialog(null, "mainRuleCode and phaseNo and idProjApply can not be empty!", "error", JOptionPane.YES_OPTION);
                    throw new IllegalArgumentException("mainRuleCode, phaseNo and idProjApply can not be empty!");
                }
                firstInstance = ruleInstanceService.getRuleInstance(index, phaseNo, idProjApply);
                if (null != firstInstance) {
                    updateCenterUI(leftJPanel, firstInstance);
                }
            }
        });

        selectOther.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                Integer index = MainFrame.this.mainRuleCode.getSelectedValue();
                String phaseNo = MainFrame.this.otherPhaseNo.getText();
                String idProjApply = MainFrame.this.idProjApply.getText();
                if (null == index || StringUtils.isEmpty(phaseNo) || StringUtils.isEmpty(idProjApply)) {
                    JOptionPane.showConfirmDialog(null, "mainRuleCode and phaseNo and idProjApply can not be empty!", "error", JOptionPane.YES_OPTION);
                    throw new IllegalArgumentException("mainRuleCode and phaseNo and idProjApply can not be empty!");
                }
                otherInstance = ruleInstanceService.getRuleInstance(index, phaseNo, idProjApply);
                if (null != otherInstance) {
                    updateCenterUI(rightJPanel, otherInstance);
                }
            }
        });

        submit.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (null != firstInstance && null != otherPhaseNo) {
                    writeSqlToFile(dealInstanceForUpdate(), idProjApply.getText() + "-" + otherPhaseNo.getText() + "-");
                } else {
                    JOptionPane.showConfirmDialog(null, "can not find firstInstance and otherInstance" + idProjApply.getText(),
                            "error", JOptionPane.OK_OPTION);
                }
            }
        });
    }

    private void writeSqlToFile(List<TagRuleInstance> instances, String fileName) {
        if (CollectionUtils.isEmpty(instances)) {
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (TagRuleInstance instance : instances) {
            buffer.append("UPDATE FP_SCORE_RULE_INSTANCE SET ");
            if (instance.getAutoScoreValue() != null) {
                buffer.append("AUTO_SCORE_VALUE = ");
                buffer.append(instance.getAutoScoreValue());
                buffer.append(", ");
            }
            if (!StringUtils.isEmpty(instance.getValueSource())) {
                buffer.append("VALUE_SOURCE = '");
                buffer.append(instance.getValueSource());
                buffer.append("', ");
            }
            if (null != instance.getSuit()) {
                buffer.append("IS_SUIT = ");
                if (instance.getSuit()) {
                    buffer.append("1");
                } else {
                    buffer.append("0");
                }
                buffer.append(", ");
            }
            buffer.delete(buffer.length() - 2, buffer.length() - 1);
            buffer.append("WHERE ID_FP_SCORE_RULE_INSTANCE = ");
            buffer.append(instance.getId());
            buffer.append(";");
            buffer.append("\r\n");
        }
        String filePath = ScoreUtil.getCurrentPath() + "sqlOutput\\";
        fileName = fileName + otherInstance.getDefinition().getRuleCode() + ".sql";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        File files = new File(filePath + fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(files);
            out.write(buffer.toString().getBytes());
            out.flush();
            JOptionPane.showConfirmDialog(null, "生产成功！", "message", JOptionPane.OK_OPTION);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "生产失败！", "error", JOptionPane.OK_OPTION);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.err.println("outputStream close error!" + e.getMessage());
                }
            }
        }
    }

    private List<TagRuleInstance> dealInstanceForUpdate() {
        BigDecimal diff = new BigDecimal("0");
        List<TagRuleInstance> ruleInstances = new ArrayList<TagRuleInstance>();
        TagRuleInstance instanceFirst = null;
        A :
        for (TagRuleInstance instance : firstInstance.getChildren()) {
            for (TagRuleInstance instance1 : instance.getChildren()) {
                if (ruleCodeProcessor.contains(instance1.getDefinition().getRuleCode())) {
                    instanceFirst = instance1;
                    break A;
                }
            }
        }
        TagRuleInstance instanceOther = null;
        B :
        for (TagRuleInstance instance : otherInstance.getChildren()) {
            for (TagRuleInstance instance1 : instance.getChildren()) {
                if (ruleCodeProcessor.contains(instance1.getDefinition().getRuleCode())) {
                    instanceOther = instance1;
                    break B;
                }
            }
        }
        if (null != instanceFirst && null != instanceOther) {
            TagRuleInstance instance3 = new TagRuleInstance();
            instance3.setId(instanceOther.getId());
            boolean flag = false;
            if (!instanceFirst.getAutoScoreValue().equals(instanceOther.getAutoScoreValue())) {
                instance3.setAutoScoreValue(instanceFirst.getAutoScoreValue());
                flag = true;
            }
            if (instanceFirst.getValueSource() != null && !instanceFirst.getValueSource().equals(instanceOther.getValueSource())) {
                instance3.setValueSource(instanceFirst.getValueSource());
                flag = true;
            }
            if (!instanceFirst.getSuit().equals(instanceOther.getSuit())) {
                instance3.setSuit(instanceFirst.getSuit());
            }
            if (flag) {
                ruleInstances.add(instance3);
            }
            TagRuleInstance parentFirst = instanceFirst.getParent();
            TagRuleInstance parentOther = instanceOther.getParent();
            TagRuleInstance grandParentFirst = parentFirst.getParent();
            TagRuleInstance grandParentOther = parentOther.getParent();

            TagRuleInstance instance2 = new TagRuleInstance();
            instance2.setId(instanceOther.getParentInstanceId());
            TagRuleInstance instance1 = new TagRuleInstance();
            instance1.setId(parentOther.getParentInstanceId());

            if (diff.compareTo(BigDecimal.ZERO) != 0) {
                diff = diff.multiply(new BigDecimal(Double.toString(instanceOther.getDefinition().getRuleWeight())));
                instance2.setAutoScoreValue(diff.add(new BigDecimal(Double.toString(parentOther.getAutoScoreValue()))).doubleValue());
                instance1.setAutoScoreValue(diff.multiply(new BigDecimal(Double.toString(parentOther.getDefinition().getRuleWeight())))
                        .add(new BigDecimal(Double.toString(grandParentOther.getAutoScoreValue()))).doubleValue());
            }
            if (!parentFirst.getSuit().equals(parentOther.getSuit())) {
                instance2.setSuit(parentFirst.getSuit());
            }
            if (!grandParentFirst.getSuit().equals(grandParentOther.getSuit())) {
                instance1.setSuit(grandParentFirst.getSuit());
            }
            if (instance2.getSuit() != null || instance2.getAutoScoreValue() != null) {
                ruleInstances.add(instance2);
            }
            if (instance1.getSuit() != null || instance1.getAutoScoreValue() != null) {
                ruleInstances.add(instance1);
            }
        }
        return ruleInstances;
    }


    private void updateCenterUI(JPanel jPanel, TagRuleInstance topTagRuleInstance) {
        if (null == jPanel || null == topTagRuleInstance) {
            return;
        }
        jPanel.removeAll();
        JLabel mainLabelText = new JLabel("主标签");
        JLabel secondLabelText = new JLabel("次标签");
        JLabel thirdLabelText = new JLabel("从标签");

        jPanel.setLayout(centerGridBagLayout);
        centerGridBagLayout.addLayoutComponent(mainLabelText,
                ScoreUtil.buildConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.NONE));
        jPanel.add(mainLabelText);

        centerGridBagLayout.addLayoutComponent(secondLabelText,
                ScoreUtil.buildConstraints(1, 0, 1, 1, 2, 0, GridBagConstraints.NONE));
        jPanel.add(secondLabelText);

        centerGridBagLayout.addLayoutComponent(thirdLabelText,
                ScoreUtil.buildConstraints(2, 0, 1, GridBagConstraints.REMAINDER, 1, 0, GridBagConstraints.BOTH));
        jPanel.add(thirdLabelText);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridheight = topTagRuleInstance.getRow() + 1;
        constraints.fill = GridBagConstraints.BOTH;

        JLabel mainLabel = new JLabel(topTagRuleInstance.getId() +
                ":" + topTagRuleInstance.getDefinition().getRuleCode() +
                ":" + topTagRuleInstance.getDefinition().getDisplayName() +
                ":" + topTagRuleInstance.getAutoScoreValue());
        mainLabel.setBorder(BorderFactory.createLineBorder(Color.PINK));
        centerGridBagLayout.addLayoutComponent(mainLabel, constraints);
        jPanel.add(mainLabel);

        for (TagRuleInstance instance2 : topTagRuleInstance.getChildren()) {
            constraints = new GridBagConstraints();
            constraints.gridheight = instance2.getChildren().size();
            constraints.fill = GridBagConstraints.BOTH;

            JLabel secondLabel = new JLabel("<html>" + instance2.getId() +
                    ":" + instance2.getDefinition().getRuleCode() +
                    ":" + instance2.getDefinition().getDisplayName() +
                    ":" + instance2.getAutoScoreValue() +
                    ":" + instance2.getSuit() + "<html/>");
            secondLabel.setBorder(BorderFactory.createLineBorder(Color.PINK));
            centerGridBagLayout.addLayoutComponent(secondLabel, constraints);
            jPanel.add(secondLabel);

            for (TagRuleInstance instance3 : instance2.getChildren()) {
                constraints = new GridBagConstraints();
                constraints.gridwidth = GridBagConstraints.REMAINDER;
                constraints.fill = GridBagConstraints.BOTH;
                JLabel label = new JLabel("<html>" + instance3.getId() +
                        ":" + instance3.getDefinition().getRuleCode() +
                        ":" + instance3.getDefinition().getDisplayName() +
                        ":" + instance3.getSuit() + "</html>");
                label.setBorder(BorderFactory.createLineBorder(Color.PINK));
                if (ruleCodeProcessor.contains(instance3.getDefinition().getRuleCode())) {
                    label.setOpaque(true);
                    label.setBackground(Color.YELLOW);
                }
                centerGridBagLayout.addLayoutComponent(label, constraints);
                jPanel.add(label);
            }
        }
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.PINK));
        centerGridBagLayout.addLayoutComponent(label, constraints);
        jPanel.add(label);
        jPanel.updateUI();
    }
}
