const getRolesUrl = '/api/getroles';
const editUserUrl = '/api/edit';
const editForm = document.getElementById('edit-form');
const editId = document.getElementById('edit-id');
const editUsername = document.getElementById('edit-username');
const editJob = document.getElementById('edit-job');
const editPass = document.getElementById('edit-password');

async function rolesForEdit() {
    let response = await fetch(getRolesUrl);
    if (response.ok) {
        let rolesData = await response.json();
        $.each(rolesData, function (i, obj) {
            if (obj.shortName === 'ADMIN') {
                editForm.editedUserRoles.options[0] = new Option('ADMIN', JSON.stringify(obj));
                console.log(obj);
                console.log(JSON.stringify(obj))
                console.log(editForm.editedUserRoles.options[0].value);
            }
            if (obj.shortName === 'USER') {
                editForm.editedUserRoles.options[1] = new Option('USER', JSON.stringify(obj));
                console.log(obj);
                console.log(JSON.stringify(obj))
                console.log(editForm.editedUserRoles.options[1].value);
            }
        });
    }
    else {
        alert("Ошибка HTTP: " + response.status);
    }
    console.log('Отработал rolesForEdit...');
}

async function fillEditModal(userId) {
    const usersIdUrl = '/api/getuser/' + userId;
    let roles = [];
    let response = await fetch(usersIdUrl);
    if (response.ok) {
        let userData = await response.json()
            .then(x => {
                editId.value = `${x.id}`;
                editUsername.value = `${x.username}`;
                editJob.value = `${x.job}`;
                editPass.value = `${x.password}`;
                roles = x.shortRoles;
                console.log('Заполнились поля до ролей...');
            }).then(() => rolesForEdit());
        let select = editForm.editedUserRoles.getElementsByTagName('option');
        console.log('Чекаем доступ к селекту ' + select[0].textContent);
        console.log('Чекаем что лежит в roles ' + roles);
        if (roles.length === 2) {
            select[0].selected = true;
        } else {
            select[1].selected = true;
        }
    } else {
        alert("Ошибка HTTP: " + response.status)
    }
}

async function editUser() {
    let select = editForm.editedUserRoles.getElementsByTagName('option');
    let thisRoles = [];
    if (select[0].selected) {
        thisRoles.push(JSON.parse(select[0].value), JSON.parse(select[1].value));
    } else {
        thisRoles.push(JSON.parse(select[1].value));
    }

    console.log(select[1].value);
    console.log(JSON.parse(select[1].value));
    console.log(thisRoles);

    let userToEdit = {
        id: editId.value,
        username: editUsername.value,
        job: editJob.value,
        password: editPass.value,
        roles: thisRoles,
    };
    const method = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userToEdit)
    }
    let response = await fetch(editUserUrl, method);
    if (response.ok) {
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}

async function buttonEdit(){
    await editUser();
    editForm.editCloseButton.click();
}

