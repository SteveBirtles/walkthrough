function updateConsoleList() {

    $.ajax({
        url: '/console/list',
        type: 'GET',
        success: consoleList => {

            if (consoleList.hasOwnProperty('error')) {

                alert(consoleList.error);

            } else {

                let consolesHTML = `<div class="container">`
                                    + `<div class="row mb-2">`
                                    + `<div class="col-3 bg-light font-weight-bold">Console Name</div>`
                                    + `<div class="col-2 bg-light font-weight-bold">Manufacturer</div>`
                                    + `<div class="col-1 bg-light font-weight-bold">Year</div>`
                                    + `<div class="col-1 bg-light font-weight-bold">Media</div>`
                                    + `<div class="col-2 bg-light font-weight-bold">Sales</div>`
                                    + `<div class="col-3 text-right bg-light font-weight-bold">Options</div>`
                                  + `</div>`;

                for (let console of consoleList) {
                    consolesHTML += `<div class="row mb-2">`
                                    + `<div class="col-3">${console.name}</div>`
                                    + `<div class="col-2">${console.manufacturer}</div>`
                                    + `<div class="col-1">${console.year}</div>`
                                    + `<div class="col-1 small">${console.mediaType}</div>`
                                    + `<div class="col-2 small">${console.sales}</div>`
                                    + `<div class="col-3 text-right">`
                                        + `<a class="btn btn-sm btn-primary mr-2"  href="/client/games.html?id=${console.id}&name=${console.name}">Games</a>`
                                        + `<a class="btn btn-sm btn-info mr-2"  href="/client/accessories.html?id=${console.id}&name=${console.name}">Accessories</a>`
                                        + `<a class="btn btn-sm btn-success"  href="/client/console.html?id=${console.id}&name=${console.name}">Edit</a>`
                                    +`</div>`
                                 + `</div>`;
                }
                consolesHTML += `</div>`;

                $('#consoles').html(consolesHTML);

            }

        }
    });

}


function pageLoad() {

    updateConsoleList();

}