var codes=new Array();
var index = 0;
var size = 284; //页数
function ajaxGet(){
	$.ajax({
		url:'http://localhost:8080/MTestWeb/getKey',
		data:{page:++index},
		type:'post',
		async:true,
		dataType:'html',
		success:function(data){
			var html  = $(data);
			var table = html.find('table');
			table.find('tbody tr').each(function(j){
				if(j == 0) return true;
				var _this = this;
				var obj ={};
				$(_this).find('td').each(function(i,e){
					switch(i){
					case 0 :	
						obj.countryName = e.innerText.trim();break;
					case 1 :
						obj.code = e.innerText.trim();break;
					case 3 :
						obj.airportName = e.innerText.trim();break;
					case 4 :
						obj.cityPinYin = e.innerText.trim();
					}
					
				});
				codes.push(obj);
				if(index < size) ajaxGet(); //写在内层 速度更快
				if(index ==  size) {
					console.log(JSON.stringify(codes));
					console.log(codes.length);
					}
				
			})	
		}
	});
}


function ajaxGetDetail(codes){
	var i = 0
	var len = codes.length;
	ajaxGet();
		function ajaxGet(){
			$.ajax({
				url:'http://localhost:8080/MTestWeb/getDetail',
				data:{code:codes[i].code},
				type:'post',
				async:true,
				dataType:'html',
				success:function(data){
					var html = $(data);
					var table = html.find('table')
					if(table.length > 0){
						var airportName = table.find('tbody tr').eq(1).find('td').eq(1).html().replace(/[a-zA-Z()]/g,"").trim();
						codes[i].airportName = airportName;
					}
					i++;
					if(i<len) ajaxGet();
					else if(i==len) console.log(codes);
					
				}
			});
		}
		
	
	
	
}

/*function filter(codes){
	for(i=0,len=codes.length;i<len;i++){
		codes[i].countryName = codes[i].countryName.trim();
		codes[i].code = codes[i].code.trim();
		codes[i].airportName = codes[i].airportName.trim();
		codes[i].cityPinYin = codes[i].cityPinYin.trim();
	}
}*/


$(function(){
	$('#btn1').click(function(){
		ajaxGet();
	});
	$('#btn2').click(function(){
		filter(airportCode);
	});
})