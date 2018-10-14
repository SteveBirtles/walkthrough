let id = -1;

function pageLoad() {

    let lastPage =  Cookies.get("breadcrumb");
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
        loadAccessory();
        resetDeleteButton();
    }

    resetForm();

}

function loadAccessory() {

    $.ajax({
        url: '/accessory/get/' + id,
        type: 'GET',
        success: accessoryDetails => {
            if (accessoryDetails.hasOwnProperty('error')) {
                alert(accessoryDetails.error);
            } else {
                $('#category').val(accessoryDetails.category);
                $('#description').val(accessoryDetails.description);
                $('#imageURL').val(accessoryDetails.imageURL);
                $('#quantity').val(accessoryDetails.quantity);
                $('#thirdParty').prop("checked", accessoryDetails.thirdParty);
        }
    });

}


function resetForm() {

    const form = $('#accessoryForm');

    form.unbind("submit");
    form.submit(event => {
        event.preventDefault();
        $.ajax({
            url: '/accessory/save/' + id,
            type: 'POST',
            data: form.serialize(),
            success: response => {
                if (response === 'OK') {
                    window.location.href = $("#back").attr("href");
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
            url: '/accessory/delete',
            type: 'POST',
            data: {"id": id},
            success: response => {
                if (response === 'OK') {
                    window.location.href = $("#back").attr("href");
                } else {
                    alert(response);
                }
            }
        });

    });

}
