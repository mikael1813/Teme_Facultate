const n = 4;
var array = [], i, j, table = document.getElementById("Table");
for (i = 0; i < n * n; i++) {
    array.push(i);
}
array = array.sort((a, b) => 0.5 - Math.random());

for (i = 0; i < n; i++) {
    var row = table.insertRow(i);
    for (j = 0; j < n; j++) {
        var cell = row.insertCell(j);
        if (array[i * n + j] != 0) {
            cell.innerHTML = array[i * n + j];
        } else {
            cell.innerHTML = ""
        }
    }
}

function findEmptyCell() {
    rows = table.rows;
    for (i = 0; i < rows.length; i++) {
        for (j = 0; j < rows[i].cells.length; j++) {
            if (rows[i].cells[j].innerHTML == "") {
                return [i, j];
            }
        }
    }
}

function change(i, j, r, c) {
    rows = table.rows;
    let aux = rows[i].cells[j].innerHTML;
    rows[i].cells[j].innerHTML = rows[r].cells[c].innerHTML;
    rows[r].cells[c].innerHTML = aux;
}

function checkKey(e) {
    e = e || window.event;

    var list = findEmptyCell();
    var r = list[0], c = list[1];

    if (e.keyCode == '38') {
        // up arrow
        if (r == 0) {
            //alert("Nu mai merge in sus");
        } else {
            change(r, c, r - 1, c);
        }
    } else if (e.keyCode == '40') {
        // down arrow
        if (r == n) {
            //alert("Nu mai merge in jos");
        } else {
            change(r, c, r + 1, c);
        }
    } else if (e.keyCode == '37') {
        // left arrow
        if (c == 0) {
            //alert("Nu mai merge in stanga");
        } else {
            change(r, c, r, c - 1);
        }
    } else if (e.keyCode == '39') {
        // right arrow
        if (c == n) {
            //alert("Nu mai merge in dreapta");
        } else {
            change(r, c, r, c + 1);
        }
    }

}

