function toSwitch() {
  let image = document.getElementById('lampImg');
  if (image.src.match("lampOn")) {
    image.src = "pic_lampOff.gif";
  } else {
    image.src = "pic_lampOn.gif";
  }
}