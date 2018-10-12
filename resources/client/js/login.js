function resetLoginForm() {

    let destination = "/client/game.html";

    checkLogin(true);

    const loginForm = $('#loginForm');
    loginForm.submit(event => {
        event.preventDefault();
        $.ajax({
            url: '/admin/login',
            type: 'POST',
            data: loginForm.serialize(),
            success: response => {
                if (response.startsWith('Error:')) {
                    alert(response);
                } else {
                    Cookies.set("sessionToken", response);
                    window.location.href = destination;
                }
            }
        });
    });

    $("#logout").click(event => {
        Cookies.remove("sessionToken");
        window.location.href = "/client/index.html";
    });
}

function checkLogin(onLoginPage) {

    let token = Cookies.get("sessionToken");

    if (token !== undefined) {
        $.ajax({
            url: '/admin/check',
            type: 'GET',
            success: username => {
                if (onLoginPage) {
                    $('#currentUser').text("Logged in as " + username);
                    $('#logout').css('visibility', 'visible');
                }
                return username;
            }
        });
    }
}

