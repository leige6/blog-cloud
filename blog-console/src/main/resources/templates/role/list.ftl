<script type="text/javascript">
    var roleDataGrid;
    $(function() {
        roleDataGrid = $('#roleDataGrid').datagrid({
            url : '/role/dataGrid.shtml',
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'id',
            sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            frozenColumns : [ [ {
                width : '100',
                title : 'id',
                field : 'id',
                sortable : true
            }, {
                width : '80',
                title : '名称',
                field : 'name',
                /*sortable : true*/
            } , {
                width : '80',
                title : '排序号',
                field : 'seq',
                sortable : true
            }, {
                width : '200',
                title : '描述',
                field : 'description'
            } , {
                width : '60',
                title : '状态',
                field : 'status',
              /*  sortable : true,*/
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '正常';
                    case 1:
                        return '停用';
                    }
                }
            }, {
                field : 'action',
                title : '操作',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                        <@shiro.hasPermission name="role:toGrant">
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-ok" data-options="plain:true,iconCls:\'fi-check icon-green\'" onclick="grantRoleFun(\'{0}\');" >授权</a>', row.id);
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="role:save">
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editRoleFun(\'{0}\');" >编辑</a>', row.id);
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="role:delete">
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteRoleFun(\'{0}\');" >删除</a>', row.id);
                        </@shiro.hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.role-easyui-linkbutton-ok').linkbutton({text:'授权'});
                $('.role-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.role-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#roleToolbar'
        });
    });

    function addRoleFun() {
        parent.$.modalDialog({
            title : '角色添加',
            iconCls:'icon-add icon-green',
            width : 500,
            height : 380,
            href : '/role/add.shtml',
            buttons : [ {
                text : '确定',
                iconCls : 'icon-ok',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = roleDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#roleAddForm');
                    f.submit();
                }
            },{
                text:'取消',
                iconCls : 'icon-cancel',
                handler:function(){
                    parent.$.modalDialog.handler.dialog('destroy');
                    parent.$.modalDialog.handler = undefined;
                }
            }  ]
        });
    }

    function editRoleFun(id) {
        if (id == undefined) {
            var rows = roleDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            roleDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '角色编辑',
            iconCls:'icon-edit icon-green',
            width : 500,
            height : 380,
            href : '/role/edit.shtml?id=' + id,
            buttons : [ {
                text : '确定',
                iconCls : 'icon-ok',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = roleDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#roleEditForm');
                    f.submit();
                }
            },{
                text:'取消',
                iconCls : 'icon-cancel',
                handler:function(){
                    parent.$.modalDialog.handler.dialog('destroy');
                    parent.$.modalDialog.handler = undefined;
                }
            }  ]
        });
    }

    function deleteRoleFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = roleDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            roleDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前角色？', function(b) {
            if (b) {
                progressLoad();
                $.post('/role/delete.shtml', {
                    id : id
                }, function(result) {
                    if (result.state) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        roleDataGrid.datagrid('reload');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }

    function grantRoleFun(id) {
        if (id == undefined) {
            var rows = roleDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            roleDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        
        parent.$.modalDialog({
            title : '授权',
            width : 500,
            height : 500,
            href : '/role/toGrant.shtml?rid=' + id,
            buttons : [ {
                text : '授权',
                iconCls : 'icon-ok',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = roleDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#roleGrantForm');
                    f.submit();
                }
            },{
                text:'取消',
                iconCls : 'icon-cancel',
                handler:function(){
                    parent.$.modalDialog.handler.dialog('destroy');
                    parent.$.modalDialog.handler = undefined;
                }
            } ]
        });
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="roleDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="roleToolbar" style="display: none;">
    <@shiro.hasPermission name="role:save">
        <a onclick="addRoleFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </@shiro.hasPermission>
</div>