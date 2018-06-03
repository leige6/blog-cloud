<script type="text/javascript">
    var userDataGrid;
    var organizationTree;
    var org_Id;

    $(function() {
        organizationTree = $('#organizationTree').tree({
            url : '/organization/tree.shtml',
            parentField : 'pid',
            lines : true,
            onClick : function(node) {
                org_Id=node.id;
                userDataGrid.datagrid('load', {
                    orgId: node.id
                });
            }
        });

        userDataGrid = $('#userDataGrid').datagrid({
            url : '/sysUser/dataGrid.shtml',
            fit : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'creatorTime',
	        sortOrder : 'asc',
            pageSize : 20,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '120',
                title : '登录名',
                field : 'userName',
                sortable : true
            }, {
                width : '120',
                title : '姓名',
                field : 'realName',
                sortable : true
            },{
                width : '80',
                title : '部门ID',
                field : 'orgId',
                hidden : true
            },{
                width : '120',
                title : '所属部门',
                field : 'orgName'
            },{
                width : '150',
                title : '创建时间',
                field : 'creatorTime',
                sortable : true
            },  {
                width : '80',
                title : '性别',
                field : 'sex',
                sortable : true,
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '男';
                    case 1:
                        return '女';
                    }
                }
            }, {
                width : '80',
                title : '年龄',
                field : 'age',
                sortable : true
            },{
                width : '100',
                title : '电话',
                field : 'moblie',
                sortable : true
            }, 
            {
                width : '200',
                title : '角色',
                field : 'roleName'
            }, {
                width : '80',
                title : '启用状态',
                field : 'isEnabled',
                sortable : true,
                formatter : function(value, row, index) {
                    if(value == 0) {
                        return "<span style=\'color:red;\'>未启用</span>";
                    }else if(value == 1) {
                        return "<span>已启用</span>";
                    }
                    return "已启用";
                }
            }, {
                width : '80',
                title : '锁定状态',
                field : 'isLocked',
                sortable : true,
                formatter : function(value, row, index) {
                    if(value == 0) {
                        return "<span>未锁定</span>";
                    }else if(value == 1) {
                        return "<span style=\'color:red;\'>已锁定</span>";
                    }
                    return "<span>已启用</span>";
                }
            }, {
                width : '80',
                title : '用户类型',
                field : 'userType',
                sortable : true,
                formatter : function(value, row, index) {
                    if(value == 0) {
                        return "超级管理员";
                    }else if(value == 1) {
                        return "普通管理员";
                    }
                    return "未知类型";
                }
            }, {
                field : 'action',
                title : '操作',
                width : 250,
                formatter : function(value, row, index) {
                    var isLocked=row['isLocked'];
                    var str = '';
                        <@shiro.hasPermission name="sysUser:edit">
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editUserFun(\'{0}\');" >编辑</a>', row.id);
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="sysUser:delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteUserFun(\'{0}\');" >删除</a>', row.id);
                        </@shiro.hasPermission>
                    if(isLocked=='1'){
                    <@shiro.hasPermission name="sysUser:lock">
                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-lock" data-options="plain:true,iconCls:\'fi-lock icon-red\'" onclick="lockUserFun(\'{0}\',\'{1}\');" >解锁</a>', row.id,isLocked);
                    </@shiro.hasPermission>
                    }else{
                    <@shiro.hasPermission name="sysUser:lock">
                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-unlock" data-options="plain:true,iconCls:\'fi-unlock icon-red\'" onclick="lockUserFun(\'{0}\',\'{1}\');" >锁定</a>', row.id,isLocked);
                    </@shiro.hasPermission>
                    }
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.user-easyui-linkbutton-lock').linkbutton({text:'解锁'});
                $('.user-easyui-linkbutton-unlock').linkbutton({text:'锁定'});
            },
            toolbar : '#userToolbar'
        });
    });
    
    function addUserFun() {
        parent.$.modalDialog({
            title : '系统用户添加',
            iconCls: 'icon-add icon-green',
            width : 500,
            height : 300,
            href : '/sysUser/toAdd.shtml',
            buttons : [ {
                text : '确定',
                iconCls: 'icon-ok',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = userDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userAddForm');
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

    function lockUserFun(id,isLocked) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = userDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            userDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        var infoStr;
        if(isLocked==1){
            infoStr="您是否要解锁当前账户？";
        }else{
            infoStr="您是否要锁定当前账户？";
        }
        parent.$.messager.confirm('询问', infoStr, function(b) {
            if (b) {
                progressLoad();
                $.post('/sysUser/lock.shtml', {
                    id : id,
                    isLocked : isLocked
                }, function(result) {
                    if (result.state) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        userDataGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }

    function deleteUserFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = userDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            userDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                progressLoad();
                $.post('/sysUser/delete.shtml', {
                    id : id
                }, function(result) {
                    if (result.state) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        userDataGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }
    
    function editUserFun(id) {
        if (id == undefined) {
            var rows = userDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            userDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '系统用户编辑',
            iconCls: 'icon-edit icon-green',
            width : 500,
            height : 300,
            href : '/sysUser/toEdit.shtml?id=' + id,
            buttons : [ {
                text : '确定',
                iconCls: 'icon-ok',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = userDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userEditForm');
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
    
    function searchUserFun() {
        if(org_Id !== null || org_Id !== undefined || org_Id !== ''){
            $('#orgId').val(org_Id);
        }
        userDataGrid.datagrid('load', $.serializeObject($('#searchUserForm')));
    }
    function cleanUserFun() {
        org_Id=null;
        $('#searchUserForm input').val('');
        userDataGrid.datagrid('load', {});
    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchUserForm">
            <table>
                <tr>
                    <input id="orgId" name="orgId" type="hidden"/>
                    <th>用户名:</th>
                    <td><input name="userName" placeholder="请输入用户名"/></td>
                    <th>姓名:</th>
                    <td><input name="name" placeholder="请输入用户姓名"/></td>
                    <th>创建时间:</th>
                    <td>
                        <input name="startTime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至
                        <input  name="endTime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchUserFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanUserFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="userDataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'west',border:true,split:false,title:'组织机构'"  style="width:150px;overflow: hidden; ">
        <ul id="organizationTree" style="width:160px;margin: 10px 10px 10px 10px"></ul>
    </div>
</div>
<div id="userToolbar" style="display: none;">
    <@shiro.hasPermission name="sysUser:add">
        <a onclick="addUserFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </@shiro.hasPermission>
</div>