package com.gzs.learn.log.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysPerfLogCountPoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysPerfLogCountPoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * @author guanzhisong
	 * @date 2019-10-22 16:37:05
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMetaIdIsNull() {
            addCriterion("meta_id is null");
            return (Criteria) this;
        }

        public Criteria andMetaIdIsNotNull() {
            addCriterion("meta_id is not null");
            return (Criteria) this;
        }

        public Criteria andMetaIdEqualTo(Long value) {
            addCriterion("meta_id =", value, "metaId");
            return (Criteria) this;
        }

        public Criteria andMetaIdNotEqualTo(Long value) {
            addCriterion("meta_id <>", value, "metaId");
            return (Criteria) this;
        }

        public Criteria andMetaIdGreaterThan(Long value) {
            addCriterion("meta_id >", value, "metaId");
            return (Criteria) this;
        }

        public Criteria andMetaIdGreaterThanOrEqualTo(Long value) {
            addCriterion("meta_id >=", value, "metaId");
            return (Criteria) this;
        }

        public Criteria andMetaIdLessThan(Long value) {
            addCriterion("meta_id <", value, "metaId");
            return (Criteria) this;
        }

        public Criteria andMetaIdLessThanOrEqualTo(Long value) {
            addCriterion("meta_id <=", value, "metaId");
            return (Criteria) this;
        }

        public Criteria andMetaIdIn(List<Long> values) {
            addCriterion("meta_id in", values, "metaId");
            return (Criteria) this;
        }

        public Criteria andMetaIdNotIn(List<Long> values) {
            addCriterion("meta_id not in", values, "metaId");
            return (Criteria) this;
        }

        public Criteria andMetaIdBetween(Long value1, Long value2) {
            addCriterion("meta_id between", value1, value2, "metaId");
            return (Criteria) this;
        }

        public Criteria andMetaIdNotBetween(Long value1, Long value2) {
            addCriterion("meta_id not between", value1, value2, "metaId");
            return (Criteria) this;
        }

        public Criteria andCountDateIsNull() {
            addCriterion("count_date is null");
            return (Criteria) this;
        }

        public Criteria andCountDateIsNotNull() {
            addCriterion("count_date is not null");
            return (Criteria) this;
        }

        public Criteria andCountDateEqualTo(String value) {
            addCriterion("count_date =", value, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateNotEqualTo(String value) {
            addCriterion("count_date <>", value, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateGreaterThan(String value) {
            addCriterion("count_date >", value, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateGreaterThanOrEqualTo(String value) {
            addCriterion("count_date >=", value, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateLessThan(String value) {
            addCriterion("count_date <", value, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateLessThanOrEqualTo(String value) {
            addCriterion("count_date <=", value, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateLike(String value) {
            addCriterion("count_date like", value, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateNotLike(String value) {
            addCriterion("count_date not like", value, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateIn(List<String> values) {
            addCriterion("count_date in", values, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateNotIn(List<String> values) {
            addCriterion("count_date not in", values, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateBetween(String value1, String value2) {
            addCriterion("count_date between", value1, value2, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDateNotBetween(String value1, String value2) {
            addCriterion("count_date not between", value1, value2, "countDate");
            return (Criteria) this;
        }

        public Criteria andCountDurationIsNull() {
            addCriterion("count_duration is null");
            return (Criteria) this;
        }

        public Criteria andCountDurationIsNotNull() {
            addCriterion("count_duration is not null");
            return (Criteria) this;
        }

        public Criteria andCountDurationEqualTo(String value) {
            addCriterion("count_duration =", value, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationNotEqualTo(String value) {
            addCriterion("count_duration <>", value, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationGreaterThan(String value) {
            addCriterion("count_duration >", value, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationGreaterThanOrEqualTo(String value) {
            addCriterion("count_duration >=", value, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationLessThan(String value) {
            addCriterion("count_duration <", value, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationLessThanOrEqualTo(String value) {
            addCriterion("count_duration <=", value, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationLike(String value) {
            addCriterion("count_duration like", value, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationNotLike(String value) {
            addCriterion("count_duration not like", value, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationIn(List<String> values) {
            addCriterion("count_duration in", values, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationNotIn(List<String> values) {
            addCriterion("count_duration not in", values, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationBetween(String value1, String value2) {
            addCriterion("count_duration between", value1, value2, "countDuration");
            return (Criteria) this;
        }

        public Criteria andCountDurationNotBetween(String value1, String value2) {
            addCriterion("count_duration not between", value1, value2, "countDuration");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalIsNull() {
            addCriterion("execute_total is null");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalIsNotNull() {
            addCriterion("execute_total is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalEqualTo(Long value) {
            addCriterion("execute_total =", value, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalNotEqualTo(Long value) {
            addCriterion("execute_total <>", value, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalGreaterThan(Long value) {
            addCriterion("execute_total >", value, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalGreaterThanOrEqualTo(Long value) {
            addCriterion("execute_total >=", value, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalLessThan(Long value) {
            addCriterion("execute_total <", value, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalLessThanOrEqualTo(Long value) {
            addCriterion("execute_total <=", value, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalIn(List<Long> values) {
            addCriterion("execute_total in", values, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalNotIn(List<Long> values) {
            addCriterion("execute_total not in", values, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalBetween(Long value1, Long value2) {
            addCriterion("execute_total between", value1, value2, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTotalNotBetween(Long value1, Long value2) {
            addCriterion("execute_total not between", value1, value2, "executeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionIsNull() {
            addCriterion("execute_exception is null");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionIsNotNull() {
            addCriterion("execute_exception is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionEqualTo(Long value) {
            addCriterion("execute_exception =", value, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionNotEqualTo(Long value) {
            addCriterion("execute_exception <>", value, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionGreaterThan(Long value) {
            addCriterion("execute_exception >", value, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionGreaterThanOrEqualTo(Long value) {
            addCriterion("execute_exception >=", value, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionLessThan(Long value) {
            addCriterion("execute_exception <", value, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionLessThanOrEqualTo(Long value) {
            addCriterion("execute_exception <=", value, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionIn(List<Long> values) {
            addCriterion("execute_exception in", values, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionNotIn(List<Long> values) {
            addCriterion("execute_exception not in", values, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionBetween(Long value1, Long value2) {
            addCriterion("execute_exception between", value1, value2, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteExceptionNotBetween(Long value1, Long value2) {
            addCriterion("execute_exception not between", value1, value2, "executeException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionIsNull() {
            addCriterion("execute_sys_exception is null");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionIsNotNull() {
            addCriterion("execute_sys_exception is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionEqualTo(Long value) {
            addCriterion("execute_sys_exception =", value, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionNotEqualTo(Long value) {
            addCriterion("execute_sys_exception <>", value, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionGreaterThan(Long value) {
            addCriterion("execute_sys_exception >", value, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionGreaterThanOrEqualTo(Long value) {
            addCriterion("execute_sys_exception >=", value, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionLessThan(Long value) {
            addCriterion("execute_sys_exception <", value, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionLessThanOrEqualTo(Long value) {
            addCriterion("execute_sys_exception <=", value, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionIn(List<Long> values) {
            addCriterion("execute_sys_exception in", values, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionNotIn(List<Long> values) {
            addCriterion("execute_sys_exception not in", values, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionBetween(Long value1, Long value2) {
            addCriterion("execute_sys_exception between", value1, value2, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteSysExceptionNotBetween(Long value1, Long value2) {
            addCriterion("execute_sys_exception not between", value1, value2, "executeSysException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionIsNull() {
            addCriterion("execute_biz_exception is null");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionIsNotNull() {
            addCriterion("execute_biz_exception is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionEqualTo(Long value) {
            addCriterion("execute_biz_exception =", value, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionNotEqualTo(Long value) {
            addCriterion("execute_biz_exception <>", value, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionGreaterThan(Long value) {
            addCriterion("execute_biz_exception >", value, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionGreaterThanOrEqualTo(Long value) {
            addCriterion("execute_biz_exception >=", value, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionLessThan(Long value) {
            addCriterion("execute_biz_exception <", value, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionLessThanOrEqualTo(Long value) {
            addCriterion("execute_biz_exception <=", value, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionIn(List<Long> values) {
            addCriterion("execute_biz_exception in", values, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionNotIn(List<Long> values) {
            addCriterion("execute_biz_exception not in", values, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionBetween(Long value1, Long value2) {
            addCriterion("execute_biz_exception between", value1, value2, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteBizExceptionNotBetween(Long value1, Long value2) {
            addCriterion("execute_biz_exception not between", value1, value2, "executeBizException");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalIsNull() {
            addCriterion("execute_time_total is null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalIsNotNull() {
            addCriterion("execute_time_total is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalEqualTo(Long value) {
            addCriterion("execute_time_total =", value, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalNotEqualTo(Long value) {
            addCriterion("execute_time_total <>", value, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalGreaterThan(Long value) {
            addCriterion("execute_time_total >", value, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalGreaterThanOrEqualTo(Long value) {
            addCriterion("execute_time_total >=", value, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalLessThan(Long value) {
            addCriterion("execute_time_total <", value, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalLessThanOrEqualTo(Long value) {
            addCriterion("execute_time_total <=", value, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalIn(List<Long> values) {
            addCriterion("execute_time_total in", values, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalNotIn(List<Long> values) {
            addCriterion("execute_time_total not in", values, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalBetween(Long value1, Long value2) {
            addCriterion("execute_time_total between", value1, value2, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeTotalNotBetween(Long value1, Long value2) {
            addCriterion("execute_time_total not between", value1, value2, "executeTimeTotal");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgIsNull() {
            addCriterion("execute_time_avg is null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgIsNotNull() {
            addCriterion("execute_time_avg is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgEqualTo(Double value) {
            addCriterion("execute_time_avg =", value, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgNotEqualTo(Double value) {
            addCriterion("execute_time_avg <>", value, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgGreaterThan(Double value) {
            addCriterion("execute_time_avg >", value, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgGreaterThanOrEqualTo(Double value) {
            addCriterion("execute_time_avg >=", value, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgLessThan(Double value) {
            addCriterion("execute_time_avg <", value, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgLessThanOrEqualTo(Double value) {
            addCriterion("execute_time_avg <=", value, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgIn(List<Double> values) {
            addCriterion("execute_time_avg in", values, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgNotIn(List<Double> values) {
            addCriterion("execute_time_avg not in", values, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgBetween(Double value1, Double value2) {
            addCriterion("execute_time_avg between", value1, value2, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeAvgNotBetween(Double value1, Double value2) {
            addCriterion("execute_time_avg not between", value1, value2, "executeTimeAvg");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxIsNull() {
            addCriterion("execute_time_max is null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxIsNotNull() {
            addCriterion("execute_time_max is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxEqualTo(Integer value) {
            addCriterion("execute_time_max =", value, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxNotEqualTo(Integer value) {
            addCriterion("execute_time_max <>", value, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxGreaterThan(Integer value) {
            addCriterion("execute_time_max >", value, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxGreaterThanOrEqualTo(Integer value) {
            addCriterion("execute_time_max >=", value, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxLessThan(Integer value) {
            addCriterion("execute_time_max <", value, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxLessThanOrEqualTo(Integer value) {
            addCriterion("execute_time_max <=", value, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxIn(List<Integer> values) {
            addCriterion("execute_time_max in", values, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxNotIn(List<Integer> values) {
            addCriterion("execute_time_max not in", values, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxBetween(Integer value1, Integer value2) {
            addCriterion("execute_time_max between", value1, value2, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMaxNotBetween(Integer value1, Integer value2) {
            addCriterion("execute_time_max not between", value1, value2, "executeTimeMax");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinIsNull() {
            addCriterion("execute_time_min is null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinIsNotNull() {
            addCriterion("execute_time_min is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinEqualTo(Integer value) {
            addCriterion("execute_time_min =", value, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinNotEqualTo(Integer value) {
            addCriterion("execute_time_min <>", value, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinGreaterThan(Integer value) {
            addCriterion("execute_time_min >", value, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinGreaterThanOrEqualTo(Integer value) {
            addCriterion("execute_time_min >=", value, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinLessThan(Integer value) {
            addCriterion("execute_time_min <", value, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinLessThanOrEqualTo(Integer value) {
            addCriterion("execute_time_min <=", value, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinIn(List<Integer> values) {
            addCriterion("execute_time_min in", values, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinNotIn(List<Integer> values) {
            addCriterion("execute_time_min not in", values, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinBetween(Integer value1, Integer value2) {
            addCriterion("execute_time_min between", value1, value2, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andExecuteTimeMinNotBetween(Integer value1, Integer value2) {
            addCriterion("execute_time_min not between", value1, value2, "executeTimeMin");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andTimestampIsNull() {
            addCriterion("timestamp is null");
            return (Criteria) this;
        }

        public Criteria andTimestampIsNotNull() {
            addCriterion("timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andTimestampEqualTo(Date value) {
            addCriterion("timestamp =", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotEqualTo(Date value) {
            addCriterion("timestamp <>", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampGreaterThan(Date value) {
            addCriterion("timestamp >", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("timestamp >=", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampLessThan(Date value) {
            addCriterion("timestamp <", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampLessThanOrEqualTo(Date value) {
            addCriterion("timestamp <=", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampIn(List<Date> values) {
            addCriterion("timestamp in", values, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotIn(List<Date> values) {
            addCriterion("timestamp not in", values, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampBetween(Date value1, Date value2) {
            addCriterion("timestamp between", value1, value2, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotBetween(Date value1, Date value2) {
            addCriterion("timestamp not between", value1, value2, "timestamp");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * @author guanzhisong
	 * @date 2019-10-22 16:37:05
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}