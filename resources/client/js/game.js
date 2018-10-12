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
        id = parseInt(params['id']);
    }

    if (id !== -1) {
        $('#delete').css('visibility', 'visible');
        loadGame();
    } else {
        $("[name='consoleId']").val(Cookies.get('consoleId'));
    }

    resetForm();

}


function loadGame() {

    $.ajax({
        url: '/game/get/' + id,
        type: 'GET',
        success: gameDetails => {
            if (gameDetails.hasOwnProperty('error')) {
                alert(gameDetails.error);
            } else {
                $("[name='consoleId']").val(gameDetails.consoleId);
                $("[name='name']").val(gameDetails.name);
                $("[name='year']").val(gameDetails.year);
                $("[name='sales']").val(gameDetails.sales);
                $("[name='imageURL']").val(gameDetails.imageURL);
            }
        }
    });

}


function resetForm() {

    const form = $('#gameForm');

    form.unbind("submit");
    form.submit(event => {
        event.preventDefault();
        $.ajax({
            url: '/game/save/' + id,
            type: 'POST',
            data: form.serialize(),
            success: response => {
                if (response === 'OK') {
                    window.location.href = Cookies.get("lastGamesURL");
                } else {
                    alert(response);
                }
            }
        });
    });
}
