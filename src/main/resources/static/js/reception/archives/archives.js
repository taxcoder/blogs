let say;
let dataInput;
let major;
let uuidInput;
let nameInput;
let emailInput;
let imageInput;
let address;
let button;
let count;
let doubleCount;
function showQR() {
    $(".qr").css("display", "flex");
    $(".qr .QR").css("display", "block");
}

function changeImageZ() {
    let imageZ = $(".images > div");
    if (imageZ.css("background-image").indexOf("Alipay") === -1) {
        imageZ.css("background-image", "url('https://www.tanxiangblog.com/images/Alipay.png')");
    }
}


function praise(res) {
    $.ajax({
        url: "/api/admin/v1/put/praise/" + $("#archivesId").val().trim(),
        dataType: "json",
        type: "put",
        success: function (data) {
            if (data.state === 1 || data.state === "1") {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg(data.message, {
                        skin: "msg-pwd",
                        id: "msg70",
                        time: "3000",
                        icon: 1,
                        area: ['250px', '50px'],
                        offset: "10px"
                    });
                });
                $(res).parent().parent().parent().children("div").first().next()
                    .children("p").last().children("span").first().text(parseInt($(res).parent().parent().parent().children("div").first().next().children("p").last().children("span").first().text()) + parseInt(1));
            } else {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg(data.message, {
                        skin: "msg-pwd",
                        id: "msg71",
                        time: "3000",
                        icon: 2,
                        anim: 6,
                        area: ['350px', '50px'],
                        offset: "10px"
                    });
                });
            }
        },
        error: function () {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("出现异常,请重试！", {
                    skin: "msg-pwd",
                    id: "msg72",
                    time: "3000",
                    icon: 2,
                    anim: 6,
                    area: ['250px', '50px'],
                    offset: "10px"
                });
            });
        }
    })
}

function changeImageW() {
    let imageW = $(".images > div");
    if (imageW.css("background-image").indexOf("weChat") === -1) {
        imageW.css("background-image", "url('https://www.tanxiangblog.com/images/WeChat.jpg')");
    }
}

function closeQR() {
    $(".qr").css("display", "none");
    $(".qr .QR").css("display", "none");
}

$(function () {
    const  ONE = 1;
    const COUNT = 1059;
    say = $(".say > p");
    dataInput = $("#dataInput");
    major = $("#major");
    uuidInput = $(".InputUUID > input");
    nameInput = $(".usernameI");
    emailInput = $(".emailI");
    imageInput = $(".imageI");
    if (say.text() === null || say.text() === "" || say.text() === undefined) {
        say.css({"background-color": "white", "padding": "0", "margin": "0"});
    }

    major.html(dataInput.text());
    major.children("code").remove();
    dataInput.remove();
    major.contents().filter(function() {
        return this.nodeType === 3
    }).remove();
    // 获取文章的评论信息
    getData();

    $(".archives .archives-body .archives-middle .archives-middle-genre .archives-middle-nav-main").css({"background-image":"url(https://api.oss.tanxiangblog.com/rotation/images/img"+randomData()+".jpg)","background-repeat":"no-repeat"});

    function randomData(){
        let res = Math.random();
        if(res === 0){
          return ran(parseInt(res * COUNT + 1));
        }
        return ran(parseInt(res * COUNT));
    }

    function ran(random){
        if(random < 10){
            return "000"+random;
        }
        if(random < 100){
            return "00"+random;
        }
        if(random < 1000){
            return "0"+random;
        }
        return random;
    }

    $("#RealSubmit").on("click",function () {
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
                    url:"/api/admin/v1/post/addArchivesComment",
                    data:
                        {
                            "username":condition===2?$(".inputOne > .InputName").children("input").first().val():nameInput.val().trim(),
                            "email":condition===2?$(".inputTwo > .InputEmail").children("input").first().val():emailInput.val().trim(),
                            "headImg":condition===2?$(".inputTwo > .InputHeader").children("input").first().val():imageInput.val().trim(),
                            "content":html,
                            "archivesId":$("#archivesId").val(),
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
                            location.reload();
                        }
                    },
                    error:function (){
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg("出现异常状态，评论发布失败！", {
                                skin: "msg-pwd",
                                id: "t7",
                                time: "1500",
                                area: ['300px', '50px'],
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
    uuidInput.on("blur",function (){

    });


    nameInput.on("blur",function (){
        nameNotNUll();
    });


    emailInput.on("blur",function (){
        emailNotNull();
    });

    imageInput.on("blur",function (){
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
        let day = dataTime.getHours()===0?dataTime.getUTCDate()+parseInt(ONE):dataTime.getUTCDate();
        return dataTime.getUTCFullYear()+"-"+(dataTime.getUTCMonth()+1)+"-"+day+" "+hours+":"+minutes+":"+seconds;
    }


    function getData(){
        loginSuccess();
        $.get({
            url:"/api/admin/v1/get/getArchivesDiscuss",
            data:{"archivesId":$("#archivesId").val()},
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
                            $discuss.children("div").children("div").children("div").last().attr("id",result[i].archivesDiscussId);
                            $discuss.children("div").children("div").children("div").last().children("button").first().addClass("t"+result[i].grade);
                            $discuss.children("div").children("div").children("div").first().children("div").children("img").first().attr("src",result[i].headImage);
                            if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].username)){
                                $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().css({"font-size":"15px"})
                            }
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().text(result[i].username);
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().attr("id","comment-"+result[i].archivesDiscussId);
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
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().attr("id","comment-"+result[i].archivesDiscussId);
                            if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].reply)){
                                $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().css("font-size","15px");
                            }
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().text(result[i].reply);
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("span").last().text(changeTimeXiu(result[i].writeTime));
                            $doubleDiscuss.children("div").children("div").first().next().text(result[i].content);
                            $doubleDiscuss.children("div").children("div").last().attr("id",result[i].archivesDiscussId);
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
                    layer.msg("出现异常，请重试!", {
                        skin: "msg-pwd",
                        id: "t9",
                        time: "1500",
                        icon: 2,
                        anim: 6,
                        area: ['250px', '50px'],
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
                url:"/api/admin/v1/post/addArchivesComment",
                data:
                    {
                        "username": condition===2?$(".inputOne > .InputName").children("input").first().val():$(data).parent().prev().children("div").first().children("div").last().children("input").val().trim(),
                        "email":condition===2?$(".inputTwo > .InputEmail").children("input").first().val():$(data).parent().prev().children("div").last().children("div").first().children("input").val().trim(),
                        "headImg":condition===2?$(".inputTwo > .InputHeader").children("input").first().val():$(data).parent().prev().children("div").last().children("div").last().children("input").val().trim(),
                        "content":html,
                        "archivesId":$("#archivesId").val(),
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
                        location.reload();
                    }
                },
                error:function (){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg("出现异常状态，评论发布失败！", {
                            skin: "msg-pwd",
                            id: "t7",
                            time: "1500",
                            area: ['300px', '50px'],
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

function getData(){
    loginSuccess();
    $.get({
        url:"/api/admin/v1/get/getArchivesDiscuss",
        data:{"archivesId":$("#archivesId").val()},
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
                        $discuss.children("div").children("div").children("div").last().attr("id",result[i].archivesDiscussId);
                        $discuss.children("div").children("div").children("div").first().children("div").children("img").first().attr("src",result[i].headImage);
                        if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].reply)){
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().css("font-size","15px");
                        }
                        $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().text(result[i].username);
                        $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().attr("id","comment-"+result[i].archivesDiscussId);
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
                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().attr("id","comment-"+result[i].archivesDiscussId);
                        if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].reply)){
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().css("font-size","15px");
                        }
                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().text(result[i].reply);
                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("span").last().text(changeTimeXiu(result[i].writeTime));
                        $doubleDiscuss.children("div").children("div").first().next().text(result[i].content);
                        $doubleDiscuss.children("div").children("div").last().attr("id",result[i].archivesDiscussId);
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
                layer.msg("出现异常,请重试!", {
                    skin: "msg-pwd",
                    id: "t9",
                    time: "1500",
                    icon: 2,
                    anim: 6,
                    area: ['250px', '50px'],
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

function skipPage(){
    setTimeout(function (){
        if(window.location.hash.indexOf("#")!==-1){
            window.location.href = window.location.hash;
        }
    },500);
}