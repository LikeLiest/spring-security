const productForm = document.getElementById('productForm');

function getCurrentTarget(event) {
    return event.target;
}

function getDataFromTarget(target) {
    const result = {};

    for (const element of target.elements) {
        if (element.name && 'value' in element) {
            result[element.name] = element.value;
        }
    }

    return result;
}

async function tryToFetchJSONDataToActionURL(from, toJsonData) {
    try {
        const response = await fetch(from.action, {
            method: 'POST',
            headers: {
                'Content-Type': 'multipart/form-data'
            },
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
}

productForm.addEventListener('submit', async function (event) {
    event.preventDefault();

    const form = getCurrentTarget(event);
    const data = getDataFromTarget(form);

    await tryToFetchJSONDataToActionURL(form, data);
});
