function editPost(id) {
    var commentValue = $('#comment'+id).text();
    $(".function").hide();
    $('#comment'+id).remove();
    $('<textarea  id="comment"   rows="5" placeholder="Enter text" onfocus="set_size(this)" >' +
        '</textarea>').appendTo('#main'+id);
    $('#comment').text(commentValue);

    $('<a class="a" href=""><input type="button" id="save" value="Save" onclick="editPostSave('+id+')">' +
        '</input></a>').appendTo('#main'+id);
    $('<input type="button" id="cancel" value="Cancel" onclick="editPostCancel('+id+')">' +
        '</input>').appendTo('#main'+id);

}
function editPostCancel(id) {
    var commentValue = $("#comment").text();
    $("#comment").remove();
    $("#save").remove();
    $("#cancel").remove();
    $('<div id="comment'+id+'" class="comment"</div>').appendTo('#main'+id);
    $("#comment"+id).text(commentValue);
    $(".function").show();
}

function editPostSave(id) {
        $(".a").attr("href", "/post/delete?id="+id+"&comment="+$('#comment').val()+"");
}