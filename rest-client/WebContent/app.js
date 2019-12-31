'use strict';
import * as jQuery from './jquery.min.js';
Window.App = (function(window) {
	
	var intf = {};

    var cookies = document.cookie ? document.cookie.split('; ') : []

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

	let lHost = location.origin;    

	intf.ws = new WebSocket("ws://{0}/{1}/websocketapp".format(location.host, location.pathname));
	intf.ws.addEventListener('open', function open() {
	  intf.ws.send('something');
	});
	
	intf.ws.addEventListener('message', function incoming(data) {
	  console.log("--WsSocketSource--");
	  console.log(data);
	});

	intf.AppLoad = function(){
		if(cookies.length){
			
		}else{
			fetch("app.html")
			.then(function(response){
				let contentType = response.headers.get('content-type');
				if (contentType.includes('text/html')){
					return response.text()
					.then(function(text){
						document.querySelector('section#main').innerHTML=text;
					})
					.then(function(){
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
					});				
				}
			});
			
		}
	}
	
	setTimeout( intf.AppLoad, 3000);

	return intf;
})(Window);

