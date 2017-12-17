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
    var text = $("#reset-confirm").text();

    if(text.length > 0){
        $("#reset-mail-confiramtion").addClass('showInfoMsg');
    }
});

