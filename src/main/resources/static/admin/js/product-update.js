function removeSizeItem(button) {
    $(button).closest('.size-item').remove();
    reindexSizes();
}

function reindexSizes() {
    $('#sizes-container .size-item').each(function(index) {
        $(this).find('input, select, textarea').each(function() {
            const name = $(this).attr('name');
            if (name) {
                const newName = name.replace(/sizesDtos\[\d+\]/, `sizesDtos[${index}]`);
                $(this).attr('name', newName);
            }
        });
    });
}

$(document).ready(function () {
    $('#add-size-btn').off('click').on('click', function () {
        const sizeCount = $('#sizes-container .size-item').length;
        const newSizeHtml = `
            <div class="size-item">
                <input type="hidden" name="sizesDtos[${sizeCount}].id" value="">
                <input type="text" name="sizesDtos[${sizeCount}].size" placeholder="Size name"
                       class="form-control" style="width: 30%;" required>
                <input type="number" name="sizesDtos[${sizeCount}].quantity" placeholder="Quantity"
                       class="form-control" min="0" style="width: 30%;" required>
                <button type="button" class="btn btn-danger remove-size-btn"
                        style="margin-left: 10px;" onclick="removeSizeItem(this)">Remove</button>
            </div>
        `;
        $('#sizes-container').append(newSizeHtml);
    });
});
