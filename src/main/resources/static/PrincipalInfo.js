fillInPrincipal();

function fillInPrincipal() {
    fetch("http://localhost:8081/user/userAuth")
        .then(res => res.json())
        .then(data => {
            $('#headerUserName').append(data.login);
            let roles = data.roles.map(role => " " + role.name.substring(5));
            $('#headerRole').append(roles);
            let user = `$(
                <tr>
                    <td>${data.id}</td>
                    <td>${data.name}</td>
                    <td>${data.lastName}</td>
                    <td>${data.age}</td>   
                    <td>${data.login}</td>
                    <td>${roles}</td>
                </tr>)`;
            $('#userTable').append(user);
        })
}