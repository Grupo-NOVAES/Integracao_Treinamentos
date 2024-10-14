const userId = document.getElementById("userId").value;
async function activeUser(){
    console.log("função chamada!")
    let response = await fetch(`http://localhost:8080/user/activeUser/${userId}`, { 
        method: "POST",
    });
    window.location.reload();
    console.log("res: "+JSON.stringify(response))
}