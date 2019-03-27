var xmlHttpRequest;

function medicalOutputReceive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
	    var response = JSON.parse(xmlHttpRequest.responseText);
	    var l1 = response[0];
	    var l2 = "";
	    for(var num = 1;num<response.length;num++){
		l2 += response[num]+",";
	    }
	    var tableElement =document.getElementById("id");
	    tableElement.rows[1].cells[0].innerText = l1;
	    tableElement.rows[2].cells[1].innerText = l2;
	}
}

window.addEventListener("load", function() {
    //var ExecuteButtonElement = document.getElementById("cofirm");
    //ExecuteButtonElement.addEventListener("click", sendWithPostMethod, false);
    var url = "medical_output?id=" + "0";
    xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.onreadystatechange = medicalOutputReceive;
    xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.send(null);
    
}, false);






