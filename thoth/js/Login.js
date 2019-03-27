var xmlHttpRequest;

function sendWithGetMethod() {
    //console.log("login!");
    //location.href = 'Main.html';
    
    var idElement = document.getElementById("name");
    var passElement = document.getElementById("pass");
	
    var url = "loginSe?name=" + idElement.value + "&pass=" + passElement.value;

	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = loginReceive;
	xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.send(null);

    //location.href = 'Main.html';
}

function loginReceive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
		location.href = response.message;
	}
}

window.addEventListener("load", function() {
    
    var ExecuteButtonElement = document.getElementById("login");
    ExecuteButtonElement.addEventListener("click", sendWithGetMethod, false);
}, false);






