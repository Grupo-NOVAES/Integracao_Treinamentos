function showMenu(){
    const menuContent = document.getElementById('dropdownMenu');

    if(menuContent.style.display==='none' || menuContent.style.display===''){
        menuContent.style.display='block';
    }else{
        menuContent.style.display='none';
    }
}