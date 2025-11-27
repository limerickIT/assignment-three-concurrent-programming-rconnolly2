const dt = new DataTable("#wishlistTable", {
    columnDefs: [
        { width: "16rem", targets: 2 },
        { orderable: false, targets: "no-sort" }
    ],
    layout: {
        topStart: [
            {
                search: {
                    placeholder: 'Product name, date...'
                }
            }
        ],
        topEnd: {
            pageLength: {
                menu: [5, 10, 25, 50]
            }
        },
        bottomEnd: {
            paging: {
                buttons: 3
            }
        }
    },
    language: {
        lengthMenu: "Show _MENU_ wishes per page",
        info: "Showing _START_ to _END_ of _TOTAL_ wishes",
        infoEmpty: "Showing 0 wishes",
        infoFiltered: "(filtered from _MAX_ total wishes)",
        zeroRecords: "No matching wishes found",
        search: "Search:",
        paginate: {
            previous: "Prev",
            next: "Next"
        }
    }
});