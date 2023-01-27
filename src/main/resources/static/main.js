const tbody = document.querySelector("#tbod");
let str = "";

$(document).ready(async function () {
    let users = await fetch("http://localhost:8080/user").then(res => res.json());

    users.forEach((u) => {
        let rol = "";
        u.roles.forEach((u) => {
            rol += u.noPrefix + "  \n";
        });
        str += `<tr id="dele${u.id}"><td>${u.id}</td>
<td>${u.username}</td>
<td>${u.email}</td>
<td>${u.age}</td>
<td>${u.department}</td>
<td>${u.password}</td>
<td>${rol}</td>
<td><button class="btn btn-info editbtn" >edit</button></td>
<td><button class="btn btn-danger delbtn"  >delete</button></td></tr>`;
    })
    tbody.innerHTML = str;
})
//Загрузка Ролей
$(document).ready(async function () {
    let listR = await fetch("http://localhost:8080/listR").then(r => r.json());
    let rol = "";
    listR.forEach((r) => rol += `<option value="${r.role}">${r.noPrefix}</option>`);
    let sel = `<select name="sele" onchange="console.log($('#select').val())" id="select"  
multiple class="form-control sel" size="3">
<option value="">no role</option>
${rol}
</select>`;
    document.getElementById('selector').innerHTML = sel;
})
//Изменение Роли
async function rol(userRol) {
    console.log(userRol);
    let listR = await fetch("http://localhost:8080/listR").then(r => r.json());
    let rol = "";
    let bul;
    for (let n = 0; n < listR.length; n++) {
        for (let i = 0; i < userRol.length; i++) {
            if (listR[n].noPrefix === userRol[i]) {
                rol += `<option value="${listR[n].role}" selected>${listR[n].noPrefix}</option>`;
                bul = true;
                break;
            }
        }
        if (bul) {
            bul = false;
            continue;
        }
        rol += `<option value="${listR[n].role}">${listR[n].noPrefix}</option>`;
    }
    let sel = `<select name="sele" onchange="console.log($('#selectEdit').val())" id="selectEdit"  
multiple class="form-control sel" size="3">
<option value="">no role</option>
${rol}
</select>`;
    document.getElementById('selectorEdit').innerHTML = sel;
}
//Доступ по роли.
$(document).ready(async function () {
    let principal = await fetch("http://localhost:8080/userPrincipal").then(r => r.json());
    for (let i = 0; i < principal.roles.length; i++) {
        console.log(principal.roles[i].role);
        if (principal.roles[i].role === 'ROLE_ADMIN') {
            console.log('содержит admin')
            return;
        }
    }
    $('#usertabl').hide();
    visualTablePrincipal();
})
//Страница Юзера
async function visualTablePrincipal() {
    let principal = await fetch("http://localhost:8080/userPrincipal").then(r => r.json());
    $('#tabl_div').hide();

    let rol = "";
    principal.roles.forEach((u) => {
        rol += u.noPrefix + "  \n";
    });
    let strPr = `<tr><td>${principal.id}</td>
<td>${principal.username}</td>
<td>${principal.email}</td>
<td>${principal.age}</td>
<td>${principal.department}</td>
<td>${rol}</td></tr>`;
    let tab = `<h2>User information-page</h2> 
<div class="tab-pane fade show active border" id="user_panel" role="tabpanel" aria-labelledby="home-tab">
<div class="pl-3 pt-1 d-flex border-bottom">
<h4>About User</h4>
</div>
<table class="table table-striped table-bordered">
<thead>
<th>ID</th>
<th>Email</th>
<th>Name</th>
<th>Age</th>
<th>Department</th>
<th>Roles</th>
</thead>         
<tbody id="tbodyPrincip">${strPr}</tbody>
</table>
</div>
</div>`
    document.getElementById('us_tab').innerHTML = tab;
}

//показать принципала
$('.action').on('click', async function () {
    visualTablePrincipal();
});

$(document).ready(async function () {
    let principal = await fetch("http://localhost:8080/userPrincipal").then(r => r.json());
    let rol = "";
    principal.roles.forEach((r) => {
        rol += r.noPrefix + "  "
    });
    document.getElementById('name_navbar').innerHTML = `<b>${principal.email}</b>` + ' with roles: ' + `<b>${rol}</b>`;
})
//Создание Юзера
const usernameCreateValue = document.getElementById('usernameCreate');
const emailCreateValue = document.getElementById('emailCreate');
const ageCreateValue = document.getElementById('ageCreate');
const departmentCreateValue = document.getElementById('departmentCreate');
const passwordCreateValue = document.getElementById('passwordCreate');
$(document).ready(async function () {
    $('.btnCreate').on('click', async function (e) {
        e.preventDefault();
        let kl = document.querySelectorAll('#tbod tr');
        console.log(kl[(kl.length) - 1]);
        let u = await fetch('http://localhost:8080/userNew', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: usernameCreateValue.value,
                email: emailCreateValue.value,
                age: ageCreateValue.value,
                department: departmentCreateValue.value,
                password: passwordCreateValue.value,
                roles: $('#select').val()
            })
        }).then(f => f.json());
        usernameCreateValue.value = "";
        emailCreateValue.value = "";
        ageCreateValue.value = "";
        departmentCreateValue.value = "";
        passwordCreateValue.value = "";
        document.getElementById('select').value = "";
        document.getElementById('ad_pan').click();
        let rol = "";
        u.roles.forEach((u) => {
            rol += u.noPrefix + " \n";
        });
        row = ` <tr id="dele${u.id}"><td>${u.id}</td>

<td>${u.email}</td>
<td>${u.username}</td>
<td>${u.age}</td>
<td>${u.department}</td>
<td>${u.password}</td>
<td>${rol}</td>
<td><button class="btn btn-info editbtn">edit</button></td>
<td><button class="btn btn-danger delbtn">delete</button></td></tr>`;
        if (u.id == null) {
            alert('a user with that name is already exists\n\nПользователь существует!');
            return;
        }
        tbody.insertAdjacentHTML('beforeend', row);
    })
})

const idFormEdit = document.getElementById('idFormEdit');
const usernameFormEdit = document.getElementById('usernameFormEdit');
const emailFormEdit = document.getElementById('emailFormEdit');
const ageFormEdit = document.getElementById('ageFormEdit');
const departmentFormEdit = document.getElementById('departmentFormEdit');
const passwordFormEdit = document.getElementById('passwordFormEdit');
let row = "";

//Кнопка Изменить
$(document).ready(function () {
    $('#submitEdit').on('click', async function (e) {
        e.preventDefault();
        let id = idFormEdit.value;
        let pol = document.getElementById(`dele${id}`);
        let u = await fetch('http://localhost:8080/changeUser', {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: idFormEdit.value,
                username: usernameFormEdit.value,
                email: emailFormEdit.value,
                age: ageFormEdit.value,
                department: departmentFormEdit.value,
                password: passwordFormEdit.value,
                roles: $('#selectEdit').val()
            })
        }).then(u => u.json());
        let rol = "";
        u.roles.forEach((u) => {
            rol += u.noPrefix + " \n";
        });
        row = ` <tr id="dele${u.id}"><td>${u.id}</td>
<td>${u.username}</td>
<td>${u.email}</td>
<td>${u.age}</td>
<td>${u.department}</td>
<td>${u.password}</td>
<td>${rol}</td>
<td><button class="btn btn-info editbtn">edit</button></td>
<td><button class="btn btn-danger delbtn">delete</button></td></tr>`;
        pol.innerHTML = row;
        $('#editMod').modal('hide');
    })
});

const idValue = document.querySelector('#idForm');
//Кнопка Удалить
$(document).ready(function () {
    $('#submitDelete').on('click', async function (e) {
        e.preventDefault();
        let id = idValue.value;
        let pol = document.getElementById(`dele${id}`);
        await fetch(`http://localhost:8080/delete/${id}`, {
            method: 'DELETE'
        });
        pol.remove();
        $('#neMod').modal('hide');
    });
});

//Поиск нужного Юзера
const on = (element, even, selector, handle) => {
    element.addEventListener(even, e => {
        if (e.target.closest(selector)) {
            handle(e)
        }
    })
}
//Модальное окно Изменение
on(document, 'click', '.editbtn', (e) => {
    e.preventDefault();
    const fila = e.target.parentNode.parentNode;
    const id = fila.firstElementChild.innerHTML;
    const username = fila.children[1].innerHTML;
    const email = fila.children[2].innerHTML;
    const age = fila.children[3].innerHTML;
    const department = fila.children[4].innerHTML;
    const password = fila.children[5].innerHTML;
    const role = fila.children[6].innerHTML;
    let rolAr = role.replace(/[^A-Za-z]+/, " ").trim().split(/\s+/);
    rol(rolAr);
    $('#idFormEdit').val(id);
    $('#usernameFormEdit').val(email);
    $('#emailFormEdit').val(username);
    $('#ageFormEdit').val(age);
    $('#departmentFormEdit').val(department);
    $('#passwordFormEdit').val(password);
    $('#editMod').modal();
})
//Модальное окно Удаление
on(document, 'click', '.delbtn', (e) => {
    e.preventDefault();
    const fila = e.target.parentNode.parentNode;
    const id = fila.firstElementChild.innerHTML;
    const username = fila.children[1].innerHTML;
    const email = fila.children[2].innerHTML;
    const age = fila.children[3].innerHTML;
    const department = fila.children[4].innerHTML;
    const password = fila.children[5].innerHTML;
    const role = fila.children[6].innerHTML;
    $('#idForm').val(id);
    $('#usernameForm').val(email);
    $('#emailForm').val(username);
    $('#ageForm').val(age);
    $('#departmentForm').val(department);
    $('#passwordForm').val(password);
    $('#roleForm').val(role);
    $('#neMod').modal();
})
