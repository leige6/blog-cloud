<script type="text/javascript">
    $(function() {
        $('#organizationEditPid').combotree({
            url : '/organization/tree.shtml?flag=false',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value :'${organization.pid!}'
        });
        
        $('#organizationEditForm').form({
            url : '/organization/edit.shtml',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为organization.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#organizationEditForm');
                    parent.$.messager.alert('提示', eval(result.msg), 'warning');
                }
            }
        });
        
    });
</script>
<div style="padding: 3px;">
    <form id="organizationEditForm" method="post">
        <table class="grid">
            <tr>
                <td>部门名称</td>
                <input name="id" type="hidden"  value="${organization.id!}" >
                <input name="creatorId" type="hidden"  value="${organization.creatorId!}" >
                <input name="creatorTime" type="hidden"  value="${(organization.creatorTime)?string("yyyy-MM-dd HH:mm:ss")}" >
                <input name="updateCount" type="hidden"  value="${organization.updateCount!}" >
                <input name="isDel" type="hidden"  value="${organization.isDel!}" >
                <input name="value" type="hidden"  value="${organization.value!}" >
                <td><input name="name" type="text" value="${organization.name}" placeholder="请输入部门名称" class="easyui-validatebox" data-options="required:true" ></td>
                <td>部门编码</td>
                <td><input name="code" type="text" value="${organization.code}" placeholder="请输入部门编码" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>排序</td>
                <td><input name="seq"  class="easyui-numberspinner" value="${organization.seq}" style="widtd: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                <td>菜单图标</td>
                <td ><input name="icon" value="${organization.icon}"/></td>
            </tr>
            <tr>
                <td>地址</td>
                <td colspan="3">
                    <input class="easyui-textbox" name="address" data-options="multiline:true" style="height:100px;width:300px" value="${organization.address}"/>
                </td>
            </tr>
            <tr>
                <td>上级部门</td>
                <td colspan="3"><select id="organizationEditPid" name="pid" style="width: 200px; height: 29px;"></select>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#organizationEditPid').combotree('clear');" >清空</a></td>
            </tr>
        </table>
    </form>
</div>
