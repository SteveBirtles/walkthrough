let id = -1;

function pageLoad() {

    let lastPage =  Cookies.get("breadcrumb")
    $("#back").attr("href", lastPage);

    let currentPage = window.location.href;
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
        resetDeleteButton();
    }

    resetForm();

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
                $('#mediaType').val(consoleDetails.mediaType);
                $('#handheld').prop("checked", consoleDetails.handheld);
            }
        }
    });

}


function resetForm() {

    const form = $('#consoleForm');

    form.unbind("submit");
    form.submit(event => {
        event.preventDefault();
        $.ajax({
            url: '/console/save/' + id,
            type: 'POST',
            data: form.serialize(),
            success: response => {
                if (response === 'OK') {
                    window.location.href = "/client/index.html";
                } else {
                    alert(response);
                }
            }
        });
    });
}


function resetDeleteButton() {

    $('#delete').css('visibility', 'visible');

    $('#delete').click(event => {
        $.ajax({
            url: '/console/delete',
            type: 'POST',
            data: {"id": id},
            success: response => {
                if (response === 'OK') {
                    window.location.href = "/client/index.html";
                } else {
                    alert(response);
                }
            }
        });

    });

}
