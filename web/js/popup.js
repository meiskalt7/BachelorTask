function PopUpShow_timeRange(id, start, finish) {
    alert(id);
    $("#id").val(id);
    $("#start").val(start);
    $("#finish").val(finish);

    $("#popup_timeRange").show();
}

function PopUpHide_timeRange() {
    $("#popup_timeRange").hide();
}

function PopUpShow_employee(id_e, surname, name, patronymic, wage, username, password, type) {
    alert(id_e);
    $("#id_e").val(id_e);
    $("#surname").val(surname);
    $("#name").val(name);
    $("#patronymic").val(patronymic);
    $("#wage").val(wage);
    $("#username").val(username);
    $("#password").val(password);
    $("#type").val(type);

    $("#popup_employee").show();
}

function PopUpHide_employee() {
    $("#popup_employee").hide();
}

function PopUpShow_category(id_c, categoryName) {
    alert(id_c);
    $("#id_c").val(id_c);
    $("#categoryName").val(categoryName);

    $("#popup_category").show();
}

function PopUpHide_category() {
    $("#popup_category").hide();
}

function PopUpShow_ingrid(id_i, name, quantity, price) {
    alert(id_i);
    $("#id_c").val(id_i);
    $("#name").val(name);
    $("#quantity").val(quantity);
    $("#price").val(price);

    $("#popup_ingrid").show();
}

function PopUpHide_ingrid() {
    $("#popup_ingrid").hide();
}

function PopUpShow_table(id_t, number, table) {
    alert(id_t);
    $("#id_t").val(id_t);
    $("#number").val(number);
    $("#table").val(table);

    $("#popup_table").show();
}

function PopUpHide_table() {
    $("#popup_table").hide();
}

function PopUpShow_reservation(id_r, name, phone, datetime, type) {
    alert(id_r);
    alert(datetime);
    $("#id_r").val(id_r);
    $("#name").val(name);
    $("#phone").val(phone);
    $("#datetime").val(datetime);
    $("#type").val(type);

    $("#popup_reservations").show();
}

function PopUpHide_reservation() {
    $("#popup_reservations").hide();
}

