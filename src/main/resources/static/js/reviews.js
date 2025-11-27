const dt = new DataTable("#reviewsTable", {
    columnDefs: [
        { orderable: false, targets: "no-sort" }
    ],
    layout: {
        topStart: [
            {
                search: {
                    placeholder: 'Search reviews...'
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
        lengthMenu: "Show _MENU_ reviews per page",
        info: "Showing _START_ to _END_ of _TOTAL_ reviews",
        infoEmpty: "No reviews found",
        infoFiltered: "(filtered from _MAX_ total reviews)",
        zeroRecords: "No matching review found",
        paginate: {
            previous: "Prev",
            next: "Next"
        }
    }
});