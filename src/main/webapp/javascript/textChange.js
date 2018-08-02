function rangeButton(startTag, endTag, id){
	var string = document.getElementsByName('message')[0].value;
	var start = 0;
	var end = 0;
	while (start < string.length){
		start = string.indexOf(startTag, start);
		end = string.indexOf(endTag, start);
		if (start == -1 || end == -1){
			document.getElementById(id).style.borderStyle = "outset";
			break;
		}
		var x = 0;
		x = event.which || event.keyCode;
		
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
			document.getElementById(id).style.borderStyle = "inset";
			break;
		}
		start = start + 1;
	}
}

function setButtonsInset(){
	rangeButton("[b]", "[/b]", "bold");
	rangeButton("[i]", "[/i]", "italic");
	rangeButton("[u]", "[/u]", "underline");
	rangeButton("[s]", "[/s]", "strike");
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

function styleFunction(startTag, endTag, id){
	// locate if the user highlighted text
	// selectionStart of the message is the beginning of the highlighted text
	// seclectionEnd is the end of the message 
	if (document.getElementsByName('message')[0].selectionStart != 
		document.getElementsByName('message')[0].selectionEnd){
		if (document.getElementById(id).style.borderStyle == "inset"){
			var string = document.getElementsByName('message')[0].value;
			var start = document.getElementsByName('message')[0].selectionStart;
			var end = document.getElementsByName('message')[0].selectionEnd;

			// deals with: [b][/b] Hiiii [b][/b]
			if (string.substring(start - 3, start) == startTag && string.substring(end, end + 4) == endTag){
				document.getElementsByName('message')[0].value = 
				string.substring(0, start - 3) + string.substring(start, end) + string.substring(end + 4);
			} 

			// deals with: [b][/b] Hi [b]iii [/b]
			else if (string.substring(start - 3, start) == startTag){
				document.getElementsByName('message')[0].value = 
				string.substring(0, start - 3) + string.substring(start, end) + startTag + string.substring(end);
			}

			// deals with: [b] Hii[/b]ii [b][/b] 
			else if (string.substring(end, end + 4) == endTag){
				document.getElementsByName('message')[0].value = 
				string.substring(0, start) + endTag + string.substring(start, end) + string.substring(end + 4);
			}

			// deals with: [b] Hi[/b]i[b]ii [/b]
			else {
				document.getElementsByName('message')[0].value = 
				string.substring(0, start) + endTag + string.substring(start, end) + 
				startTag + string.substring(end);
			}
		}
		else {
			var string = document.getElementsByName('message')[0].value;
			var start = document.getElementsByName('message')[0].selectionStart;
			var end = document.getElementsByName('message')[0].selectionEnd;

			// deals with: [/b][b] Hiiiiiiii [/b][b]
			if (string.substring(start - 4, start) == endTag && string.substring(end, end + 3) == startTag){
				document.getElementsByName('message')[0].value = 
				string.substring(0, start - 4) + string.substring(start, end) + string.substring(end + 3);
			}

			// deals with: [/b][b] Hii[/b]iiiiii [b]
			else if (string.substring(start - 4, start) == endTag){
				document.getElementsByName('message')[0].value = 
				string.substring(0, start - 4) + string.substring(start, end) + endTag +  string.substring(end);
			}

			// deals with: [/b] Hiii[b]iiiii [/b][b]
			else if (string.substring(end, end + 3) == startTag){
				document.getElementsByName('message')[0].value = 
				string.substring(0, start) + startTag + string.substring(start, end) + string.substring(end + 3);
			}
			else {
				document.getElementsByName('message')[0].value = 
				string.substring(0, start) + startTag + string.substring(start, end) + 
				endTag + string.substring(end);
			}
		}
	}

	else{
		var start = document.getElementsByName('message')[0].selectionStart;
		var string = document.getElementsByName('message')[0].value;
		if (document.getElementById(id).style.borderStyle == "inset"){
			console.log (string.substring(start - 3, start));

			if (string.substring(start - 3, start) == startTag && string.substring(start, start + 4) == endTag){
				document.getElementsByName('message')[0].value = 
				string.substring(0, start - 3) + string.substring(start + 4);
			}

			// do nothing if cursor before or after a tag 
			// don't want chain of tags if user decides to press the bold button a lot of times
			else if (string.substring(start - 3, start) == startTag){
			}
			else if (string.substring(start, start + 4) == endTag){
			}

			else {
				document.getElementsByName('message')[0].value = 
				string.substring(0, start) + endTag + startTag + string.substring(start);
			}
		}
		else {
			if (string.substring(start - 4, start) == endTag && string.substring(start, start + 3) == startTag){
				document.getElementsByName('message')[0].value = 
				string.substring(0, start - 4) + string.substring(start + 3);
			}

			// do nothing if cursor before or after a tag 
			// don't want chain of tags if user decides to press the bold button a lot of times
			else if (string.substring(start - 4, start) == endTag){
			}
			else if (string.substring(start, start + 3) == startTag){
			}
			
			else {
				document.getElementsByName('message')[0].value =
				string.substring(0, start) + startTag + endTag + string.substring(start);
			}
		}
	}
}

function boldFunction(){
	styleFunction("[b]", "[/b]", "bold");
}

function italicFunction(){
	styleFunction("[i]", "[/i]", "italic");
}

function underlineFunction(){
	styleFunction("[u]", "[/u]", "underline");
}

function strikeFunction(){
	styleFunction("[s]", "[/s]", "strike");
}

function addLink() {
	var message = document.getElementsByName('message')[0].value;
	var link = document.getElementsByName('link')[0].value;
	if (link != ""){
		if (message == ""){
			message += "[url]" + link + "[/url]"; 
		}
		else {
			message += " [url]" + link + "[/url]"; 
		}
	}
	document.getElementsByName('message')[0].value = message;
}