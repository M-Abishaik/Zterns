var text=document.getElementById("status");
var items=[];

var data=document.getElementById("data");
var submitButton=document.getElementById("submit");
var list=document.getElementById("mylist");
text.innerHTML="You have an empty list";

if(localStorage.getItem("isSet")=="yes"){
    var result = localStorage.getItem("list");
    var items= JSON.parse(result);
    addList(items);
}

submitButton.addEventListener('click' ,function(){
    text.innerHTML="";
    items.push(data.value);
    console.log(items);
    data.value="";
    addList(items);
});

function addList(items){
    list.innerHTML="";
    for(index=0;index<items.length;index++){
    list.innerHTML+= "<li id="+index+" type='litem'>"+items[index]+"<button type='close'>Delete</button></li>";
    }
    storeItems(items);
}
list.addEventListener('click',function(event){
        var element=event.target;
        console.log(element.getAttribute("type"));
        if(event.target.getAttribute("type")=="close"){
            var parent = element.parentElement;
            var index= parent.getAttribute("id");
            list.removeChild(parent);
            deleteList(items,Number(index));
        }
        else if(event.target.getAttribute("type")=="litem"){
            var id=event.target.getAttribute("id");
            document.getElementById(id).classList.add("liner");
        }
});
function storeItems(items){
    localStorage.setItem("isSet","yes");
    localStorage.setItem("list",JSON.stringify(items));
}

function deleteList(items,index){
    items.splice(index,1);
    storeItems(items);
}