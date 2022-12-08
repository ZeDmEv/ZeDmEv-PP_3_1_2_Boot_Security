const addUserUrl = '/api/add';
const addForm = document.getElementById('add-form');
const addUsername = document.getElementById('add-username');
const addJob = document.getElementById('add-job');
const addPass = document.getElementById('add-password');

async function addUser() {
    let select = addForm.addRoles.getElementsByTagName('option');
    let addedRoles = [];
    if (select[0].selected) {
        addedRoles.push(JSON.parse(select[0].value), JSON.parse(select[1].value));
    } else {
        addedRoles.push(JSON.parse(select[1].value));
    }

    let userToAdd = {
        username: addUsername.value,
        job: addJob.value,
        password: editPass.value,
        roles: addedRoles,
    };
    const method = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userToAdd)
    }
    let response = await fetch(editUserUrl, method);
    if (response.ok) {
        console.log('response of add-user request OK')
        $('#add-form').trigger("reset");
        setTimeout(() => (getAdminPage()), 100);
        $(`.nav-tabs a[href="#UsersTable"]`).tab("show");
    } else {
        alert("Ошибка HTTP: " + response.status);
    }

}