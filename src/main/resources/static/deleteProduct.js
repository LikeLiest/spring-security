document.getElementById('deleteProduct').addEventListener('click', async function (event) {
    event.preventDefault();

    const productId = this.getAttribute('data-product-id');

    try {
        const response = await fetch(`/catalog/products/delete/${productId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('Продукт успешно удален');
            window.location.href = '/catalog/products/list';
        } else {
            const errorText = await response.text();
            console.log(errorText);
        }
    } catch (error) {
        console.error('Ошибка при удалении продукта: ', error);
        alert('Произошла ошибка при удалении продукта.');
    }
});
