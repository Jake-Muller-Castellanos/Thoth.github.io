var xmlHttpRequest;
var sutate = 0;

function diagnosisHisReceive() {
	if(xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
	    var response = JSON.parse(xmlHttpRequest.responseText);
	    var id = document.getElementById("diId");
	    id.innerHTML += '<ul id="sample5"></ul>';
	    id = document.getElementById("sample5");
	    id.innerHtml = "";
	    for(var num =0;num*3<response.length;num++){
		var diId="di"+ num+ '';
		id.innerHTML +='<li data-text="結果:'+response[num*3+2]+'">'+response[num*3]+'の'+response[num*3+1]+'</li><br>';
		
	    }
	    var obj = document.getElementById("sample5").getElementsByTagName("li");
	    var length = obj.length;
	    
	    for(var i = 0;i<length;i++){
		obj.item(i).onmouseover = function(){
		    var elem = document.createElement("div");
		    elem.innerHTML = this.getAttribute('data-text');
		    elem.className = "sample5-tooltips";
		    this.appendChild(elem);
		}
		obj.item(i).onmouseout = function(){
		    this.removeChild(this.childNodes.item(this.childNodes.length - 1));
		}
	    }
	}
}

window.addEventListener("load", function() {
    //var ExecuteButtonElement = document.getElementById("cofirm");
    //ExecuteButtonElement.addEventListener("click", sendWithGetMethod, false);
    var url = "diagnosisHis?id=" + "0";


	xmlHttpRequest = new XMLHttpRequest();
	xmlHttpRequest.onreadystatechange = diagnosisHisReceive;
	xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.send(null);
    
    
    
}, false);






