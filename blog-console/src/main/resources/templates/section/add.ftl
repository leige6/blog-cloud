<script type="text/javascript">
    $(function() {
        $('#sectionAddPid').combotree({
            url : '/section/tree.shtml',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });
        
        $('#sectionAddForm').form({
            url : '/section/add.shtml',
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
                    var form = $('#sectionAddForm');
                    parent.$.messager.alert('提示', eval(result.msg), 'warning');
                }
            }
        });
        
    });
</script>
<div style="padding: 3px;">
    <form id="sectionAddForm" method="post">
        <table class="grid">
            <tr>
                <td>栏目名称</td>
                <td><input name="name" type="text" placeholder="请输入栏目名称" class="easyui-validatebox" data-options="required:true" ></td>
                <td>栏目编码</td>
                <td><input name="code" type="text" placeholder="请输入栏目编码" class="easyui-validatebox" data-options="required:true" ></td>
            </tr>
            <tr>
                <td>排序</td>
                <td><input name="seq" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false" value="0"></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>上级栏目</td>
                <td colspan="3"><select id="sectionAddPid" name="pid" style="width:200px;height: 29px;"></select>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#sectionAddPid').combotree('clear');" >清空</a></td>
            </tr>
        </table>
    </form>
</div>