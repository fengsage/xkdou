//var domain = "http://www.xkdou.com";
var domain = "http://localhost:8080";

loadJsOrCss(domain+"/css/style.css","css");
loadJsOrCss("http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js","js", function(){
	jqInit();
});


function loadJsOrCss(filename, filetype, callback){ 
	if (filetype=="js"){
		var fileref=document.createElement('script'); 
		fileref.type= "text/javascript"; 
		fileref.src= filename; 
	} else if (filetype=="css"){
		var fileref=document.createElement("link"); 
		fileref.rel="stylesheet"; 
		fileref.type="text/css"; 
		fileref.href=filename; 
	}

	fileref.onload = function(){
		callback && callback();
	}

	fileref.onerror = function(){
		// "javascript file load error."
	}
	
	document.getElementsByTagName("head")[0].appendChild(fileref);
}

function jqInit(){

	(function() {
		var cache = {};
		this.tmpl = function tmpl(str, data) {
			var fn = !/\W/.test(str) ? cache[str] = cache[str]
					|| tmpl(document.getElementById(str).innerHTML) : new Function(
					"obj", "var p=[],print=function(){p.push.apply(p,arguments);};"
							+ "with(obj){p.push('"
							+ str.replace(/[\r\t\n]/g, " ").split("<%").join("\t")
									.replace(/((^|%>)[^\t]*)'/g, "$1\r").replace(
											/\t=(.*?)%>/g, "',$1,'").split("\t")
									.join("');").split("%>").join("p.push('")
									.split("\r").join("\\'")
							+ "');}return p.join('');");
			return data ? fn(data) : fn;
		};
	})();

	$(function() {
		var url = domain+"/api/random";

		var tmp = '<div class="mod_lost_child">'
					+ '<div class="hd">'
						+ '<p class="wrong">404</p>'
						+ '<p class="other_info">您访问的页面找不回来了！<span>但我们可以一起寻找失踪宝贝</span></p>'
					+ '</div>'
					+ '<div class="bd">'
						+ '<div class="child_info">'
							+ '<p class="child_pic"><a target="_blank" href="<%=infoFromUrl%>"><img src="<%=pic%>" alt="" title="<%=realname%>" width="246" height="330" style="margin-left: -7.413412563667228px; margin-top: 0px; "></a></p>'
							+ '<div class="info_person">'
								+ '<p><span>姓 名：</span><%=realname%></p><p><span>性 别：</span><%if(sex==0){%>男<%}else{%>女<%}%></p><p><span>出生日期：</span><%=birthday%></p>'
								+ '<p><span>失踪时间：</span><%=lostTime%></p>'
								+ '<p><span>失踪地点：</span><%=lostAddr%></p>'
								+ '<p><span>失踪人特征描述：</span><%=tezheng%></p>'
							+'</div>'
						+'</div>'
					+'</div>'
					+'<div class="ft">'
							+ '<p class="baby_back"><a target="_blank" href="http://bbs.baobeihuijia.com/" title="宝贝回家">宝贝回家</a></p>'
							+ '<p class="side_infos"><a target="_blank" href="<%=infoFromUrl%>" title="查看详细信息">详细</a>'
								+ '<span class="symbol"></span>' 
								+ '<a href="/">返回首页</a>' 
							+ '</p>'
					+ '</div>' 
				+ '</div>';

		$
				.ajax({
					type : "GET",
					url : url,
					dataType : "jsonp",
					jsonp : "jsoncallback",
					success : function(data) {
						if (data.length > 0) {
							json = data[0];
							$(".mod_lost_child").html(tmpl(tmp,json));
						}
					}
				});
	});
}

