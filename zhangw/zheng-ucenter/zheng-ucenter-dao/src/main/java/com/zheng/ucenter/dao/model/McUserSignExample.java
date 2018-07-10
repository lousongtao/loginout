package com.zheng.ucenter.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class McUserSignExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private static final long serialVersionUID = 1L;

    public McUserSignExample() {
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

    protected abstract static class GeneratedCriteria implements Serializable {
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

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNull() {
            addCriterion("sign_time is null");
            return (Criteria) this;
        }

        public Criteria andSignTimeIsNotNull() {
            addCriterion("sign_time is not null");
            return (Criteria) this;
        }

        public Criteria andSignTimeEqualTo(Date value) {
            addCriterion("sign_time =", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotEqualTo(Date value) {
            addCriterion("sign_time <>", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThan(Date value) {
            addCriterion("sign_time >", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sign_time >=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThan(Date value) {
            addCriterion("sign_time <", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeLessThanOrEqualTo(Date value) {
            addCriterion("sign_time <=", value, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeIn(List<Date> values) {
            addCriterion("sign_time in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotIn(List<Date> values) {
            addCriterion("sign_time not in", values, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeBetween(Date value1, Date value2) {
            addCriterion("sign_time between", value1, value2, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTimeNotBetween(Date value1, Date value2) {
            addCriterion("sign_time not between", value1, value2, "signTime");
            return (Criteria) this;
        }

        public Criteria andSignTypeIsNull() {
            addCriterion("sign_type is null");
            return (Criteria) this;
        }

        public Criteria andSignTypeIsNotNull() {
            addCriterion("sign_type is not null");
            return (Criteria) this;
        }

        public Criteria andSignTypeEqualTo(Byte value) {
            addCriterion("sign_type =", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotEqualTo(Byte value) {
            addCriterion("sign_type <>", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeGreaterThan(Byte value) {
            addCriterion("sign_type >", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("sign_type >=", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeLessThan(Byte value) {
            addCriterion("sign_type <", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeLessThanOrEqualTo(Byte value) {
            addCriterion("sign_type <=", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeIn(List<Byte> values) {
            addCriterion("sign_type in", values, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotIn(List<Byte> values) {
            addCriterion("sign_type not in", values, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeBetween(Byte value1, Byte value2) {
            addCriterion("sign_type between", value1, value2, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("sign_type not between", value1, value2, "signType");
            return (Criteria) this;
        }

        public Criteria andSignLonIsNull() {
            addCriterion("sign_lon is null");
            return (Criteria) this;
        }

        public Criteria andSignLonIsNotNull() {
            addCriterion("sign_lon is not null");
            return (Criteria) this;
        }

        public Criteria andSignLonEqualTo(BigDecimal value) {
            addCriterion("sign_lon =", value, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLonNotEqualTo(BigDecimal value) {
            addCriterion("sign_lon <>", value, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLonGreaterThan(BigDecimal value) {
            addCriterion("sign_lon >", value, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLonGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sign_lon >=", value, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLonLessThan(BigDecimal value) {
            addCriterion("sign_lon <", value, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLonLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sign_lon <=", value, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLonIn(List<BigDecimal> values) {
            addCriterion("sign_lon in", values, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLonNotIn(List<BigDecimal> values) {
            addCriterion("sign_lon not in", values, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLonBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sign_lon between", value1, value2, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLonNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sign_lon not between", value1, value2, "signLon");
            return (Criteria) this;
        }

        public Criteria andSignLatIsNull() {
            addCriterion("sign_lat is null");
            return (Criteria) this;
        }

        public Criteria andSignLatIsNotNull() {
            addCriterion("sign_lat is not null");
            return (Criteria) this;
        }

        public Criteria andSignLatEqualTo(BigDecimal value) {
            addCriterion("sign_lat =", value, "signLat");
            return (Criteria) this;
        }

        public Criteria andSignLatNotEqualTo(BigDecimal value) {
            addCriterion("sign_lat <>", value, "signLat");
            return (Criteria) this;
        }

        public Criteria andSignLatGreaterThan(BigDecimal value) {
            addCriterion("sign_lat >", value, "signLat");
            return (Criteria) this;
        }

        public Criteria andSignLatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sign_lat >=", value, "signLat");
            return (Criteria) this;
        }

        public Criteria andSignLatLessThan(BigDecimal value) {
            addCriterion("sign_lat <", value, "signLat");
            return (Criteria) this;
        }

        public Criteria andSignLatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sign_lat <=", value, "signLat");
            return (Criteria) this;
        }

        public Criteria andSignLatIn(List<BigDecimal> values) {
            addCriterion("sign_lat in", values, "signLat");
            return (Criteria) this;
        }

        public Criteria andSignLatNotIn(List<BigDecimal> values) {
            addCriterion("sign_lat not in", values, "signLat");
            return (Criteria) this;
        }

        public Criteria andSignLatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sign_lat between", value1, value2, "signLat");
            return (Criteria) this;
        }

        public Criteria andSignLatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sign_lat not between", value1, value2, "signLat");
            return (Criteria) this;
        }

        public Criteria andSingAddressIsNull() {
            addCriterion("sing_address is null");
            return (Criteria) this;
        }

        public Criteria andSingAddressIsNotNull() {
            addCriterion("sing_address is not null");
            return (Criteria) this;
        }

        public Criteria andSingAddressEqualTo(String value) {
            addCriterion("sing_address =", value, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressNotEqualTo(String value) {
            addCriterion("sing_address <>", value, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressGreaterThan(String value) {
            addCriterion("sing_address >", value, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressGreaterThanOrEqualTo(String value) {
            addCriterion("sing_address >=", value, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressLessThan(String value) {
            addCriterion("sing_address <", value, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressLessThanOrEqualTo(String value) {
            addCriterion("sing_address <=", value, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressLike(String value) {
            addCriterion("sing_address like", value, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressNotLike(String value) {
            addCriterion("sing_address not like", value, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressIn(List<String> values) {
            addCriterion("sing_address in", values, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressNotIn(List<String> values) {
            addCriterion("sing_address not in", values, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressBetween(String value1, String value2) {
            addCriterion("sing_address between", value1, value2, "singAddress");
            return (Criteria) this;
        }

        public Criteria andSingAddressNotBetween(String value1, String value2) {
            addCriterion("sing_address not between", value1, value2, "singAddress");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
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