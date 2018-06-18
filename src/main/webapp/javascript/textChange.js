function boldFunction(){
	if (document.getElementById('bold').style.borderStyle == "inset"){
	  document.getElementsByName('message')[0].value += "[/b]";
	  document.getElementById('bold').style.borderStyle = "outset";
	}
	else {
	  document.getElementsByName('message')[0].value += "[b]";
	  document.getElementById('bold').style.borderStyle = "inset";
	}
}
function italicFunction (){
	if (document.getElementById('italic').style.borderStyle == "inset"){
	  document.getElementsByName('message')[0].value += "[/i]";
	  document.getElementById('italic').style.borderStyle = "outset";
	}
	else {
	  document.getElementsByName('message')[0].value += "[i]";
	  document.getElementById('italic').style.borderStyle = "inset";
	}
}
function underlineFunction(){
	if (document.getElementById('underline').style.borderStyle == "inset"){
	  document.getElementsByName('message')[0].value += "[/u]";
	  document.getElementById('underline').style.borderStyle = "outset";
	}
	else {
	  document.getElementsByName('message')[0].value += "[u]";
	  document.getElementById('underline').style.borderStyle = "inset";
	}
}