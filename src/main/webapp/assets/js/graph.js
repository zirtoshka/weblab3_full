import {showToast} from './utils.js';

const canvas = document.getElementById('coordinateCanvas');
const centerX = canvas.width / 2;
const centerY = canvas.height / 2;
const pxR = 50;
const ctx = canvas.getContext('2d');
var rValue = null;
export function updateR(r) {
    console.log("sdfmsdflklkferklfkerfepkfrfkref");
    rValue = r;
    drawGraph();
}


document.getElementById('user-request:slider').onSlideEnd = (() => {
    console.log("suka")
    updateR(document.getElementById('user-request:r').value);
    drawPointsFromTable();
})

function updateRValue(newValue) {
    rValue = newValue;
    console.log('Updated rValue:', rValue);
}
document.addEventListener('DOMContentLoaded', function () {
    drawGraph();
    let storedPoints = sessionStorage.getItem('points');
    console.log(storedPoints);
    if (storedPoints) {
        console.log("lolik");
        //points = JSON.parse(storedPoints);
        drawPoints();
    } else {
        sessionStorage.setItem('points', JSON.stringify([]));
        console.log("bolik");
    }
});


canvas.addEventListener('click', function (event) {
    console.log(rValue);
        if (rValue == null) showToast("Can't find coordinates, please choose value for r")
        else {
            let loc = windowToCanvas(canvas, event.clientX, event.clientY);
            let r = rValue;
            let x = xFromCanvas(loc.x);
            let y = yFromCanvas(loc.y);
           // updateTextInputs(x,y);
            let storagePoints = JSON.parse(sessionStorage.getItem('points'));

            storagePoints.push(JSON.stringify({"x": x, "y": y}));

            sessionStorage.setItem('points', JSON.stringify(storagePoints));


            console.log(x);
            console.log(y);
            console.log(r);

            //вызываем ремут комманд, передаем данные для точки
            addDotFromCanvas(
                [
                    {name: "x", value: x.toString()},
                    {name: "y", value: y.toString()},
                    {name: "r", value: r.toString()}
                ]
            )
            drawPointsFromTable();

        }
    }
);

export function drawPoints() {
    let points = JSON.parse(sessionStorage.getItem('points'));
    for (const pointString of points) {
        const point = JSON.parse(pointString);
        console.log(point.x);
        drawPoint(point.x, point.y, true);
    }
}

function   updateTextInputs(x,y){
    document.getElementById("x").setAttribute("value", x);
    document.getElementById("y").setAttribute("value", y);

}

//todo wtf blin
function clearTable() {
    sessionStorage.setItem('points', JSON.stringify([]));

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    drawGraph();

    let table = document.getElementById("results-table");
    let rowCount = table.rows.length;
    for (let i = rowCount - 1; i > 0; i--) {
        table.deleteRow(i);
    }

}


function xFromCanvas(x) {
    return (x - centerX) / pxR;
}

function yFromCanvas(y) {
    return (centerY - y) / pxR;
}

function xToCanvas(x) {
    return (x * pxR) + centerX;
}

function yToCanvas(y) {
    return centerY - (y * pxR);
}

function windowToCanvas(canvas, x, y) {
    let bbox = canvas.getBoundingClientRect();
    return {
        x: x - bbox.left * (canvas.width / bbox.width),
        y: y - bbox.top * (canvas.height / bbox.height)
    };
}


export function drawGraph() {
    const axisLength = 500;
    const r = pxR * rValue;

    ctx.clearRect(0, 0, canvas.width, canvas.height);
    shadeRegion(ctx, centerX, centerY, r);

    // Draw X-axis
    drawArrow(ctx, -centerX, centerY, canvas.width, centerY);
    drawTicks(ctx, centerX, centerY, axisLength, 'x');
    drawAxisLabel(ctx, 'X', canvas.width - 10, centerY + 20);

    // Draw Y-axis
    drawArrow(ctx, centerX, centerY * 2, centerX, 0);
    drawTicks(ctx, centerX, centerY, axisLength, 'y');
    drawAxisLabel(ctx, 'Y', centerX - 20, 10);

    if(rValue){
    drawAxisLabel(ctx, 'R', centerX + r, centerY - 5);
    drawAxisLabel(ctx, 'R/2', centerX + r / 2, centerY - 5);
    drawAxisLabel(ctx, 'R', centerX + 5, centerY - r);
    drawAxisLabel(ctx, 'R/2', centerX + 5, centerY - r / 2);
    drawAxisLabel(ctx, '- R', centerX - r, centerY - 5);
    drawAxisLabel(ctx, '- R/2', centerX - r / 2, centerY - 5);
    drawAxisLabel(ctx, '- R', centerX + 5, centerY + r);
    drawAxisLabel(ctx, '- R/2', centerX + 5, centerY + r / 2);}


    // points


}

export function drawPoint(x, y, res) {

    x = xToCanvas(x);
    y = yToCanvas(y);
    ctx.beginPath();
    if (res) {
        ctx.fillStyle = 'rgb(6,246,23)';

    } else {
        ctx.fillStyle = 'rgb(17,3,3)';
    }
    ctx.moveTo(x, y);
    // ctx.fillRect(x, y, 3, 3);
    ctx.arc(x, y, 1.8, 0, Math.PI * 2);
    ctx.fill();
    ctx.stroke();
    ctx.fillStyle = 'rgba(245,87,245,0.73)';

}



function drawPointsFromTable() {
    var table = document.getElementById('results-table'); // Получаем таблицу по её id
    var rows = table.getElementsByTagName('tr'); // Получаем все строки таблицы

    // Проходимся по каждой строке таблицы, начиная с 1, чтобы пропустить заголовок
    for (var i = 1; i < rows.length; i++) {
        var cells = rows[i].getElementsByTagName('td'); // Получаем ячейки текущей строки
        var x = cells[0].innerText; // Значение X в первой ячейке
        var y = cells[1].innerText; // Значение Y во второй ячейке
        var r = cells[3].innerText; // Значение R
        var result = cells[4].innerText; // Значение Result в пятой ячейке

        // Обработка значений x, y, result
        if (r===rValue){
            console.log("sdfsdf");
        drawPoint(x, y, result=="kill");}
    }
}

export function drawArrow(context, fromX, fromY, toX, toY) {
    context.beginPath();
    context.moveTo(fromX, fromY);
    context.lineTo(toX, toY);
    context.stroke();

    // Draw arrowhead
    const headLength = 7;
    const angle = Math.atan2(toY - fromY, toX - fromX);
    context.lineTo(toX - headLength * Math.cos(angle - Math.PI / 6), toY - headLength * Math.sin(angle - Math.PI / 6));
    context.moveTo(toX, toY);
    context.lineTo(toX - headLength * Math.cos(angle + Math.PI / 6), toY - headLength * Math.sin(angle + Math.PI / 6));
    context.stroke();
}

function drawTicks(context, centerX, centerY, length, axis) {
    const numTicks = 11;
    const tickSpacing = length / (numTicks - 1);
    const tickSize = 5;
    context.beginPath();

    for (let i = 0; i < numTicks; i++) {
        const tickPosition = i * tickSpacing - length / 2;
        if (axis === 'x') {
            context.moveTo(centerX + tickPosition, centerY - tickSize / 2);
            context.lineTo(centerX + tickPosition, centerY + tickSize / 2);
        } else {
            context.moveTo(centerX - tickSize / 2, centerY + tickPosition);
            context.lineTo(centerX + tickSize / 2, centerY + tickPosition);
        }
    }

    context.stroke();
}

function drawAxisLabel(context, label, x, y) {
    context.fillStyle = 'rgba(0, 0, 0, 1)'; // Цвет и прозрачность заливки

    context.font = '14px Arial';
    context.fillText(label, x, y);
}

function shadeRegion(context, centerX, centerY, r) {
    context.fillStyle = 'rgba(255, 0, 255, 1)'; // Цвет и прозрачность заливки

    //part of circle
    context.beginPath();
    context.arc(centerX, centerY, r / 2, 0, Math.PI / 2, false);
    context.lineTo(centerX, centerY);
    context.closePath();
    context.fill();

    //squart
    context.beginPath();
    context.rect(centerX, centerY, r / 2, -r);
    context.fill();

    //tringale
    context.beginPath();
    context.moveTo(centerX, centerY);
    context.lineTo(centerX - r / 2, centerY);
    context.lineTo(centerX, centerY - r / 2);
    context.lineTo(centerX, centerY);
    context.fill();

}