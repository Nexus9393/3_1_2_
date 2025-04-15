document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.btn-edit').forEach(button => {
        button.addEventListener('click', function () {
            const row = this.closest('tr');
            const id = row.cells[0].innerText;
            const name = row.cells[1].innerText;
            const email = row.cells[2].innerText;
            const roles = row.cells[3].innerText.split(', ').map(role => role.trim());

            document.getElementById('editId').value = id;
            document.getElementById('editName').value = name;
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
            const name = row.cells[1].innerText;
            const email = row.cells[2].innerText;
            const roles = row.cells[3].innerText;

            document.getElementById('deleteId').innerText = id;
            document.getElementById('deleteName').innerText = name;
            document.getElementById('deleteEmail').innerText = email;
            document.getElementById('deleteRoles').innerText = roles;
            document.getElementById('deleteUserId').value = id;
        });
    });
});