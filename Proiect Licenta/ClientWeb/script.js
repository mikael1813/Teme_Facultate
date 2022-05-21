//import io from 'socket.io-client';

var canvas, ctx, flag = false,
    prevX = 0,
    currX = 0,
    prevY = 0,
    currY = 0,
    dot_flag = false;

var masks = [], class_names, masks_id = [];

var x = "black",
    y = 20;


var socket = io('http://localhost:5000');
console.log("da")
socket.emit('a', {data: 'I\'m connected!'});
socket.on('connect', function () {
    console.log('connected');

});


socket.on('inpainted_image', (image) => {
    console.log("trying to get inpainted_image");
    let img = document.getElementById("canvasimg");
    alert(image)
    img.src = 'data:image/png;base64,' + image;
    document.getElementById('canvasimg').style.display = "block";
    format_images()
});

socket.on('ai_masks', (json) => {
    json = JSON.parse(json)
    class_names = json[0]["key0"]["class_names"];
    alert(class_names)
    let nr = json[0]["key0"]["nr_mask"];
    alert(nr)
    //let canvas1 = document.getElementById('layer1');
    //let div = document.getElementById('main_div');
    //div.removeChild(canvas1);

    let div = document.getElementById('div_input');

    for (let i = 1; i < nr + 1; i++) {
        masks.push(json[i]["key" + i]["image"]);
        //alert(json[i]["key" + i]["class_id"]);
        masks_id.push(json[i]["key" + i]["class_id"]);
        //alert(masks[i - 1])
        addCanvas(masks[i - 1], i - 1);

        let checkbox = document.createElement('input');
        checkbox.checked = true;
        checkbox.type = 'checkbox';
        checkbox.id = 'checkbox' + i;
        checkbox.value = 'mask' + (i - 1);
        checkbox.addEventListener("click", function () {
            onMaskClicked(checkbox, 'mask' + (i - 1));
        });

        var label = document.createElement('label')
        label.htmlFor = 'checkbox' + i;
        label.appendChild(document.createTextNode('Mask' + i + ': ' + class_names[masks_id[i - 1]]));

        var br = document.createElement('br');
        div.appendChild(checkbox);
        div.appendChild(label);
        div.appendChild(br);
    }
});

function onMaskClicked(cb, name) {
    console.log(cb.checked);
    console.log(name);
    if (cb.checked) {
        document.getElementById(name).style.display = "block";
    } else {
        console.log("se incearca none")
        document.getElementById(name).style.display = "none";
    }
}

function init() {

    canvas = document.getElementById('layer2');
    ctx = canvas.getContext("2d");
    w = canvas.width;
    h = canvas.height;

    canvas.addEventListener("mousemove", function (e) {
        findxy('move', e)
    }, false);
    canvas.addEventListener("mousedown", function (e) {
        findxy('down', e)
    }, false);
    canvas.addEventListener("mouseup", function (e) {
        findxy('up', e)
    }, false);
    canvas.addEventListener("mouseout", function (e) {
        findxy('out', e)
    }, false);
}

function color(obj) {
    switch (obj.id) {
        case "green":
            x = "green";
            break;
        case "blue":
            x = "blue";
            break;
        case "red":
            x = "red";
            break;
        case "yellow":
            x = "yellow";
            break;
        case "orange":
            x = "orange";
            break;
        case "black":
            x = "black";
            break;
        case "white":
            x = "white";
            break;
    }
    // if (x == "white") y = 14;
    // else y = 2;

}

function draw() {
    var scrolledX = window.scrollX;
    var scrolledY = window.scrollY;
    ctx.beginPath();
    ctx.moveTo(prevX - 10 + scrolledX, prevY - 70 + scrolledY);
    ctx.lineTo(currX - 10 + scrolledX, currY - 70 + scrolledY);
    ctx.strokeStyle = x;
    ctx.lineWidth = y;
    ctx.stroke();
    ctx.closePath();
}

function erase() {
    var m = confirm("Want to clear");
    if (m) {
        ctx.clearRect(0, 0, w, h);
        document.getElementById("canvasimg").style.display = "none";
    }
}

function save() {
    document.getElementById("canvasimg").style.border = "2px solid";
    var dataURL = canvas.toDataURL();
    document.getElementById("canvasimg").src = dataURL;
    //document.getElementById("canvasimg").style.display = "inline";
}

function findxy(res, e) {
    if (res == 'down') {
        prevX = currX;
        prevY = currY;
        currX = e.clientX - canvas.offsetLeft;
        currY = e.clientY - canvas.offsetTop;

        flag = true;
        dot_flag = true;
        if (dot_flag) {
            ctx.beginPath();
            ctx.fillStyle = x;
            ctx.fillRect(currX, currY, 2, 2);
            ctx.closePath();
            dot_flag = false;
        }
    }
    if (res == 'up' || res == "out") {
        flag = false;
    }
    if (res == 'move') {
        if (flag) {
            prevX = currX;
            prevY = currY;
            currX = e.clientX - canvas.offsetLeft;
            currY = e.clientY - canvas.offsetTop;
            draw();
        }
    }
}


let base64String = "";

function handleImage(e) {
    var canvas = document.getElementById('layer1');
    var canvas2 = document.getElementById('layer2');
    var ctx = canvas.getContext('2d');
    var reader = new FileReader();
    reader.onload = function (event) {
        base64String = reader.result.replace("data:", "")
            .replace(/^.+,/, "");
        var img = new Image();
        img.onload = function () {
            // let width = window.innerWidth/2;
            // let img_width = img.width;
            // let times = 0;
            // while (img_width > width) {
            //     img_width = img_width / 2;
            //     times += 1;
            // }
            w = img.width
            h = img.height
            canvas.width = img.width;//(Math.pow(2,times));
            canvas2.width = img.width;//(Math.pow(2,times));
            canvas.height = img.height;//(Math.pow(2,times));
            canvas2.height = img.height;//(Math.pow(2,times));
            ctx.drawImage(img, 0, 0);
            deleteCheckBoxes();
            masks = [];
            masks_id = [];
        }
        img.src = event.target.result;
    }
    reader.readAsDataURL(e.target.files[0]);
    document.getElementById('canvasimg').style.display = "none";
}


function displayString() {

    console.log("Base64String about to be printed");
    alert(base64String);

    //var mask = new Image();
    fill_mask();
    var mask = document.getElementById('layer3').toDataURL().split(';base64,')[1];

    sendImage(base64String, mask)
}

// Sending and receiving data in JSON format using POST method
//

function sendImage(b64_string, mask) {

    var xhr = new XMLHttpRequest();
    var url = "http://127.0.0.1:5000/todo/api/v1.0/tasks";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
            console.log(json.email + ", " + json.password);
        }
    };
    var data = JSON.stringify({
        'id': 3,
        'title': b64_string,
        'description': mask,
        'done': 0
    });
    window.alert(data);
    xhr.send(data);
}

function sendImageForAI(b64_string) {

    var xhr = new XMLHttpRequest();
    var url = "http://127.0.0.1:5000/todo/api/v1.0/put_masks";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
            console.log(json.email + ", " + json.password);
        }
    };
    var data = JSON.stringify({
        'id': 3,
        'title': b64_string,
        'description': '',
        'done': 0
    });
    window.alert(data);
    xhr.send(data);
}

function get() {
    var img = receiveImage();

    var image = document.getElementById("canvasimg");
    image.src = 'data:image/png;base64,' + img;
}

function receiveImage() {
    theUrl = "http://127.0.0.1:5000/todo/api/v1.0/inpainted_image"
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false); // false for synchronous request
    xmlHttp.send(null);
    var object = JSON.parse(xmlHttp.responseText);
    return object.image;
}


function setMasks() {
    sendImageForAI(base64String);
    // let json = getMasks();
    // class_names = json[0]["key0"]["class_names"];
    // let nr = json[0]["key0"]["nr_mask"];
    // for (let i = 1; i < nr + 1; i++) {
    //     masks.push(json[i]["key" + i]["image"]);
    //     //alert(json[i]["key" + i]["class_id"]);
    //     masks_id.push(json[i]["key" + i]["class_id"]);
    //     //alert(masks[i - 1])
    //     addCanvas(masks[i - 1], i - 1);
    // }

}

function addCanvas(img, id) {
    let canvas_aux = document.createElement('canvas');
    canvas_aux.width = w;
    canvas_aux.height = h;

    let div = document.getElementById('main_div');
    canvas_aux.id = 'mask' + id;
    //canvas.src = 'data:image/png;base64,' + img;
    //canvas.src = img;
    canvas_aux.classList.add('canvas2');
    div.appendChild(canvas_aux);

    let ctx_aux = canvas_aux.getContext('2d');
    //alert("Pana aici sa ajuns");
    let image = new Image();
    image.src = "data:image/png;base64," + img;
    image.onload = function () {
        ctx_aux.drawImage(image, 0, 0);
        //canvas_aux.width = image.width;
        //canvas_aux.height = image.height;

        let imageData = ctx_aux.getImageData(0, 0, w, h);
        var data = imageData.data;
        var removeBlack = function () {
            let r = Math.floor(Math.random() * 255);
            let g = Math.floor(Math.random() * 255);
            let b = Math.floor(Math.random() * 255);
            for (var i = 0; i < data.length; i += 4) {
                if (data[i] + data[i + 1] + data[i + 2] < 10) {
                    data[i + 3] = 0; // alpha
                }
                if (data[i] > 250 && data[i + 1] > 250 && data[i + 2] > 250) {
                    data[i] = r; // r
                    data[i + 1] = g; // g
                    data[i + 2] = b; // b
                }
            }
            imageData.data = data;
            ctx_aux.putImageData(imageData, 0, 0);
        };

        // function fadeOut() {
        //     ctx_aux.fillStyle = "rgba(0, 0, 0, 0.05)";
        //     ctx_aux.fillRect(0, 0, w, h);
        //     setTimeout(fadeOut, 1000);
        // }
        //
        // fadeOut();
        removeBlack();

    };


    if (id === 20) {
        //let img2 = document.getElementById('canvasimg');
        //img2.src = "data:image/png;base64," + img;
        //img2.style.display = "block";
    }
    //format_images()

}

function getMasks() {
    theUrl = "http://127.0.0.1:5000/todo/api/v1.0/masks"
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false); // false for synchronous request
    xmlHttp.send(null);
    var object = JSON.parse(xmlHttp.responseText);
    //alert(object[0]["key0"]["image"]);
    return object;
}

// function receiveImage() {
//     fetch("http://127.0.0.1:5000/todo/api/v1.0/inpainted_image").then(function (response) {
//         alert(response)
//         return response.json();
//     }).then(function (data) {
//         alert(data)
//         console.log(data);
//     }).catch(function () {
//         console.log("Booo");
//     })
// }


