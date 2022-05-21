var data

$(document).ready(function () {
    $.ajax({
        url: "orase.txt", success: function (result) {
            //result = replaceAll(result, "(", "");
            //result = replaceAll(result, ")", "");
            result = result.split(/[ (),]+/);
            //alert(result[8]);
            data = result;
        }
    });
    $("#plecare li").dblclick(function () {
        let i;
        $("#sosire").empty();
        for (i = 0; (i < data.length); i = i + 3) {
            //alert(data[i]);
            if (data[i + 1] == $(this).text()) {
                var str = '<li>' + data[i + 2] + '</li>';
                $("#sosire").append(str);
            }
        }
    });
});


function replaceAll(str, find, replace) {
    var escapedFind = find.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "\\$1");
    return str.replace(new RegExp(escapedFind, 'g'), replace);
}


