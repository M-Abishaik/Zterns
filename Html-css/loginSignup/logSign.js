var loginUsername = document.getElementById("loginUsername");
var loginPassword = document.getElementById("loginPassword");

var registerUsername = document.getElementById("registerUsername");
var registerPassword = document.getElementById("registerPassword");


function viewlogin() {
  var x = document.getElementById("signup");
  var y = document.getElementById("login");
  console.log(x);
  x.style.display = "none";
  y.style.display = "block";
}

function viewsignup() {
  var y = document.getElementById("signup");
  var x = document.getElementById("login");
  y.style.display = "block";
  x.style.display = "none";
}

async function validateLogin() {

  var name = loginUsername.value;
  var pass = loginPassword.value;


  let result = await fetch("http:192.168.100.162:3000/auth/login", {
    method: "post",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ "username": name, "password": password })
  });

  let body = await result.json();
  console.log(body);

  if (body['isSuccess']) {
    sessionStorage.setItem("token", body["responseBody"]["token"]);
    window.location = "first.html"
  } else {
    alert(body['responseBody']['token']);
  }
}

async function validateRegister() {

  var name = registerUsername.value;
  var pass = registerPassword.value;

  let result = await fetch("http:192.168.100.162:3000/auth/register", {
    method: "post",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ "username": name, "password": password })
  });

  let body = await result.json();
  console.log(body);
  sessionStorage.setItem("token", body["responseBody"]["token"]);

}