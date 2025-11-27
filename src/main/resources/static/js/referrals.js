new DataTable("#referralsTable", {
    layout: {
        topStart: [ { search: { placeholder: 'Search referrals...' } } ],
        topEnd: {
            pageLength: {
                menu: [5, 10, 25, 50]
            }
        },
        bottomStart: { info: {} },
        bottomEnd: { paging: { buttons: 3 } }
    },
    language: {
        lengthMenu: "Show _MENU_ referrals per page",
        info: "Showing _START_ to _END_ of _TOTAL_ referrals",
        search: "",
        emptyTable: "You have no referrals yet",
        zeroRecords: "No matching referrals found"
    }
});