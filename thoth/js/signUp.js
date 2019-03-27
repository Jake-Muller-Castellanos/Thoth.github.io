var xmlHttpRequest;

function sendWithGetMethod() {
    //console.log("signin!");
    //location.href = 'Main.html';
    
    var idElement = document.getElementById("name");
    var passElement = document.getElementById("pass");
	
    var url = "signupSe?name=" + idElement.value + "&pass=" + passElement.value;

    console.log(idElement.value);

	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = signupReceive;
	xmlHttpRequest.open("GET", url, true);
	xmlHttpRequest.send(null);
}

function signupReceive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
		var response = JSON.parse(xmlHttpRequest.responseText);
		location.href = response.message;
	}
}

window.addEventListener("load", function() {
    var ExecuteButtonElement = document.getElementById("signUp");
    ExecuteButtonElement.addEventListener("click", sendWithGetMethod, false);
}, false);






