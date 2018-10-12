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
        loadConsole();
    }

}

function loadConsole() {

    $.ajax({
        url: '/console/get/' + id,
        type: 'GET',
        success: consoleDetails => {
            if (consoleDetails.hasOwnProperty('error')) {
                alert(consoleDetails.error);
            } else {
                $('#name').val(consoleDetails.name);
                $('#manufacturer').val(consoleDetails.manufacturer);
                $('#imageURL').val(consoleDetails.imageURL);
                $('#notes').val(consoleDetails.notes);
                $('#year').val(consoleDetails.year);
                $('#sales').val(consoleDetails.sales);
                $('#handheld').val(consoleDetails.handheld);
                $('#mediaType').val(consoleDetails.mediaType);
            }
        }
    });

}