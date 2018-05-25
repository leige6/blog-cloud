package com.leige.blog.common.enums;

/**
 *  creat by leige
 */
public enum ResultEnum {
    SYS_REQUEST_SUCCESS(200,"请求成功"),
    SYS_INNER_ERROR(500,"系统内部错误"),
    LOGIN_IN_SUCCESS(1000,"登陆成功"),
    CAPTCHA_NOT_BLANK(1001, "验证码不能为空"),
    CAPTCHA_ERROR(1002, "验证码错误"),
    PASSWORD_NOT_BLANK(1003, "密码不能为空"),
    PASSWORD_ERROR(1004, "密码错误"),
    USERNAME_NOT_BLANK(1005, "用户名不能为空"),
    USERNAME_ERROR(1006, "用户名不存在"),
    USERNAME_IS_DISABLE(1007, "账户未启用"),
    USERNAME_IS_LOCK(1008, "账户已经被锁,请稍后再试"),
    USER_ADD_SUCCESS(1009,"用户添加成功"),
    USER_EDIT_SUCCESS(1010,"用户更新成功"),
    USER_ADD_FAIL(1011,"用户添加失败"),
    USER_EDIT_FAIL(1012,"用户更新失败"),
    USER_DELETE_SUCCESS(1013,"用户删除成功"),
    USER_DELETE_FAIL(1014,"用户删除失败"),
    LOGIN_OUT_SUCCESS(1015,"用户退出成功"),
    USER_LOCK_SUCCESS(1016,"用户锁定成功"),
    USER_LOCK_FAIL(1017,"用户锁定失败"),
    USER_UNLOCK_SUCCESS(1018,"用户解锁成功"),
    USER_UNLOCK_FAIL(1019,"用户解锁失败"),
    USER_EDIT_PWD_SUCCESS(1020,"密码修改成功"),
    USER_EDIT_PWD_FAIL(1021,"密码修改失败"),
    USER_EDIT_PWD_UN_EQUAL(1022,"原密码错误"),
    USER_UNAUTHENTICATED(2000,"账户未认证"),
    USER_UNAUTHOR(2001,"账户未授权"),
    RESOURCE_ADD_SUCCESS(3000,"资源添加成功"),
    RESOURCE_EDIT_SUCCESS(3001,"资源更新成功"),
    RESOURCE_ADD_FAIL(3002,"资源添加失败"),
    RESOURCE_EDIT_FAIL(3003,"资源更新失败"),
    RESOURCE_DELETE_SUCCESS(3004,"资源删除成功"),
    RESOURCE_DELETE_FAIL(3005,"资源删除失败"),
    ROLE_ADD_SUCCESS(4000,"角色添加成功"),
    ROLE_EDIT_SUCCESS(4001,"角色更新成功"),
    ROLE_ADD_FAIL(4002,"角色添加失败"),
    ROLE_EDIT_FAIL(4003,"角色更新失败"),
    ROLE_DELETE_SUCCESS(4004,"角色删除成功"),
    ROLE_DELETE_FAIL(4005,"角色删除失败"),
    ROLE_GRANT_SUCCESS(4006,"角色授权成功"),
    ROLE_GRANT_FAIL(4007,"角色授权失败"),
    ORG_ADD_SUCCESS(5000,"部门添加成功"),
    ORG_EDIT_SUCCESS(5001,"部门更新成功"),
    ORG_ADD_FAIL(5002,"部门添加失败"),
    ORG_EDIT_FAIL(5003,"部门更新失败"),
    ORG_DELETE_SUCCESS(5004,"部门删除成功"),
    ORG_DELETE_FAIL(5005,"部门删除失败"),
    ARTICLE_ADD_SUCCESS(6000,"文章添加成功"),
    ARTICLE_EDIT_SUCCESS(6001,"文章更新成功"),
    ARTICLE_ADD_FAIL(6002,"文章添加失败"),
    ARTICLE_EDIT_FAIL(6003,"文章更新失败"),
    ARTICLE_DELETE_SUCCESS(6004,"文章删除成功"),
    ARTICLE_DELETE_FAIL(6005,"文章删除失败"),
    ARTICLE_PUBLISH_SUCCESS(6006,"文章发布成功"),
    ARTICLE_PUBLISH_FAIL(6007,"文章发布失败"),
    ARTICLE_UNPUBLISH_SUCCESS(6008,"文章取消发布成功"),
    ARTICLE_UNPUBLISH_FAIL(6009,"文章取消发布失败"),
    SECTION_ADD_SUCCESS(6010,"栏目添加成功"),
    SECTION_EDIT_SUCCESS(6011,"栏目更新成功"),
    SECTION_ADD_FAIL(6012,"栏目添加失败"),
    SECTION_EDIT_FAIL(6013,"栏目更新失败"),
    SECTION_DELETE_SUCCESS(6014,"栏目删除成功"),
    SECTION_DELETE_FAIL(6015,"栏目删除失败");
    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
