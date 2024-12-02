const btnLogout = document.getElementById("btnLogOut");

function logOut(event) {
    console.log("saindo")
    if (event && event.type === "click") {
        window.location.href = "/logout";
    }
}

btnLogout.addEventListener('click',logOut)