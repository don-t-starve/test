DECLARE
  TYPE SCORE_LIST IS RECORD(
       ID_SCORE NUMBER,
       MANUAL_SCORE NUMBER, 
       MANUAL_SCORE_REASON VARCHAR2(4000), 
       SCORE_RULE_DEF_ID NUMBER
);
  TYPE SCORE_LIST_TAB IS TABLE OF SCORE_LIST;  
  CURSOR cur_score IS SELECT * FROM score_INS t WHERE t.MANUAL_SCORE_REASON = 'AA';--SELECT��任��ǰ�����һ�ڴ��ins
  v_score cur_score%ROWTYPE;
  V_LIST SCORE_LIST_TAB;
  CNT INT;
BEGIN
  SELECT I.ID_SCORE, I.MANUAL_SCORE, I.MANUAL_SCORE_REASON, I.SCORE_RULE_DEF_ID bulk collect INTO V_LIST 
  FROM SCORE_INS I WHERE I.MANUAL_SCORE_REASON = 'BB';--select��任��Ҫ���޸ĵ���һ��ins
  CNT := V_LIST.COUNT;
  OPEN cur_score;
  LOOP 
     FETCH cur_score INTO v_score;
     EXIT WHEN cur_score%NOTFOUND;
          FOR I IN 1 .. CNT LOOP
              IF v_score.SCORE_RULE_DEF_ID = V_LIST(I).SCORE_RULE_DEF_ID THEN--��������ѭ��def��ͬ�ĲŸ���
                UPDATE SCORE_INS SET MANUAL_SCORE = v_score.MANUAL_SCORE WHERE ID_SCORE = V_LIST(I).ID_SCORE;
                commit;
              END IF; 
          END LOOP;
   END LOOP;
   CLOSE cur_score;
END;
