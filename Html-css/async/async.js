/*var array = [{id: 1, desc:"Hi"}, {id: 2, desc:"Hello"}, {id: 3, desc:"Hey"}, {id: 4, desc:"Bye"}, {id: 5, desc:"Good"}]

console.log(array);


function checkRange(randNum) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
            array.forEach(function(arrayItem){
                var num = arrayItem.id;

                if(num === randNum){
                    resolve(num);
                } else{
                    reject("No");
                }
            })
        }, 1000);
    });
}

function updateDescription(num, array) {
    return new Promise((resolve, reject) => {
        setTimeout(() => {
           array[num].desc = "Good Afternoon";
        }, 1000);
    });
}



var randNum = Math.random();
console.log(Math.round(randNum));

async function fetchDetails() {
    try{
        let Num = await(checkRange(Math.round(randNum)));
        console.log(Num);
        updateDescription(Num, array);
    }catch(err){
        console.log(err);
    }

/*checkRange(Math.round(randNum)).then(num => {
    console.log(num);
    updateDescription(num, array);
}).catch(err => {
    console.error(err);
});
}

fetchDetails();*/

var div = document.getElementById("json");


async function fetchFunction() {
    let result = await fetch("https://jsonplaceholder.typicode.com/posts", {
        method: "get",
    });

    let body = await result.json();
    for(let i=0;i<body.length;i++) {
        var li = document.createElement('li');
        var span = document.createElement('span');
        span.innerHTML = body[i].id + " " + body[i].title;
        li.appendChild(span);
        div.append(li);
    }
}


fetchFunction();

