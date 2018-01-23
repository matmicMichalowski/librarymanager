/**
 * Check if server return error message, and add CSS class if error exist
 */
$(document).ready(function(){
    var text = $("#email-error").text();

    if(text.length > 0){
        $("#error-container").addClass('showMsg');
    }
});

/**
 * Check if server return error message, and add CSS class if error exist
 */
$(document).ready(function(){
    var text = $("#password-error").text();

    if(text.length > 0){
        $("#password-alert").addClass('showMsg');
    }
});

/**
 * Show confirmation message on reset password request
 */
$(document).ready(function(){
    var text = $("#reset-confirm").text();

    if(text.length > 0){
        $("#reset-mail-confiramtion").addClass('showInfoMsg');
    }
});

/**
 * Show information when employee email is being changed
 */
$(document).ready(function(){
    $("#email-change").focus(function(){
            $("#update-mail-container").addClass('showInfoMsg');
            $("#update-msg").text('After changing your email address, you will be logged out. ' +
                'Thereafter you will be able to log in with new username.');

    });
});

/**
 * Check if server return error message, and add CSS class if error exist
 */
$(document).ready(function(){
    var msg = $("#error-msg").text();

        if (msg.length > 0){
            $("#delete-error").addClass('showMsg');
        }
});



