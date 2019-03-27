var xmlHttpRequest;



function sendWithPostMethod(){
    var qElements = [];
    var bool = true;
    var result = ["a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a"];
    for(var num = 1; num<=20;num++){
	var str = 'q';
	str += num +'';
	var element = document.getElementsByName(str);
	for(var i = 0;i<element.length;i++){
	    if(element[i].checked){
		result[num-1] = i+'';
		
		var row = document.getElementById("tableId").rows.item(num);
		var cell = row.cells.item(0);
		cell.style.color = "black";
		break;
	    }
	    result[num-1] = 4+'';
	    var row = document.getElementById("tableId").rows.item(num);
	    var cell = row.cells.item(0);
	    cell.style.color = "red";
	    
	}
	qElements.push(document.getElementsByName(str));
    }
    for(var i=0;i<20;i++){
	if(result[i]==4+'') bool = false;
    }
    if(bool){
	var url = "medical_input";
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = medical_input_receive;
	xmlHttpRequest.open("POST", url, true);
	xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlHttpRequest.send("q1=" + result[0] + "&q2="+result[1]+ "&q3="+result[2]+ "&q4="+result[3]+ "&q5=" +result[4]+ "&q6=" +result[5]+ "&q7="+result[6]+ "&q8="+result[7]+ "&q9="+result[8]+ "&q10="+result[9]+ "&q11="+result[10]+ "&q12="+result[11]+ "&q13="+result[12]+ "&q14="+result[13]+ "&q15="+result[14]+ "&q16="+result[15]+ "&q17="+result[16]+ "&q18="+result[17]+ "&q19="+result[18]+ "&q20="+result[19]);
    }
}

function medical_input_receive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
		location.href = response.message;
	}
}

window.addEventListener("load", function() {
    var ExecuteButtonElement = document.getElementById("confirm");
    ExecuteButtonElement.addEventListener("click", sendWithPostMethod, false);
    
    
}, false);






