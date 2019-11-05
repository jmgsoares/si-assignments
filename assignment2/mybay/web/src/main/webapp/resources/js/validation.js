function validationSignUp() {
    let pwd = document.getElementById("SignUp:password").value;
    let pwd2 = document.getElementById("SignUp:confirm").value;

    if (pwd !== pwd2) {
        alert("Passwords don't match!");
        return false;
    }

    document.getElementById("SignUp:password").value = md5(document.getElementById("SignUp:password").value);

    return true;
}

function validationLogin() {
    let pwd = document.getElementById("Login:password").value;
    document.getElementById("Login:password").value = md5(pwd);
    return true;
}

function validationUpdate() {
    let pwd = document.getElementById("UpdatePassword:password").value;
    let pwd2 = document.getElementById("UpdatePassword:confirm").value;

    let pwd3 = document.getElementById("UpdatePassword:newPassword").value;

    if(pwd !== pwd2) {
        alert("Passwords don't match!")
        return false;
    }

    document.getElementById("UpdatePassword:newPassword").value = md5(pwd3);
    return true;
}