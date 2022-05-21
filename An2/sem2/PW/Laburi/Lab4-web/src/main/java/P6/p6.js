Table = document.getElementById("Table").rows;
const n = 4;
$(document).ready(function (){
    var array = [], i, j;
    for (i = 0; i < n * n; i++) {
        array.push(i);
    }
    array = array.sort((a, b) => 0.5 - Math.random());

    var tr = "";
    for (i = 0; i < n; i++) {
        var td = "";
        for (j = 0; j < n; j++) {
            if (array[i * n + j] != 0) {
                td = td + "<td>" + array[i * n + j] + "</td>";
            } else {
                td = td + "<td>" + "" + "</td>";
            }
        }
        tr = tr + "<tr>" + td + "</tr>";
    }
    $("#Table").append(tr);
});


function findEmptyCell() {
    var table = [];
    $("tr").each(function () {
        var rows = [];
        $(this).children().each(function () {
            rows.push($(this).text());
        });
        table.push(rows);
    });

    for (i = 0; i < table.length; i++) {
        for (j = 0; j < table[0].length; j++) {
            if (table[i][j] == "") {
                return [i, j];
            }
        }
    }
}

function change(i, j, r, c) {
    rows = Table;
    let aux = rows[i].cells[j].innerHTML;
    rows[i].cells[j].innerHTML = rows[r].cells[c].innerHTML;
    rows[r].cells[c].innerHTML = aux;
}

$(document).on('keypress', function(e) {
    var list = findEmptyCell();
    var r = list[0], c = list[1];
    if (e.which === 119) {
        // up arrow
        if (r == 0) {
            //alert("Nu mai merge in sus");
        } else {
            change(r, c, r - 1, c);
        }
    } else if (e.which === 115) {
        // down arrow
        if (r == n) {
            //alert("Nu mai merge in jos");
        } else {
            change(r, c, r + 1, c);
        }
    } else if (e.which === 97) {
        // left arrow
        if (c == 0) {
            //alert("Nu mai merge in stanga");
        } else {
            change(r, c, r, c - 1);
        }
    } else if (e.which === 100) {
        // right arrow
        if (c == n) {
            //alert("Nu mai merge in dreapta");
        } else {
            change(r, c, r, c + 1);
        }
    }
});




