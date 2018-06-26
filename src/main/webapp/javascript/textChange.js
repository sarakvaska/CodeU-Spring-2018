function smileEmoji(){
	document.getElementsByName('message')[0].focus();
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :) " + string.substring(end);
	}
	else {
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :) " + string.substring(start);
	}
}

function devilEmoji(){
	document.getElementsByName('message')[0].focus();
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " >:)  " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " >:) " + string.substring(start);
	}
}

function happyEmoji(){
	document.getElementsByName('message')[0].focus();
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :D " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :D " + string.substring(start);
	}
}

function angelEmoji(){
	document.getElementsByName('message')[0].focus();
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " 0:) " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " 0:) " + string.substring(start);
	}
}

function tearEmoji(){
	document.getElementsByName('message')[0].focus();
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :'D " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :'D " + string.substring(start);
	}
}


function boldFunction(){

	// locate if the user highlighted text
	// selectionStart of the message is the beginning of the highlighted text
	// seclectionEnd is the end of the message 
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var string = document.getElementsByName('message')[0].value;
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + "[b]" + string.substring(start, end) + 
		"[/b]" + string.substring(end);
	}

	// otherwise add to end for now
	else if (document.getElementById('bold').style.borderStyle == "inset"){
		  document.getElementsByName('message')[0].value += "[/b]";
		  document.getElementById('bold').style.borderStyle = "outset";
	}
	else {
	  document.getElementsByName('message')[0].value += "[b]";
	  document.getElementById('bold').style.borderStyle = "inset";
	}
}
function italicFunction (){
	// locate if the user highlighted text
	// selectionStart of the message is the beginning of the highlighted text
	// seclectionEnd is the end of the message 
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var string = document.getElementsByName('message')[0].value;
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + "[i]" + string.substring(start, end) + 
		"[/i]" + string.substring(end);
	}

	// otherwise add to end for now
	else if (document.getElementById('italic').style.borderStyle == "inset"){
	  document.getElementsByName('message')[0].value += "[/i]";
	  document.getElementById('italic').style.borderStyle = "outset";
	}
	else {
	  document.getElementsByName('message')[0].value += "[i]";
	  document.getElementById('italic').style.borderStyle = "inset";
	}
}
function underlineFunction(){
	// locate if the user highlighted text
	// selectionStart of the message is the beginning of the highlighted text
	// seclectionEnd is the end of the message 
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var string = document.getElementsByName('message')[0].value;
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + "[u]" + string.substring(start, end) + 
		"[/u]" + string.substring(end);
	}

	// otherwise add to end for now
	else if (document.getElementById('underline').style.borderStyle == "inset"){
	  document.getElementsByName('message')[0].value += "[/u]";
	  document.getElementById('underline').style.borderStyle = "outset";
	}
	else {
	  document.getElementsByName('message')[0].value += "[u]";
	  document.getElementById('underline').style.borderStyle = "inset";
	}
}