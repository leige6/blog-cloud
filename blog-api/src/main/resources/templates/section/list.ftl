<script type="text/javascript">
    var sectionTreeGrid;
    $(function() {
        sectionTreeGrid = $('#sectionTreeGrid').treegrid({
            url : '/section/treeGrid.shtml',
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
                title : '栏目名称',
                width : 300
            },{
                field : 'code',
                title : '栏目编码',
                width : 150
            }, {
                field : 'seq',
                title : '排序',
                width : 100
            },  {
                title : '创建时间',
                field : 'creatorTime',
                width : 200
            },{
                field : 'pid',
                title : '上级栏目ID',
                width : 150,
                hidden : true
            }, {
                field : 'action',
                title : '操作',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                        <@shiro.hasPermission name="section:edit">
                            str += $.formatString('<a href="javascript:void(0)" class="section-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editSectionFun(\'{0}\');" >编辑</a>', row.id);
                        </@shiro.hasPermission>
                        <@shiro.hasPermission name="section:delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="section-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteSectionFun(\'{0}\');" >删除</a>', row.id);
                        </@shiro.hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.section-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.section-easyui-linkbutton-del').linkbutton({text:'删除'});
            },
            toolbar : '#orgToolbar'
        });
    });
    
    function editSectionFun(id) {
        if (id != undefined) {
            sectionTreeGrid.treegrid('select', id);
        }
        var node = sectionTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.modalDialog({
                title : '栏目编辑',
                iconCls: 'icon-edit icon-green',
                width : 500,
                height : 300,
                href : '/section/toEdit.shtml?id=' + node.id,
                buttons : [ {
                    text : '确定',
                    iconCls: 'icon-ok',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = sectionTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#sectionEditForm');
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
    
    function deleteSectionFun(id) {
        if (id != undefined) {
            sectionTreeGrid.treegrid('select', id);
        }
        var node = sectionTreeGrid.treegrid('getSelected');
        if (node) {
            parent.$.messager.confirm('询问', '您是否要删除当前栏目？删除当前资源会连同子栏目一起删除!', function(b) {
                if (b) {
                    progressLoad();
                    $.post('/section/delete.shtml', {
                        id : node.id
                    }, function(result) {
                        if (result.state) {
                            parent.$.messager.alert('提示', result.msg, 'info');
                            sectionTreeGrid.treegrid('reload');
                        }else{
                            parent.$.messager.alert('提示', result.msg, 'info');
                        }
                        progressClose();
                    }, 'JSON');
                }
            });
        }
    }
    
    function addSectionFun() {
        parent.$.modalDialog({
            title : '栏目添加',
            iconCls: 'icon-add icon-green',
            width : 500,
            height : 300,
            href : '/section/toAdd.shtml',
            buttons : [ {
                text : '确定',
                iconCls: 'icon-ok',
                handler : function() {
                    parent.$.modalDialog.openner_treeGrid = sectionTreeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#sectionAddForm');
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
        <table id="sectionTreeGrid"></table>
    </div>
    <div id="orgToolbar" style="display: none;">
        <@shiro.hasPermission name="section:add">
            <a onclick="addSectionFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
        </@shiro.hasPermission>
    </div>
</div>