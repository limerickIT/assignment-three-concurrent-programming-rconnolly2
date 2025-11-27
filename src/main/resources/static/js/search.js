// I add an event listener to all checkboxes to submit from if clicked
document.querySelectorAll('#filterForm input[type="checkbox"]').forEach(cb =>
    cb.addEventListener('change', () =>
        document.getElementById('filterForm').submit()
    )
)

new DataTable("#productsTable", {
    columnDefs: [
        { orderable: false, targets: [0, 6] }
    ],
    layout: {
        topStart: {},
        topEnd: { pageLength: { menu: [5, 10, 25, 50] } },
        bottomStart: { info: {} },
        bottomEnd: { paging: { buttons: 3 } }
    },
    language: {
        lengthMenu: "Show _MENU_ products per page",
        info: "Showing _START_ to _END_ of _TOTAL_ products",
        emptyTable: "You have no orders yet",
        infoEmpty: "No products found",
    }
})