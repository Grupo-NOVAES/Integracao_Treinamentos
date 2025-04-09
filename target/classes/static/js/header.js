const btnLogout = document.getElementById("btnLogOut");
const btnNotification = document.getElementById("btnNotification");

function logOut(event) {
    console.log("saindo")
    if (event && event.type === "click") {
        window.location.href = "/logout";
    }
}

function toggleNotificationModal() {
    const modal = document.getElementById("notificationsModal");
    const icon = document.getElementById("btnNotification");
    const isHidden = modal.hasAttribute("hidden");

    if (isHidden) {
        const rect = icon.getBoundingClientRect();

        const modalWidth = modal.offsetWidth || 400;

        console.log("modal width: "+modalWidth)
        const top = rect.top + rect.height + window.scrollY;
        const left = rect.right - modalWidth + window.scrollX - 15;

        modal.style.top = `${top}px`;
        modal.style.left = `${left}px`;

        modal.removeAttribute("hidden");
    } else {
        modal.setAttribute("hidden", true);
    }
}


btnLogout.addEventListener('click',logOut)
btnNotification.addEventListener('click',toggleNotificationModal)