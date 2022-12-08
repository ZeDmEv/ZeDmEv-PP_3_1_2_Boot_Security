const deleteUserUrl = '/api/delete';
const deleteForm = document.getElementById('delete-form');
const deleteId = document.getElementById('delete-id');
const deleteUsername = document.getElementById('delete-username');
const deleteJob = document.getElementById('delete-job');
const deletePass = document.getElementById('delete-password');

async function rolesForDelete() {
    let response = await fetch(getRolesUrl);
    if (response.ok) {
        let rolesData = await response.json();
        $.each(rolesData, function (i, obj) {
            if (obj.shortName === 'ADMIN') {
                deleteForm.deleteUserRoles.options[0] = new Option('ADMIN', JSON.stringify(obj));
            }
            if (obj.shortName === 'USER') {
                deleteForm.deleteUserRoles.options[1] = new Option('USER', JSON.stringify(obj));
            }
        });
    }
    else {
        alert("Ошибка HTTP: " + response.status);
    }
    console.log('Отработал rolesForEdit...');
}

async function fillDeleteModal(userId) {
    console.log('Запуск скрипта fillDeleteModal...')
    const deleteUserIdUrl = '/api/getuser/' + userId;
    let roles = [];
    let response = await fetch(deleteUserIdUrl);
    if (response.ok) {
        let deleteUserData = await response.json()
            .then(x => {
                deleteId.value = `${x.id}`;
                deleteUsername.value = `${x.username}`;
                deleteJob.value = `${x.job}`;
                deletePass.value = `${x.password}`;
                roles = x.shortRoles;
                console.log('Заполнились поля до ролей...');
            }).then(() => rolesForDelete());
        let select = deleteForm.deleteUserRoles.getElementsByTagName('option');
        if (roles.length === 2) {
            select[0].selected = true;
        } else {
            select[1].selected = true;
        }
        /*deleteForm.deleteButton.addEventListener('submit', function (evt){
            evt.preventDefault();
            deleteUser();
        })*/
    } else {
        alert("Ошибка HTTP: " + response.status)
    }
}
async function deleteUser() {
    let select = deleteForm.deleteUserRoles.getElementsByTagName('option');
    thisRoles = [];
    if (select[0].selected) {
        thisRoles.push(JSON.parse(select[0].value), JSON.parse(select[1].value));
    } else {
        thisRoles.push(JSON.parse(select[1].value));
    }

    let userToDelete = {
        id: deleteId.value,
        username: deleteUsername.value,
        job: deleteJob.value,
        password: deletePass.value,
        roles: thisRoles,
    };
    const method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userToDelete)
    }
    let response = await fetch(deleteUserUrl, method);
    if (response.ok) {
    } else {
        alert("Ошибка HTTP: " + response.status);
    }
}
async function delButton() {
    await deleteUser();
    deleteForm.closeDeleteButton.click();
}