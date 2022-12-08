const addRolesUrl = '/api/getroles';
async function rolesForAdd() {
    let response = await fetch(addRolesUrl);
    if (response.ok) {
        let newRolesData = await response.json();
        $.each(newRolesData, function (i, obj) {
            if (obj.shortName === 'ADMIN') {
                addForm.addRoles.options[0] = new Option('ADMIN', JSON.stringify(obj));
            }
            if (obj.shortName === 'USER') {
                addForm.addRoles.options[1] = new Option('USER', JSON.stringify(obj));
            }
        });
    }
    else {
        alert("Ошибка HTTP: " + response.status);
    }
}
rolesForAdd();