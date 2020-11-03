<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>菜单管理</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="icon" href="favicon.ico">
	<link rel="stylesheet" href="${ctx }/resources/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${ctx }/resources/css/public.css" media="all" />
	<link rel="stylesheet" href="${ctx }/resources/layui_ext/dtree/dtree.css">
	<link rel="stylesheet" href="${ctx }/resources/layui_ext/dtree/font/dtreefont.css">
	
	<style type="text/css">
		.select-test{position: absolute;max-height: 500px;height: 350px;overflow: auto;width: 100%;z-index: 123;display: none;border:1px solid silver;top: 42px;}
		.layui-show{display: block!important;}
	</style>
</head>
<body class="childrenBody">

	<!-- 搜索条件开始 -->
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
	  <legend>查询条件</legend>
	</fieldset>
	<form class="layui-form" method="post" id="searchFrm">
		<div class="layui-form-item">
		    <div class="layui-inline">
		      <label class="layui-form-label">菜单名称:</label>
		      <div class="layui-input-inline">
		        <input type="text" name="title"  autocomplete="off" class="layui-input">
		      </div>
		    </div>
		    <div class="layui-inline">
		      <button type="button" class="layui-btn layui-btn-normal  layui-icon layui-icon-search" id="doSearch">查询</button>
		      <button type="reset" class="layui-btn layui-btn-warm  layui-icon layui-icon-refresh">重置</button>
		    </div>
		 </div>
	</form>
	<!-- 搜索条件结束 -->
	
	
	<!-- 数据表格开始 -->
	<table class="layui-hide" id="menuTable" lay-filter="menuTable"></table>
	<!-- 表格工具条 -->
	<div id="menuToolBar" style="display: none;">
		    <button type="button" class="layui-btn layui-btn-sm" lay-event="add">增加</button>
	</div>
	<!-- 操作列绑定的工具条 -->
	<div id="menuBar" style="display: none;">
	  <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
	  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</div>
	<!-- 数据表格结束 -->


	<!-- 添加或修改的弹出层开始 -->
	<div style="display: none;padding: 20px" id="saveOrUpdateDiv" >
		<form class="layui-form"  lay-filter="dataFrm" id="dataFrm">
			<div class="layui-form-item">
				  <label class="layui-form-label">父级菜单：</label>
				   <div class="layui-input-block">
				      <div class="layui-unselect layui-form-select" id="pid_div">
				        <div class="layui-select-title">
				          <input type="hidden" name="pid"  id="pid">
				          <input type="text" name="pid_str" id="pid_str" placeholder="请选择" readonly="" class="layui-input layui-unselect">
				          <i class="layui-edge"></i>
				        </div>
				      </div>
				      <div class="layui-card select-test" id="menuSelectDiv">
						  <!-- 下拉树开始 -->
				        <div class="layui-card-body">
				          <div id="toolbarDiv"><ul id="menuTree" class="dtree" data-id="0" style="width: 100%;"></ul></div>
				        </div>
						  <!-- 下拉树结束 -->
				      </div>
				  </div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">菜单名称:</label>
				<div class="layui-input-block">
					<input type="hidden" name="id">
					<input type="text" name="title"  placeholder="请输入菜单名称" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">菜单地址:</label>
				<div class="layui-input-block">
					<input type="text" name="href" placeholder="请输入菜单地址" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">菜单图标:</label>
					<div class="layui-input-inline">
						<input type="text" name="icon"   placeholder="请输入菜单图标" lay-verify="required" autocomplete="off"
							class="layui-input">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">TARGET:</label>
					<div class="layui-input-inline">
						<input type="text" name="target"  placeholder="请输入TRAGET"  autocomplete="off"
							class="layui-input">
					</div>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">是否展开:</label>
					<div class="layui-input-inline">
						 <input type="radio" name="spread" value="1" title="展开">
						 <input type="radio" name="spread" value="0" title="不展开"  checked="checked">
					</div>
				</div>
				<div class="layui-inline">
					<label class="layui-form-label">是否可用:</label>
					<div class="layui-input-inline">
						 <input type="radio" name="available" value="1" checked="checked" title="可用">
						 <input type="radio" name="available" value="0" title="不可">
					</div>
				</div>
			</div>
			<div class="layui-form-item" style="text-align: center;">
			    <div class="layui-input-block">
			      <button type="button" class="layui-btn layui-btn-normal layui-btn-sm layui-icon layui-icon-release" lay-filter="doSubmit" lay-submit="">提交</button>
			      <button type="reset" class="layui-btn layui-btn-warm layui-btn-sm layui-icon layui-icon-refresh" >重置</button>
			    </div>
		  	</div>
		</form>
	
	</div>
	<!-- 添加和修改的弹出层结束 -->
	
	<script src="${ctx }/resources/layui/layui.js"></script>
	<script type="text/javascript">

		// 用于表格重载 tableIns.reload(options)
		//【通过 var tableIns = table.render() 得到，表示仅限方法级渲染】
		//【参数 options 即为各项基础参数】
	    var tableIns;

	    // 表示使用到layui第三方组件
	    layui.extend({
			dtree:'${ctx}/resources/layui_ext/dist/dtree'
		}).use([ 'jquery', 'layer', 'form', 'table','dtree'  ], function() {
			var $ = layui.jquery;
			var layer = layui.layer;
			var form = layui.form;
			var table = layui.table;
			var dtree=layui.dtree;
			// 渲染数据表格
			tableIns = table.render({
				elem: '#menuTable'   // 渲染的目标对象
				// url：接口地址。默认会自动传递两个参数：?page=1&limit=30（该参数可通过 request 自定义）
				// page 代表当前页码、limit 代表每页数据量
			    ,url:'${ctx}/menu/loadAllMenu.action' // 数据接口【
			    ,title: '菜单数据表'// 数据导出来的标题【文件导出时会用到】
			    ,toolbar:"#menuToolBar"   // 表格的工具条
			    ,height:'full-150'
			    ,cellMinWidth:100 // 设置列的最小默认宽度
			    ,page: true  // 是否启用分页
				,text:{
			    	none: '暂无相关数据' // 默认  无数据【2.2.5新增】
				}
			    ,cols: [[   // 列表数据【设置表头】
			       {type: 'checkbox', fixed: 'left'} // 设置列类型【复选框，同时fixed表示将复选框定义在左侧，不随滚动条的变化而变化】
			      ,{field:'id', title:'ID',align:'center',width:'80'} // 设置字段名
			      ,{field:'pid', title:'父节点ID',align:'center',width:'100'}
			      ,{field:'title', title:'菜单名称',align:'center',width:'120'}
			      ,{field:'href', title:'菜单地址',align:'center',width:'220'}
			      ,{field:'spread', title:'是否展开',align:'center',width:'100',templet:function(d){
			    	  return d.spread=='1'?'<font color=blue>展开</font>':'<font color=red>不展开</font>';
			      }}
			      ,{field:'target', title:'TARGET',align:'center',width:'100'}
			      ,{field:'icon', title:'菜单图标',align:'center',width:'100',templet:function(d){
			    	  return "<div class='layui-icon'>"+d.icon+"</div>";
			      }}
			      ,{field:'available', title:'是否可用',align:'center',width:'100',templet:function(d){
			    	  return d.available=='1'?'<font color=blue>可用</font>':'<font color=red>不可用</font>';
			      }}
			      ,{fixed: 'right', title:'操作', toolbar: '#menuBar', width:180,align:'center'}
			      // 【操作列固定在右侧，toolbar：绑定工具条模板。可在每行对应的列中出现一些自定义的操作性按钮】
			    ]]
			})

			// 模糊查询【搜索功能】
			$("#doSearch").click(function(){
				// 获取需要传递的参数
				var params=$("#searchFrm").serialize();
				// 表格重载
				tableIns.reload({
					url:"${ctx}/menu/loadAllMenu.action?"+params
				})
			});
			
			// 监听头部工具栏事件【添加行】
			table.on("toolbar(menuTable)",function(obj){
				 switch(obj.event){
				    case 'add':
				      openAddMenu();
				    break;
				  };
			})

			// 监听行工具事件【删除/修改行】
		   	table.on('tool(menuTable)', function(obj){
			   var data = obj.data; // 获得当前行数据
			   var layEvent = obj.event; // 获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
			   if(layEvent === 'del'){ // 删除
				  // 先判断当前菜单有没有子节点
				  $.post("${ctx}/menu/checkMenuHasChildren.action?id="+data.id,function(obj){
					  if(obj.code>=0){
						  layer.msg("当前菜单有子节点同，请先删子节点");
					  }else{
						  layer.confirm('真的删除【'+data.title+'】这个菜单吗', function(index){
						       // 向服务端发送删除指令
						       $.post("${ctx}/menu/deleteMenu.action",{id:data.id},function(res){
						    	   layer.msg(res.msg);
						    	    // 表格重载
									tableIns.reload();
									// 刷新左边的树
									window.parent.left.menuTree.reload();
									// 刷新添加和修改的下拉树
									menuTree.reload();
						       })
						     }); 
					  }
				  })
			   } else if(layEvent === 'edit'){ // 编辑
			     openUpdateMenu(data);
			   }
			 });


			// 声明一个url【作用：在添加和修改综合页面中，当提交之后进行保存时用来区分是添加提交请求还是修改提交请求】
			var url;
			// 声明一个mainIndex【作用：标记弹出层（是添加弹出层还是修改弹出层),在保存操作中最后需要关闭弹出层，使用全局变量来作为一个标记】
			var mainIndex;

			// 添加行
			function openAddMenu(){
				mainIndex=layer.open({
					type:1, // 基本层类型【可传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）】
					title:'添加菜单', // 标题
					content:$("#saveOrUpdateDiv"), // 内容【可传入的值：String/DOM/Array，默认：''】
					area:['800px','450px'], // 宽高
					// 层弹出后的成功回调方法
					success:function(index){ // 【success会携带两个参数，分别是当前层DOM当前层索引。】
						// 清空表单数据
						$("#dataFrm")[0].reset();
						// 移除打开的样式
						$("#menuSelectDiv").removeClass("layui-show");
						// 执行添加操作
						url="${ctx}/menu/addMenu.action";
					}
				});
			}

			// 编辑行
			function openUpdateMenu(data){
				mainIndex=layer.open({
					type:1,
					title:'修改菜单',
					content:$("#saveOrUpdateDiv"),
					area:['800px','450px'],
					success:function(index){
						// 把数据放入form种【当前data为json格式】
						form.val("dataFrm",data);
						// 发送请求给后端
						url="${ctx}/menu/updateMenu.action";
						// 反选下拉树
						var pid = data.pid;
					    var params = dtree.dataInit("menuTree", pid);
					    // 移除打开的样式
					    $("#menuSelectDiv").removeClass("layui-show");
					    // 给下拉框的text框赋值
					    $("#pid_str").val(params.context);
					}
				});
			}

			// 保存【监听表单提交按钮事件】
			form.on("submit(doSubmit)",function(obj){
				// 序列化表单数据【作用是将表单中的数据作为参数拼接到url后】
				var params=$("#dataFrm").serialize();
				// 提交请求
				$.post(url,params,function(obj){
					// 提交成功后的提示信息
					layer.msg(obj.msg);
					// 关闭弹出层
					layer.close(mainIndex)
					// 刷新数据表格
					tableIns.reload();
					// 刷新左边的树
					window.parent.left.menuTree.reload();
					// 刷新添加和修改的下拉树
					menuTree.reload();
				})
			});

			// 初始化添加和修改页面的下拉树
			var menuTree = dtree.render({
				  elem: "#menuTree",
				  dataStyle: "layuiStyle",  // 使用layui风格的数据格式
			      response:{message:"msg",statusCode:0},  // 修改response中返回数据的定义
		          dataFormat: "list",  // 配置data的风格为list
		          url: "${ctx}/menu/loadMenuManagerLeftTreeJson.action?spread=1",  // 使用url加载（可与data加载同时存在）
				  icon: "2",
				  accordion:true
				});

			//
			$("#pid_div").on("click",function(){
				  $(this).toggleClass("layui-form-selected");
				  $("#menuSelectDiv").toggleClass("layui-show layui-anim layui-anim-upbit");
				});

			//
			dtree.on("node(menuTree)", function(obj){
				  $("#pid_str").val(obj.param.context);
				  $("#pid").val(obj.param.nodeId);
				  $("#pid_div").toggleClass("layui-form-selected");
				  $("#menuSelectDiv").toggleClass("layui-show layui-anim layui-anim-upbit");
				});
		});

	    //
		function reloadTable(id){
			tableIns.reload({
				url:"${ctx}/menu/loadAllMenu.action?id="+id
			})
		}
	</script>
</body>
</html>