<script type="text/javascript">
    $(function() {
        $('#resourceAddPid').combotree({
            url : '/common/allTree.shtml',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });

        $('#resourceAddForm').form({
            url : '/resource/save.shtml',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
                    parent.layout_west_tree.tree('reload');
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#resourceAddForm');
                    parent.$.messager.alert('提示', result.msg, 'info');
                }
            }
        });
        
    });
</script>
<div style="padding: 3px;">
    <form id="resourceAddForm" method="post">
        <input type="hidden" name="token" value="${token!}">
        <table class="grid">
            <tr>
                <td>资源名称</td>
                <td><input name="name" type="text" placeholder="请输入资源名称" class="easyui-validatebox span2" data-options="required:true" ></td>
                <td>资源类型</td>
                <td>
                    <select name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="0">菜单</option>
                        <option value="1">按钮</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>资源路径</td>
                <td><input name="url" type="text" placeholder="请输入资源路径" class="easyui-validatebox span2" data-options="width:140,height:29" ></td>
                <td>资源值</td>
                <td>
                    <input name="value" type="text" placeholder="请输入资源值" class="easyui-validatebox span2" data-options="required:true" >
                </td>
            </tr>
            <tr>
                <td>菜单图标</td>
                <td ><input name="icon" /></td>
                <td>排序</td>
                <td><input name="seq" value="0"  class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
            </tr>
            <tr>
                <td>打开方式</td>
                <td>
                    <select name="openMode" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option>无(用于上层菜单)</option>
                        <option value="ajax" selected="selected">ajax</option>
                        <option value="iframe">iframe</option>
                    </select>
                </td>
                <td>菜单状态</td>
                <td>
                    <select name="isOpend" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="0">关闭</option>
                        <option value="1">打开</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>资源描述</td>
                <td colspan="3">
                    <input class="easyui-textbox" name="description"  data-options="multiline:true" style="height:100px;width:300px"/>
                </td>
            </tr>
            <tr>
                <td>上级资源</td>
                <td colspan="3">
                    <select id="resourceAddPid" name="pid" style="width: 200px; height: 29px;"></select>
                    <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>