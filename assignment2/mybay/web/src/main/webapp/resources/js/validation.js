function showMessage(){
    let pwd = document.getElementById("SignUp:password").value;
    let pwd2 = document.getElementById("SignUp:confirm").value;

    console.log(pwd);
    console.log(pwd2);

    if(pwd !== pwd2) {
        alert("Passwords don't match!")
        return false;
    }

    pwd = md5(pwd);

    return true;
}
