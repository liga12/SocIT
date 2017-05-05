$(function(){
    $("input[type='submit']").click(function(){
        var $fileUpload = $("input[type='file']");
        if (parseInt($fileUpload.get(0).files.length)>2){
            alert("You can only upload a maximum of 10 files");
                $('#send').attr('action', ' ');
        }
    });
});