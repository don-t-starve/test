DECLARE
  TYPE SCORE_LIST IS RECORD(
       ID_SCORE NUMBER,
       MANUAL_SCORE NUMBER, 
       MANUAL_SCORE_REASON VARCHAR2(4000), 
       SCORE_RULE_DEF_ID NUMBER
);
  TYPE SCORE_LIST_TAB IS TABLE OF SCORE_LIST;  
  CURSOR cur_score IS SELECT * FROM score_INS t WHERE t.MANUAL_SCORE_REASON = 'AA';--SELECT语句换成前手最后一岗打分ins
  v_score cur_score%ROWTYPE;
  V_LIST SCORE_LIST_TAB;
  CNT INT;
BEGIN
  SELECT I.ID_SCORE, I.MANUAL_SCORE, I.MANUAL_SCORE_REASON, I.SCORE_RULE_DEF_ID bulk collect INTO V_LIST 
  FROM SCORE_INS I WHERE I.MANUAL_SCORE_REASON = 'BB';--select语句换成要被修改的那一套ins
  CNT := V_LIST.COUNT;
  OPEN cur_score;
  LOOP 
     FETCH cur_score INTO v_score;
     EXIT WHEN cur_score%NOTFOUND;
          FOR I IN 1 .. CNT LOOP
              IF v_score.SCORE_RULE_DEF_ID = V_LIST(I).SCORE_RULE_DEF_ID THEN--两个集合循环def相同的才更新
                UPDATE SCORE_INS SET MANUAL_SCORE = v_score.MANUAL_SCORE WHERE ID_SCORE = V_LIST(I).ID_SCORE;
                commit;
              END IF; 
          END LOOP;
   END LOOP;
   CLOSE cur_score;
END;


--投后画像重评存储过程
DECLARE
  TYPE FLOW_TASK IS RECORD (
    ID_PE_FLOW_TASK_INFO VARCHAR2(32)
  );
  TYPE SCORE_REEVALUATE (
    ID_PE_SCORE_REEVALUATE VARCHAR(32)
  );
  VALUE_FLOW_TASK FLOW_TASK;--对象的创建
  TYPE SCORE_REEVALUATE_TAB IS TABLE OF SCORE_REEVALUATE;
  SCORE_REEVALUATE_LIST SCORE_REEVALUATE_TAB;--列表的创建
  
  CURSOR MODE_CURSOR IS SELECT M.MODE_ID FROM PE_FLOW_MODE_INFO M
  LEFT JOIN PE_FLOW_CATALOG_INFO C ON M.MODE_ID = C.MODE_ID
  WHERE C.MODE_TYPE = '' AND M.PHASE_NO != '8000' ORDER BY M.PHASE_NO;--创建游标
  VALUE_MODE MODE_CURSOR%ROWTYPE;--实例游标对象
BEGIN
  SELECT R.ID_PE_SCORE_REEVALUATE
