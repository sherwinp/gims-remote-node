'use strict';
import * as jQuery from './jquery.min.js';
window.App = (function(window) {
	
	var intf = {};

    String.prototype.format = function () {
        var attr = this;
        for (var j in arguments) {
            attr = attr.replace(new RegExp("\\{" + j + "\\}", 'g'), arguments[j]);
        }
        return attr;
    }
	
	intf.runTemplate = function(tplnode, docnode) {
		return render(tplnode, docnode);
	}
	
	function render(tplnode, docnode) {

		if (!'content' in document.createElement('template'))
			return;
		if (docnode == null || tplnode == null)
			return;
		var parentNode = document.querySelector("#main").parentNode;
		tplnode.content.append(document.querySelector("#main"));
		parentNode.append(tplnode.content.firstElementChild);
		parentNode.setAttribute("id", "main");
	}

    intf.randstring = function(len, arr) { 
            var ans = ''; 
            for (var i = len; i > 0; i--) { 
                ans +=  
                  arr[Math.floor(Math.random() * arr.length)]; 
            } 
            return ans; 
    } 

	
	var context = location.pathname.substring(0, location.pathname.indexOf("/",2)); 
	var url = "ws://" + location.host + context + "/websocketapp";
    var cookies = document.cookie ? document.cookie.split('; ') : []
	if(cookies.length){
		intf.ws = new WebSocket(url);
	
		intf.ws.addEventListener('open', function open() {
		  intf.ws.send('something');
		});
		
		intf.ws.addEventListener('message', function incoming(data) {
		  console.log("--WsSocketSource--");
		  console.log(data);
		});		
	}
	intf.AppLoad = function(){
		if(cookies.length){
		
		}else{
			fetch("app.html")
			.then(function(response){
				let contentType = response.headers.get('content-type');
				if (contentType.includes('text/html')){
					return response.text()
					.then(function(text){
						if(document.querySelector('section#main')!=null){
							document.querySelector('section#main').innerHTML=text;
						}
					})
					.then(function(){
						var loginSection = document.querySelector('div#login>form');
						if(loginSection!=null){
						document.querySelector('div#login>form').addEventListener('click', function(event) {
							event.preventDefault();
							if(event.srcElement.value=='Login'){
							  var serialized = [];
							  if(document.querySelector('input#username').value.length>2){
							     var form = document.querySelector('div#login>form');
								     form.submit();
							  }
							}
							console.log(event);
						});
						}
					});				
				}
			});
			
		}
	}
	
	intf.setupEventSource = function() {
        var output = document.getElementById("output");
        if (typeof(EventSource) !== "undefined") {
          var msg = document.getElementById("textID").value;
          var source = new EventSource("sseasync?msg=" + msg);
          source.onmessage = function(event) {
            output.innerHTML += event.data + "<br>";
          };
          source.addEventListener('close', function(event) {
            output.innerHTML += event.data + "<hr/>";
            source.close();
            }, false);
        } else {
          output.innerHTML = "Sorry, Server-Sent Events are not supported in your browser";
        }
        return false;
      }
	
	setTimeout( intf.AppLoad, 1000);

	return intf;
})(Window);

