package com.zheng.upms.dao.model;

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

        public Criteria andSignIdIsNull() {
            addCriterion("sign_id is null");
            return (Criteria) this;
        }

        public Criteria andSignIdIsNotNull() {
            addCriterion("sign_id is not null");
            return (Criteria) this;
        }

        public Criteria andSignIdEqualTo(Long value) {
            addCriterion("sign_id =", value, "signId");
            return (Criteria) this;
        }

        public Criteria andSignIdNotEqualTo(Long value) {
            addCriterion("sign_id <>", value, "signId");
            return (Criteria) this;
        }

        public Criteria andSignIdGreaterThan(Long value) {
            addCriterion("sign_id >", value, "signId");
            return (Criteria) this;
        }

        public Criteria andSignIdGreaterThanOrEqualTo(Long value) {
            addCriterion("sign_id >=", value, "signId");
            return (Criteria) this;
        }

        public Criteria andSignIdLessThan(Long value) {
            addCriterion("sign_id <", value, "signId");
            return (Criteria) this;
        }

        public Criteria andSignIdLessThanOrEqualTo(Long value) {
            addCriterion("sign_id <=", value, "signId");
            return (Criteria) this;
        }

        public Criteria andSignIdIn(List<Long> values) {
            addCriterion("sign_id in", values, "signId");
            return (Criteria) this;
        }

        public Criteria andSignIdNotIn(List<Long> values) {
            addCriterion("sign_id not in", values, "signId");
            return (Criteria) this;
        }

        public Criteria andSignIdBetween(Long value1, Long value2) {
            addCriterion("sign_id between", value1, value2, "signId");
            return (Criteria) this;
        }

        public Criteria andSignIdNotBetween(Long value1, Long value2) {
            addCriterion("sign_id not between", value1, value2, "signId");
            return (Criteria) this;
        }

        public Criteria andUIdIsNull() {
            addCriterion("u_id is null");
            return (Criteria) this;
        }

        public Criteria andUIdIsNotNull() {
            addCriterion("u_id is not null");
            return (Criteria) this;
        }

        public Criteria andUIdEqualTo(Integer value) {
            addCriterion("u_id =", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotEqualTo(Integer value) {
            addCriterion("u_id <>", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdGreaterThan(Integer value) {
            addCriterion("u_id >", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("u_id >=", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLessThan(Integer value) {
            addCriterion("u_id <", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdLessThanOrEqualTo(Integer value) {
            addCriterion("u_id <=", value, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdIn(List<Integer> values) {
            addCriterion("u_id in", values, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotIn(List<Integer> values) {
            addCriterion("u_id not in", values, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdBetween(Integer value1, Integer value2) {
            addCriterion("u_id between", value1, value2, "uId");
            return (Criteria) this;
        }

        public Criteria andUIdNotBetween(Integer value1, Integer value2) {
            addCriterion("u_id not between", value1, value2, "uId");
            return (Criteria) this;
        }

        public Criteria andUNameIsNull() {
            addCriterion("u_name is null");
            return (Criteria) this;
        }

        public Criteria andUNameIsNotNull() {
            addCriterion("u_name is not null");
            return (Criteria) this;
        }

        public Criteria andUNameEqualTo(String value) {
            addCriterion("u_name =", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameNotEqualTo(String value) {
            addCriterion("u_name <>", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameGreaterThan(String value) {
            addCriterion("u_name >", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameGreaterThanOrEqualTo(String value) {
            addCriterion("u_name >=", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameLessThan(String value) {
            addCriterion("u_name <", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameLessThanOrEqualTo(String value) {
            addCriterion("u_name <=", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameLike(String value) {
            addCriterion("u_name like", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameNotLike(String value) {
            addCriterion("u_name not like", value, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameIn(List<String> values) {
            addCriterion("u_name in", values, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameNotIn(List<String> values) {
            addCriterion("u_name not in", values, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameBetween(String value1, String value2) {
            addCriterion("u_name between", value1, value2, "uName");
            return (Criteria) this;
        }

        public Criteria andUNameNotBetween(String value1, String value2) {
            addCriterion("u_name not between", value1, value2, "uName");
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

        public Criteria andSignTypeEqualTo(Integer value) {
            addCriterion("sign_type =", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotEqualTo(Integer value) {
            addCriterion("sign_type <>", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeGreaterThan(Integer value) {
            addCriterion("sign_type >", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_type >=", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeLessThan(Integer value) {
            addCriterion("sign_type <", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeLessThanOrEqualTo(Integer value) {
            addCriterion("sign_type <=", value, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeIn(List<Integer> values) {
            addCriterion("sign_type in", values, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotIn(List<Integer> values) {
            addCriterion("sign_type not in", values, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeBetween(Integer value1, Integer value2) {
            addCriterion("sign_type between", value1, value2, "signType");
            return (Criteria) this;
        }

        public Criteria andSignTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_type not between", value1, value2, "signType");
            return (Criteria) this;
        }

        public Criteria andLonIsNull() {
            addCriterion("lon is null");
            return (Criteria) this;
        }

        public Criteria andLonIsNotNull() {
            addCriterion("lon is not null");
            return (Criteria) this;
        }

        public Criteria andLonEqualTo(BigDecimal value) {
            addCriterion("lon =", value, "lon");
            return (Criteria) this;
        }

        public Criteria andLonNotEqualTo(BigDecimal value) {
            addCriterion("lon <>", value, "lon");
            return (Criteria) this;
        }

        public Criteria andLonGreaterThan(BigDecimal value) {
            addCriterion("lon >", value, "lon");
            return (Criteria) this;
        }

        public Criteria andLonGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lon >=", value, "lon");
            return (Criteria) this;
        }

        public Criteria andLonLessThan(BigDecimal value) {
            addCriterion("lon <", value, "lon");
            return (Criteria) this;
        }

        public Criteria andLonLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lon <=", value, "lon");
            return (Criteria) this;
        }

        public Criteria andLonIn(List<BigDecimal> values) {
            addCriterion("lon in", values, "lon");
            return (Criteria) this;
        }

        public Criteria andLonNotIn(List<BigDecimal> values) {
            addCriterion("lon not in", values, "lon");
            return (Criteria) this;
        }

        public Criteria andLonBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lon between", value1, value2, "lon");
            return (Criteria) this;
        }

        public Criteria andLonNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lon not between", value1, value2, "lon");
            return (Criteria) this;
        }

        public Criteria andLatIsNull() {
            addCriterion("lat is null");
            return (Criteria) this;
        }

        public Criteria andLatIsNotNull() {
            addCriterion("lat is not null");
            return (Criteria) this;
        }

        public Criteria andLatEqualTo(BigDecimal value) {
            addCriterion("lat =", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatNotEqualTo(BigDecimal value) {
            addCriterion("lat <>", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatGreaterThan(BigDecimal value) {
            addCriterion("lat >", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("lat >=", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatLessThan(BigDecimal value) {
            addCriterion("lat <", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("lat <=", value, "lat");
            return (Criteria) this;
        }

        public Criteria andLatIn(List<BigDecimal> values) {
            addCriterion("lat in", values, "lat");
            return (Criteria) this;
        }

        public Criteria andLatNotIn(List<BigDecimal> values) {
            addCriterion("lat not in", values, "lat");
            return (Criteria) this;
        }

        public Criteria andLatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lat between", value1, value2, "lat");
            return (Criteria) this;
        }

        public Criteria andLatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("lat not between", value1, value2, "lat");
            return (Criteria) this;
        }

        public Criteria andSignAddressIsNull() {
            addCriterion("sign_address is null");
            return (Criteria) this;
        }

        public Criteria andSignAddressIsNotNull() {
            addCriterion("sign_address is not null");
            return (Criteria) this;
        }

        public Criteria andSignAddressEqualTo(String value) {
            addCriterion("sign_address =", value, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressNotEqualTo(String value) {
            addCriterion("sign_address <>", value, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressGreaterThan(String value) {
            addCriterion("sign_address >", value, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressGreaterThanOrEqualTo(String value) {
            addCriterion("sign_address >=", value, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressLessThan(String value) {
            addCriterion("sign_address <", value, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressLessThanOrEqualTo(String value) {
            addCriterion("sign_address <=", value, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressLike(String value) {
            addCriterion("sign_address like", value, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressNotLike(String value) {
            addCriterion("sign_address not like", value, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressIn(List<String> values) {
            addCriterion("sign_address in", values, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressNotIn(List<String> values) {
            addCriterion("sign_address not in", values, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressBetween(String value1, String value2) {
            addCriterion("sign_address between", value1, value2, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignAddressNotBetween(String value1, String value2) {
            addCriterion("sign_address not between", value1, value2, "signAddress");
            return (Criteria) this;
        }

        public Criteria andSignGpsxIsNull() {
            addCriterion("sign_gpsx is null");
            return (Criteria) this;
        }

        public Criteria andSignGpsxIsNotNull() {
            addCriterion("sign_gpsx is not null");
            return (Criteria) this;
        }

        public Criteria andSignGpsxEqualTo(Float value) {
            addCriterion("sign_gpsx =", value, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsxNotEqualTo(Float value) {
            addCriterion("sign_gpsx <>", value, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsxGreaterThan(Float value) {
            addCriterion("sign_gpsx >", value, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsxGreaterThanOrEqualTo(Float value) {
            addCriterion("sign_gpsx >=", value, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsxLessThan(Float value) {
            addCriterion("sign_gpsx <", value, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsxLessThanOrEqualTo(Float value) {
            addCriterion("sign_gpsx <=", value, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsxIn(List<Float> values) {
            addCriterion("sign_gpsx in", values, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsxNotIn(List<Float> values) {
            addCriterion("sign_gpsx not in", values, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsxBetween(Float value1, Float value2) {
            addCriterion("sign_gpsx between", value1, value2, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsxNotBetween(Float value1, Float value2) {
            addCriterion("sign_gpsx not between", value1, value2, "signGpsx");
            return (Criteria) this;
        }

        public Criteria andSignGpsyIsNull() {
            addCriterion("sign_gpsy is null");
            return (Criteria) this;
        }

        public Criteria andSignGpsyIsNotNull() {
            addCriterion("sign_gpsy is not null");
            return (Criteria) this;
        }

        public Criteria andSignGpsyEqualTo(Float value) {
            addCriterion("sign_gpsy =", value, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignGpsyNotEqualTo(Float value) {
            addCriterion("sign_gpsy <>", value, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignGpsyGreaterThan(Float value) {
            addCriterion("sign_gpsy >", value, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignGpsyGreaterThanOrEqualTo(Float value) {
            addCriterion("sign_gpsy >=", value, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignGpsyLessThan(Float value) {
            addCriterion("sign_gpsy <", value, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignGpsyLessThanOrEqualTo(Float value) {
            addCriterion("sign_gpsy <=", value, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignGpsyIn(List<Float> values) {
            addCriterion("sign_gpsy in", values, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignGpsyNotIn(List<Float> values) {
            addCriterion("sign_gpsy not in", values, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignGpsyBetween(Float value1, Float value2) {
            addCriterion("sign_gpsy between", value1, value2, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignGpsyNotBetween(Float value1, Float value2) {
            addCriterion("sign_gpsy not between", value1, value2, "signGpsy");
            return (Criteria) this;
        }

        public Criteria andSignViaIsNull() {
            addCriterion("sign_via is null");
            return (Criteria) this;
        }

        public Criteria andSignViaIsNotNull() {
            addCriterion("sign_via is not null");
            return (Criteria) this;
        }

        public Criteria andSignViaEqualTo(Integer value) {
            addCriterion("sign_via =", value, "signVia");
            return (Criteria) this;
        }

        public Criteria andSignViaNotEqualTo(Integer value) {
            addCriterion("sign_via <>", value, "signVia");
            return (Criteria) this;
        }

        public Criteria andSignViaGreaterThan(Integer value) {
            addCriterion("sign_via >", value, "signVia");
            return (Criteria) this;
        }

        public Criteria andSignViaGreaterThanOrEqualTo(Integer value) {
            addCriterion("sign_via >=", value, "signVia");
            return (Criteria) this;
        }

        public Criteria andSignViaLessThan(Integer value) {
            addCriterion("sign_via <", value, "signVia");
            return (Criteria) this;
        }

        public Criteria andSignViaLessThanOrEqualTo(Integer value) {
            addCriterion("sign_via <=", value, "signVia");
            return (Criteria) this;
        }

        public Criteria andSignViaIn(List<Integer> values) {
            addCriterion("sign_via in", values, "signVia");
            return (Criteria) this;
        }

        public Criteria andSignViaNotIn(List<Integer> values) {
            addCriterion("sign_via not in", values, "signVia");
            return (Criteria) this;
        }

        public Criteria andSignViaBetween(Integer value1, Integer value2) {
            addCriterion("sign_via between", value1, value2, "signVia");
            return (Criteria) this;
        }

        public Criteria andSignViaNotBetween(Integer value1, Integer value2) {
            addCriterion("sign_via not between", value1, value2, "signVia");
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