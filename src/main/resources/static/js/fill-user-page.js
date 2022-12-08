const urluser = '/api/user'

async function getUserPage() {
    let response = await fetch(urluser);
    if (response.ok) {
        let userData = await response.json();
        fillUserTableBody(userData);
    } else {
        alert("Ошибка HTTP: " + response.status)
    }
}

function fillUserTableBody(userData) {
    let tableRow = document.createElement("tr");
    let ShortRoles = [];
    for (const role of userData.roles) {
        ShortRoles.push(" " + role.name
            .toString()
            .replace('ROLE_', '')
            .toString())
    }
    let StringRoles = ShortRoles.sort().toString().replace(',', ' ');

    tableRow.innerHTML =
        `<tr>
                    <td class="text-center"> ${userData.id} </td>
                    <td class="text-center"> ${userData.username} </td>
                    <td class="text-center"> ${userData.password} </td>
                    <td class="text-center"> ${StringRoles} </td>
                    <td class="text-center"> ${userData.job} </td>
                    </tr>`
    document.getElementById(`tbody`)
        .append(tableRow);
}

getUserPage();