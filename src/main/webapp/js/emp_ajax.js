$(document).ready(function() {
    getAllEmps();
    setupButtonClickHandlers();
});

function setupButtonClickHandlers(){
    $(document).on( "click", "button.forward_emp", forward_emp);
    $(document).on( "click", "button.back_emp", back_emp);
    $(document).on( "click", "button.update_emps", update_form_emp);
    $(document).on( "click", "button.delete_emps", delete_emp);
    $(document).on( "click", "button.add_emps", add_form_emp);
    $(document).on( "click", "button.cancel_emps", cansel_update_emp);
    $(document).on( "click", "button.save_emps", save_update_emp);
    $(document).on("click", "button.close_dialog_e", close_dialog_e);

    $(document).on("click", "button.all_emps", createTable);
}

//locale variables
var currantPage = 1;
var maxPage = 0;
var allEmps = [];

//Read all employee
function getAllEmps(){
    $.ajax({
        url : 'emps/all',
        type: 'get',
        success : function(data) {
            allEmps = data;
            console.log(allEmps);
            createTable();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#resp_e').html("<dialog id=\"dialog_e\"><p>"+"Server error, can`t find data about departments"+
                "</p><button class=\"close_dialog btn btn-outline-dark\">Close</button></dialog>");
            var dialog = document.getElementById('dialog_e');
            dialog.show();
        }
    });
}
function update_form_emp(){
    console.log("update_emp click");
    createTable($(this).attr("value"));
}
function delete_emp(){
    console.log("delete_emp click");
    var id = $(this).attr("value");

    $.ajax({
        url : 'emps/delete',
        //type: 'delete',
        type: 'post',
        data: {
            id: id
        },
        success : function(data) {

            var index = allEmps.map(function(e) { return e.id; }).indexOf(Number(id));
            allEmps.splice(index, 1);
            createTable();

            $('#resp_e').html("<dialog id=\"dialog_e\"><p>"+"Employee was deleted"+
                "</p><button class=\"close_dialog_e btn btn-outline-dark\">Close</button></dialog>");
            var dialog = document.getElementById('dialog_e');
            dialog.show();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#resp_e').html("<dialog id=\"dialog_e\"><p>"+"Employee can`t be deleted"+
                "</p><button class=\"close_dialog_e btn btn-outline-dark\">Close</button></dialog>");
            var dialog = document.getElementById('dialog_e');
            dialog.show();
        }
    });
}
function add_form_emp(){
    console.log("add_form_emp click");
    maxPage = Math.ceil(allEmps.length / 10);
    currantPage = maxPage;
    createTable();

    $(".employees_table").append(function(){
        console.log("rows: " + $(".employees_table").find(".add_row_emps").length);
        if($(".employees_table").find(".add_row_emps").length === 0){
            var result = "<tr class=\"add_row_emps\"><form><td></td>" +
                "<td id=\"text_surname\"><input type=\"text\" id=\"surname_add\"/></td>" +
                "<td><input type=\"text\" id=\"name_add\"/></td>" +
                "<td><input type=\"text\" id=\"fatherName_add\"/></td>" +
                "<td><input type=\"date\" id=\"dataOfBirthday_add\"/></td>"+
                "<td><select id=\"departmentName_add\">";
            for (x=0; x<allDeps.length;x++) {
                result+="<option value=\""+allDeps[x].id + "," +allDeps[x].depName + ","+ allDeps[x].description +"\">"+allDeps[x].depName+"</option>";
            }
            result+="<td><button class=\"cancel_add_emps btn btn-warning\">cancel</button></td>" +
                "<td><button class=\"save_add_emps btn btn-success\">save</button></td></form></tr>";


            return result;
        }
    });
    $(".cancel_add_emps").on( "click", cansel_update_emp);
    $(".save_add_emps").on( "click", save_add_emp);
}
function cansel_update_emp(){
    console.log("cansel_update_emps click");
    createTable();
}
function save_update_emp(){
    console.log("save_update_emps click");
    var emp_id = document.getElementById("emp_id").textContent;
    var surname = document.getElementById("surname").value;
    var name = document.getElementById("name").value;
    var fatherName = document.getElementById("fatherName").value;
    var dataOfBirthday = document.getElementById("dataOfBirthday").value;
    var dep_id = document.getElementById("departmentName").value;
    var dep_name = document.getElementById('departmentName').selectedOptions[0].text;

    $.ajax({
        url : 'emps/update',
        type: 'put',
        data: {
            id: emp_id,
            surname: surname,
            name: name,
            fatherName: fatherName,
            dob: dataOfBirthday,
            department: dep_id
        },
        success : function(data) {

            var index = allEmps.map(function(e) { return e.id; }).indexOf(Number(data.id));
            allEmps[index].surname = surname;
            allEmps[index].name = name;
            allEmps[index].fatherName = fatherName;
            allEmps[index].dob = dataOfBirthday;
            allEmps[index].dep_id = Number(dep_id);
            allEmps[index].departmentName = dep_name;
            createTable();

            $('#resp_e').html("<dialog id=\"dialog_e\"><p>"+"Employee was updated"+
                "</p><button class=\"close_dialog_e btn btn-outline-dark\">Close</button></dialog>");
            var dialog = document.getElementById('dialog_e');
            dialog.show();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#resp_e').html("<dialog id=\"dialog_e\"><p>"+"Fill in fields: surname, name, department"+
                "</p><button class=\"close_dialog_e btn btn-outline-dark\">Close</button></dialog>");
            var dialog = document.getElementById('dialog_e');
            dialog.show();
        }
    });
}
function save_add_emp(){
    console.log("save_add_emps click");
    var surname = document.getElementById("surname_add").value;
    var name = document.getElementById("name_add").value;
    var fatherName = document.getElementById("fatherName_add").value;
    var dataOfBirthday = document.getElementById("dataOfBirthday_add").value;

    var department = document.getElementById("departmentName_add").value.split(",");
    console.log(department);
    var dep_id = department[0];
    var dep_name = department[1];
    var descr = department[2];
    console.log(dep_id + " " + dep_name + " " + descr);

    var validSurname = false;
    var validName = false;

    console.log("surname: " + surname + " name: " + name);
    if(/\d/.test(surname) || surname === ""){
        document.getElementById("surname_add").setAttribute("placeholder", "Fill in surname");
    }
    else{
        validSurname = true;
    }

    if( /\d/.test(name) || name === ""){
        document.getElementById("name_add").setAttribute("placeholder", "Fill in name");
    }
    else{
        validName = true;
    }
    console.log("validSurname: " + validSurname + " validName: " + validName);
    if(validSurname && validName){
        $.ajax({
            url : 'emps/add',
            type: 'POST',
            data: {
                surname: surname,
                name: name,
                fatherName: fatherName,
                dob: dataOfBirthday,
                department: dep_id
            },
            success : function(data) {

                console.log(data)

                allEmps.push({'id': Number(data.id), 'surname': surname, 'name': name, 'fatherName': fatherName,
                        'dob': dataOfBirthday, 'department': {"id":dep_id, "depName" : dep_name, "description": descr}});
                createTable();

                $('#resp_e').html("<dialog id=\"dialog_e\"><p>"+"Employee was added"+
                    "</p><button class=\"close_dialog_e btn btn-outline-dark\">Close</button></dialog>");
                var dialog = document.getElementById('dialog_e');
                dialog.show();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#resp_e').html("<dialog id=\"dialog_e\"><p>"+"Fill in fields: surname, name, department"+
                    "</p><button class=\"close_dialog_e btn btn-outline-dark\">Close</button></dialog>");
                var dialog = document.getElementById('dialog_e');
                dialog.show();
            }
        });
    }
}
function createTable(val){
    $('#resp_ajax_emps').empty();
    var index = Number(val);
    maxPage = Math.ceil(allEmps.length / 10);
    console.log("------>");
    console.log(index);
    if(!isNaN(index)){
        var position = allEmps.map(function(e) { return e.id; }).indexOf(index);
        currantPage = Math.ceil((position + 1) / 10);
        console.log("Create index: " + index + " , currantPage: " + currantPage );
    }


    var start = 0 + (10 * (currantPage-1));
    var end = 10 + (10 * (currantPage-1));
    console.log("Create table");
    console.log(allEmps[1].department);
    var tableE = allEmps.slice(start, end);
    console.log("table: ")
    console.log(tableE);


    var result="<fieldset><legend><ul class=\"bullet\"><li>Employees table</li></ul></legend><button class=\"add_emps out btn btn-primary\">Add</button>"+
        "<table class=\"employees_table table table-bordered table-sm table-hover\" id=\"employees\"><thead>"+
        "<tr class=\"active bg-info\"><td class=\"text-light\" scope=\"col\">Id</td><td class=\"text-light\" scope=\"col\">Surname</td>"+
        "<td class=\"text-light\" scope=\"col\">Name</td><td class=\"text-light\" scope=\"col\">Father Name</td>"+
        "<td class=\"text-light\" scope=\"col\">Data of birthday</td><td class=\"text-light\" scope=\"col\">Department</td><td class=\"text-light\" colspan=\"2\">Actions</tr></thead>";
    for (i=0; i<tableE.length;i++) {
        var id = tableE[i].id;
        var surname = tableE[i].surname;
        var name = tableE[i].name;
        var fatherName = tableE[i].fatherName;
        var dataOfBirthday = tableE[i].dob;
        var departmentName = tableE[i].department.depName;
        var dep_id = tableE[i].department.id;
        var data = dataOfBirthday.split("-");

        if(index === id){

            result+="<tr scope=\"row\"><form><td class=\"\" id=\"emp_id\">" + id + "</td>" +
                "<td class=\"\"><input type=\"text\" id=\"surname\" value=\"" + surname +"\"/></td>" +
                "<td class=\"\"><input type=\"text\" id=\"name\" value=\"" + name +"\"/></td>" +
                "<td class=\"\"><input type=\"text\" id=\"fatherName\" value=\"" + fatherName +"\"/></td>" +
                "<td class=\"\"><input type=\"date\" id=\"dataOfBirthday\" value=\"" + dataOfBirthday +"\"/></td>" +
                "<td class=\"\"><select id=\"departmentName\"><option selected value=\""+dep_id+"\">"+departmentName+"</option>";
            for (x=0; x<allDeps.length;x++) {
                if(Number(dep_id) !== allDeps[x].id){
                    result+="<option value=\""+allDeps[x].id+"\">"+allDeps[x].depName+"</option>";
                }
            }
            result+="<td><button class=\"cancel_emps btn btn-warning\">cancel</button></td>" +
                "<td><button class=\"save_emps btn btn-success\">save</button></td></form></tr>";
        }else{
            result+="<tr scope=\"row\"><td>"+id+"</td><td>"+surname+"</td><td>"+name+"</td><td>"+fatherName+"</td><td>"
                +data[2]+"."+data[1]+"."+data[0]+"</td><td>"+departmentName+"</td>"+
                "<td><button class=\"delete_emps btn btn-outline-danger\" value=\""+id+"\">Delete</button></td>"+
                "<td><button class=\"update_emps btn btn-outline-primary\" value=\""+id+"\">Update</button></td></tr>";
        }
    }
    result+="</table><button style=\"float: left;\" class=\"back_emp btn btn-info\"><-</button>"+
        "<div style=\"float: left;\"  class=\"btn btn-info\">"+currantPage + " / " + maxPage+"</div>"+
        "<button style=\"float: left;\" class=\"forward_emp btn btn-info\">-></button>"+
        "</fieldset>";
    $('#resp_ajax_emps').html(result);
}
//Pagination
function forward_emp(){
    console.log("forward_emp click");
    if(currantPage < maxPage){
        currantPage++;
    }
    console.log("page: " + currantPage);
    createTable();
}
function back_emp(){
    console.log("back_emp click");
    if(currantPage > 1){
        currantPage--;
    }
    console.log("page: " + currantPage);
    createTable();
}

function close_dialog_e(){
    console.log("close the employee dialog")
    var dialog = document.getElementById('dialog_e');
    dialog.close();
}