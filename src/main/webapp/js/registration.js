function validateForm() {
    valid = false;
    if(document.singup.password.value == document.singup.password2.value){
        valid = true;
    }
    else{
        alert( "Passwords are not equals!" );
        document.singup.password2.value="";
        document.singup.password.value="";
    }
    return valid;
}