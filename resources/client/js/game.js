let id = -1;

function pageLoad() {

    let currentPage = window.location.pathname;
    Cookies.set("destination", currentPage);

    checkLogin();

    $("#logout").click(event => {
        Cookies.remove("sessionToken");
        window.location.href = "/client/index.html";
    });

    let params = getQueryStringParameters();
    if (params['id'] !== undefined) {
        id = params['id'];
    }

    if (id !== -1) {
        loadGame();
    }

}


function loadGame() {

    $.ajax({
        url: '/game/get/' + id,
        type: 'GET',
        success: gameDetails => {
            if (gameDetails.hasOwnProperty('error')) {
                alert(gameDetails.error);
            } else {
                $('#name').val(gameDetails.name);
                $('#year').val(gameDetails.year);
                $('#sales').val(gameDetails.sales);
                $('#imageURL').val(gameDetails.imageURL);
            }
        }
    });

}