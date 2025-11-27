new DataTable("#ordersTable", {
    columnDefs: [
        { orderable: false, targets: [1, 2] }
    ],
    layout: {
        topStart: [ { search: { placeholder: 'Search orders...' } } ],
        topEnd: {
            pageLength: {
                menu: [5, 10, 25, 50],
                label: 'orders per page'
            }
        },
        bottomStart: { info: {} },
        bottomEnd: { paging: { buttons: 3 } }
    },
    language: {
        lengthMenu: "Show _MENU_ orders per page",
        info: "Showing _START_ to _END_ of _TOTAL_ orders",
        search: "",
        emptyTable: "You have no orders yet",
        zeroRecords: "No matching orders found"
    }
});