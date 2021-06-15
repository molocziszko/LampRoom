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