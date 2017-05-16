/**
 * Created by ZheLin on 5/5/2017.
 */
$(document).ready(function() {
    var url ="/tableFilter";

    $("#langFilter").change(function(event){
        var lang = $("#langFilter").prop("value");
        $("#tbody1").load(url,{langFilter:lang});
        //alert("hey");
    });

});