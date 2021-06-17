function toSwitch(clickedID) {

    var image = document.getElementById('lampImg');
    if (clickedID == "btnOn") {
        image.src = "../images/pic_lampOn.jpg";
        flag = true;
    } else {
        image.src = "../images/pic_lampOff.jpg"
        flag = false;
    }
}


var status = 0;
var image = document.getElementById('lampImg');

function toSwitch(clickedID) {
    status = clickedID == "btnOn" ? 1 : 0;

    changeStatus();

    document.addEventListener('onload', () => {
    fetch(
        'http://localhost:8080/rooms/{id},
        { method: 'GET'})
        .then((response) => response.json())
        .then((data) => changeImage(JSON.decode(data)['enabled']))
    })
}



function changeStatus() {
    image.addEventListener('click', () => {
        fetch(
            'http://localhost:8080/rooms/{id},
            {
                method: 'PUT',
                data: {'enabled': 1}
            })
            .then(() => {
            this.status = !this.status;
            changeImage(status)
            })
    })
}

function changeImage(status) {
    switch(status) {
        case 0:
            image.src = '../images/pic_lampOff.jpg';
            break;
        case 1:
            image.src = '../images/pic_lampOn.jpg';
            break;
    }
}
