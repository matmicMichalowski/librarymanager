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
            },{
                "targets": [8],
                "visible": false,
                "searchable": false
            }
        ]
    });
});



$(document).ready(function(){

    var tableData = $("#user-newloan-table").DataTable();
    var userId = $("#user-id").text();


    $("#user-newloan-table tbody").on('click', 'tr', function () {
        var data = tableData.row(this).data();
        $(".user-id-field").val(userId);
        $(".item-id-field").val(data[0]);
        $("#item-author").val(data[3]);
        $("#item-title").val(data[2]);
        $("#item-type").val(data[1]);
        $("#item-distribution").val(data[5]);
        $("#item-genre").val(data[4]);
        $("#item-year").val(data[7]);
        $("#item-publisher").val(data[6]);
        $("#item-isn").val(data[8]);



    })

});