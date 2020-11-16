let pageNumber;
let good;
let ml;
let divRight;
let divLeft;
let uuidInput;
let nameInput;
let emailInput;
let imageInput;
let discussClick;
let doubleDiscuss;
let address;
let button;
let count;
let doubleCount;
$(function () {
    const five = parseInt(5);
    doubleDiscuss = $("#doubleDiscussUl");
    discussClick = $(".discuss");
    ml = $(".middle-between-paging-style-ul ul li");
    pageNumber = $("#pageNumber");
    divLeft = $("#div-left");
    divRight = $("#div-right");
    uuidInput = $(".InputUUID > input");
    nameInput = $(".usernameI");
    emailInput = $(".emailI");
    imageInput = $(".imageI");
    ml.hover(function () {
            $(this).css("background", "cornflowerblue");
            $(this).children().css("color", "white");
        }
        , function () {
            $(this).css("background", "white");
            $(this).children().css("color", "#333");
        });
    initializationInfo();
    function initializationInfo(){
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
                pageDaily($(this));
                left();
            } else if ($(this).children("i").length === 0 && parseInt($(this).text()) === parseInt(ml.last().text())) {
                pageDaily($(this));
                right();
            } else if ($(this).children("i").length === 0) {
                pageDaily($(this));
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
            pageDaily($(this));
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
            pageDaily(data.prev());
        } else if (data.next().text() === pageNumber.val()) {
            if (parseInt(data.prev().text()) + parseInt(6) < parseInt(ml.last().text())) {
                addRight();
                if (parseInt(ml.eq(2).text()) - parseInt(6) > ml.first().text()) {
                    ml.eq(1).html("<i class='layui-icon layui-icon-more' style='font-size: 15px'></i>");
                }
                pageDaily(data.prev());
            } else {
                right();
                pageDaily($(".btnColor"));
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

    function btnData(data) {
        let JsonData = JSON.parse(data.data);
        let between = $(".informal-middle-between");
        let middle = $(".informal-middle-between .informal-ul .informal-li");
        if(JsonData.length < between.children("ul").children("li").length){
            middle.eq(JsonData.length -1).nextAll().remove();
        }else if(JsonData.length > between.children("ul").children("li").length){
            for(let i=0,len = JsonData.length - between.children("ul").children("li").length;i< len;i++){
                between.children("ul").append(between.children("ul").children("li").eq(0).clone(true,true));
            }
        }

        $(".informal-middle-between ul li .informal-content .content").each(function (index,element) {
            $(element).children("div").first().children("div").last().text(changeTime(JsonData[index].uploadTime));
            $(element).children("div").eq(1).children("p").html(JsonData[index].content);
            $(element).children("div").last().children("div").first().children("span").text(JsonData[index].discussNumber);
            $(element).children("div").last().children("div").last().children("span").text(JsonData[index].goodNumber);
            $(element).children("div").last().children("div").last().children("input").val(JsonData[index].dailyId);
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
        ml.eq(2).text(parseInt(ml.eq(2).text()) + five);
        ml.eq(3).text(parseInt(ml.eq(3).text()) + five);
        ml.eq(4).text(parseInt(ml.eq(4).text()) + five);
        ml.eq(5).text(parseInt(ml.eq(5).text()) + five);
        ml.eq(6).text(parseInt(ml.eq(6).text()) + five);
    }

    function subLeft() {
        ml.eq(2).text(parseInt(ml.eq(2).text()) - five);
        ml.eq(3).text(parseInt(ml.eq(3).text()) - five);
        ml.eq(4).text(parseInt(ml.eq(4).text()) - five);
        ml.eq(5).text(parseInt(ml.eq(5).text()) - five);
        ml.eq(6).text(parseInt(ml.eq(6).text()) - five);
    }

    function changeTime(time) {
        let date = new Date(time);
        let year = date.getUTCFullYear();
        let month = date.getUTCMonth() + 1;
        let day = date.getUTCDate();
        return year + "-" + month + "-" + day;
    }

    function pageDaily(showDate) {
        $.get({
            url: "/api/admin/v1/get/getDailyData/" + showDate.text(),
            dataType: "json",
            success: function (data) {
                if (data.state === 0 || data.state === "0") {
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
                } else {
                    btnData(data);
                }
            },
            error: function () {
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

    $(".good").click(function () {
        good = $(this);
        $.ajax({
            url:"/api/admin/v1/put/dailyPraise/"+$(this).children("input").last().val().trim(),
            dataType:"json",
            type:"put",
            success:function (data) {
                if(data.state===1||data.state==="1"){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg77",
                            time: "3000",
                            icon: 1,
                            area: ['300px', '50px'],
                            offset: "10px"
                        });
                    });
                    good.children("span").text(parseInt(good.children("span").text()) + parseInt(1));
                }else{
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg78",
                            time: "3000",
                            icon: 2,
                            anim: 6,
                            area: ['350px', '50px'],
                            offset: "10px"
                        });
                    });
                }
            },error:function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("出现异常请重试!", {
                        skin: "msg-pwd",
                        id: "msg79",
                        time: "3000",
                        icon: 2,
                        anim: 6,
                        area: ['300px', '50px'],
                        offset: "10px"
                    });
                });
            }
        });
    });



    $(".panel-content-close").click(function (){
        panelClose();
    });

    function panelClose(){
        $(".header").css("position","relative");
        $(".informal").css({"position":"relative","margin-top":"40px"});
        $(".footer").css("display","block");
        $(".panel").css("display","none");
        $(".panel-double").css("display","none");
        $(".InputName > input").val("");
        $(".InputEmail > input").val("");
        $(".InputHeader > input").val("");
        $("#RealText").val("");
        $("#textReal").val("");
        let dis = $("#discussUl").children("li");
        let len = dis.length - 2;
        for(let i=0;i<len;++i){
            $("#discussUl").children("li").first().remove();
        }
    }

    $("#RealSubmit").click(function () {
        //访客
        let condition = 1;
        if($(".head-sculpture").children().length!==0){
            //登录者
            condition = 2;
        }
        let html = $("#RealText").val().trim();
        if(html===null||html===""||html===undefined){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("文本不允许为空!", {
                    skin: "msg-pwd",
                    id: "t1",
                    time: "3000",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
        }else{
            let result;
            if(condition===1){
                let nameFlag = nameNotNUll();
                if(!nameFlag){
                    result = false;
                    setTimeout(function (){
                        print(emailNotNull());
                    },1000);
                }else{
                    result = print(emailNotNull());
                }
            }else if (condition===2){
                result = true;
            }else{
                result = false;
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("请刷新，重新进行尝试！", {
                        skin: "msg-pwd",
                        id: "t10",
                        time: "1500",
                        icon: 2,
                        anim: 6,
                        area: ['250px', '50px'],
                        offset: "10px"
                    });
                });
            }
            if(result){
                $.post({
                    url:"/api/admin/v1/post/addComment",
                    data:
                        {
                            "username":condition===2?$(".inputOne > .InputName").children("input").first().val():nameInput.val().trim(),
                            "email":condition===2?$(".inputTwo > .InputEmail").children("input").first().val():emailInput.val().trim(),
                            "headImg":condition===2?$(".inputTwo > .InputHeader").children("input").first().val():imageInput.val().trim(),
                            "content":html,
                            "dailyId":$("#daily").val(),
                            "reply":null,
                            "state":"1",
                            "attachment":null,
                            "condition":condition
                        },
                    dataType:"json",
                    success:function (data){
                        if(data.state===0||data.state==="0"){
                            layui.use('layer', function () {
                                let layer = layui.layer;
                                layer.msg(data.message, {
                                    skin: "msg-pwd",
                                    id: "t5",
                                    time: "1500",
                                    area: ['200px', '50px'],
                                    icon: 2,
                                    anim: 6,
                                    offset: "10px"
                                });
                            });
                        }else if(data.state==="1"||data.state===1){
                            layui.use('layer', function () {
                                let layer = layui.layer;
                                layer.msg(data.message, {
                                    skin: "msg-pwd",
                                    id: "t6",
                                    time: "1500",
                                    area: ['100px', '50px'],
                                    icon: 1,
                                    offset: "10px"
                                });
                            });
                            if(address!==undefined&&address!==""&&address!==null){
                                panelClose();
                                getData(address);
                                address.children("span").text(parseInt(address.children("span").text())+parseInt(1));
                            }
                        }
                    },
                    error:function (){
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg("出现异常状态，评论发布失败！", {
                                skin: "msg-pwd",
                                id: "t7",
                                time: "1500",
                                area: ['250px', '50px'],
                                icon: 2,
                                anim: 6,
                                offset: "10px"
                            });
                        });
                    }
                });
            }
        }
    });

    function print(emailFlag){
        if(!emailFlag){
            setTimeout(function (){
               headImage();
            },1000);
            return false;
        }else{
           return headImage();
        }
    }
    uuidInput.blur(function (){

    });


    nameInput.blur(function (){
        nameNotNUll();
    });


    emailInput.blur(function (){
     emailNotNull();
    });

    imageInput.blur(function (){
        headImage();
    });

    function emailNotNull(){
        let regex = /^\w+((-\w)|(\.\w))*@[A-Za-z0-9]+((\.\|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if(emailInput.val().trim()===""||emailInput.val().trim()===undefined||emailInput.val().trim()===null){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("邮箱不能为空!", {
                    skin: "msg-pwd",
                    id: "t3",
                    time: "1500",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
            return false;
        }else if(!regex.test(emailInput.val().trim())){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('邮箱的格式不正确！', {
                    skin: "msg-pwd",
                    id: "t4",
                    time: "1500",
                    icon: 2,
                    area: ['200px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
            return false;
        }else if(emailInput.val().trim().length > 25){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('邮箱长度过长！！', {
                    skin: "msg-pwd",
                    id: "t23",
                    time: "1500",
                    icon: 2,
                    area: ['200px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
            return false;
        }
        return true;
    }

    function headImage(){
        let pre = imageInput.val().trim().substring(imageInput.val().trim().lastIndexOf("\.")+1);
        if(imageInput.val().trim()===""||imageInput.val().trim()===null||imageInput.val().trim()===undefined){
            return true;
        }else if(imageInput.val().trim().lastIndexOf("\.")===-1||!(pre==="jpeg"||pre==="jpg"||pre==="png")){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("图片后缀格式需为jpeg、jpg、png)!", {
                    skin: "msg-pwd",
                    id: "t5",
                    time: "1500",
                    area: ['350px', '50px'],
                    icon: 2,
                    anim: 6,
                    offset: "10px"
                });
            });
            return false;
        }
        return true;
    }


    function nameNotNUll(){
        if(nameInput.val().trim()===""||nameInput.val().trim()===undefined||nameInput.val().trim()===null){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("用户名不能为空!", {
                    skin: "msg-pwd",
                    id: "t2",
                    time: "1500",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
            return false;
        }else if(nameInput.val().trim().length>15){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("用户名不能大于15个字符!", {
                    skin: "msg-pwd",
                    id: "t20",
                    time: "1500",
                    icon: 2,
                    anim: 6,
                    area: ['300px', '50px'],
                    offset: "10px"
                });
            });
            return false;
        }
        return true;
    }

    function loginSuccess(){
        let headSculpture = $(".head-sculpture");
        if(headSculpture.children().length!==0){
            $(".inputOne > .InputName").children("input").first().val(headSculpture.children("div").children("div").children("ul").children("li").first().children("p").first().text());
            $(".InputEmail").children("input").first().val("xxxxxxxxxxx@qq.com");
            $(".InputHeader").children("input").first().val(headSculpture.children("img").first().attr("src"));
            $(".inputOne").css("display","none");
            $(".inputTwo").css("display","none");
        }
    }


    function changeTimeXiu(data){
        let dataTime = new Date(data);
        let hours = dataTime.getHours()<10?"0"+dataTime.getHours():dataTime.getHours();
        let minutes = dataTime.getUTCMinutes()<10?"0"+dataTime.getUTCMinutes():dataTime.getUTCMinutes();
        let seconds = dataTime.getUTCSeconds()<10?"0"+dataTime.getUTCSeconds():dataTime.getUTCSeconds();
        let day = dataTime.getHours()===0?dataTime.getUTCDate()+parseInt(1):dataTime.getUTCDate();
        return dataTime.getUTCFullYear()+"-"+(dataTime.getUTCMonth()+1)+"-"+day+" "+hours+":"+minutes+":"+seconds;
    }

    discussClick.click(function (){
        address = $(this);
        getData(address);
    });

    function getData(res){
        $(".header").css("position","fixed");
        $(".panel-double").css({"display":"flex"});
        $(".informal").css({"position":"fixed","margin-top":"130px"});
        $("#daily").val(res.next().children("input").val());
        $(".footer").css("display","none");
        $(".panel").css("display","flex");
        loginSuccess();
        $.get({
            url:"/api/admin/v1/get/getDiscuss",
            data:{"dailyId":res.next().children("input").val()},
            dataType:"json",
            success:function (data){
                if(data.state===1||data.state==="1"){
                    let result =JSON.parse(data.data);
                    let discussUl = $("#discussUl");
                    count = 0;
                    doubleCount = 0;
                    for(let i=0;i<result.length;++i){
                        if(result[i].grade===1||result[i].grade==="1"){
                            let $discuss = discussUl.children("li").last().prev().clone(true,true);
                            $discuss.css("display","block");
                            $discuss.children("div").children("div").children("div").eq(1).text(result[i].content);
                            $discuss.children("div").children("div").children("div").last().children("button").first().val(result[i].username);
                            $discuss.children("div").children("div").children("div").last().attr("id",result[i].discussId);
                            $discuss.children("div").children("div").children("div").last().children("button").first().addClass("t"+result[i].grade);
                            $discuss.children("div").children("div").children("div").first().children("div").children("img").first().attr("src",result[i].headImage);
                            if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].username)){
                                $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().css({"font-size":"15px"})
                            }
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().text(result[i].username);
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("span").last().text(changeTimeXiu(result[i].writeTime));
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().next().remove();
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").last().remove();
                            if(discussUl.children("li").length<3){
                                discussUl.prepend($discuss);
                            }else {
                                discussUl.children("li").eq(count++).after($discuss);
                            }
                        }else if(result[i].grade===2||result[i].grade==="2"){
                            let doubleDiscussUl = $("#doubleDiscussUl");
                            let $doubleDiscuss = doubleDiscussUl.children("li").last().prev().clone(true,true);
                            $doubleDiscuss.css("display","block");
                            $doubleDiscuss.children("div").children("div").children("div").children("img").attr("src",result[i].headImage);
                            if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].username)){
                                $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().css({"font-size":"15px"})
                            }
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().text(result[i].username);
                            if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].reply)){
                                $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().css("font-size","15px");
                            }
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().text(result[i].reply);
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("span").last().text(changeTimeXiu(result[i].writeTime));
                            $doubleDiscuss.children("div").children("div").first().next().text(result[i].content);
                            $doubleDiscuss.children("div").children("div").last().attr("id",result[i].discussId);
                            $doubleDiscuss.children("div").children("div").last().children("button").first().addClass("t"+result[i].grade);
                            $doubleDiscuss.children("div").children("div").last().children("button").val(result[i].username);
                            let att = "#"+result[i].attachmentDiscussId;
                            if(!$(att).children("button").first().hasClass("t2")){
                                if($(att).next().children("li").length<3){
                                    $(att).next().prepend($doubleDiscuss);
                                }else{
                                    $(att).next().children("li").last().prev().before($doubleDiscuss);
                                }
                            }else{
                                $(att).parent().parent().parent().children("li").last().prev().before($doubleDiscuss);
                            }
                        }
                    }
                }else{
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "t8",
                            time: "1500",
                            icon: 2,
                            anim: 6,
                            area: ['200px', '50px'],
                            offset: "10px"
                        });
                    });
                }
            },
            error:function (){
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("出现异常请重试", {
                        skin: "msg-pwd",
                        id: "t9",
                        time: "1500",
                        icon: 2,
                        anim: 6,
                        area: ['200px', '50px'],
                        offset: "10px"
                    });
                });
            }
        });
    }
});

function replyButton(data){
    let $li = $("#doubleDiscussLi").clone(true,true);
    $li.css("display","block");
    $li.attr("id","");
    $(data).parent().next().prepend($li);
    $(data).parent().next().children("li").first().children("div").last().children("input").first().val($(data).val());
    $(data).parent().next().children("li").first().children("div").last().children("input").first().attr("id",$(data).parent().attr("id"));
    $(data).css("display","none");
    $(data).attr("disabled","true");
}

function replyButtonDouble(data){
   let $li = $("#doubleDiscussLi").clone(true,true);
    $li.css("display","block");
    $li.attr("id","");
    $(data).parent().parent().parent().after($li);
    $(data).parent().parent().parent().next().children("div").last().children("input").first().val($(data).val());
    $(data).parent().parent().parent().next().children("div").last().children("input").first().attr("id",$(data).parent().attr("id"));
    $(data).css("display","none");
    $(data).attr("disabled","true");
}

function discussClose(data){
    if($(data).parent().parent().index()===0){
        $(data).parent().parent().parent().prev().children("button").first().css("display","block");
        $(data).parent().parent().parent().prev().children("button").first().removeAttr("disabled");
    }else{
        $(data).parent().parent().prev().children("div").first().children("div").last().children("button").first().css("display","block");
        $(data).parent().parent().prev().children("div").first().children("div").last().children("button").first().removeAttr("disabled");
    }
    $(data).parent().parent().remove();
}

function emailNotNull(data){
    let regex = /^\w+((-\w)|(\.\w))*@[A-Za-z0-9]+((\.\|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    if($(data).val()===""||$(data).val()===undefined||$(data).val()===null){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg("邮箱不能为空!", {
                skin: "msg-pwd",
                id: "t11",
                time: "1500",
                icon: 2,
                anim: 6,
                area: ['200px', '50px'],
                offset: "10px"
            });
        });
        return false;
    }else if(!regex.test($(data).val())){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg('邮箱的格式不正确！', {
                skin: "msg-pwd",
                id: "t10",
                time: "1500",
                icon: 2,
                area: ['200px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        return false;
    }else if($(data).val().length > 25){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg('邮箱长度过长！！', {
                skin: "msg-pwd",
                id: "t10",
                time: "1500",
                icon: 2,
                area: ['200px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        return false;
    }
    return true;
}

function headImage(data){
    if($(data).val()===""||$(data).val()===null||$(data).val()===undefined){
        return true;
    }else if($(data).val().trim().lastIndexOf("\.")===-1
        ||!($(data).val().trim().substring($(data).val().trim().lastIndexOf("\.")+1)==="jpeg"
        ||$(data).val().trim().substring($(data).val().trim().lastIndexOf("\.")+1)==="jpg"
        ||$(data).val().trim().substring($(data).val().trim().lastIndexOf("\.")+1)==="png")){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg("图片后缀格式需为jpeg、jpg、png)!", {
                skin: "msg-pwd",
                id: "t9",
                time: "1500",
                area: ['350px', '50px'],
                icon: 2,
                anim: 6,
                offset: "10px"
            });
        });
        return false;
    }
    return true;
}


function nameNotNUll(data){
    if($(data).val()===""||$(data).val()===undefined||$(data).val()===null){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg("用户名不能为空!", {
                skin: "msg-pwd",
                id: "t8",
                time: "1500",
                icon: 2,
                anim: 6,
                area: ['200px', '50px'],
                offset: "10px"
            });
        });
        return false;
    }else if(nameInput.val().trim().length>15){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg("用户名不能大于15个字符!", {
                skin: "msg-pwd",
                id: "t24",
                time: "1500",
                icon: 2,
                anim: 6,
                area: ['300px', '50px'],
                offset: "10px"
            });
        });
        return false;
    }
    return true;
}


function dUser(data){
    return nameNotNUll(data);
}

function dEmail(data){
    return emailNotNull(data);
}

function
dImage(data){
    return headImage(data);
}

function submitDiscuss(data){
    //访客
    let condition = 1;
    if($(".head-sculpture").children().length!==0){
        //登录者
        condition = 2;
    }
 let html = $(data).parent().children("textarea").val().trim();
 if(html===""||html===null||html===undefined){
     layui.use('layer', function () {
         let layer = layui.layer;
         layer.msg("文本不能为空!", {
             skin: "msg-pwd",
             id: "t7",
             time: "1500",
             icon: 2,
             anim: 6,
             area: ['200px', '50px'],
             offset: "10px"
         });
     });
 }else{
     // let directionU = $(data).parent().prev().children("div").first().children("div").first().children("input");
     let directionN = $(data).parent().prev().children("div").first().children("div").last().children("input");
     let directionE = $(data).parent().prev().children("div").last().children("div").first().children("input");
     let directionI = $(data).parent().prev().children("div").last().children("div").last().children("input");
     let dName = dUser(directionN);
     let result;
     if(condition===1){
         if(!dName){
             result = false;
             setTimeout(function (){
                 printDouble(dEmail(directionE),directionI);
             },1000);
         }else{
             result = printDouble(dEmail(directionE),directionI);
         }
     }else if (condition===2){
         result = true;
     }else{
         result = false;
         layui.use('layer', function () {
             let layer = layui.layer;
             layer.msg("请刷新，重新进行尝试！", {
                 skin: "msg-pwd",
                 id: "t10",
                 time: "1500",
                 icon: 2,
                 anim: 6,
                 area: ['250px', '50px'],
                 offset: "10px"
             });
         });
     }

     if(result){
         $.post({
             url:"api/admin/v1/post/addComment",
             data:
                 {
                     "username": condition===2?$(".inputOne > .InputName").children("input").first().val():$(data).parent().prev().children("div").first().children("div").last().children("input").val().trim(),
                     "email":condition===2?$(".inputTwo > .InputEmail").children("input").first().val():$(data).parent().prev().children("div").last().children("div").first().children("input").val().trim(),
                     "headImg":condition===2?$(".inputTwo > .InputHeader").children("input").first().val():$(data).parent().prev().children("div").last().children("div").last().children("input").val().trim(),
                     "content":html,
                     "dailyId":$("#daily").val(),
                     "reply":$(data).parent().children("input").first().val(),
                     "state":"2",
                     "attachment":$(data).parent().children("input").first().attr("id"),
                     "condition": condition
                },
             dataType:"json",
             success:function (data){
                 if(data.state===0||data.state==="0"){
                     layui.use('layer', function () {
                         let layer = layui.layer;
                         layer.msg(data.message, {
                             skin: "msg-pwd",
                             id: "t5",
                             time: "1500",
                             icon: 2,
                             anim: 6,
                             area: ['300px', '50px'],
                             offset: "10px"
                         });
                     });
                 }else if(data.state==="1"||data.state===1){
                     layui.use('layer', function () {
                         let layer = layui.layer;
                         layer.msg(data.message, {
                             skin: "msg-pwd",
                             id: "t6",
                             time: "1500",
                             area: ['100px', '50px'],
                             icon: 1,
                             offset: "10px"
                         });
                     });
                     if(address!==undefined&&address!==""&&address!==null){
                         panelClose();
                         getData(address);
                         address.children("span").text(parseInt(address.children("span").text())+parseInt(1));
                     }
                 }
             },
             error:function (){
                 layui.use('layer', function () {
                     let layer = layui.layer;
                     layer.msg("出现异常状态，评论发布失败！", {
                         skin: "msg-pwd",
                         id: "t7",
                         time: "1500",
                         area: ['250px', '50px'],
                         icon: 2,
                         anim: 6,
                         offset: "10px"
                     });
                 });
             }
         });
     }
 }
}

function printDouble(emailFlag,imageFlag){
    if(!emailFlag){
        setTimeout(function (){
            dImage(imageFlag);
        },1000);
        return false;
    }else{
        return dImage(imageFlag);
    }
}

function panelClose(){
    let disc = $("#discussUl");
    $(".header").css("position","relative");
    $(".informal").css({"position":"relative","margin-top":"40px"});
    $(".panel").css("display","none");
    $(".footer").css("display","block");
    $(".panel-double").css("display","none");
    let dis = disc.children("li");
    let len = dis.length - 2;
    for(let i=0;i<len;++i){
        disc.children("li").first().remove();
    }
}

function loginSuccess(){
    let headSculpture = $(".head-sculpture");
    if(headSculpture.children().length!==0){
        $(".InputName").children("input").first().val(headSculpture.children("div").children("div").children("ul").children("li").first().children("p").first().text());
        $(".InputEmail").children("input").first().val("xxxxxxxxxxx@qq.com");
        $(".InputHeader").children("input").first().val(headSculpture.children("img").first().attr("src"));
        $(".inputOne").css("display","none");
        $(".inputTwo").css("display","none");
    }
}

function getData(res){
    $(".header").css("position","fixed");
    $(".panel-double").css({"display":"flex"});
    $(".informal").css({"position":"fixed","margin-top":"130px"});
    $(".footer").css("display","none")
    $("#daily").val(res.next().children("input").val());
    $(".panel").css("display","flex");
    loginSuccess();
    $.get({
        url:"/api/admin/v1/get/getDiscuss",
        data:{"dailyId":res.next().children("input").val()},
        dataType:"json",
        success:function (data){
            if(data.state===1||data.state==="1"){
                let result =JSON.parse(data.data);
                let discussUl = $("#discussUl");
                count = 0;
                doubleCount = 0;
                for(let i=0;i<result.length;++i){
                    if(result[i].grade===1||result[i].grade==="1"){
                        let $discuss = discussUl.children("li").last().prev().clone(true,true);
                        $discuss.css("display","block");
                        $discuss.children("div").children("div").children("div").eq(1).text(result[i].content);
                        $discuss.children("div").children("div").children("div").last().children("button").first().val(result[i].username);
                        $discuss.children("div").children("div").children("div").last().attr("id",result[i].discussId);
                        $discuss.children("div").children("div").children("div").first().children("div").children("img").first().attr("src",result[i].headImage);
                        if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].reply)){
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().css("font-size","15px");
                        }
                        $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().text(result[i].username);
                        $discuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().text(result[i].reply);
                        $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("span").first().text(changeTimeXiu(result[i].writeTime));
                        $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().next().remove();
                        $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").last().remove();
                        if(discussUl.children("li").length<3){
                            discussUl.prepend($discuss);
                        }else {
                            discussUl.children("li").eq(count++).after($discuss);
                        }
                    }else if(result[i].grade===2||result[i].grade==="2"){
                        let doubleDiscussUl = $("#doubleDiscussUl");
                        let $doubleDiscuss = doubleDiscussUl.children("li").last().prev().clone(true,true);
                        $doubleDiscuss.css("display","block");
                        $doubleDiscuss.children("div").children("div").children("div").children("img").attr("src",result[i].headImage);
                        if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].username)){
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().css("font-size","15px");
                        }
                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().text(result[i].username);
                        if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].reply)){
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().css("font-size","15px");
                        }
                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().text(result[i].reply);
                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("span").last().text(changeTimeXiu(result[i].writeTime));
                        $doubleDiscuss.children("div").children("div").first().next().text(result[i].content);
                        $doubleDiscuss.children("div").children("div").last().attr("id",result[i].discussId);
                        $doubleDiscuss.children("div").children("div").last().children("button").first().addClass("t"+result[i].grade);
                        $doubleDiscuss.children("div").children("div").last().children("button").val(result[i].username);
                        let att = "#"+result[i].attachmentDiscussId;
                        if(!$(att).children("button").first().hasClass("t2")){
                            if($(att).next().children("li").length<3){
                                $(att).next().prepend($doubleDiscuss);
                            }else{
                                $(att).next().children("li").last().prev().before($doubleDiscuss);
                            }
                        }else{
                            $(att).parent().parent().parent().children("li").last().prev().before($doubleDiscuss);
                        }
                    }
                }
            }else{
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg(data.message, {
                        skin: "msg-pwd",
                        id: "t8",
                        time: "1500",
                        icon: 2,
                        anim: 6,
                        area: ['200px', '50px'],
                        offset: "10px"
                    });
                });
            }
        },
        error:function (){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("出现异常请重试", {
                    skin: "msg-pwd",
                    id: "t9",
                    time: "1500",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
        }
    });
}

function changeTimeXiu(data){
    let dataTime = new Date(data);
    let hours = dataTime.getHours()<10?"0"+dataTime.getHours():dataTime.getHours();
    let minutes = dataTime.getUTCMinutes()<10?"0"+dataTime.getUTCMinutes():dataTime.getUTCMinutes();
    let seconds = dataTime.getUTCSeconds()<10?"0"+dataTime.getUTCSeconds():dataTime.getUTCSeconds();
    let day = dataTime.getHours()===0?dataTime.getUTCDate()+parseInt(1):dataTime.getUTCDate();
    return dataTime.getUTCFullYear()+"-"+(dataTime.getUTCMonth()+1)+"-"+day+" "+hours+":"+minutes+":"+seconds;
}