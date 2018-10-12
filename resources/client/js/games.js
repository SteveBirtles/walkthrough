function getQueryStringParameters() {
    let params = [];
    let q = document.URL.split('?')[1];
    if (q !== undefined) {
        q = q.split('&');
        for (let i = 0; i < q.length; i++) {
            let bits = q[i].split('=');
            params.push(bits[1]);
            params[bits[0]] = bits[1];
        }
    }
    return params;
}

function updateGamesList(id) {

    $.ajax({
        url: '/game/list/' + id,
        type: 'GET',
        success: gameslist => {

            if (gameslist.hasOwnProperty('error')) {

                alert(gameslist.error);

            } else {

                let gamesHTML = `<div class="container">`
                                    + `<div class="row mb-2">`
                                    + `<div class="col-3 bg-light font-weight-bold">Game Name</div>`
                                    + `<div class="col-1 bg-light font-weight-bold">Year</div>`
                                    + `<div class="col-2 bg-light font-weight-bold">Sales</div>`
                                    + `<div class="col-2 bg-light font-weight-bold">Image</div>`
                                    + `<div class="col-3 text-right bg-light font-weight-bold">Options</div>`
                                  + `</div>`;

                for (let game of gameslist) {
                    gamesHTML += `<div class="row mb-2">`
                                    + `<div class="col-3">${game.name}</div>`
                                    + `<div class="col-1">${game.year}</div>`
                                    + `<div class="col-2">${game.sales}</div>`
                                    + `<div class="col-2 small"><img width="100" height="100" src="${game.imageURL}"></div>`
                                    + `<div class="col-3 text-right">`
                                        + `<a class="btn btn-sm btn-success"  href="/client/game.html?id=${game.id}">Edit</a>`
                                    +`</div>`
                                 + `</div>`;
                }
                gamesHTML += `</div>`;

                $('#games').html(gamesHTML);

            }

        }
    });

}


function pageLoad() {

    let params = getQueryStringParameters();

    $('#console').text(decodeURI(params['name']));

    updateGamesList(params['id']);

}