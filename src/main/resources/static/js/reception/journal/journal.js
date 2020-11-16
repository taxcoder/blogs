let pageNumberJour;
let ml;
let divRight;
let divLeft;
let dataP;
let jourLi;
$(function () {
    jourLi = $(".journal .journal-main .middle-area-footmark .middle-area-footmark-back .jourUl .jourLi");
    dataP = $(".journal .journal-main .middle-area-footmark .middle-area-footmark-back ul li .part .frame .context .context-div p");
    ml = $(".middle-between-paging-style-ul ul li");
    pageNumberJour = $("#pageNumberJour");
    divLeft = $("#div-left");
    divRight = $("#div-right");

    ml.hover(function () {
            $(this).css("background", "cornflowerblue");
            $(this).children().css("color", "white");
        }
        , function () {
            $(this).css("background", "white");
            $(this).children().css("color", "#333");
        });

    initializationJour();
    function initializationJour(){
        for(let i=0;i<jourLi.length;++i){
            let $p = jourLi.eq(i).children("div").children("div").last().children("div").children("div").children("p").innerHeight();
            if($p > 85){
                jourLi.eq(i).css("height",$p+40);
            }
        }

        if (pageNumberJour.val() > 9) {
            divLeft.css("display", "none");
            divRight.css("display", "inline-block");
        } else {
            divLeft.css("display", "none");
            divRight.css("display", "none");
        }

        if (pageNumberJour.val() === 1 || pageNumberJour.val() === "1") {
            $(".middle-between-paging-style").css("display", "flex");
            $(".middle-between-paging-style-page").css("display", "none");
            $(".middle-between-paging-style-ul ul li").eq(0).siblings().css("display", "none");
        } else if (parseInt(pageNumberJour.val()) <= 9) {
            $(".middle-between-paging-style").css("display", "flex");
            $(".middle-between-paging-style-page").css("display", "none");
            $(".middle-between-paging-style-ul ul li").eq(parseInt(pageNumberJour.val()) - 1).nextAll().css("display", "none");
        } else {
            $(".middle-between-paging-style").css("display", "flex");
            ml.eq(7).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
            ml.last().text(pageNumberJour.val())
        }
    }

    ml.click(function () {
        if (!$(this).hasClass("btnColor") && pageNumberJour.val() > 9) {
            if ($(this).children("i").length === 0 && parseInt($(this).text()) === parseInt(ml.first().text())) {
                queryChangeLog($(this));
                left();
            } else if ($(this).children("i").length === 0 && parseInt($(this).text()) === parseInt(ml.last().text())) {
                queryChangeLog($(this));
                right();
            } else if ($(this).children("i").length === 0) {
                queryChangeLog($(this));
                $(this).addClass("btnColor").siblings().removeClass("btnColor");
                divLeft.css("display","block");
                divRight.css("display","block");
            } else {
                if ($(this).next().text() === ml.last().text() && parseInt($(this).prev().text()) + parseInt(6) >= parseInt(ml.last().text())) {
                    right();
                } else if ($(this).next().text() === ml.last().text() && parseInt($(this).prev().text()) + parseInt(6) < parseInt(ml.last().text())) {
                    addRight();
                } else if ($(this).prev().text() === ml.first().text() && $(this).next().text() - 5 <= ml.first().text()) {
                    left();
                } else if ($(this).prev().text() === ml.first().text() && $(this).next().text() - 5 > ml.first().text()) {
                    subLeft();
                }

                if (parseInt(ml.eq(2).text()) - parseInt(6) > ml.first().text()) {
                    ml.eq(1).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
                } else {
                    left();
                }

                if (parseInt(ml.eq(6).text()) + parseInt(6) < ml.last().text()) {
                    ml.eq(7).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
                } else {
                    right();
                }
            }
        } else if (!$(this).hasClass("btnColor") && pageNumberJour.val() <= 9) {
            $(this).addClass("btnColor").siblings().removeClass("btnColor");
            queryChangeLog($(this));
        }
    });

    divRight.click(function () {
        let data = $(".btnColor").next();
        if (data.children("i").length === 0 && data.text() !== ml.first().text()) {
            divLeft.css("display", "inline-block");
        }
        if (data.children("i").length === 0 && data.text() === ml.last().text()) {
            divRight.css("display", "none");
        }
        if (data.children("i").length === 0) {
            data.addClass("btnColor").siblings().removeClass("btnColor");
            queryChangeLog(data.prev());
        } else if (data.next().text() === pageNumberJour.val()) {
            if (parseInt(data.prev().text()) + parseInt(6) < parseInt(ml.last().text())) {
                addRight();
                if (parseInt(ml.eq(2).text()) - parseInt(6) > ml.first().text()) {
                    ml.eq(1).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
                }
                queryChangeLog(data.prev());
            } else {
                right();
                queryChangeLog($(".btnColor"));
            }
        }
    });

    divLeft.click(function () {
        let data = $(".btnColor").prev();
        if (data.children("i").length === 0 && data.text() !== ml.last().text()) {
            divRight.css("display", "inline-block");
        }
        if (data.children("i").length === 0 && data.text() === ml.first().text()) {
            divLeft.css("display", "none");
        }
        if (data.children("i").length === 0) {
            data.addClass("btnColor").siblings().removeClass("btnColor");
            queryChangeLog(data.next());
        } else if (data.prev().text() === ml.first().text()) {
            if (parseInt(data.next().text()) - parseInt(5) <= 3) {
                left();
                queryChangeLog(data.next());
            } else {
                subLeft();
                if (parseInt(ml.eq(6).text()) + parseInt(6) < ml.last().text()) {
                    ml.eq(7).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
                }
                queryChangeLog($(".btnColor"));
            }
        }
    });

    function btnDataFootMark(data) {
        let JsonData = JSON.parse(data.data);
        let between = $(".middle-area-footmark-back");
        let middle = $(".middle-area-footmark-back ul li");
        if(JsonData.length < between.children("ul").children("li").length){
            middle.eq(JsonData.length -1).nextAll().remove();
        }else if(JsonData.length > between.children("ul").children("li").length){
            for(let i=0,len = JsonData.length - between.children("ul").children("li").length;i< len;i++){
                between.children("ul").append(between.children("ul").children("li").eq(0).clone());
            }
        }

        $(".middle-area-footmark-back ul li .part").each(function (index,element) {
            $(element).children("div").first().children("span").first().text(changeTimeYear(JsonData[index].uploadTime));
            $(element).children("div").first().children("span").last().text(changeTimeMonthAndDay(JsonData[index].uploadTime));
            $(element).children("div").last().children("div").children("div").first().children("p").text(JsonData[index].content);
        });

        document.getElementById("tx").scrollIntoView();
    }

    function right() {
        ml.last().addClass("btnColor").siblings().removeClass("btnColor");
        divRight.css("display", "none");
        divLeft.css("display", "inline-block");
        ml.eq(7).text(pageNumberJour.val() - 1);
        ml.eq(6).text(pageNumberJour.val() - 2);
        ml.eq(5).text(pageNumberJour.val() - 3);
        ml.eq(4).text(pageNumberJour.val() - 4);
        ml.eq(3).text(pageNumberJour.val() - 5);
        ml.eq(2).text(pageNumberJour.val() - 6);
        ml.eq(1).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
    }

    function left() {
        ml.first().addClass("btnColor").siblings().removeClass("btnColor");
        divLeft.css("display", "none");
        divRight.css("display", "inline-block");
        ml.eq(1).text(2);
        ml.eq(2).text(3);
        ml.eq(3).text(4);
        ml.eq(4).text(5);
        ml.eq(5).text(6);
        ml.eq(6).text(7);
        ml.eq(7).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
    }

    function addRight() {
        ml.eq(2).text(parseInt(ml.eq(2).text()) + parseInt(5));
        ml.eq(3).text(parseInt(ml.eq(3).text()) + parseInt(5));
        ml.eq(4).text(parseInt(ml.eq(4).text()) + parseInt(5));
        ml.eq(5).text(parseInt(ml.eq(5).text()) + parseInt(5));
        ml.eq(6).text(parseInt(ml.eq(6).text()) + parseInt(5));
    }

    function subLeft() {
        ml.eq(2).text(parseInt(ml.eq(2).text()) - parseInt(5));
        ml.eq(3).text(parseInt(ml.eq(3).text()) - parseInt(5));
        ml.eq(4).text(parseInt(ml.eq(4).text()) - parseInt(5));
        ml.eq(5).text(parseInt(ml.eq(5).text()) - parseInt(5));
        ml.eq(6).text(parseInt(ml.eq(6).text()) - parseInt(5));
    }

    function changeTimeYear(time) {
        let date = new Date(time);
        let year = date.getUTCFullYear();
        return year+"年";
    }

    function changeTimeMonthAndDay(time) {
        let date = new Date(time);
        let month = date.getUTCMonth()+1;
        let day = date.getUTCDate();

        if(month < 10 && day < 10){
            return "0"+month+"月"+"0"+day+"日";
        }else if(month < 10){
            return "0"+month+"月"+day+"日";
        }else if(day < 10){
            return month+"月"+"0"+day+"日";
        }else{
            return month+"月"+day+"日";
        }
    }

    function queryChangeLog(showDate) {
        $.get({
            url: "/api/admin/v1/get/changeLogPaging/" + showDate.text(),
            dataType: "json",
            success: function (data) {
                if (data.state === 0 || data.state === "0") {
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg90",
                            time: "3000",
                            icon: 2,
                            anim: 6,
                            area: ['300px', '50px'],
                            offset: "10px"
                        });
                    });
                } else {
                    btnDataFootMark(data);
                }
            },
            error: function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("数据拉取异常!", {
                        skin: "msg-pwd",
                        id: "msg91",
                        time: "3000",
                        icon: 2,
                        anim: 6,
                        area: ['300px', '50px'],
                        offset: "10px"
                    });
                });
            }
        });
    }
});