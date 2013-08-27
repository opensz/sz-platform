/*UE.plugins['scriptitsm'] = function() {
	var me = this;
	me.commands['scriptitsm'] = {
		execCommand : function(cmd,value) {
			var doc=me.document;
			var scriptDiv=doc.getElementById("scriptForm");
			if(scriptDiv!=null){
				doc.removeChild(scriptDiv);
			}
			var script=doc.createElement("script");
			script.id="scriptForm";
			scriptDiv.innerHTML=value;
			doc.body.appendChild(scriptDiv);
			scriptDiv.style.display="none";	
		},
		queryCommandState : function() {
				return this.highlight ? -1 : 0;
		}
	};
};
*/
UE.commands['scriptitsm'] = {
	    execCommand : function(cmd,value){
	    	var me = this;
	    	var doc=me.document;
			var scriptDiv=doc.getElementById("scriptForm");
			if(typeof scriptDiv != 'undefined' && scriptDiv!=null){	
				var parentNode=scriptDiv.parentNode;
				parentNode.removeChild(scriptDiv);
				/*window.setTimeout(function(){
					var script2=me.document.createElement("div");
					script2.id="scriptForm";
					console.log(me.document);
					//var scriptDiv2=doc.getElementById("scriptForm");
					
					//console.log(script);
					//doc.body.appendChild(scriptDiv2);
					
				}, 500);*/
				me.setContent(doc.body.innerHTML+"<script id='scriptForm'>"+value+"</script>");
				//doc.body.innerHTML=doc.body.innerHTML+"<script id='scriptForm'>"+value+"</script>";
				
			}
			else{
				/*var script=doc.createElement("script");
				script.id="scriptForm";
				console.log(doc);
				scriptDiv=doc.getElementById("scriptForm");
				scriptDiv.innerHTML=value;
				doc.body.appendChild(scriptDiv);*/
				me.setContent(doc.body.innerHTML+"<script id='scriptForm'>"+value+"</script>");
				
			}
			me.scriptHtml=value;
			 //me.fireEvent('selectionchange', true, true);
			/*var script=doc.createElement("script");
			script.id="scriptForm";
			//scriptDiv.innerHTML=value;
			console.log(value);
			doc.body.appendChild(scriptDiv);*/
			//scriptDiv.style.display="none";
	    },
		queryCommandState : function() {
			return this.highlight ? -1 : 0;
		}
};
