$(document).ready(function () {
    $('#user-newloan-table').DataTable({
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false,
            "order": []
        },
            {
                "targets": [4],
                "visible": false,
                "searchable": false
            },{
                "targets": [5],
                "visible": false,
                "searchable": false
            },{
                "targets": [6],
                "visible": false,
                "searchable": false
            },{
                "targets": [7],
                "visible": false,
                "searchable": false
            }
        ]
    });
});


$(document).ready(function(){

    var tableData = $("#user-newloan-table").DataTable();

    $("#user-newloan-table tbody").on('click', 'tr', function () {
        var data = tableData.row(this).data();
        $(".item-id-field").text(data[0]);
        $("#item-author").val(data[2]);
    })

});