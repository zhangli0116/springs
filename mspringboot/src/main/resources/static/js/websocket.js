var stompClient = null;

/**
 * 建立连接并订阅消息
 * @returns {*}
 */
function connect(){
    var subscription;
    console.log('connect...');
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({},function (message) {
        if(message.body){
            //console.log("got message with body " + message.body);
        }else {
            console.log("get empty message");
        }
        //订阅一个目的地
        //STOMP消息的body必须为字符串
        subscription = stompClient.subscribe('/topic/greetings',function(msg){
            console.log("接受到java后台发送的消息，" + msg.body);
        });
        
    })

    return subscription;
}

/**
 * 中止订阅消息
 */
function unsubscription(subscription){
    if(subscription){
        subscription.unsubscribe();
    }
}

/**
 * 断开连接
 */
function  disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendName(){
    //STOMP消息的body必须为字符串
    stompClient.send('/app/hello',{},JSON.stringify({'username':$("#name").val()}));
    //直接发送数据
    //stompClient.send('/topic/greeting',{},JSON.stringify({'username':$("#name").val()}));
}

$(function(){
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });

    $('#getUser').on('click',function () {
      $.post('/getSocketCacheUser',function (data) {

      })
    })
})