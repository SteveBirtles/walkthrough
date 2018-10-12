function updateAccessoriesList(id) {

    $.ajax({
        url: '/accessory/list/' + id,
        type: 'GET',
        success: accessoriesList => {

            if (accessoriesList.hasOwnProperty('error')) {

                alert(accessoriesList.error);

            } else {

                let accessoriesHTML = `<div class="container">`
                    + `<div class="row mb-2">`
                    + `<div class="col-3 bg-light font-weight-bold">Description</div>`
                    + `<div class="col-1 bg-light font-weight-bold">Quantity</div>`
                    + `<div class="col-2 bg-light font-weight-bold">ThirdParty</div>`
                    + `<div class="col-2 bg-light font-weight-bold">Image</div>`
                    + `<div class="col-3 text-right bg-light font-weight-bold">Options</div>`
                    + `</div>`;

                for (let accessory of accessoriesList) {
                    accessoriesHTML += `<div class="row mb-2">`
                        + `<div class="col-3">${accessory.description}</div>`
                        + `<div class="col-1">${accessory.quantity}</div>`
                        + `<div class="col-2">${accessory.thirdParty}</div>`
                        + `<div class="col-2 small"><a href="${accessory.imageURL}" target=”_blank”><img width="120" height="90" src="${accessory.imageURL}"></a></div>`
                        + `<div class="col-3 text-right">`
                        + `<a class="btn btn-sm btn-success"  href="/client/accessory.html?id=${accessory.id}">Edit</a>`
                        +`</div>`
                        + `</div>`;
                }
                accessoriesHTML += `</div>`;

                $('#accessories').html(accessoriesHTML);

            }

        }
    });

}


function pageLoad() {

    let params = getQueryStringParameters();

    $('#console').text(decodeURI(params['name']));

    updateAccessoriesList(params['id']);

}