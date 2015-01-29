$(function(){
	 // 签收
    $('.claim').button({
        icons: {
            primary: 'ui-icon-person'
        }
    });
    
    // 办理
    $('.handle').button({
        icons: {
            primary: 'ui-icon-comment'
        }
    }).click(handle);
});

var handleOpts = {
		usertask1:{
			width:300,
			height:300,
			open:function(id) {
				loadDetail.call(this, id);
			},
			btns:[{
				text: '同意',
				click:function() {
					var taskId = $(this).data('taskId');
					complete(taskId,[{
						key: 'deptLeaderPass',
						value: true,
					}]);
				}
			},{
				text: '不同意',
				click:function() {
					var taskId = $(this).data('taskId');
					complete(taskId,[{
						key: 'deptLeaderPass',
						value: false,
						types:'B'
					}]);
				}
			}]
		}
}

function handle() {
	var tkey = $(this).attr('tkey'); //usertask1
	var tname = $(this).attr('tname');
	var taskId = $(this).parents('tr').attr('tid');
	var rowId = $(this).parents('tr').attr('id');
	$('#'+tkey).data({
		taskId:taskId
	}).dialog({
		title: '流程办理[' + tname + ']',
		modal: true,
		width: handleOpts[tkey].width,
		height: handleOpts[tkey].height,
		open: function() {
			handleOpts[tkey].open.call(this, rowId, taskId);
		},
		buttons: handleOpts[tkey].btns
	});
	
}

/**
 * 加载详细信息
 * @param {Object} id
 */
function loadDetail(id, withVars, callback) {
    var dialog = this;
    $.getJSON(ctx + '/oa/bangong/detail/' + id, function(data) {
        detail = data;
        $.each(data, function(k, v) {
			// 格式化日期
			if (k == 'applyTime' || k == 'startTime' || k == 'endTime') {
				$('.view-info td[name=' + k + ']', dialog).text(new Date(v).format('yyyy-MM-dd hh:mm'));
			} else {
	            $('.view-info td[name=' + k + ']', dialog).text(v);
			}
			
        });
		if ($.isFunction(callback)) {
			callback(data);
		}
    });
}

function complete(taskId, variables) {
    var dialog = this;
    
	// 转换JSON为字符串
    var keys = "", values = "", types = "";
	if (variables) {
		$.each(variables, function() {
			if (keys != "") {
				keys += ",";
				values += ",";
				types += ",";
			}
			keys += this.key;
			values += this.value;
			types += this.type;
		});
	}
	
	$.blockUI({
        message: '<h2><img src="' + ctx + '/images/ajax/loading.gif" align="absmiddle"/>正在提交请求……</h2>'
    });
	
	// 发送任务完成请求
    $.post(ctx + '/oa/bangong/complete/' + taskId, {
        keys: keys,
        values: values,
        types: types
    }, function(resp) {
		$.unblockUI();
        if (resp == 'success') {
            alert('任务完成');
            location.reload();
        } else {
            alert('操作失败!');
        }
    });
}

