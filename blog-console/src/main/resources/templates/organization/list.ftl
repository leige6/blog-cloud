<script type="text/javascript">
    var organizationTreeGrid;
    $(function() {
        organizationTreeGrid = $('#organizationTreeGrid').treegrid({
            url : '/organization/treeGrid.shtml',
            idField : 'id',
            treeField : 'name',
            parentField : 'pid',
            fit : true,
            fitColumns : false,
            border : false,
            frozenColumns : [ [ {
                title : '编号',
                field : 'id',
                width : 80
            } ] ],
            columns : [ [ {
                field : 'name',
                title : '部门名称',
                width : 300
            },{
                field : 'code',
                title : '部门编码',
                width : 150
            }, {
                field : 'seq',
                title : '排序',
                width : 100
            }, {
                field : 'iconCls',
                title : '图标',
                width : 200
            },  {
                title : '创建时间',
                field : 'creatorTime',
                width : 200
            },{
                field : 'pid',
                title : '上级资源ID',
                width : 150,
                hidden : true
            }, {
                field : 'address',
                title : '地址',
                width : 500
            } , {
                field : 'action',
                title : '操作',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                        <@shiro.hasPermission name="org:edit">
                            str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editOrganizationFun(\'{0}\');" >编辑</a>', row.id);
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="org:delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="organization-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteOrganizationFun(\'{0}\');" >删除</a>', row.id);
                        </@shiro.hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.organization-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.organization-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#orgToolbar'
        });
    });
    
    function editOrganizationFun(id) {
        if (id != undefined) {
            organizationTreeGrid.treegrid('select', id);
        }
        var node = organizationTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.modalDialog({
                title : '部门编辑',
                iconCls: 'icon-edit icon-green',
                width : 500,
                height : 300,
                href : '/organization/toEdit.shtml?id=' + node.id,
                buttons : [ {
                    text : '确定',
                    iconCls: 'icon-ok',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = organizationTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#organizationEditForm');
                        f.submit();
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        parent.$.modalDialog.handler.dialog('destroy');
                        parent.$.modalDialog.handler = undefined;
                    }
                } ]
            });
        }
    }
    
    function deleteOrganizationFun(id) {
        if (id != undefined) {
            organizationTreeGrid.treegrid('select', id);
        }
        var node = organizationTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function(b) {
                if (b) {
                    progressLoad();
                    $.post('/organization/delete.shtml', {
                        id : node.id
                    }, function(result) {
                        if (result.state) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            organizationTreeGrid.treegrid('reload');
                        }else{
                            parent.$.messager.alert('提示', result.msg, 'info');
                        }
                        progressClose();
                    }, 'JSON');
                }
            });
        }
    }
    
    function addOrganizationFun() {
        parent.$.modalDialog({
            title : '部门添加',
            iconCls: 'icon-add icon-green',
            width : 500,
            height : 300,
            href : '/organization/toAdd.shtml',
            buttons : [ {
                text : '确定',
                iconCls: 'icon-ok',
                handler : function() {
                    parent.$.modalDialog.openner_treeGrid = organizationTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#organizationAddForm');
                    f.submit();
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    parent.$.modalDialog.handler.dialog('destroy');
                    parent.$.modalDialog.handler = undefined;
                }
            } ]
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"  style="overflow: hidden;">
        <table id="organizationTreeGrid"></table>
    </div>
    <div id="orgToolbar" style="display: none;">
        <@shiro.hasPermission name="org:add">
            <a onclick="addOrganizationFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
        </@shiro.hasPermission>
    </div>
</div>