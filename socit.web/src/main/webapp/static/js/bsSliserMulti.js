$(document).ready(function () {
    var myArray = $('.bxslider');
    $.each(myArray, function () {
        $('#' + $(this).attr('id')).bxSlider({});
    });
});
