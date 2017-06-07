var gameId;
var playerMove, finished;
var creatorScore, playerScore, lastPlayerResult, lastCreatorResult, minPoints, currentAccountId;
var playerId, creatorId;

var timerId;

var req;

function init() {
    gameId= $("#gameId").data('value');
    playerMove= $("#playerMoveFlag").data('value');
    minPoints= $("#minPoints").data('value');


    creatorScore = $("#creatorScore").data('value');
    finished = $("#finished").data('value');
    playerScore = $("#playerScore").data('value');
    lastPlayerResult = $("#lastPlayerResult").data('value');
    lastCreatorResult = $("#lastCreatorResult").data('value');
    currentAccountId = $("#currentAccountId").data('value');
    playerId = $("#playerId").data('value');
    creatorId = $("#creatorId").data('value');

    infoUpdate();
    timerId = setInterval(proccessInfoReq, 3000);
};

function hideAllButtons(){
    document.getElementById("playerMove").style.display = "none";
    document.getElementById("playerPass").style.display = "none";
    document.getElementById("creatorPass").style.display = "none";
    document.getElementById("creatorMove").style.display = "none";
    document.getElementById("toWaitingGame").style.display = "none";
}

function infoUpdate() {

    hideAllButtons();
    if((playerMove == true) && (currentAccountId == playerId)){
        //alert("playerMove = block, creatorMove = none");
        document.getElementById("playerMove").style.display = "block";
        if(playerScore >= minPoints){
            document.getElementById("playerPass").style.display = "block";
        }
    }
    if((playerMove == false) && (currentAccountId == creatorId)) {
        //alert("playerMove = none, creatorMove = block");
        document.getElementById("creatorMove").style.display = "block";
        if(creatorScore >= minPoints){
            document.getElementById("creatorPass").style.display = "block";
        }
    }
    //alert("check -> finished = "+ finished);
    if(finished == true) {
        //alert("finished: "+ finished);
        if(lastCreatorResult == -2){
            //alert("creatorWon");
            creatorWon();
        } else if(lastPlayerResult == -2){
           // alert("playerWon");
            playerWon();
        }else if(lastCreatorResult == -3){
            //alert("draw");
            draw();
        }
    } else {
        if(creatorScore != 0){
            //alert("creatorScore != 0");
            document.getElementById("creatorPoints").innerHTML=creatorScore+" (+"+lastCreatorResult+")";
            if(lastCreatorResult == -1){
                //alert("lastCreatorResult == -1");
                document.getElementById("creatorPoints").innerHTML="I'm pass ("+creatorScore+")";
                document.getElementById("creatorMove").style.display = "none";
                document.getElementById("creatorPass").style.display = "none";
            }
        }
        if(playerScore != 0){
            //alert("playerScore != 0");
            document.getElementById("playerPoints").innerHTML=playerScore+" (+"+lastPlayerResult+")";
            if(lastPlayerResult == -1){
                //alert("lastPlayerResult == -1");
                document.getElementById("playerPoints").innerHTML="I'm pass ("+playerScore+")";
                document.getElementById("playerMove").style.display = "none";
                document.getElementById("playerPass").style.display = "none";
            }
        }
    }
};

function showPlayAgainButton() {
    document.getElementById("toWaitingGame").style.display = "block";
}

function playerWon() {
    hideAllButtons();clearInterval(timerId);
    var player = document.getElementById("playerPoints");
    var creator = document.getElementById("creatorPoints");

    player.style.color= 'green';
    creator.style.color= 'red';

    player.innerHTML="Victory!"+" ("+playerScore+")";
    creator.innerHTML="Fail!"+" ("+creatorScore+")";

    document.getElementById("playerEndResult").innerHTML="+"+rate+" $";
    document.getElementById("creatorEndResult").innerHTML="-"+rate+" $";

    showPlayAgainButton();
}
function creatorWon() {
    hideAllButtons();clearInterval(timerId);
    var player = document.getElementById("playerPoints");
    var creator = document.getElementById("creatorPoints");

    player.style.color= 'red';
    creator.style.color= 'green';

    creator.innerHTML="Victory!"+" ("+creatorScore+")";
    player.innerHTML="Fail!"+" ("+playerScore+")";

    document.getElementById("playerEndResult").innerHTML="-"+rate+" $";
    document.getElementById("creatorEndResult").innerHTML="+"+rate+" $";

    showPlayAgainButton();
}
function draw() {
    hideAllButtons();clearInterval(timerId);
    var player = document.getElementById("playerPoints");
    var creator = document.getElementById("creatorPoints");

    creator.innerHTML="Draw"+" ("+creatorScore+")";
    player.innerHTML="Draw"+" ("+playerScore+")";
    showPlayAgainButton();

}

function step() {
    hideAllButtons();
    proccessReq(playerMove, false);
}
function pass() {
    hideAllButtons();
    proccessReq(playerMove, true);
}

function proccessReq(playerMove, pass){
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
    req.send("command=multiGameMove&playerMove="+playerMove+"&gameId="+gameId+"&pass="+pass);
}

function proccessInfoReq(){
    // Возвращает содержимое  XMLHttpRequest
    req = newXMLHttpRequest();
    // Оператор для получения сообщения обратного вызова
    // из объекта запроса
    req.onreadystatechange = checkForChange;

    // Открываем HTTP-соединение с помощью POST-метода к
    //сервлету корзины покупателя.
    // Третий параметр определяет, что запрос  асинхронный.
    req.open("POST", "/main", true);

    // Определяет, что в содержимом запроса есть данные
    req.setRequestHeader("Content-Type",
        "application/x-www-form-urlencoded");

    // Посылаем закодированные данные, говорящие о том, что я хочу добавить
    // определенный продукт в корзину.
    req.send("command=checkForChanges&gameId="+gameId);
}

function updateGame(responseXML) {
    if (responseXML == null) {
        return false;
    } else {
        finished =   responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("finished")[0].childNodes[0].nodeValue;
        playerMove = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("playerMove")[0].childNodes[0].nodeValue;
        creatorScore = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("creatorScore")[0].childNodes[0].nodeValue;
        playerScore = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("playerScore")[0].childNodes[0].nodeValue;
        lastPlayerResult = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("lastPlayerResult")[0].childNodes[0].nodeValue;
        lastCreatorResult = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("lastCreatorResult")[0].childNodes[0].nodeValue;
        rate = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("rate")[0].childNodes[0].nodeValue;

        var moneyPlayer = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("moneyPlayer")[0].childNodes[0].nodeValue;
        var moneyCreator = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("moneyCreator")[0].childNodes[0].nodeValue;

        if(currentAccountId == playerId){
            document.getElementById("MoneyAmount").innerHTML=moneyPlayer+" $";
        }
        if(currentAccountId == creatorId){
            document.getElementById("MoneyAmount").innerHTML=moneyCreator+" $";
        }

        finished = toBool(finished);
        playerMove = toBool(playerMove);
        //alert("check fromReq -> finished = "+ finished);

        infoUpdate();
    }
}
function toBool(smth) {
    if(smth == "true"){
        smth = true;
    }else if(smth == "false"){
        smth = false;
    }
    return smth;
}

function updateGameIfChanged(responseXML) {
    if (responseXML == null) {
        return false;
    } else {
        finished =   responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("finished")[0].childNodes[0].nodeValue;
        playerMove = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("playerMove")[0].childNodes[0].nodeValue;
        creatorScore = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("creatorScore")[0].childNodes[0].nodeValue;
        playerScore = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("playerScore")[0].childNodes[0].nodeValue;
        lastPlayerResult = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("lastPlayerResult")[0].childNodes[0].nodeValue;
        lastCreatorResult = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("lastCreatorResult")[0].childNodes[0].nodeValue;

        var moneyPlayer = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("moneyPlayer")[0].childNodes[0].nodeValue;
        var moneyCreator = responseXML.getElementsByTagName("MultiGame")[0].getElementsByTagName("moneyCreator")[0].childNodes[0].nodeValue;

        if(currentAccountId == playerId){
            document.getElementById("MoneyAmount").innerHTML=moneyPlayer+" $";
        }
        if(currentAccountId == creatorId){
            document.getElementById("MoneyAmount").innerHTML=moneyCreator+" $";
        }

        finished = toBool(finished);
        playerMove = toBool(playerMove);
        //alert("checkRequest -> playerMove = "+ playerMove);

        infoUpdate();
    }
}
function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            updateGame(req.responseXML);
        }
    }
}
function checkForChange() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            updateGameIfChanged(req.responseXML);
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