//Функция отображения PopUp
function PopUpShow(id, start, finish) {
    alert(id);
    $("#id").val(id);
    $("#start").val(start);
    $("#finish").val(finish);

    $("#popup1").show();
}
//Функция скрытия PopUp
function PopUpHide() {
    $("#popup1").hide();
}