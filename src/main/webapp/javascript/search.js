function mySearch() {
    var filterMessage, indivMessage, p, i;
    filterMessage = document.getElementById("searchInput").value.toLowerCase();
    indivMessage = document.getElementById("messageList").getElementsByTagName("li");
    for (i = 0; i < indivMessage.length; i++) {
        p = indivMessage[i].getElementsByTagName("p")[0];
        if (p.innerHTML.toLowerCase().indexOf(filterMessage) > -1) {
            indivMessage[i].style.display = "";
        } else {
            indivMessage[i].style.display = "none";
        }
    }
}
