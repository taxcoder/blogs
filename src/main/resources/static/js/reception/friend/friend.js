let friend;
$(function (){
    friend = $(".friend");
    //第一次时，进行窗口的变化
    friend.css({"height":document.body.scrollHeight-90,"min-height":$(window).height() - 138});
    if(document.body.scrollHeight > $(window).height()){
        friend.css("margin-bottom","100px");
    }
});