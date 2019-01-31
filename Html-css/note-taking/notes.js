var parent = document.getElementById('txt');
var ul = document.getElementById('menu');
var descValue = document.getElementById('txtarea');
var emptyspan;
var textarea;

var setTitle = function (el) {
    emptyspan.innerHTML = ellipsify(el.value);
}

function ellipsify(str) {
    if (str.length > 10) {
        return (str.substring(0, 10) + "...");
    }
    else {
        return str;
    }
}


var constructli = function (title) {
    var li = document.createElement('li');
    var span = document.createElement('span');
    span.innerHTML = title;
    li.appendChild(span);
    li.setAttribute("type", "show");
    ul.appendChild(li);
    return span;
}




function addNewElement() {

    if (descValue.value === '') {
        alert("You must write something!");
    } else {

        emptyspan = constructli("");
        textarea = document.getElementById('txtarea');
        textarea.value = "";
        textarea.focus();

    }
}