async function getUser(id) {
    let url = "http://localhost:8081/admin/users/" + id;
    let response = await fetch(url);
    return await response.json();
}