var xmlHttpRequest;

function sendWithGetMethod() {
    
	
    var url = "echo?id=" + "0";


	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = diseaseListReceive;
	xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.send(null);
}

function diseaseListReceive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
	    var response = JSON.parse(xmlHttpRequest.responseText);
	    var id = document.getElementById('diseListId');
	    for(var num = 0;num < response.length;num++){
		
		var d = "d" + num + ''; 
		id.innerHTML += '<div class="ribbon_box">' + '<div class="ribbon"> <h3>病名:'+response[num]+'</h3></div>' + '<p id="'+d+'"></p></div><br>';
		//id.innerHTML +='<p id="'+d+'"></p><br>';
		var a = document.createElement("a");
		a.href = "https://medicalnote.jp/diseases/" + response[num];
		var str = document.createTextNode("リンク");
		a.appendChild(str);
		document.getElementById(d).appendChild(a);
	    }
	}
}

window.addEventListener("load", function() {
    //var ExecuteButtonElement = document.getElementById("cofirm");
    //ExecuteButtonElement.addEventListener("click", sendWithGetMethod, false);
    var url = "diseaseListSe?id=" + "0";


    /*console.log("List!!");
    var response = ["かぜ","麻疹","胸膜炎","気管支炎"];
    var id = documents.getElementById('id');
    for(var data in response){
	id.innerHTML = '<div class="ribbon"> <h3>病名:'+response+'</h3></div><p><a href = "https://medicalnote.jp/diseases/'+response+'>リンク</a></p>';*/
    
	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = diseaseListReceive;
	xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.send(null);
    
    
    
}, false);






