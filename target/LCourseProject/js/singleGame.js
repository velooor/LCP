document.getElementById("playAgain").style.display = "none";
document.getElementById("pass").style.display = "none";

var playerResults= $("#playerRes").data('value');
var casinoResults= $("#casinoRes").data('value');
var steps= $("#steps").data('value');
var minPoints= $("#minPoints").data('value');
var rate= $("#rate").data('value');

var currentStep = 0;
var playerScore = 0;
var casinoScore = 0;
var casinoStop = false;
var end = false;

var req;

/*window.onbeforeunload = function () {
    return (end ? null : "You will lost the rate! Close?");
};*/

function playerPass() {
    if(casinoStop){
        if(playerScore > casinoScore){
            playerWon();
        } else if(playerScore < casinoScore){
            casinoWon();
        } else{
            draw();
        }
    }
    else{
        for(var i = currentStep; i < steps; i++){
            casinoScore+=casinoResults[i];
            document.getElementById("casinoPoints").innerHTML=casinoScore+" (+"+casinoResults[i]+")";
        }
        if(casinoScore > 21){
            playerWon();
        } else if(casinoScore == 21){
            casinoWon();
        }else if(playerScore > casinoScore){
            playerWon();
        } else if(playerScore < casinoScore){
            casinoWon();
        } else{
            draw();
        }
    }
};

function processStep() {
    playerScore+=playerResults[currentStep];
    if(playerScore >= minPoints){
        document.getElementById("pass").style.display = "block";
    }
    document.getElementById("playerPoints").innerHTML=playerScore+" (+"+playerResults[currentStep]+")";

    if(playerScore > 21) {
        casinoWon();
    } else if(playerScore == 21){
        playerWon();
    }


    if(!casinoStop && !end){
        casinoScore+=casinoResults[currentStep];
        document.getElementById("casinoPoints").innerHTML=casinoScore+" (+"+casinoResults[currentStep]+")";

        if(currentStep == (steps-1) && casinoScore <= 21){
            casinoStop=true;
            document.getElementById("casinoPoints").innerHTML="I'm pass"+" ("+casinoScore+")";
        } else if(casinoScore > 21) {
            playerWon();
        }
        currentStep++;
    }

}

function playerWon() {
    end = true;
    casinoStop=true;
    document.getElementById("button").style.display = "none";
    document.getElementById("pass").style.display = "none";
    document.getElementById("playAgain").style.display = "block";
    var player = document.getElementById("playerPoints");
    var casino = document.getElementById("casinoPoints");

    player.style.color= 'green';
    casino.style.color= 'red';

    player.innerHTML="Victory!"+" ("+playerScore+")";
    casino.innerHTML="Fail!"+" ("+casinoScore+")";

    document.getElementById("playerEndResult").innerHTML="+"+rate+" $";
    document.getElementById("casinoEndResult").innerHTML="-"+rate+" $";

    endOfRound(1);
}

function casinoWon() {
    end = true;
    casinoStop=true;
    document.getElementById("button").style.display = "none";
    document.getElementById("pass").style.display = "none";
    document.getElementById("playAgain").style.display = "block";
    var player = document.getElementById("playerPoints");
    var casino = document.getElementById("casinoPoints");

    player.style.color= 'red';
    casino.style.color= 'green';

    player.innerHTML="Fail!"+" ("+playerScore+")";
    casino.innerHTML="Victory!"+" ("+casinoScore+")";

    document.getElementById("playerEndResult").innerHTML="-"+rate+" $";
    document.getElementById("casinoEndResult").innerHTML="+"+rate+" $";

    endOfRound(2);
}

function draw() {
    end = true;
    casinoStop=true;
    document.getElementById("button").style.display = "none";
    document.getElementById("pass").style.display = "none";
    document.getElementById("playAgain").style.display = "block";
    var player = document.getElementById("playerPoints");
    var casino = document.getElementById("casinoPoints");

    player.style.color= 'gray';
    casino.style.color= 'gray';

    player.innerHTML="Draw:"+" ("+playerScore+")";
    casino.innerHTML="Draw:"+" ("+casinoScore+")";

    endOfRound(0);
}
function endOfRound(result) {

    // Возвращает содержимое  XMLHttpRequest
    req = newXMLHttpRequest();
    // Оператор для получения сообщения обратного вызова
    // из объекта запроса
    req.onreadystatechange = callback;

    // Открываем HTTP-соединение с помощью POST-метода к
    //сервлету корзины покупателя.
    // Третий параметр определяет, что запрос  асинхронный.
    req.open("POST", "/main", true);

    // Определяет, что в содержимом запроса есть данные
    req.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded");

    // Посылаем закодированные данные, говорящие о том, что я хочу добавить
    // определенный продукт в корзину.
    req.send("command=endOfSingleRound&result="+result+"&rate="+rate);
}

function updateRate(responseXML) {
    if (responseXML == null) {
        return false;
    } else {
        var cb = responseXML.getElementsByTagName("CreditBalance")[0];
        var money = cb.getElementsByTagName("moneyAmount")[0].childNodes[0].nodeValue;
        var elem = document.getElementById("MoneyAmount").innerHTML=money+" $";
    }
}
function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            updateRate(req.responseXML);
        }
    }
}

function newXMLHttpRequest() {
    var xmlreq = false;
    if (window.XMLHttpRequest) {
        // Создадим XMLHttpRequest объект для не-Microsoft браузеров
        xmlreq = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        // Создадим XMLHttpRequest с помощью MS ActiveX
        try {
            // Попробуем создать XMLHttpRequest для поздних версий
            // Internet Explorer
            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e1) {
            // Не удалось создать требуемый ActiveXObject
            try {
                // Пробуем вариант, который поддержат более старые версии
                //  Internet Explorer
                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
                // Не в состоянии создать XMLHttpRequest с помощью ActiveX
            }
        }
    }
    return xmlreq;
}