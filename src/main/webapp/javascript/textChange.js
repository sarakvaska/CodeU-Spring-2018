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