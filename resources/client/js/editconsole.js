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
                $("[name='name']").val(consoleDetails.name);
                $("[name='manufacturer']").val(consoleDetails.manufacturer);
                $("[name='imageURL']").val(consoleDetails.imageURL);
                $("[name='notes']").val(consoleDetails.notes);
                $("[name='year']").val(consoleDetails.year);
                $("[name='sales']").val(consoleDetails.sales);
                $("[name='mediaType']").val(consoleDetails.mediaType);
                $("[name='handheld']").prop("checked", consoleDetails.handheld);
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
            url: '/console/delete/' + id,
            type: 'POST',
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
