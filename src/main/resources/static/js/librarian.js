// Default table

$(document).ready(function () {

    $('#locked-action-col-table').DataTable({
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false,
            "order": []
        }
        ]
    });
});



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


// Table with scrolling instead of paging

$(document).ready(function () {
    $('#user-loan-table').DataTable({
        "columnDefs": [{
            "targets": 'no-sort',
            "orderable": false,
            "order": []
        }],
        "scrollY": "200px",
        "scrollCollapse": true,
        "paging": false
    });
});


$(function ($) {
    var window = $(window),
        menuItems = $('#navbar-collapse');

    window.resize(function resize() {
        if(window.width() < 768){
            return menuItems.removeClass('')
        }
    })
});


$(window).resize(function(){

    var windowWidth = $(window).width();
    var maxWidthForClass = 1200;
    var minWidthForClass = 992;

    if (windowWidth <= maxWidthForClass && windowWidth >= minWidthForClass){
        $("#item-image").addClass("item-image-top");
    }else {
        $("#item-image").removeClass("item-image-top");
    }


});

$(window).resize(function(){

    var windowWidth = $(window),
        element = $(".action-buttons");

    if (windowWidth.width() < 992){
        element.removeClass("btn-group");
        element.addClass("btn-group-vertical");
    }else{
        element.addClass("btn-group");
        element.removeClass("btn-group-vertical");
    }

});


// Enable showing chosen file name
$(function(){
    $(document).on('change', ':file', function(){
        var input = $(this),
            numFiles = input.get(0).files ? input.get(0).files.length : 1,
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });

    $(document).ready(function(){
        $(':file').on('fileselect', function(event, numFiles, label){
            var input = $(this).parents('.input-group').find(':text'),
                log = numFiles > 1 ? numFiles + ' files selected': label;

            if(input.length){
                input.val(log);
            }else{
                if(log)alert(log);
            }
        });
    });
});
