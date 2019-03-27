var xmlHttpRequest;



function quizOutputReceive(){
    if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
	var response = JSON.parse(xmlHttpRequest.responseText);
	var id = document.getElementById('quizTable');
	var rows = id.rows.length;
	for ( var i = 0; i < rows; i++) {
	    var cell = id.rows[i].insertCell(-1);
	    var cols = id.rows[i].cells.length;
	    if (cols > 10) {
		continue;
	    }
	}
	var cell1 = id.rows[0].cells[1];
	cell1.innerHTML = "あなたの回答";
	cell1 = id.rows[0].cells[2];
	cell1.innerHTML = "解答";
	for(var num = 1;num<=10;num++){
	    var cell = id.rows[num].cells[1];
	    cell.innerHTML ="";
	    cell.innerHTML +=response[num*2-2];
	    var cell1 = id.rows[num].cells[2];
	    cell1.innerHTML ="";
	    cell1.innerHTML +=response[num*2-1];
	}
	var changeElem = document.getElementById("change");
	changeElem.removeChild(document.getElementById("fin"));
	changeElem.innerHTML = '<button class="bubbly-button" name="finish" id="finish">終了</button>';
	var finElm = document.getElementById("finish");
	finElm.addEventListener("click", function(){
	    location.href = "Main.html";
	}, false);
    }
}

function quizInputReceive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
	    var response = JSON.parse(xmlHttpRequest.responseText);
	    var id = document.getElementById('quizTable');
	    id.innerHTML = "<tr><th>問題</th><td>選択肢</td></tr>";
	    
	    //console.log("output");
	    for(var num =0;num*5<response.length;num++){
		//res = response[num*4+3];
		//id.innerHTML = "";
		id.innerHTML += '<tr><th>'+response[num*5]+'</th><td><input type="radio" name="q'+num+'"value="1">'+response[num*5+2]+'<input type="radio" name="q'+num+'"value="2">'+response[num*5+3]+'<input type="radio" name="q'+num+'"value="3">'+response[num*5+4]+'</td></tr>';
	    }
	    var finElement = document.getElementById("fin");
	    console.log(toString.call(finElement));
	    finElement.addEventListener("click", function(){
		
		var result = ["a","a","a","a","a","a","a","a","a","a"];
		for(var num = 0;num<10;num++){
		    var str = 'q';
		    str += num + '';
		    var element = document.getElementsByName(str);
		    for(var i = 0;i<element.length;i++){
			if(element[i].checked){
			    result[num] = i+'';
			    break;
			}
			result[num] = "a";
		    }
		    //console.log(str+":"+result[num-1]);
		}
		var url = "quiz_input";
		xmlHttpRequest = new XMLHttpRequest();
		xmlHttpRequest.onreadystatechange = quizOutputReceive;
		xmlHttpRequest.open("POST", url, true);
		xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xmlHttpRequest.send("q1=" + result[0] + "&q2="+result[1]+ "&q3="+result[2]+ "&q4="+result[3]+ "&q5=" +result[4]+ "&q6=" +result[5]+ "&q7="+result[6]+ "&q8="+result[7]+ "&q9="+result[8]+ "&q10="+result[9]);
	    }, false);
	}
}

window.addEventListener("load", function() {
    var url = "quiz_input?id=" + "0";


	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = quizInputReceive;
	xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.send(null);
    
    
    
}, false);






