<table>
    <tr>
    <th>Nume</th>
    <th>Prenume</th>
    <th>Telefon</th>
    <th>E-Mail</th>
    </tr>

    <tbody id="data"></tbody>
</table>

<script>
    alert("haha")
    var ajax = new XMLHttpRequest();
    ajax.open("GET", "data.php", true);
    ajax.send();

    ajax.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            alert(this.responseText);
            var data = JSON.parse(this.responseText);
            console.log(data);

            var html = "";
            for(var a = 0; a < data.length; a++) {
                var nume = data[a].Nume;
                var prenume = data[a].Prenume;
                var telefon = data[a].Telefon;
                var mail = data[a].E-Mail;

                html += "<tr>";
                html += "<td>" + nume + "</td>";
                html += "<td>" + prenume + "</td>";
                html += "<td>" + telefon + "</td>";
                html += "<td>" + mail + "</td>";
                html += "</tr>";
            }
            document.getElementById("data").innerHTML += html;
        }
    };
</script>