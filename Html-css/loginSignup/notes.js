var parent = document.getElementById('txt');
var ul = document.getElementById('menu');
var descValue = document.getElementById('txtarea');
var emptyspan;
var textarea;
var description;


var jsonObject = {};
var jsonArray = [];
jsonObject.jsonArray = jsonArray;

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
        setTimeout(() => {
            description = textarea.value;
            sendNotesToServer(description);
        }, 3000);
    }
}

async function sendNotesToServer(description) {

    console.log(description);

    var tokenValue = localStorage.getItem("token");

    let result = await fetch("http:192.168.100.162:3000/notes", {
        method: "post",
        headers: {
            "Authorization": "Bearer " + tokenValue,
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ "description": description })
    });

    let body = await result.json();
    console.log(body.responseBody.id);
    //localStorage.setItem("token", body["responseBody"]["token"]);
}

async function getAllNotes() {
    ul.innerHTML="";
    var tokenValue = localStorage.getItem("token");

    let result = await fetch("http:192.168.100.162:3000/notes", {
        method: "get",
        headers: {
            "Authorization": "Bearer " + tokenValue,
            "Content-Type": "application/json"
        }
    });

    let body = await result.json();
    console.log(body);

    for (var i = 0; i < body.responseBody.length; i++) {
        var id = body.responseBody[i].id;
        var description = body.responseBody[i].description;

        /*var li = document.createElement('li');
        var span = document.createElement('span');
        span.innerHTML = id + " " + description;
        li.appendChild(span);

        ul.append(li);*/
        ul.innerHTML += "<li id=" + id + " type='litem'>" + description + "</li>";
        var notes = {
            "id": id,
            "description": description
        }

        jsonObject.jsonArray.push(notes);
    }
    console.log(jsonObject);
}

ul.addEventListener('click', function (event) {
    var element = event.target;
    var index = element.getAttribute("id");

    console.log(index);
    deleteNoteByID(index);
});

async function deleteNoteByID(index) {

    console.log(index);

    var tokenValue = localStorage.getItem("token");

    let result = await fetch("http:192.168.100.162:3000/notes/" + index, {
        method: "delete",
        headers: {
            "Authorization": "Bearer " + tokenValue,
            "Content-Type": "application/json"
        },
    });

    let body = await result.json();
    console.log(body);
    getAllNotes();
}