let modalVisible = false;


document.addEventListener('click', function(event) {
    const btnOptions = document.getElementById('options');
    const ModalAdd = document.getElementById('addModal');
    const target = event.target;

    if (modalVisible && !btnOptions.contains(target) && !target.closest('button')) {
        hideModalOptions();
    }

    if(modalVisible && !ModalAdd.contains(target) && !target.closest('button')){
        hideModalAdd()
    }

});

document.getElementById("btnOptionUser").addEventListener('click')

function showModalOptions(button){
    const options = document.getElementById('options');
    const rect = button.getBoundingClientRect();

    const marginLeft = 125;
    const marginTop = -40;

    let modalLeft = rect.left - marginLeft;
    let modalTop = rect.top + marginTop;

    options.style.top = `${modalTop}px`;
    options.style.left = `${modalLeft}px`;

    options.style.display = 'block';
    modalVisible = true;
}
function hideModalOptions(){
    const options = document.getElementById('options');
    options.style.display="none";
}




function showModalAdd(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display = 'block';
    modalVisible = true;
}
function hideModalAdd(){
    const AddModal = document.getElementById('addModal');
    AddModal.style.display="none";
    modalVisible = false;
}



function showMenu() {
    const dropdownMenu = document.getElementById('dropdownMenu');
    dropdownMenu.classList.toggle('active');
}
