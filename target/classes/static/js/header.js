const btnLogout = document.getElementById("btnLogOut");
const btnNotification = document.getElementById("btnNotification");
const notificationsModal = document.getElementById("notificationsModal");

function logOut(event) {
    console.log("saindo")
    if (event && event.type === "click") {
        window.location.href = "/logout";
    }
}

function toggleNotificationModal() {
    const modal = notificationsModal;
    const icon = btnNotification;
    const isHidden = modal.hasAttribute("hidden");

    if (isHidden) {
        const rect = icon.getBoundingClientRect();
        const modalWidth = modal.offsetWidth || 400;

        const top = rect.top + rect.height + window.scrollY;
        const left = rect.right - modalWidth + window.scrollX - 15;

        modal.style.top = `${top}px`;
        modal.style.left = `${left}px`;

        modal.removeAttribute("hidden");
    } else {
        modal.setAttribute("hidden", true);
    }
}

function handleClickOutside(event) {
    if (
        !notificationsModal.hasAttribute("hidden") &&
        !notificationsModal.contains(event.target) &&
        !btnNotification.contains(event.target)
    ) {
        notificationsModal.setAttribute("hidden", true);
    }   
}

btnLogout.addEventListener('click', logOut);
btnNotification.addEventListener('click', toggleNotificationModal);
document.addEventListener('click', handleClickOutside);
