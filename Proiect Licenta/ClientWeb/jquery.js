$(window).resize(function () {
    format_images()
});

function format_images() {
    let width_layer = $('#layer1').width();
    let width_window = $(window).width();
    if (width_layer > width_window * 83 / 200) {
        $('#canvasimg').addClass('canvas-img-vertical').removeClass('canvas-img-horizontal');
    } else {
        $('#canvasimg').addClass('canvas-img-horizontal').removeClass('canvas-img-vertical');
    }
}

function selectAll() {
    $(':checkbox').each(function () {
        if (this.checked === false) {
            this.click();
        }
    });
}

function unselectAll() {
    $(':checkbox').each(function () {
        if (this.checked === true) {
            this.click();
        }
    });
}

function fill_mask() {
    let canvas = document.getElementById('layer3');
    let ctx = canvas.getContext('2d');
    let canvas_2 = document.getElementById('layer1');
    canvas.width = canvas_2.width;
    canvas.height = canvas_2.height;
    $(':checkbox').each(function () {
        if (this.checked) {
            id = this.value;
            let canvas_aux = document.getElementById(id);
            ctx.drawImage(canvas_aux, 0, 0);
        }
    });
    canvas_2 = document.getElementById('layer2');
    ctx.drawImage(canvas_2, 0, 0);
}

function deleteCheckBoxes() {
    $(':checkbox').each(function () {
        $('#div_input').empty().append("<input type='text' id='filtru' placeholder='Filter masks' style='width: 100%; padding: 0;border: none;'>");
    });
    const regex = new RegExp(/mask[0-9]+/);
    $('.canvas2').each(function () {
        if (regex.test(this.id)) {
            this.remove();
        }
    });
}
