<script type="text/javascript">
    $(function() {
        $('#roleAddForm').form({
            url : '/role/save.shtml',
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
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#roleAddForm');
                    parent.$.messager.alert('错误', eval(result.msg), 'error');
                }
            }
        });
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="roleAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>角色名称</td>
                    <td><input name="name" type="text" placeholder="请输入角色名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>角色值</td>
                    <td><input name="value" type="text" placeholder="请输入角色值" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td>排序</td>
                    <td><input name="seq" value="0" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                </tr>
                <tr>
                    <td>状态</td>
                    <td >
                        <select name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">正常</option>
                            <option value="1">停用</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>角色描述</td>
                    <td colspan="3">
                        <input class="easyui-textbox" name="description"  data-options="multiline:true" style="height:100px;width:300px"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>