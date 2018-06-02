<script type="text/javascript">
    $(function() {
        $('#resourceEditPid').combotree({
            url : '/common/allTree.shtml',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${resource.pid!}'
        });

        $('#resourceEditForm').form({
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
                    var form = $('#resourceEditForm');
                    parent.$.messager.alert('提示', result.msg, 'info');
                }
            }
        });
        $("#resourceEditType").val("${resource.type!}");
        $("#resourceEditOpenMode").val("${resource.openMode!}");
        $("#resourceEditOpened").val("${resource.isOpend!}");
    });
</script>
<div style="padding: 3px;">
    <form id="resourceEditForm" method="post">
        <input type="hidden" name="token" value="${token!}">
        <table  class="grid">
            <tr>
                <td>资源名称</td>
                <td>
                    <input name="id" type="hidden"  value="${resource.id!}" >
                    <input name="creatorId" type="hidden"  value="${resource.creatorId!}" >
                    <input name="creatorTime" type="hidden"  value="${(resource.creatorTime)?string("yyyy-MM-dd HH:mm:ss")}" >
                    <input name="updateCount" type="hidden"  value="${resource.updateCount!}" >
                    <input name="isDel" type="hidden"  value="${resource.isDel!}" >
                    <input name="name" type="text" placeholder="请输入资源名称" value="${resource.name!}" class="easyui-validatebox span2" data-options="required:true" >
                </td>
                <td>资源类型</td>
                <td>
                    <select id="resourceEditType" name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="0">菜单</option>
                        <option value="1">按钮</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>资源路径</td>
                <td><input name="url" type="text" value="${resource.url!}" placeholder="请输入资源路径" class="easyui-validatebox span2" ></td>
                <td>资源值</td>
                <td>
                    <input name="value" type="text" placeholder="请输入资源名称" value="${resource.value!}" class="easyui-validatebox span2" data-options="required:true" >
                </td>

            </tr>
            <tr>
                <td>菜单图标</td>
                <td><input name="icon" value="${resource.icon!}"/></td>
                <td>排序</td>
                <td><input name="seq" value="${resource.seq!}" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
            </tr>
            <tr>
                <td>打开方式</td>
                <td>
                    <select id="resourceEditOpenMode" name="openMode" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option>无(用于上层菜单)</option>
                        <option value="ajax">ajax</option>
                        <option value="iframe">iframe</option>
                    </select>
                </td>
                <td>菜单状态</td>
                <td>
                    <select id="resourceEditOpened" name="isOpend" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="0">关闭</option>
                        <option value="1">打开</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>资源描述</td>
                <td colspan="3">
                    <input class="easyui-textbox" name="description" value="${resource.description!}" data-options="multiline:true" style="height:100px;width:300px"/>
                </td>
            </tr>
            <tr>
                <td>上级资源</td>
                <td colspan="3"><select id="resourceEditPid" name="pid" style="width: 200px; height: 29px;"></select>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a></td>
            </tr>
        </table>
    </form>
</div>
