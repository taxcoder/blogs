let pageNumber;
let ml;
let divRight;
let divLeft;
$(function () {
    ml = $(".middle-between-paging-style-ul ul li");
    pageNumber = $("#pageNumber");
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
    initializationMark();
    function initializationMark(){
        if (pageNumber.val() > 9) {
            divLeft.css("display", "none");
            divRight.css("display", "inline-block");
        } else {
            divLeft.css("display", "none");
            divRight.css("display", "none");
        }

        if (pageNumber.val() === 1 || pageNumber.val() === "1") {
            $(".middle-between-paging-style").css("display", "flex");
            $(".middle-between-paging-style-page").css("display", "none");
            $(".middle-between-paging-style-ul ul li").eq(0).siblings().css("display", "none");
        } else if (parseInt(pageNumber.val()) <= 9) {
            $(".middle-between-paging-style").css("display", "flex");
            $(".middle-between-paging-style-page").css("display", "none");
            $(".middle-between-paging-style-ul ul li").eq(parseInt(pageNumber.val()) - 1).nextAll().css("display", "none");
        } else {
            $(".middle-between-paging-style").css("display", "flex");
            ml.eq(7).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
            ml.last().text(pageNumber.val())
        }
    }
    ml.click(function () {
        if (!$(this).hasClass("btnColor") && pageNumber.val() > 9) {
            if ($(this).children("i").length === 0 && parseInt($(this).text()) === parseInt(ml.first().text())) {
                pageFootMark($(this));
                left();
            } else if ($(this).children("i").length === 0 && parseInt($(this).text()) === parseInt(ml.last().text())) {
                pageFootMark($(this));
                right();
            } else if ($(this).children("i").length === 0) {
                pageFootMark($(this));
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
        } else if (!$(this).hasClass("btnColor") && pageNumber.val() <= 9) {
            $(this).addClass("btnColor").siblings().removeClass("btnColor");
            pageFootMark($(this));
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
            pageFootMark(data.prev());
        } else if (data.next().text() === pageNumber.val()) {
            if (parseInt(data.prev().text()) + parseInt(6) < parseInt(ml.last().text())) {
                addRight();
                if (parseInt(ml.eq(2).text()) - parseInt(6) > ml.first().text()) {
                    ml.eq(1).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
                }
                pageFootMark(data.prev());
            } else {
                right();
                pageFootMark($(".btnColor"));
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
            pageDaily(data.next());
        } else if (data.prev().text() === ml.first().text()) {
            if (parseInt(data.next().text()) - parseInt(5) <= 3) {
                left();
                pageDaily(data.next());
            } else {
                subLeft();
                if (parseInt(ml.eq(6).text()) + parseInt(6) < ml.last().text()) {
                    ml.eq(7).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
                }
                pageDaily($(".btnColor"));
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
            $(element).children("div").last().children("div").children("div").first().children("a").attr("href","/archives/"+JsonData[index].archivesId);
            $(element).children("div").last().children("div").children("div").first().children("a").children("p").first().text(JsonData[index].title);
            $(element).children("div").last().children("div").children("div").last().children("div").children("a").first().attr("href","/home?type="+JsonData[index].typeId);
            $(element).children("div").last().children("div").children("div").last().children("div").children("a").first().children("span").first().text(JsonData[index].archivesTypeName);
        });

        document.getElementById("tx").scrollIntoView();
    }

    function right() {
        ml.last().addClass("btnColor").siblings().removeClass("btnColor");
        divRight.css("display", "none");
        divLeft.css("display", "inline-block");
        ml.eq(7).text(pageNumber.val() - 1);
        ml.eq(6).text(pageNumber.val() - 2);
        ml.eq(5).text(pageNumber.val() - 3);
        ml.eq(4).text(pageNumber.val() - 4);
        ml.eq(3).text(pageNumber.val() - 5);
        ml.eq(2).text(pageNumber.val() - 6);
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

    function pageFootMark(showDate) {
        $.get({
            url: "/api/admin/v1/get/getFootMarkData/" + showDate.text(),
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