/*康复疗程*/
$(".atten_a").click(function(){
	$(".atten_a").removeClass("active");
	$(this).addClass("active");
})
/*患者病例*/
$(".caseList_ul a").click(function(){
	$(".caseList_ul a").removeClass("active");
	$(this).addClass("active");
})
/*病例测评*/
$(".illness_ul3 img,.illness_ul2 img").click(function(){

	var str = "<img class=\"select-img\" src=\"images/select.png\" style=\"position: absolute;z-index: 100;\">";
	$(this).before(str);

	$(this).parent("li").siblings().find(".select-img").remove();
})
/*训练感受*/
$(".feel-txt-a").click(function(){
	var class_val = $(this).attr("class");
	var feel_val = $("#feel-text").val();
	if (feel_val == undefined || feel_val == "null" || feel_val == "") {
		feel_val == "";
	}
	var a_txt =	$(this).text();
	if (class_val.indexOf("active") != -1) {//包含active
		$(this).removeClass("active");
		feel_val = feel_val.replace(a_txt,"");
		/*var a = "a,b,d,f,g,s";
		a = a.replace("b,", "");*/
		$("#feel-text").val(feel_val);
	}
	else{
		$(this).addClass("active");
		$("#feel-text").val(feel_val + a_txt);
	}
	
})
/*修改信息*/
$("#message-input").focus(function(){
	$(".delect-img").show();
})
$("#message-input").blur(function(){
	$(".delect-img").hide();
})

$(".delect-img").click(function(){
	$("#message-input").val("");
})