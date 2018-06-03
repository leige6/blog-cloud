<!DOCTYPE html>
<html>
<head>
    <link id="easyuiTheme" rel="stylesheet" type="text/css" href="/plugs/easyui/themes/gray/easyui.css" />
    <link id="easyuiTheme" rel="stylesheet" type="text/css" href="/plugs/easyui/themes/icon.css" />
    <link href="/plugs/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet" >
    <script type="text/javascript" src="/plugs/easyui/jquery.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/plugs/easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/plugs/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/arrayToTree.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/extJs.js" charset="utf-8"></script>
    <script type="text/javascript">
        var basePath = "";
        window.UEDITOR_HOME_URL = "/plugs/ueditor/";
        window.UEDITOR_SERVER_URL = "/common/ueditor.shtml";
    </script>
    <style type="text/css">
        .fitem {
            margin-top: 20px;
            width: 100%;
        }
        .fitem .left{
            display: inline-block;
            width: 15%;
            text-align: right;
            padding-right: 5%;
            font-size: 14px;
        }
        .fitem .right{
            display: inline-block;
            width: 70%;
        }
        .fitem .editor-left{
            vertical-align:top;
        }
    </style>
</head>
<body>
<div class="out">
    <form id="fm" method="post">
        <input type="hidden" name="id" value="${article.id}"/>
        <div class="fitem">
            <div class="left">标题:</div>
            <div class="right">
                <input name="title" class="easyui-textbox" data-options="required:true,validType:['isBlank','length[1,100]']" value="${article.title}">
            </div>
        </div>
        <div class="fitem">
            <div class="left editor-left">文章摘要:</div>
            <div class="right">
                <input class="easyui-textbox" name="summary"  value="${article.summary}" data-options="multiline:true,validType:['isBlank','length[1,100]']" style="height:100px;width:300px"/>
            </div>
        </div>
        <div class="fitem">
            <div class="left">文章来源:</div>
            <div class="right">
                <input name="source" class="easyui-textbox" value="${article.source}" data-options="required:true,validType:['isBlank','length[1,100]']">
            </div>
        </div>
        <div class="fitem">
            <div class="left">是否允许评论:</div>
            <div class="right">
                <select name="replyFlag" class="easyui-combobox" value="${article.replyFlag}" data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
                    <option value="0" <#if (article.replyFlag=0)>selected</#if>>否</option>
                    <option value="1" <#if (article.replyFlag=1)>selected</#if>>是</option>
                </select>
            </div>
        </div>
        <div class="fitem">
            <div class="left">是否置顶:</div>
            <div class="right">
                <select name="topFlag" class="easyui-combobox" value="${article.topFlag}" data-options="width:140,height:29,editable:false,panelHeight:'auto'" >
                    <option value="0" <#if (article.topFlag=0)>selected</#if>>否</option>
                    <option value="1" <#if (article.topFlag=1)>selected</#if>>是</option>
                </select>
            </div>
        </div>
        <div class="fitem">
            <div class="left">所属栏目:</div>
            <div class="right">
                <select id="articleEditSectionId" name="sectionId" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true"></select>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#articleEditSectionId').combotree('clear');" >清空</a>
            </div>
        </div>
        <div class="fitem">
            <div class="left editor-left">内容:</div>
            <div class="right">
                <script id="editor" name="editor" type="text/plain" style="width:100%;height:500px;">${article.content}</script>
            </div>
        </div>
        <input type="hidden" name="content" id="content" />
        <input type="hidden" name="publishStatus" id="publishStatus" />
    </form>
</div>
</body>
<script type="text/javascript" charset="utf-8" src="/plugs/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/plugs/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="/plugs/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
$(function() {
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    $('#articleEditSectionId').combotree({
        url : '/section/tree.shtml?flag=false',
        parentField : 'pid',
        lines : true,
        panelHeight : 'auto',
        value :'${article.sectionId!}'
    });

    var ue = UE.getEditor('editor');

});

function optSubmit(state){
    var length = UE.getEditor('editor').getContentLength(true);
    if(length > 10000){
        parent.$.messager.alert('提示','内容字数大于10000，无法保存！', 'info');
        return;
    }
    if(length <= 0){
        parent.$.messager.alert('提示','内容不能为空或全为空格！', 'info');
        return;
    }
    $('#fm').form('submit',{
        url: '/article/edit.shtml',
        onSubmit:function() {
            $("#content").val(UE.getEditor('editor').getContent());
            $("#publishStatus").val(state);
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
                setTimeout('window.parent.closeDialog()',1000);
            } else {
                parent.$.messager.alert('错误', result.msg, 'error');
            }
        }
    });
}
</script>
