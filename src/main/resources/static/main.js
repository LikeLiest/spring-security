const productForm = document.getElementById('productForm');

productForm.addEventListener('submit', async function (event) {
    event.preventDefault();

    const form = event.target;
    const data = {
        name: form.name.value,
        details: form.details.value,
    };

    try {
        const response = await fetch(form.action, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('Продукт успешно создан');
        } else {
            const errorText = await response.text();
            console.log(errorText);
        }
    } catch (error) {
        console.error('Ошибка при отправке формы: ', error);
        alert('Произошла ошибка при отправке данных.');
    }
})


