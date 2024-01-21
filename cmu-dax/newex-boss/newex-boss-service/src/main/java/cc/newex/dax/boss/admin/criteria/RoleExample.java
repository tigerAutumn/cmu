package cc.newex.dax.boss.admin.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台系统角色表 查询条件类
 *
 * @author mbg.generated
 * @date 2018-09-11 15:04:23
 */
public class RoleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RoleExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(final String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setDistinct(final boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public List<Criteria> getOredCriteria() {
        return this.oredCriteria;
    }

    public void or(final Criteria criteria) {
        this.oredCriteria.add(criteria);
    }

    public Criteria or() {
        final Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        final Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        final Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            this.criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return this.criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return this.criteria;
        }

        public List<Criterion> getCriteria() {
            return this.criteria;
        }

        protected void addCriterion(final String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.criteria.add(new Criterion(condition));
        }

        protected void addCriterion(final String condition, final Object value, final String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(final String condition, final Object value1, final Object value2, final String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            this.addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            this.addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(final Integer value) {
            this.addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(final List<Integer> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(final List<Integer> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNull() {
            this.addCriterion("admin_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIsNotNull() {
            this.addCriterion("admin_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdEqualTo(final Integer value) {
            this.addCriterion("admin_user_id =", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotEqualTo(final Integer value) {
            this.addCriterion("admin_user_id <>", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThan(final Integer value) {
            this.addCriterion("admin_user_id >", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("admin_user_id >=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThan(final Integer value) {
            this.addCriterion("admin_user_id <", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("admin_user_id <=", value, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdIn(final List<Integer> values) {
            this.addCriterion("admin_user_id in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotIn(final List<Integer> values) {
            this.addCriterion("admin_user_id not in", values, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("admin_user_id between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andAdminUserIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("admin_user_id not between", value1, value2, "adminUserId");
            return (Criteria) this;
        }

        public Criteria andModulesIsNull() {
            this.addCriterion("modules is null");
            return (Criteria) this;
        }

        public Criteria andModulesIsNotNull() {
            this.addCriterion("modules is not null");
            return (Criteria) this;
        }

        public Criteria andModulesEqualTo(final String value) {
            this.addCriterion("modules =", value, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesNotEqualTo(final String value) {
            this.addCriterion("modules <>", value, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesGreaterThan(final String value) {
            this.addCriterion("modules >", value, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesGreaterThanOrEqualTo(final String value) {
            this.addCriterion("modules >=", value, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesLessThan(final String value) {
            this.addCriterion("modules <", value, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesLessThanOrEqualTo(final String value) {
            this.addCriterion("modules <=", value, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesLike(final String value) {
            this.addCriterion("modules like", value, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesNotLike(final String value) {
            this.addCriterion("modules not like", value, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesIn(final List<String> values) {
            this.addCriterion("modules in", values, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesNotIn(final List<String> values) {
            this.addCriterion("modules not in", values, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesBetween(final String value1, final String value2) {
            this.addCriterion("modules between", value1, value2, "modules");
            return (Criteria) this;
        }

        public Criteria andModulesNotBetween(final String value1, final String value2) {
            this.addCriterion("modules not between", value1, value2, "modules");
            return (Criteria) this;
        }

        public Criteria andPermissionsIsNull() {
            this.addCriterion("permissions is null");
            return (Criteria) this;
        }

        public Criteria andPermissionsIsNotNull() {
            this.addCriterion("permissions is not null");
            return (Criteria) this;
        }

        public Criteria andPermissionsEqualTo(final String value) {
            this.addCriterion("permissions =", value, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsNotEqualTo(final String value) {
            this.addCriterion("permissions <>", value, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsGreaterThan(final String value) {
            this.addCriterion("permissions >", value, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsGreaterThanOrEqualTo(final String value) {
            this.addCriterion("permissions >=", value, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsLessThan(final String value) {
            this.addCriterion("permissions <", value, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsLessThanOrEqualTo(final String value) {
            this.addCriterion("permissions <=", value, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsLike(final String value) {
            this.addCriterion("permissions like", value, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsNotLike(final String value) {
            this.addCriterion("permissions not like", value, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsIn(final List<String> values) {
            this.addCriterion("permissions in", values, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsNotIn(final List<String> values) {
            this.addCriterion("permissions not in", values, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsBetween(final String value1, final String value2) {
            this.addCriterion("permissions between", value1, value2, "permissions");
            return (Criteria) this;
        }

        public Criteria andPermissionsNotBetween(final String value1, final String value2) {
            this.addCriterion("permissions not between", value1, value2, "permissions");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            this.addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            this.addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(final String value) {
            this.addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(final String value) {
            this.addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(final String value) {
            this.addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(final String value) {
            this.addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(final String value) {
            this.addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(final String value) {
            this.addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(final String value) {
            this.addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(final List<String> values) {
            this.addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(final List<String> values) {
            this.addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(final String value1, final String value2) {
            this.addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(final String value1, final String value2) {
            this.addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            this.addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            this.addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(final String value) {
            this.addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(final String value) {
            this.addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(final String value) {
            this.addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(final String value) {
            this.addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(final String value) {
            this.addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(final String value) {
            this.addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(final String value) {
            this.addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(final List<String> values) {
            this.addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(final List<String> values) {
            this.addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(final String value1, final String value2) {
            this.addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(final String value1, final String value2) {
            this.addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andIsSystemIsNull() {
            this.addCriterion("is_system is null");
            return (Criteria) this;
        }

        public Criteria andIsSystemIsNotNull() {
            this.addCriterion("is_system is not null");
            return (Criteria) this;
        }

        public Criteria andIsSystemEqualTo(final Integer value) {
            this.addCriterion("is_system =", value, "isSystem");
            return (Criteria) this;
        }

        public Criteria andIsSystemNotEqualTo(final Integer value) {
            this.addCriterion("is_system <>", value, "isSystem");
            return (Criteria) this;
        }

        public Criteria andIsSystemGreaterThan(final Integer value) {
            this.addCriterion("is_system >", value, "isSystem");
            return (Criteria) this;
        }

        public Criteria andIsSystemGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("is_system >=", value, "isSystem");
            return (Criteria) this;
        }

        public Criteria andIsSystemLessThan(final Integer value) {
            this.addCriterion("is_system <", value, "isSystem");
            return (Criteria) this;
        }

        public Criteria andIsSystemLessThanOrEqualTo(final Integer value) {
            this.addCriterion("is_system <=", value, "isSystem");
            return (Criteria) this;
        }

        public Criteria andIsSystemIn(final List<Integer> values) {
            this.addCriterion("is_system in", values, "isSystem");
            return (Criteria) this;
        }

        public Criteria andIsSystemNotIn(final List<Integer> values) {
            this.addCriterion("is_system not in", values, "isSystem");
            return (Criteria) this;
        }

        public Criteria andIsSystemBetween(final Integer value1, final Integer value2) {
            this.addCriterion("is_system between", value1, value2, "isSystem");
            return (Criteria) this;
        }

        public Criteria andIsSystemNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("is_system not between", value1, value2, "isSystem");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            this.addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            this.addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(final Integer value) {
            this.addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(final Integer value) {
            this.addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(final Integer value) {
            this.addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(final Integer value) {
            this.addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(final Integer value) {
            this.addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(final List<Integer> values) {
            this.addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(final List<Integer> values) {
            this.addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(final Integer value1, final Integer value2) {
            this.addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andSequenceIsNull() {
            this.addCriterion("sequence is null");
            return (Criteria) this;
        }

        public Criteria andSequenceIsNotNull() {
            this.addCriterion("sequence is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceEqualTo(final Integer value) {
            this.addCriterion("sequence =", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotEqualTo(final Integer value) {
            this.addCriterion("sequence <>", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThan(final Integer value) {
            this.addCriterion("sequence >", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("sequence >=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThan(final Integer value) {
            this.addCriterion("sequence <", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceLessThanOrEqualTo(final Integer value) {
            this.addCriterion("sequence <=", value, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceIn(final List<Integer> values) {
            this.addCriterion("sequence in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotIn(final List<Integer> values) {
            this.addCriterion("sequence not in", values, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sequence between", value1, value2, "sequence");
            return (Criteria) this;
        }

        public Criteria andSequenceNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("sequence not between", value1, value2, "sequence");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameIsNull() {
            this.addCriterion("admin_username is null");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameIsNotNull() {
            this.addCriterion("admin_username is not null");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameEqualTo(final String value) {
            this.addCriterion("admin_username =", value, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameNotEqualTo(final String value) {
            this.addCriterion("admin_username <>", value, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameGreaterThan(final String value) {
            this.addCriterion("admin_username >", value, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("admin_username >=", value, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameLessThan(final String value) {
            this.addCriterion("admin_username <", value, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameLessThanOrEqualTo(final String value) {
            this.addCriterion("admin_username <=", value, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameLike(final String value) {
            this.addCriterion("admin_username like", value, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameNotLike(final String value) {
            this.addCriterion("admin_username not like", value, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameIn(final List<String> values) {
            this.addCriterion("admin_username in", values, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameNotIn(final List<String> values) {
            this.addCriterion("admin_username not in", values, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameBetween(final String value1, final String value2) {
            this.addCriterion("admin_username between", value1, value2, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andAdminUsernameNotBetween(final String value1, final String value2) {
            this.addCriterion("admin_username not between", value1, value2, "adminUsername");
            return (Criteria) this;
        }

        public Criteria andCommentIsNull() {
            this.addCriterion("comment is null");
            return (Criteria) this;
        }

        public Criteria andCommentIsNotNull() {
            this.addCriterion("comment is not null");
            return (Criteria) this;
        }

        public Criteria andCommentEqualTo(final String value) {
            this.addCriterion("comment =", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotEqualTo(final String value) {
            this.addCriterion("comment <>", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThan(final String value) {
            this.addCriterion("comment >", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(final String value) {
            this.addCriterion("comment >=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThan(final String value) {
            this.addCriterion("comment <", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLessThanOrEqualTo(final String value) {
            this.addCriterion("comment <=", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentLike(final String value) {
            this.addCriterion("comment like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotLike(final String value) {
            this.addCriterion("comment not like", value, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentIn(final List<String> values) {
            this.addCriterion("comment in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotIn(final List<String> values) {
            this.addCriterion("comment not in", values, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentBetween(final String value1, final String value2) {
            this.addCriterion("comment between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCommentNotBetween(final String value1, final String value2) {
            this.addCriterion("comment not between", value1, value2, "comment");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNull() {
            this.addCriterion("created_date is null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIsNotNull() {
            this.addCriterion("created_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedDateEqualTo(final Date value) {
            this.addCriterion("created_date =", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotEqualTo(final Date value) {
            this.addCriterion("created_date <>", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThan(final Date value) {
            this.addCriterion("created_date >", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("created_date >=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThan(final Date value) {
            this.addCriterion("created_date <", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateLessThanOrEqualTo(final Date value) {
            this.addCriterion("created_date <=", value, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateIn(final List<Date> values) {
            this.addCriterion("created_date in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotIn(final List<Date> values) {
            this.addCriterion("created_date not in", values, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateBetween(final Date value1, final Date value2) {
            this.addCriterion("created_date between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andCreatedDateNotBetween(final Date value1, final Date value2) {
            this.addCriterion("created_date not between", value1, value2, "createdDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNull() {
            this.addCriterion("updated_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIsNotNull() {
            this.addCriterion("updated_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateEqualTo(final Date value) {
            this.addCriterion("updated_date =", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotEqualTo(final Date value) {
            this.addCriterion("updated_date <>", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThan(final Date value) {
            this.addCriterion("updated_date >", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateGreaterThanOrEqualTo(final Date value) {
            this.addCriterion("updated_date >=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThan(final Date value) {
            this.addCriterion("updated_date <", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateLessThanOrEqualTo(final Date value) {
            this.addCriterion("updated_date <=", value, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateIn(final List<Date> values) {
            this.addCriterion("updated_date in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotIn(final List<Date> values) {
            this.addCriterion("updated_date not in", values, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateBetween(final Date value1, final Date value2) {
            this.addCriterion("updated_date between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andUpdatedDateNotBetween(final Date value1, final Date value2) {
            this.addCriterion("updated_date not between", value1, value2, "updatedDate");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNull() {
            this.addCriterion("broker_id is null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIsNotNull() {
            this.addCriterion("broker_id is not null");
            return (Criteria) this;
        }

        public Criteria andBrokerIdEqualTo(final Integer value) {
            this.addCriterion("broker_id =", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotEqualTo(final Integer value) {
            this.addCriterion("broker_id <>", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThan(final Integer value) {
            this.addCriterion("broker_id >", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("broker_id >=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThan(final Integer value) {
            this.addCriterion("broker_id <", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("broker_id <=", value, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdIn(final List<Integer> values) {
            this.addCriterion("broker_id in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotIn(final List<Integer> values) {
            this.addCriterion("broker_id not in", values, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("broker_id between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andBrokerIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("broker_id not between", value1, value2, "brokerId");
            return (Criteria) this;
        }

        public Criteria andFieldIsNull(final String fieldName) {
            this.addCriterion(fieldName + " is null");
            return (Criteria) this;
        }

        public Criteria andFieldIsNotNull(final String fieldName) {
            this.addCriterion(fieldName + " is not null");
            return (Criteria) this;
        }

        public Criteria andFieldEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " =", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <>", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " >", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldGreaterThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " >=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThan(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLessThanOrEqualTo(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " <=", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldLike(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotLike(final String fieldName, final Object fieldValue) {
            this.addCriterion(fieldName + " not like", fieldValue, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldIn(final String fieldName, final List<Object> fieldValues) {
            this.addCriterion(fieldName + " in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotIn(final String fieldName, final List<Object> fieldValues) {
            this.addCriterion(fieldName + " not in", fieldValues, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            this.addCriterion(fieldName + " between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }

        public Criteria andFieldNotBetween(final String fieldName, final Object fieldValue1, final Object fieldValue2) {
            this.addCriterion(fieldName + " not between", fieldValue1, fieldValue2, "fieldName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private final String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private final String typeHandler;

        public String getCondition() {
            return this.condition;
        }

        public Object getValue() {
            return this.value;
        }

        public Object getSecondValue() {
            return this.secondValue;
        }

        public boolean isNoValue() {
            return this.noValue;
        }

        public boolean isSingleValue() {
            return this.singleValue;
        }

        public boolean isBetweenValue() {
            return this.betweenValue;
        }

        public boolean isListValue() {
            return this.listValue;
        }

        public String getTypeHandler() {
            return this.typeHandler;
        }

        protected Criterion(final String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(final String condition, final Object value, final String typeHandler) {
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

        protected Criterion(final String condition, final Object value) {
            this(condition, value, null);
        }

        protected Criterion(final String condition, final Object value, final Object secondValue, final String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(final String condition, final Object value, final Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}