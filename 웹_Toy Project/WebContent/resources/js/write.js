document.getElementById("font_size").addEventListener("click", chageLangSelectSize);
document.getElementById("font_color").addEventListener("click", chageLangSelectSize);
document.getElementById("background_color").addEventListener("click", chageLangSelectSize);
document.getElementById("text_align").addEventListener("click", chageLangSelectSize);



function chageLangSelectSize() {
   var langSelect = document.getElementById("font_size");

   var selectValue = langSelect.options[langSelect.selectedIndex].value;

   document.getElementById("content").style.fontSize = selectValue;
}

function chageLangSelectColor() {
   var langSelect = document.getElementById("font_color");

   var selectValue = langSelect.options[langSelect.selectedIndex].value;

   document.getElementById("content").style.color = selectValue;
}

function chageLangSelectBackground() {
   var langSelect = document.getElementById("background_color");

   var selectValue = langSelect.options[langSelect.selectedIndex].value;

   document.getElementById("content").style.backgroundColor = selectValue;
}

function chageLangSelectAlign() {
   var langSelect = document.getElementById("text_align");

   var selectValue = langSelect.options[langSelect.selectedIndex].value;

   document.getElementById("content").style.textAlign = selectValue;
}