<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<script type="text/javascript">
    $(function() {
        $('#editUserPwdForm').form({
            url : '/sysUser/editPwd.shtml',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.state) {
                    parent.$.messager.alert('提示', result.msg, 'info');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="editUserPwdForm" method="post">
            <table class="grid">
                <tr>
                    <td>登录名</td>
                    <td><@security.authentication property="principal.username"></@security.authentication></td>
                </tr>
                <tr>
                    <td>原密码</td>
                    <td><input name="oldPwd" type="password" placeholder="请输入原密码" class="easyui-validatebox" data-options="required:true"></td>
                </tr>
                <tr>
                    <td>新密码</td>
                    <td><input name="pwd" type="password" placeholder="请输入新密码" class="easyui-validatebox" data-options="required:true"></td>
                </tr>
                <tr>
                    <td>重复密码</td>
                    <td><input name="rePwd" type="password" placeholder="请再次输入新密码" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#editUserPwdForm input[name=pwd]\']'"></td>
                </tr>
            </table>
        </form>
    </div>
</div>