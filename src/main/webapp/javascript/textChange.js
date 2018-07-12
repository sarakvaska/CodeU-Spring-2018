function boldRangeButton(){
	var string = document.getElementsByName('message')[0].value;
	var start = 0;
	var end = 0;
	while (string.includes("[b]")){
		start = string.indexOf("[b]", start);
		end = string.indexOf("[/b]", start);
		if (start == -1 || end == -1){
			document.getElementById('bold').style.borderStyle = "outset";
			break;
		}
		var x = 0;
		x = event.which || event.keyCode;
		console.log(start, end);
		console.log (document.getElementsByName('message')[0].selectionStart, document.getElementsByName('message')[0].selectionEnd);
		
		var sel_start = document.getElementsByName('message')[0].selectionStart;
		var sel_end = document.getElementsByName('message')[0].selectionEnd;

		if (x == 37){
			sel_start -= 1;
			sel_end -= 1;
		}
		else if (x == 39){
			sel_start += 1;
			sel_end += 1;
		}
		if (sel_start >= start + 3  && 
			sel_start <= end &&
			sel_end >= start + 3 &&
			sel_end <= end) {
			document.getElementById('bold').style.borderStyle = "inset";
			break;
		}
		start = end + 1;
	}
}

function italicRangeButton(){
	var string = document.getElementsByName('message')[0].value;
	var start = 0;
	var end = 0;
	while (string.includes("[i]")){
		start = string.indexOf("[i]", start);
		end = string.indexOf("[/i]", start);
		if (start == -1 || end == -1){
			document.getElementById('italic').style.borderStyle = "outset";
			break;
		}
		var x = 0;
		x = event.which || event.keyCode;
		console.log(start, end);
		console.log (document.getElementsByName('message')[0].selectionStart, document.getElementsByName('message')[0].selectionEnd);
		
		var sel_start = document.getElementsByName('message')[0].selectionStart;
		var sel_end = document.getElementsByName('message')[0].selectionEnd;

		if (x == 37){
			sel_start -= 1;
			sel_end -= 1;
		}
		else if (x == 39){
			sel_start += 1;
			sel_end += 1;
		}
		if (sel_start >= start + 3  && 
			sel_start <= end &&
			sel_end >= start + 3 &&
			sel_end <= end) {
			document.getElementById('italic').style.borderStyle = "inset";
			break;
		}
		start = end + 1;
	}
}

function underlineRangeButton(){
	var string = document.getElementsByName('message')[0].value;
	var start = 0;
	var end = 0;
	while (string.includes("[u]")){
		start = string.indexOf("[u]", start);
		end = string.indexOf("[/u]", start);
		if (start == -1 || end == -1){
			document.getElementById('underline').style.borderStyle = "outset";
			break;
		}
		var x = 0;
		x = event.which || event.keyCode;
		console.log(start, end);
		console.log (document.getElementsByName('message')[0].selectionStart, document.getElementsByName('message')[0].selectionEnd);
		
		var sel_start = document.getElementsByName('message')[0].selectionStart;
		var sel_end = document.getElementsByName('message')[0].selectionEnd;

		if (x == 37){
			sel_start -= 1;
			sel_end -= 1;
		}
		else if (x == 39){
			sel_start += 1;
			sel_end += 1;
		}
		if (sel_start >= start + 3  && 
			sel_start <= end &&
			sel_end >= start + 3 &&
			sel_end <= end) {
			document.getElementById('underline').style.borderStyle = "inset";
			break;
		}
		start = end + 1;
	}
}


function setButtonsInset(){
	boldRangeButton();
	italicRangeButton();
	underlineRangeButton();
}

function smileEmoji(){
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
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " >:)  " + string.substring(end);
	}
	else {
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " >:) " + string.substring(start);
	}
}

function happyEmoji(){
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

function angryEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " >:( " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " >:( " + string.substring(start);
	}
}

function sadEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :( " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :( " + string.substring(start);
	}
}

function tearCryEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :'( " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :'( " + string.substring(start);
	}
}

function stoicEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :| " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :| " + string.substring(start);
	}
}

function mehEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :/ " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :/ " + string.substring(start);
	}
}

function coolEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " B) " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " B) " + string.substring(start);
	}
}

function smoochEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :* " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :* " + string.substring(start);
	}
}

function woahEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :O " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :O " + string.substring(start);
	}
}

function tongueEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :P " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :P " + string.substring(start);
	}
}

function xPEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " xP " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " xP " + string.substring(start);
	}
}

function winkEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " ;) " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " ;) " + string.substring(start);
	}
}

function twoArrowEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " >_< " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " >_< " + string.substring(start);
	}
}

function superMehEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " -_- " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " -_- " + string.substring(start);
	}
}

function happyGoLuckyEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " ^_^ " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " ^_^ " + string.substring(start);
	}
}

function superCryEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " T_T " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " T_T " + string.substring(start);
	}
}

function wahEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " D: " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " D: " + string.substring(start);
	}
}

function noTalkEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :x " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :x " + string.substring(start);
	}
}

function sickEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :@ " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " :@ " + string.substring(start);
	}
}

function heartEmoji(){
	var string = document.getElementsByName('message')[0].value;
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		var start = document.getElementsByName('message')[0].selectionStart;
		var end = document.getElementsByName('message')[0].selectionEnd;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " <3 " + string.substring(end);
	}
	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		document.getElementsByName('message')[0].value = 
		string.substring(0, start) + " <3 " + string.substring(start);
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