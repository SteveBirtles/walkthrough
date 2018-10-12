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
        loadAccessory();
    }

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
                $('#thirdParty').val(accessoryDetails.thirdParty);
            }
        }
    });

}