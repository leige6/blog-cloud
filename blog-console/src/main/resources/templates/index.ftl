<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta name="author" content="磊哥，leige" />
     <title>磊哥博客后台管理系统</title>
    <link rel="shortcut icon" href="/image/favicon.ico" />
    <script type="text/javascript" src="/plugs/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
    <script type="text/javascript" src="/plugs/easyui/jquery.min.js" charset="utf-8"></script>
    <link id="easyuiTheme" rel="stylesheet" type="text/css" href="/plugs/easyui/themes/gray/easyui.css" />
    <link id="easyuiTheme" rel="stylesheet" type="text/css" href="/plugs/easyui/themes/icon.css" />
    <script type="text/javascript" src="/plugs/easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="/plugs/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/arrayToTree.js" charset="utf-8"></script>
    <script type="text/javascript" src="/js/extJs.js" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="/css/common.css?v=10" />
    <link rel="stylesheet" type="text/css" href="/plugs/foundation-icons/foundation-icons.css"/>
    <script type="text/javascript">
    var index_tabs;
    var indexTabsMenu;
    var layout_west_tree;
    $(function() {
        $('#index_layout').layout({fit : true});
        
        index_tabs = $('#index_tabs').tabs({
            fit : true,
            border : false,
            onContextMenu : function(e, title) {
                e.preventDefault();
                indexTabsMenu.menu('show', {
                    left : e.pageX,
                    top : e.pageY
                }).data('tabTitle', title);
            },
            tools : [{
                iconCls : 'fi-home',
                handler : function() {
                    index_tabs.tabs('select', 0);
                }
            }, {
                iconCls : 'fi-loop',
                handler : function() {
                    refreshTab();
                }
            }, {
                iconCls : 'fi-x',
                handler : function() {
                    var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                    var tab = index_tabs.tabs('getTab', index);
                    if (tab.panel('options').closable) {
                        index_tabs.tabs('close', index);
                    }
                }
            } ]
        });
        // 选项卡菜单
        indexTabsMenu = $('#tabsMenu').menu({
            onClick : function(item) {
                var curTabTitle = $(this).data('tabTitle');
                var type = $(item.target).attr('type');
                if (type === 'refresh') {
                    refreshTab();
                    return;
                }
                if (type === 'close') {
                    var t = index_tabs.tabs('getTab', curTabTitle);
                    if (t.panel('options').closable) {
                        index_tabs.tabs('close', curTabTitle);
                    }
                    return;
                }
                var allTabs = index_tabs.tabs('tabs');
                var closeTabsTitle = [];
                $.each(allTabs, function() {
                    var opt = $(this).panel('options');
                    if (opt.closable && opt.title != curTabTitle
                            && type === 'closeOther') {
                        closeTabsTitle.push(opt.title);
                    } else if (opt.closable && type === 'closeAll') {
                        closeTabsTitle.push(opt.title);
                    }
                });
                for ( var i = 0; i < closeTabsTitle.length; i++) {
                    index_tabs.tabs('close', closeTabsTitle[i]);
                }
            }
        });
        
        layout_west_tree = $('#layout_west_tree').tree({
            url : '/common/getMenu.shtml',
            parentField : 'pid',
            onClick : function(node) {
                var opts = {
                    title : node.text,
                    border : false,
                    closable : true,
                    fit : true,
                    iconCls : node.iconCls
                };
                var url = node.attributes;
                if (url && url.indexOf("http") == -1) {
                    url = '' + url;
                }
                if (node.openMode == 'iframe') {
                    opts.content = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
                    addTab(opts);
                } else if (url) {
                    opts.href = url;
                    addTab(opts);
                }
            }
        });
    });

    function addTab(opts) {
        var t = $('#index_tabs');
        if (t.tabs('exists', opts.title)) {
            t.tabs('select', opts.title);
        } else {
            t.tabs('add', opts);
        }
    }
    
    function refreshTab() {
        var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
        var tab = index_tabs.tabs('getTab', index);
        var options = tab.panel('options');
        if (options.content) {
            index_tabs.tabs('update', {
                tab: tab,
                options: {
                    content: options.content
                }
            });
        } else {
            tab.panel('refresh', options.href);
        }
    }
    
    function logout(){
        $.messager.confirm('提示','确定要退出?',function(r){
            if (r){
                progressLoad();
                $.post('/login/out.shtml', function(result) {
                    if(result.state){
                        progressClose();
                        parent.$.messager.alert('提示', result.msg, 'info');
                        window.location.href='';
                    }
                }, 'json');
            }
        });
    }

    function editUserPwd() {
        parent.$.modalDialog({
            title : '修改密码',
            iconCls: 'fi-wrench icon-green',
            width : 300,
            height : 250,
            href : '/sysUser/toEditPwd.shtml',
            buttons : [ {
                text : '确定',
                iconCls: 'icon-ok',
                handler : function() {
                    var f = parent.$.modalDialog.handler.find('#editUserPwdForm');
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
</head>
<body>
    <div id="loading" style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
        <img src="/image/ajax-loader.gif" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
    </div>
    <div id="index_layout">
        <div data-options="region:'north',border:false" style="height:60px;line-height:60px;overflow: hidden;background-color: rgb(238, 238, 238);">
            <div>
                <span style="float: right; padding-right: 20px; margin-top: 15px; color: #333">
                    <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" icon="fi-torso">
	                    <b><@shiro.principal></@shiro.principal></b>&nbsp;&nbsp;
                    </a>
                    <@shiro.hasPermission name="sysUser:editPwd">
                        <a href="javascript:void(0)" onclick="editUserPwd()" class="easyui-linkbutton" plain="true" icon="fi-wrench" >修改密码</a>
                    </@shiro.hasPermission>&nbsp;&nbsp;
                    <a href="javascript:void(0)" onclick="logout()" class="easyui-linkbutton" plain="true" icon="fi-x">安全退出</a>
                </span>
                <span class="header"></span>
                <span style="font-size:20px;line-height:60px;font-weight:300;float:left;font-family: 宋体">管理系统</span>
            </div>
        </div>
        <div data-options="region:'west',split:true" title="菜单" style="width: 160px; overflow: hidden;overflow-y:auto; padding:0px">
            <div class="well well-small" style="padding: 5px 5px 5px 5px;">
                <ul id="layout_west_tree"></ul>
            </div>
        </div>
        <div data-options="region:'center'" style="overflow: hidden;">
            <div id="index_tabs" style="overflow: hidden;">
                <div title="首页" data-options="iconCls:'fi-home',border:false" style="overflow: hidden;">
                    正在建设中。。。。。。
                   <#-- <iframe align="center" width="100%" height="100%" frameborder="no" border="0" src="https://www.baidu.com"></iframe>-->
                </div>
            </div>
        </div>
        <div data-options="region:'south',border:false" style="height: 30px;line-height:30px; overflow: hidden;text-align: center;background-color: #eee" >Copyright © 2018 power by <a href="http://leige.lolabc.cn/" target="_blank">磊哥技术</a></div>
    </div>
    <div id="tabsMenu">
        <div data-options="iconCls:'fi-loop'" type="refresh" style="font-size: 12px;">刷新</div>
        <div class="menu-sep"></div>
        <div data-options="iconCls:'fi-x'" type="close" style="font-size: 12px;">关闭</div>
        <div data-options="iconCls:''" type="closeOther">关闭其他</div>
        <div data-options="iconCls:''" type="closeAll">关闭所有</div>
    </div>
    
    <!--[if lte IE 7]>
    <div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 8</a> 或以下浏览器：
    <a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/" target="_blank">Opera</a></p></div>
    <![endif]-->
    <style>
        /*ie6提示*/
        #ie6-warning{width:100%;position:absolute;top:0;left:0;background:#fae692;padding:5px 0;font-size:12px}
        #ie6-warning p{width:960px;margin:0 auto;}
    </style>
</body>
</html>