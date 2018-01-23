/**
 * Prepare DataTable for Item
 */
$(document).ready(function () {
    $('#main-items-list').DataTable({
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false,
            "order": []
        },
            {
                "targets": [2],
                "visible": false
            },{
                "targets": [8],
                "visible": false
            },{
                "targets": [6],
                "visible": false
            },{
                "targets": [3],
                "visible": false
            }
        ]
    });
});


/**
 * Change button grouping in table after window resize event
 */
$(window).resize(function(){

    var windowWidth = $(window).width(),
        actionBtns = $(".action-buttons");

    if (windowWidth < 1395){
        actionBtns.addClass("btn-group-vertical");
        actionBtns.removeClass("btn-group");
    }else {
        actionBtns.addClass("btn-group");
        actionBtns.removeClass("btn-group-vertical");
    }
});
