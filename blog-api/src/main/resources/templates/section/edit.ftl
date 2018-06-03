<script type="text/javascript">
    $(function() {
        $('#sectionEditPid').combotree({
            url : '/section/tree.shtml?flag=false',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value :'${section.pid!}'
        });
        
        $('#sectionEditForm').form({
            url : '/section/edit.shtml',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为section.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#sectionEditForm');
                    parent.$.messager.alert('提示', eval(result.msg), 'warning');
                }
            }
        });
        
    });
</script>
<div style="padding: 3px;">
    <form id="sectionEditForm" method="post">
        <table class="grid">
            <tr>
                <td>栏目名称</td>
                <input name="id" type="hidden"  value="${section.id!}" >
                <input name="creatorId" type="hidden"  value="${section.creatorId!}" >
                <input name="creatorTime" type="hidden"  value="${(section.creatorTime)?string("yyyy-MM-dd HH:mm:ss")}" >
                <input name="updateCount" type="hidden"  value="${section.updateCount!}" >
                <input name="isDel" type="hidden"  value="${section.isDel!}" >
                <input name="value" type="hidden"  value="${section.value!}" >
                <td><input name="name" type="text" value="${section.name}" placeholder="请输入栏目名称" class="easyui-validatebox" data-options="required:true" ></td>
                <td>栏目编码</td>
                <td><input name="code" type="text" value="${section.code}" placeholder="请输入栏目编码" data-options="required:true"/></td>
            </tr>
            <tr>
                <td>排序</td>
                <td><input name="seq"  class="easyui-numberspinner" value="${section.seq}" style="widtd: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>上级栏目</td>
                <td colspan="3"><select id="sectionEditPid" name="pid" style="width: 200px; height: 29px;"></select>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#sectionEditPid').combotree('clear');" >清空</a></td>
            </tr>
        </table>
    </form>
</div>
