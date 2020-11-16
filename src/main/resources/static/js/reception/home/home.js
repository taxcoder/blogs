let pageNumber;
let ml;
let divRight;
let divLeft;
let typeName;
let tagName;
$(function () {
    const count = 1059;
    ml = $(".middle-between-paging-style-ul ul li");
    pageNumber = $("#pageNumber");
    divLeft = $("#div-left");
    divRight = $("#div-right");
    typeName = $("#typeName");
    tagName = $("#tagName");
    $(".middle-between-classification-right-box ul li a").click(function () {
        $(this).css("font-size","20px");
    });

    ml.hover(function () {
        $(this).css("background", "cornflowerblue");
        $(this).children().css("color", "white");
    }, function () {
        $(this).css("background", "white");
        $(this).children().css("color", "#333");
    });

    initializationHome();
    function initializationHome(){
        changeBackground();
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

    function changeBackground(){
        let lazy = $(".lazy");
        for(let i=0;i<lazy.length;++i){
            let number = parseInt(randomNumber());
            let num;
            if(number < 10){
                num = "000"+number===0?1:number;
            }else if(number >= 10 && number < 100){
                num = "00"+number;
            } else if(number >= 100 && number < 1000){
                num = "0"+number;
            }else{
                num = number;
            }
            lazy.eq(i).css("background-image","url(https://api.oss.tanxiangblog.com/rotation/images/img"+num+".jpg)");
        }
    }

    ml.click(function () {
        if (!$(this).hasClass("btnColor") && pageNumber.val() > 9) {
            if ($(this).children("i").length === 0 && parseInt($(this).text()) === parseInt(ml.first().text())) {
                if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                    page($(this));
                }else if(window.location.href.indexOf("types")!==-1){
                    getPageData($(this));
                }else if(window.location.href.indexOf("tags")!==-1){
                    tagArchives($(this));
                }else if(window.location.href.indexOf("search")!==-1){
                    search($(this));
                }
                left();
            } else if ($(this).children("i").length === 0 && parseInt($(this).text()) === parseInt(ml.last().text())) {
                if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                    page($(this));
                }else if(window.location.href.indexOf("types")!==-1){
                    getPageData($(this));
                }else if(window.location.href.indexOf("tags")!==-1){
                    tagArchives($(this));
                }else if(window.location.href.indexOf("search")!==-1){
                    search($(this));
                }
                right();
            } else if ($(this).children("i").length === 0) {
                if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                    page($(this));
                }else if(window.location.href.indexOf("types")!==-1){
                    getPageData($(this));
                }else if(window.location.href.indexOf("tags")!==-1){
                    tagArchives($(this));
                }else if(window.location.href.indexOf("search")!==-1){
                    search($(this));
                }
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
          if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                    page($(this));
                }else if(window.location.href.indexOf("types")!==-1){
                    getPageData($(this));
                }else if(window.location.href.indexOf("tags")!==-1){
                    tagArchives($(this));
                }else if(window.location.href.indexOf("search")!==-1){
                     search($(this));
                  }
                }
            });

    divRight.click(function () {
        let data = $(".btnColor").next();

        if(data.children("i").length === 0 && data.text()!==ml.first().text()){
            divLeft.css("display","inline-block");
        }

        if(data.children("i").length === 0 && data.text()===ml.last().text()){
            divRight.css("display","none");
        }

        if (data.children("i").length === 0) {
            data.addClass("btnColor").siblings().removeClass("btnColor");
            if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                page(data);
            }else if(window.location.href.indexOf("types")!==-1){
                getPageData(data);
            }else if(window.location.href.indexOf("tags")!==-1){
                tagArchives(data);
            }else if(window.location.href.indexOf("search")!==-1){
                search(data);
            }
        } else if (data.next().text() === pageNumber.val()) {
            if (parseInt(data.prev().text()) + parseInt(6) < parseInt(ml.last().text())) {
                addRight();
                if (parseInt(ml.eq(2).text()) - parseInt(6) > ml.first().text()) {
                    ml.eq(1).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
                }
                if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                    page(data.prev());
                }else if(window.location.href.indexOf("types")!==-1){
                    getPageData(data.prev());
                }else if(window.location.href.indexOf("tags")!==-1){
                    tagArchives(data.prev());
                }else if(window.location.href.indexOf("search")!==-1){
                    search(data.prev());
                }
            } else {
                right();
                if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                    page($(".btnColor"));
                }else if(window.location.href.indexOf("types")!==-1){
                    getPageData($(".btnColor"));
                }else if(window.location.href.indexOf("tags")!==-1){
                    tagArchives($(".btnColor"));
                }else if(window.location.href.indexOf("search")!==-1){
                    search($(".btnColor"));
                }
            }
        }
    });

    divLeft.click(function () {
        let data = $(".btnColor").prev();

        if(data.children("i").length === 0 && data.text()!==ml.last().text()){
            divRight.css("display","inline-block");
        }

        if(data.children("i").length === 0 && data.text()===ml.first().text()){
            divLeft.css("display","none");
        }

        if (data.children("i").length === 0) {
            data.addClass("btnColor").siblings().removeClass("btnColor");
            if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                page(data);
            }else if(window.location.href.indexOf("types")!==-1){
                getPageData(data);
            }else if(window.location.href.indexOf("tags")!==-1){
                tagArchives(data);
            }else if(window.location.href.indexOf("search")!==-1){
                search(data);
            }
        } else if (data.prev().text() === ml.first().text()) {
            if (parseInt(data.next().text()) - parseInt(5) <= 3) {
                left();
                if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                    page($(".btnColor"));
                }else if(window.location.href.indexOf("types")!==-1){
                    getPageData($(".btnColor"));
                }else if(window.location.href.indexOf("tags")!==-1){
                    tagArchives($(".btnColor"));
                }else if(window.location.href.indexOf("search")!==-1){
                    search($(".btnColor"));
                }
            } else {
                subLeft();
                if (parseInt(ml.eq(6).text()) + parseInt(6) < ml.last().text()) {
                    ml.eq(7).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
                }

                if(window.location.href.indexOf("types")===-1 && window.location.href.indexOf("tags")===-1 && window.location.href.indexOf("search")===-1){
                    page(data.next());
                }else if(window.location.href.indexOf("types")!==-1){
                    getPageData(data.next());
                }else if(window.location.href.indexOf("tags")!==-1){
                    tagArchives(data.next());
                }else if(window.location.href.indexOf("search")!==-1){
                    search(data.next());
                }
             }
        }
    });

    function btn(data) {
        let JsonData = JSON.parse(data.data);
        let between = $(".middle-between-list");
        let middle = $(".middle-between-list ul li");
        if(JsonData.length < between.children("ul").children("li").length){
            middle.eq(JsonData.length -1).nextAll().remove();
        }else if(JsonData.length > between.children("ul").children("li").length){
            for(let i=0,len = JsonData.length - between.children("ul").children("li").length;i< len;i++){
                between.children("ul").append(between.children("ul").children("li").eq(0).clone());
            }
        }
        changeBackground();
        $(".middle-between-list ul li > .middle-between-list-article").each(function (index,element) {
            $(element).children("div").eq(1).children("p").children("a").attr("href","/archives/"+JsonData[index].archivesId);
            $(element).children("div").eq(1).children("p").children("a").text(JsonData[index].title);
            $(element).children("div").eq(2).children("p").text(JsonData[index].subTitle);
            $(element).children("div").eq(3).children("div").eq(0).children("span").text(changeTime(JsonData[index].uploadTime));
            $(element).children("div").eq(3).children("div").eq(1).children("span").text(JsonData[index].readNumber);
            $(element).children("div").eq(3).children("div").eq(2).children("span").text(JsonData[index].goodNumber);
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

    function getPageData(showPage) {
        $.get({
            url:"/api/admin/v1/get/archives/types/"+typeName.val()+"/pages/"+showPage.text(),
            dataType:"json",
            success:function (data) {
                if(data.state===0||data.state==="0"){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg75",
                            time: "3000",
                            icon: 2,
                            anim: 6,
                            area: ['300px', '50px'],
                            offset: "10px"
                        });
                    });
                }else if(data.state === "-1" || data.state===-1){
                    window.location.replace("/404");
                }else{
                    btn(data);
                }
            },
            error:function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("数据拉取异常!", {
                        skin: "msg-pwd",
                        id: "msg76",
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

    function changeTime(time) {
        let date = new Date(time);
        let year = date.getUTCFullYear();
        let month = date.getUTCMonth()+1;
        let day = date.getUTCDate();
        return year+"-"+month+"-"+day;
    }

    function tagArchives(current) {
        $.get({
            url:"/api/admin/v1/get/archives/tags/"+tagName.val()+"/pages/"+current.text(),
            dataType:"json",
            success:function (data) {
                if(data.state===0||data.state==="0"){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg92",
                            time: "3000",
                            icon: 2,
                            anim: 6,
                            area: ['300px', '50px'],
                            offset: "10px"
                        });
                    });
                }else if(data.state===-1||data.state==="-1"){
                    window.location.replace("/404");
                }else{
                    btn(data);
                }
            },
            error:function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("数据拉取异常!", {
                        skin: "msg-pwd",
                        id: "msg94",
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

    function search(current) {
        $.get({
            url:"/api/admin/v1/get/archives/search/"+$("#data").val()+"/pages/"+current.text(),
            dataType:"json",
            success:function (data) {
                if(data.state===0 || data.state === "0"){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg96",
                            time: "3000",
                            icon: 2,
                            anim: 6,
                            area: ['300px', '50px'],
                            offset: "10px"
                        });
                    });
                }else if(data.state===-1||data.state==="-1"){
                    window.location.replace("/404");
                }else{
                    console.log(data);
                    btn(data);
                }
            },
            error:function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("数据拉取异常!", {
                        skin: "msg-pwd",
                        id: "msg97",
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

    function page(showDate) {
        $.get({
            url:"/api/admin/v1/get/archives/pages/"+showDate.text(),
            dataType:"json",
            success:function (data) {
                if(data.state===0 || data.state === "0"){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg73",
                            time: "3000",
                            icon: 2,
                            anim: 6,
                            area: ['300px', '50px'],
                            offset: "10px"
                        });
                    });
                }else{
                    btn(data);
                }
            },
            error:function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("数据拉取异常!", {
                        skin: "msg-pwd",
                        id: "msg74",
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

    function randomNumber(){
        let res = Math.random();
       if(res === 0){
           return  res * count + 1;
       }
        return res * count;
    }
});