let time = 0;
let msg;
let email;
let loginName;
let data;

function forgetClose() {
$(".user").css("display","none");
$(".forgetPwd").css("display","none");
}

function updateClose() {
    $(".user").css("display","none");
    $(".updatePwd").css("display","none");
}


function login(){
    $(".user").css({"display":"flex","justify-content":"center","align-items":"center"});
    $(".user .login-register").css("display","block");
    $(".login-input").css("display","block");
    $(".register-input").css("display","none");
}

function PageClose(){
    $(".user").css("display","none");
    $(".user .login-register").css("display","none");
}

function UserRegister() {
    $(".user .login-register .login-input").css("display","none");
    $(".user .login-register .register-input").css("display","block");
}

function goLogin(){
    $(".user .login-register .login-input").css("display","block");
    $(".user .login-register .register-input").css("display","none");
}

function forgetPassword(){
    $(".login-register").css("display","none");
    $(".forgetPwd").css("display","block");
}

function goToLogin(){
    $(".login-register").css("display","block");
    $(".forgetPwd").css("display","none");
    goLogin();
}

$(function () {

    loginName = $("#loginName");
    msg = $(".input-msg");
    email = $("#email");

    $(".search-a").click(function () {
        let searchBox = $(".search-box");
        if(searchBox.val().trim()!==""){
            window.location.replace("/home/search/"+searchBox.val().trim());
        }else{
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.msg("查询条件为空!",{
                    skin:"msg-pwd",
                    id: "msg97",
                    time:"5000",
                    icon:2,
                    area:['300px','50px'],
                    anim:6,
                    offset:"10px"
                });
            });
        }
    });

    $(".option-list").hover(function () {
        $(".User-Username").css("height","0");
        $(".User-Username > p").css("display","none");
    },function () {
        $(".User-Username").css("height","55px");
        $(".User-Username > p").css("display","block");
    });

    loginName.blur(function () {
        if(loginName.val() === "" || loginName.val() === null){
            loginName.css({"border":"2px solid red","border-radius":"5px"});
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.msg("登录名不能为空!",{
                    skin:"msg-pwd",
                    id: "msg1",
                    time:"5000",
                    icon:2,
                    area:['300px','50px'],
                    anim:6,
                    offset:"10px"
                });
            });
        }
    });

    loginName.focus(function () {
        loginName.css({"border":"none","border-bottom":"1px solid #56ffb4"});
    });

    email.blur(function () {
        if(email.val() === "" || email.val() === null){
            email.css({"border":"2px solid red","border-radius":"5px"});
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.msg("邮箱不能为空!",{
                    skin:"msg-pwd",
                    id: "msg2",
                    time:"5000",
                    icon:2,
                    area:['300px','50px'],
                    anim:6,
                    offset:"10px"
                });
            });
        }
    });

    email.focus(function () {
        email.css({"border":"none","border-bottom":"1px solid #56ffb4"});
    });

    $("td").hover(function () {
        $(this).css({
            "animation-name":"big",
            "animation-fill-mode":"forwards",
            " animation-timing-function":"linear",
            "animation-duration":"0.5s",
        });
    },function () {
        $(this).css("animation","none");
    });

    msg.focus(function () {
        $(this).css("border-bottom","1px solid orange");
    });

    msg.blur(function () {
        $(this).css("border-bottom","1px solid cornflowerblue");
    });

    window.addEventListener('scroll',function (){
        if($(document).scrollTop()>800){
            $("#gotop").css("display","block");
        }else{
            $("#gotop").css("display","none");
        }
    },false);

    $(window).scroll(function (){
        $("#gotop").click(function() {
            TweenLite.to(window, 1.5, {scrollTo:0, ease: Expo.easeInOut});
            let huojian = new TimelineLite();
            huojian.to("#gotop", 1, {rotationY:720, scale:0.6, y:"+=40", ease:  Power4.easeOut})
                .to("#gotop", 1, {y:-1000, opacity:0, ease:  Power4.easeOut}, 0.6)
                .to("#gotop", 1, {y:0, rotationY:0, opacity:1, scale:1, ease: Expo.easeOut, clearProps: "all"}, "1.4");
        });
    });

    $(".popular .popular-new .popular-new-context .classify-p").on("click",function (){
        let res = $(this);
        $.get({
            url:"/api/admin/v1/get/getNewsArchives",
            dataType:"json",
            success:function (data){
                if(data.state===1||data.state==="1"){
                    let result = JSON.parse(data.data);
                    showData(res,result,"archives");
                }else{
                    layui.use('layer', function(){
                        let layer = layui.layer;
                        layer.msg(data.message,{
                            skin:"msg-pwd",
                            id: "t81",
                            time:"1500",
                            icon:2,
                            area:['200px','50px'],
                            anim:6,
                            offset:"10px"
                        });
                    });
                }
            },
            error:function (){
                layui.use('layer', function(){
                    let layer = layui.layer;
                    layer.msg("数据获取异常!",{
                        skin:"msg-pwd",
                        id: "t80",
                        time:"1500",
                        icon:2,
                        area:['200px','50px'],
                        anim:6,
                        offset:"10px"
                    });
                });
            }
        });
    });

    $(".popular .popular-new .popular-new-context .popular-p").on("click",function (){
        let res = $(this);
        $.get({
            url:"/api/admin/v1/get/getHotsArchives",
            dataType:"json",
            success:function (data){
                if(data.state===1||data.state==="1"){
                    let result = JSON.parse(data.data);
                    showData(res,result,"archives");
                }else{
                    layui.use('layer', function(){
                        let layer = layui.layer;
                        layer.msg(data.message,{
                            skin:"msg-pwd",
                            id: "t82",
                            time:"1500",
                            icon:2,
                            area:['200px','50px'],
                            anim:6,
                            offset:"10px"
                        });
                    });
                }
            },
            error:function (){
                layui.use('layer', function(){
                    let layer = layui.layer;
                    layer.msg("数据获取异常!",{
                        skin:"msg-pwd",
                        id: "t83",
                        time:"1500",
                        icon:2,
                        area:['200px','50px'],
                        anim:6,
                        offset:"10px"
                    });
                });
            }
        });
    });

    $(".popular .popular-new .popular-new-context .discuss-p").on("click",function (){
        let res = $(this);
        $.get({
            url:"/api/admin/v1/get/getDiscussArchives",
            dataType:"json",
            success:function (data){
                if(data.state===1||data.state==="1"){
                    let result = JSON.parse(data.data);
                    showData(res,result,"discuss");
                }else{
                    layui.use('layer', function(){
                        let layer = layui.layer;
                        layer.msg(data.message,{
                            skin:"msg-pwd",
                            id: "t82",
                            time:"1500",
                            icon:2,
                            area:['200px','50px'],
                            anim:6,
                            offset:"10px"
                        });
                    });
                }
            },
            error:function (){
                layui.use('layer', function(){
                    let layer = layui.layer;
                    layer.msg("数据获取异常!",{
                        skin:"msg-pwd",
                        id: "t83",
                        time:"1500",
                        icon:2,
                        area:['200px','50px'],
                        anim:6,
                        offset:"10px"
                    });
                });
            }
        });
    });

    function changeTime(data){
        let time = new Date(data);
        let year = time.getUTCFullYear();
        let month = time.getUTCMonth()+parseInt(1);
        let date = time.getUTCDate();
        return year+"-"+month+"-"+date;
    }

    function showData(res,result,data){
        res.siblings().css({"font-weight":"500","color":"black","border-bottom":"none"});
        res.css({"font-weight":"700","color":"#00a3d4","border-bottom":"2px solid #00a3d4"});
        if(res.parent().parent().parent().children("div").last().children("ul").children("li").length === 0){
            return false;
        } else if(result.length < res.parent().parent().parent().children("div").last().children("ul").children("li").length){
            for(let i=0;i<result.length;++i){
                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").first().children("img")
                    .attr("src",data==="discuss"?result[i].headImage:"https://api.oss.tanxiangblog.com/images/users/head.png");

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").first().children("a").first()
                    .attr("href",data==="discuss"?"/archives/"+result[i].archivesId+"#comment-"+result[i].archivesDiscussId:"/archives/"+result[i].archivesId);

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").first().children("a").first()
                    .attr("title",data==="discuss"?result[i].content:result[i].title);

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").first().children("a").first().children("p")
                    .text(data==="discuss"?result[i].content:result[i].title);

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").last().children("p").first()
                    .text(data==="discuss"?result[i].username:"");

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").last().children("p").last()
                    .text(changeTime(data==="archives"?result[i].uploadTime:data==="discuss"?result[i].writeTime:""));

            }
            let num = res.parent().parent().parent().children("div").last().children("ul").children("li").length - result.length;
            for(let i=0;i<num;++i){
                res.parent().parent().parent().children("div").last().children("ul").children("li").last().remove();
            }
        }else if(result.length > res.parent().parent().parent().children("div").last().children("ul").children("li").length){
            let num = result.length - res.parent().parent().parent().children("div").last().children("ul").children("li").length;
            for(let i=0;i<num;++i){
                res.parent().parent().parent().children("div").last().children("ul").first().append(res.parent().parent().parent().children("div").last().children("ul").children("li").first().clone(true,true));
            }
            for(let i=0;i<result.length;++i){
                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").first().children("img")
                    .attr("src",data==="discuss"?result[i].headImage:"https://api.oss.tanxiangblog.com/images/users/head.png");

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").first().children("a").first()
                    .attr("href",data==="discuss"?"/archives/"+result[i].archivesId+"#comment-"+result[i].archivesDiscussId:"/archives/"+result[i].archivesId);

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").first().children("a").first()
                    .attr("title",data==="discuss"?result[i].content:result[i].title);

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").first().children("a").first().children("p")
                    .text(data==="discuss"?result[i].content:result[i].title);

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").last().children("p").first()
                    .text(data==="discuss"?result[i].username:"");

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").last().children("p").last()
                    .text(changeTime(data==="archives"?result[i].uploadTime:data==="discuss"?result[i].writeTime:""));

            }
        }else{
            for(let i=0;i<result.length;++i){
                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").first().children("img")
                    .attr("src",data==="discuss"?result[i].headImage:"https://api.oss.tanxiangblog.com/images/users/head.png");

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").first().children("a").first()
                    .attr("href",data==="discuss"?"/archives/"+result[i].archivesId+"#comment-"+result[i].archivesDiscussId:"/archives/"+result[i].archivesId);

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").first().children("a").first()
                    .attr("title",data==="discuss"?result[i].content:result[i].title);

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").first().children("a").first().children("p")
                    .text(data==="discuss"?result[i].content:result[i].title);

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").last().children("p").first()
                    .text(data==="discuss"?result[i].username:"");

                res.parent().parent().parent().children("div").last().children("ul").children("li").eq(i).children("div").last().children("div").last().children("p").last()
                    .text(changeTime(data==="archives"?result[i].uploadTime:data==="discuss"?result[i].writeTime:""));
            }
        }
    }
});

setInterval(function () {
    let date = parseInt(new Date().getTime()) - parseInt(1594137600000);
    let day = parseInt(parseInt(date) /86400000);
    let hour = parseInt((parseInt(date) - (86400000 * day))/3600000);
    let min =  parseInt((parseInt(date) - (86400000 * day) - (3600000 * hour)) /60000);
    let second = parseInt((parseInt(date) - (86400000 * day) - (3600000 * hour) - (60000 * min))/1000);
    if(second < 10){
        $("#running-date").text(day+"天"+hour+"时"+min+"分"+"0"+second+"秒");
    }else{
        $("#running-date").text(day+"天"+hour+"时"+min+"分"+second+"秒");
    }
},1000);

function searchData() {
    let searchBox = $(".search-box");
    if (event.keyCode === 13) {
        if(searchBox.val().trim() !== ""){
            window.location.replace("/home/search/"+searchBox.val().trim());
        }else{
            layui.use('layer', function(){
                let layer = layui.layer;
                layer.msg("查询条件为空!",{
                    skin:"msg-pwd",
                    id: "msg96",
                    time:"5000",
                    icon:2,
                    area:['300px','50px'],
                    anim:6,
                    offset:"10px"
                });
            });
        }
    }
}
