<script type="text/javascript">
    var resourceTreeGrid;
    $(function () {
        resourceTreeGrid = $('#resourceTreeGrid').treegrid({
            url: '/resource/treeGrid.shtml',
            idField: 'id',
            treeField: 'name',
            parentField: 'pid',
            fit: true,
            fitColumns: false,
            border: false,
            frozenColumns: [[{
                title: '编号',
                field: 'id',
                width: 80
            }]],
            columns: [[{
                field: 'name',
                title: '资源名称',
                width: 350
            }, {
                field: 'url',
                title: '资源路径',
                width: 350
            }, {
                field: 'openMode',
                title: '打开方式',
                width: 160
            }, {
                field: 'isOpend',
                title: '菜单状态',
                width: 100,
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '打开';
                    } else {
                        return '关闭';
                    }
                }
            }, {
                field: 'seq',
                title: '排序',
                width: 80
            }, {
                field: 'iconCls',
                title: '图标',
                width: 200
            }, {
                field: 'type',
                title: '资源类型',
                width: 100,
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0:
                            return '菜单';
                        case 1:
                            return '按钮';
                    }
                }
            }, {
                field: 'pid',
                title: '上级资源ID',
                width: 200,
                hidden: true
            }, /*{
                field : 'status',
                title : '状态',
                width : 40,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '正常';
                    case 1:
                        return '停用';
                    }
                }
            }, */{
                field: 'action',
                title: '操作',
                width: 300,
                formatter: function (value, row, index) {
                    var str = '';
                <@shiro.hasPermission name="resource:save">
                    str += $.formatString('<a href="javascript:void(0)" class="resource-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editResourceFun(\'{0}\');" >编辑</a>', row.id);
                </@shiro.hasPermission>
                <@shiro.hasPermission name="resource:delete">
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="resource-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteResourceFun(\'{0}\');" >删除</a>', row.id);
                </@shiro.hasPermission>
                    return str;
                }
            }]],
            onLoadSuccess: function (data) {
                $('.resource-easyui-linkbutton-edit').linkbutton({text: '编辑'});
                $('.resource-easyui-linkbutton-del').linkbutton({text: '删除'});
            },
            toolbar: '#resourceToolbar'
        });
    });

    function editResourceFun(id) {
        if (id != undefined) {
            resourceTreeGrid.treegrid('select', id);
        }
        var node = resourceTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.modalDialog({
                title: '资源编辑',
                iconCls: 'icon-edit icon-green',
                width: 500,
                height: 450,
                href: '/resource/edit.shtml?id=' + node.id,
                buttons: [{
                    text: '确定',
                    iconCls: 'icon-ok',
                    handler: function () {
                        parent.$.modalDialog.openner_treeGrid = resourceTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#resourceEditForm');
                        f.submit();
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        parent.$.modalDialog.handler.dialog('destroy');
                        parent.$.modalDialog.handler = undefined;
                    }
                }]
            });
        }
    }

    function deleteResourceFun(id) {
        if (id != undefined) {
            resourceTreeGrid.treegrid('select', id);
        }
        var node = resourceTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function (b) {
                if (b) {
                    progressLoad();
                    $.post('/resource/delete.shtml', {
                        id: node.id
                    }, function (result) {
                        progressClose();
                        if (result.state) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            resourceTreeGrid.treegrid('reload');
                            parent.indexMenuZTree.reAsyncChildNodes(null, "refresh");
                        } else {
                            parent.$.messager.alert('提示', result.msg, 'info');
                        }
                    }, 'JSON');
                }
            });
        }
    }

    function addResourceFun() {
        parent.$.modalDialog({
            title: '资源添加',
            iconCls: 'icon-add icon-green',
            width: 500,
            height: 450,
            href: '/resource/add.shtml',
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    parent.$.modalDialog.openner_treeGrid = resourceTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#resourceAddForm');
                    f.submit();
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    parent.$.modalDialog.handler.dialog('destroy');
                    parent.$.modalDialog.handler = undefined;
                }
            }
            ]
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" style="overflow: hidden;">
        <table id="resourceTreeGrid"></table>
    </div>
</div>
<div id="resourceToolbar" style="display: none;">
<@shiro.hasPermission name="resource:save">
    <a onclick="addResourceFun();" href="javascript:void(0);" class="easyui-linkbutton"
       data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
</@shiro.hasPermission>
</div>