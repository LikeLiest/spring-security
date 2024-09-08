function clearCookies() {
    fetch('http://localhost:8080/LinkWorld/cookie/clear', {
        method: "POST"
    })
        .then(response => {
            if (response.ok) {
                reloadPage();
            } else {
                alert('Что-то не так');
            }
        })
}

const deleteUserButtons = document.querySelectorAll('.delete-user')

deleteUserButtons.forEach(button => {
    button.addEventListener('click', (event) => {
        event.preventDefault();
        const userId = button.closest('div').querySelector('.user-id').textContent;
        console.log(userId)
        fetch(`/LinkWorld/deleteUserByIdAndClearSession/${userId}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Пользователь успешно удален');
                    toLoginPage();
                } else {
                    alert('Не удалось удалить пользователя')
                }
            })
    });
});

function logout() {
    fetch('http://localhost:8080/LinkWorld/auth/logout', {
        method: 'GET',
        credentials: 'same-origin'
    }).then(response => {
        if (response.ok) {
            clearCookies();
            reloadPage();
        } else {
            console.error('Logout failed');
        }
    }).catch(error => {
        console.error('Network error:', error);
    });
}

function reloadPage() {
    window.location.reload();
}

function toLoginPage() {
    window.location = 'http://localhost:8080/LinkWorld/auth/login';
}