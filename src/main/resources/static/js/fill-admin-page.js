const urladmin = '/api/admin'

async function getAdminPage() {
    let response = await fetch(urladmin);
    if (response.ok) {
        console.log('responce of getAdminPage OK')
        let usersData = await response.json();
        fillAdminTableBody(usersData);
    } else {
        alert("Ошибка HTTP: " + response.status)
    }
}

function fillAdminTableBody(usersData) {
    $("#adminbody").empty();
    for (const userData of usersData) {
        let tableRow = document.createElement("tr");
        let ShortRolesAdminPage = [];
        for (const role of userData.roles) {
            ShortRolesAdminPage.push(" " + role.name.toString()
                .replace('ROLE_', '')
                .toString())
        }

        let StringRoles = ShortRolesAdminPage
            .sort()
            .toString()
            .replace(',', ' ');

        tableRow.innerHTML =
            `<tr>
                    <td class="text-center"> ${userData.id} </td>
                    <td class="text-center"> ${userData.username} </td>
                    <td class="text-center"> ${userData.password} </td>
                    <td class="text-center"> ${StringRoles} </td>
                    <td class="text-center"> ${userData.job} </td>
                    <td class="text-center">
                        <button class="btn btn-info"
                            data-target="#modal-edit"
                            data-toggle="modal"
                            type="submit"
                            onclick="fillEditModal(${userData.id})">Edit
                        </button>
                    </td>
                    <td class="text-center">
                    <button class="btn btn-danger"
                            data-target="#modal-delete"
                            data-toggle="modal"
                            type="submit"
                            onclick="fillDeleteModal(${userData.id})">Delete
                        </button>
                        </td>
                    </tr>`
        document.getElementById(`adminbody`)
            .append(tableRow);
    }
}

getAdminPage();