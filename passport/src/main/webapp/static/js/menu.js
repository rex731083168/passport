
function loadMenu(path) {
	var timestamp=new Date().getTime();
	$.ajax({
        url: path + "getMenu?tm="+ timestamp, 
        type: "GET",
        async: false,
        dataType: "json",
        success: function(data){
        	var noSubTpl = '<li class="{active} ">\
        		<a href="{url}" menuid={menuid}, onclick="menuStore(this)">\
        		<i class="{icon}"></i>\
        		<span class="title">{name}</span>\
        		</a>\
        		</li>';
        	var subTpl = '<li class="{active} ">\
        		<a href="{url}" menuid={menuid} onclick="menuStore(this)">\
        		<i class="{icon}"></i>\
        		<span class="title">{name}</span>\
        		<span class="selected"></span>\
        		<span class="arrow open"></span>\
        		</a>\
        		<ul class="sub-menu">{subMenus}\
        		</ul>\
        		</li>';
        	var menuStr = "";
        	for (var i = 0; i < data.subMenus.length; i++ ) {
        		var active = "";
        		var name = data.subMenus[i].menuName;
        		var menuid = data.subMenus[i].menuId;
        		if (bInMenu(menuid) == 1)
        			active = "active";
        		var url = data.subMenus[i].menuUrl.substr(1);
        		if (data.subMenus[i].menuUrl == "javascript:void(0)" || data.subMenus[i].menuUrl == "#")
        			url = data.subMenus[i].menuUrl;
        		var icon = data.subMenus[i].icon;
        		var subMenus = data.subMenus[i].subMenus;
        		if (subMenus == null || subMenus.length==0) {
        			menuStr += noSubTpl.format({name: name, active:active, menuid:menuid, icon: icon, url: url});
        			continue;
        		}
        		var sMenu = "";
        		for (var j = 0; j < subMenus.length; j++) {
        			var sActive = "";
        			var sName = subMenus[j].menuName;
        			var sMenuid = subMenus[j].menuId;
        			if (bInMenu(sMenuid) == 1)
        				sActive = "active";
        			var sUrl = subMenus[j].menuUrl.substr(1);
        			if (subMenus[j].menuUrl == "javascript:void(0)" || subMenus[j].menuUrl == "#")
        				sUrl = subMenus[j].menuUrl;
        			var sIcon = subMenus[j].icon;
        			var mSubMenus = subMenus[j].subMenus;
        			if (mSubMenus == null || mSubMenus.length==0) {
        				sMenu += noSubTpl.format({name: sName, active: sActive, menuid:sMenuid, icon: sIcon, url: sUrl});
        				continue;
        			}
        			
        			var mMenu = "";
        			for (var k = 0; k < mSubMenus.length; k++) {
        				var mActive = "";
        				mName = mSubMenus[k].menuName;
        				mMenuid = mSubMenus[k].menuId;
        				if (bInMenu(mMenuid) == 1)
        					mActive = "active";
        				mUrl = mSubMenus[k].menuUrl.substr(1);
        				if (mSubMenus[k].menuUrl == "javascript:void(0)" || mSubMenus[k].menuUrl == "#")
        					mUrl = mSubMenus[k].menuUrl;
        				mIcon = mSubMenus[k].icon;
        				mMenu += noSubTpl.format({name: mName, active: mActive, menuid:mMenuid, icon: mIcon, url: mUrl});
        			}
        			sMenu += subTpl.format({name: sName, active: sActive, menuid:sMenuid, icon: sIcon, url: sUrl, subMenus: mMenu});
        		}
        		menuStr += subTpl.format({name: name, active:active, menuid:menuid, icon: icon, url: url, subMenus: sMenu});
        	}
        	// console.log(menuStr);
        	if ($(".page-sidebar-menu").find("li").length == 2) {
        		$(".page-sidebar-menu").append(menuStr);
        	}
        }
    });
	
}

function bInMenu(menuid) {
	var menuIs = menuIds.split(",");
	for(var i=0; i<menuIs.length; i++) {
		if (menuid == menuIs[i]) {
			return 1;
		}
	}
}

function menuStore(ele) {
	// dom object to jquery object
	var $ele = $(ele);
	var pMenuId = "";
	var curId = $ele.attr("menuid").split(",")[0];
	var menuIds = curId;
	var count = 0 ;
	$(".page-sidebar-menu li.open").each(function(){
		var id = $(this).find("a:first").attr("menuid");
		menuIds += "," + id ;
		count++;
	});
	if (count == 0) {
		// 当前菜单激活状态
		if ($ele.parent().parent().find(".active").length > 0 ) {
			var delId = ''; 
			$ele.parent().parent().find(".active").each(function(){
				// 同菜单层级中的其他菜单
				if ($(this).find("a:first").attr("menuid") != curId) {
					delId = $(this).find("a:first").attr("menuid");
				}
			});
			$(".page-sidebar-menu li.active").each(function(){
				// 不是同一菜单
				if (delId != '') {
					if ($(this).find("a:first").attr("menuid") != delId) {
						menuIds += "," + $(this).find("a:first").attr("menuid");
					}
				} else {
					menuIds += "," + $(this).find("a:first").attr("menuid");
				}
			});
		}
	}
	$.ajax({
			url: "setMenu",
			data: {menuIds: menuIds},
		});
}