async function openAndFillInTheModal(form, modal, id){
    modal.show();
    let user = await getUser(id);
    form.id.value = user.id;
    form.name.value = user.name;
    form.lastName.value = user.lastName;
    form.age.value = user.age;
    form.login.value = user.login;
    form.roles.value = user.roles[0].id;
}