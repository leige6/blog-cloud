<script type="text/javascript">
    var articleDataGrid;
    var sectionTree;
    var section_Id;

    $(function() {
        sectionTree = $('#sectionTree').tree({
            url : '/section/tree.shtml',
            parentField : 'pid',
            lines : true,
            onClick : function(node) {
                section_Id=node.id;
                articleDataGrid.datagrid('load', {
                    sectionId: node.id
                });
            }
        });

        articleDataGrid = $('#articleDataGrid').datagrid({
            url : '/article/dataGrid.shtml',
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
            columns : [ [
                {
                    width : '150',
                    title : '文章标题',
                    field : 'title',
                    sortable : true
                }, {
                    width : '200',
                    title : '文章摘要',
                    field : 'summary',
                    sortable : true
                },{
                    width : '120',
                    title : '文章来源',
                    field : 'source'
                },  {
                    width : '80',
                    title : '是否置顶',
                    field : 'topFlag',
                    sortable : true,
                    formatter : function(value, row, index) {
                        switch (value) {
                            case 0:
                                return '否';
                            case 1:
                                return '是';
                        }
                    }
                }, {
                    width : '80',
                    title : '浏览量',
                    field : 'viewNumber',
                    sortable : true
                },{
                    width : '80',
                    title : '回复量',
                    field : 'replyNumber',
                    sortable : true
                },{
                    width : '100',
                    title : '所属板块',
                    field : 'sectionId',
                    hidden : true
                },
                {
                    width : '100',
                    title : '所属标签',
                    field : 'tagId'
                },  {
                    width : '150',
                    title : '创作人',
                    field : 'creatorName'
                },  {
                    width : '150',
                    title : '发布状态',
                    field : 'publishStatus',
                    formatter : function(value, row, index) {
                        if(value == 0) {
                            return "<span style=\'color:red;\'>未发布</span>";
                        }else if(value == 1) {
                            return "<span style=\'color:green;\'>已发布</span>";
                        }
                        return "已发布";
                    }
                },{
                    width : '150',
                    title : '创建时间',
                    field : 'creatorTime',
                    sortable : true
                }, {
                    field : 'action',
                    title : '操作',
                    width : 250,
                    formatter : function(value, row, index) {
                        var isPublished=row['publishStatus'];
                        var str = '';
                    <@shiro.hasPermission name="article:edit">
                        str += $.formatString('<a href="javascript:void(0)" class="article-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'fi-pencil icon-blue\'" onclick="editArticleFun(\'{0}\');" >编辑</a>', row.id);
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="article:delete">
                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        str += $.formatString('<a href="javascript:void(0)" class="article-easyui-linkbutton-del" data-options="plain:true,iconCls:\'fi-x icon-red\'" onclick="deleteArticleFun(\'{0}\');" >删除</a>', row.id);
                    </@shiro.hasPermission>
                        if(isPublished=='1'){
                        <@shiro.hasPermission name="article:publish">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="article-easyui-linkbutton-published" data-options="plain:true,iconCls:\'fi-arrow-down icon-green\'" onclick="pubArticleFun(\'{0}\',\'{1}\');" >取消发布</a>', row.id,isPublished);
                        </@shiro.hasPermission>
                        }else{
                        <@shiro.hasPermission name="article:publish">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="article-easyui-linkbutton-unPublished" data-options="plain:true,iconCls:\'fi-arrow-up icon-red\'" onclick="pubArticleFun(\'{0}\',\'{1}\');" >发布</a>', row.id,isPublished);
                        </@shiro.hasPermission>
                        }
                        return str;
                    }
                }
            ] ],
            onLoadSuccess:function(data){
                $('.article-easyui-linkbutton-edit').linkbutton({text:'编辑'});
                $('.article-easyui-linkbutton-del').linkbutton({text:'删除'});
                $('.article-easyui-linkbutton-published').linkbutton({text:'取消发布'});
                $('.article-easyui-linkbutton-unPublished').linkbutton({text:'发布'});
            },
            toolbar : '#articleToolbar'
        });
    });


    function pubArticleFun(id,isPublished) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = userDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            articleDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        var infoStr;
        if(isPublished==1){
            infoStr="您确定要取消发布此文章？";
        }else{
            infoStr="您确定要发布此文章？";
        }
        parent.$.messager.confirm('询问', infoStr, function(b) {
            if (b) {
                progressLoad();
                $.post('/article/publish.shtml', {
                    id : id,
                    isPublished : isPublished
                }, function(result) {
                    if (result.state) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        articleDataGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }

    function addArticleFun() {
        $('#addArticleFormUI').attr("src","/article/toAdd.shtml");
        $('#dlg').dialog('open').dialog('setTitle','文章添加');
    }

    function editArticleFun(id) {
        $('#addArticleFormUI').attr("src","/article/toEdit.shtml?id="+id);
        $('#dlg').dialog('open').dialog('setTitle','文章编辑');
    }

    //关闭dialog函数
    function  closeDialog(){
        $('#dlg').dialog('close');
        $('#addArticleFormUI').attr("src","");
        articleDataGrid.datagrid('reload');
        //$(this).dialog('destroy');
    }



    function deleteArticleFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = articleDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            articleDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前文章？', function(b) {
            if (b) {
                progressLoad();
                $.post('/article/delete.shtml', {
                    id : id
                }, function(result) {
                    if (result.state) {
                        parent.$.messager.alert('提示', result.msg, 'info');
                        articleDataGrid.datagrid('reload');
                    } else {
                        parent.$.messager.alert('错误', result.msg, 'error');
                    }
                    progressClose();
                }, 'JSON');
            }
        });
    }


    function searchArticleFun() {
        if(section_Id !== null || section_Id !== undefined || section_Id !== ''){
            $('#sectionId').val(section_Id);
        }
        articleDataGrid.datagrid('load', $.serializeObject($('#searchUserForm')));
    }
    function cleanArticleFun() {
        org_Id=null;
        $('#searchUserForm input').val('');
        articleDataGrid.datagrid('load', {});
    }



    function saveArticle(state){
        /* //去掉a标签的href和onclick事件 防止重复提交
        removeHrefAndOnclick("saveA");
        removeHrefAndOnclick("tijiaoA"); */

        /* 调用共通js中是否为IE的判定方法 */
        if(window.frames["addArticleFormUI"].contentWindow==undefined){
            window.frames["addArticleFormUI"].optSubmit(state);
        }else{
            window.frames["addArticleFormUI"].contentWindow.optSubmit(state);
        }

    }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="searchUserForm">
            <table>
                <tr>
                    <input id="sectionId" name="sectionId" type="hidden"/>
                    <th>文章标题:</th>
                    <td><input name="title" placeholder="请输入文章标题"/></td>
                    <th>创作人:</th>
                    <td><input name="publishor" placeholder="请输入发布人"/></td>
                    <th>发布时间:</th>
                    <td>
                        <input name="startTime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至
                        <input  name="endTime" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-magnifying-glass',plain:true" onclick="searchArticleFun();">查询</a>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'fi-x-circle',plain:true" onclick="cleanArticleFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'文章列表'" >
        <table id="articleDataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'west',border:true,split:false,title:'所属栏目'"  style="width:150px;overflow: hidden; ">
        <ul id="sectionTree" style="width:160px;margin: 10px 10px 10px 10px"></ul>
    </div>
</div>
<div id="articleToolbar" style="display: none;">
    <@shiro.hasPermission name="article:add">
        <a onclick="addArticleFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-plus icon-green'">添加</a>
    </@shiro.hasPermission>
</div>

<div id="dlg" class="easyui-dialog" style="padding:10px 20px;width: 800px;height: 700px;overflow:hidden;"
     closed="true" buttons="#dlg-buttons" modal="true" data-options="iconCls:'icon-add icon-green',maximizable:true,resizable:false,draggable:false">
    <iframe id='addArticleFormUI' border='0' vspace='0' hspace='0'
            marginwidth='0' marginheight='0' framespacing='0' frameborder='0' scrolling='yes'
            style="height:100%;width:100%;left:10px;top:8px" src="">
    </iframe>
</div>

<div id="dlg-buttons">
    <a  href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveArticle(1)" style="width:90px">发布</a>
    <a  href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-save" onclick="saveArticle(0)" style="width:90px">保存</a>
    <a id="closeBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeDialog()" style="width:90px">关闭</a>
</div>