document.querySelectorAll('.btn-edit').forEach(button => {
    button.addEventListener('click', function () {
        const row = this.closest('tr');
        const id = row.cells[0].innerText;
        const firstName = row.cells[1].innerText;
        const lastName = row.cells[2].innerText;
        const age = row.cells[3].innerText;
        const email = row.cells[4].innerText;
        const roles = row.cells[5].innerText.split(', ').map(role => role.trim());

        console.log('Edit button clicked:', { id, firstName, lastName, age, email, roles });

        document.getElementById('editId').value = id;
        document.getElementById('editFirstName').value = firstName;
        document.getElementById('editLastName').value = lastName;
        document.getElementById('editAge').value = age === 'N/A' ? '' : age;
        document.getElementById('editEmail').value = email;
        const roleSelect = document.getElementById('editRoles');
        Array.from(roleSelect.options).forEach(option => {
            option.selected = roles.includes(option.text);
        });
    });
});

document.querySelectorAll('.btn-delete').forEach(button => {
    button.addEventListener('click', function () {
        const row = this.closest('tr');
        const id = row.cells[0].innerText;
        const firstName = row.cells[1].innerText;
        const lastName = row.cells[2].innerText;
        const email = row.cells[4].innerText;
        const roles = row.cells[5].innerText;

        document.getElementById('deleteId').innerText = id;
        document.getElementById('deleteName').innerText = firstName + ' ' + lastName;
        document.getElementById('deleteEmail').innerText = email;
        document.getElementById('deleteRoles').innerText = roles;
        document.getElementById('deleteUserId').value = id;
    });
});