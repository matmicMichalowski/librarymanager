//
// $(".form-checkemail").submit(function(event){
//
//     var correctEmail = true;
//
//     $.ajax({
//         url: "/reset-request",
//         type: "POST",
//         async: false,
//         data: {id : $("#email").val()},
//         correctEmail: function (error) {
//             if (error){
//                 $("#email-alert").show();
//                 $("#email-error").html(error);
//                 correctEmail = false;
//             }
//         }
//     });
//     return correctEmail;
// });

$(document).ready(function(){
    var text = $("#email-error").text();

    if(text.length > 0){
        $("#error-container").addClass('showMsg');
    }
});

$(document).ready(function(){
    var text = $("#password-error").text();

    if(text.length > 0){
        $("#password-alert").addClass('showMsg');
    }
});

$(document).ready(function(){
    var text = $("#reset-confirm").text();

    if(text.length > 0){
        $("#reset-mail-confiramtion").addClass('showInfoMsg');
    }
});


$(document).ready(function(){
    $("#email-change").focus(function(){
            $("#update-mail-container").addClass('showInfoMsg');
            $("#update-msg").text('After changing your email address, you will be logged out. ' +
                'Thereafter you will be able to log in with new username.');

    });
});

$(document).ready(function(){
    var msg = $("#error-msg").text();

        if (msg.length > 0){
            $("#delete-error").addClass('showMsg');
        }
});



