let board;
let webInput;
let nameInput;
let emailInput;
let imageInput;
let desc;
let Image;
let web;
let name;
let Email;
$(function (){
    const ONE = 1;
    Email = $(".Email > input");
    name = $(".Name > input");
    web = $(".Web > input");
    Image = $(".Image > input");
    desc = $(".Desc > input");
    webInput = $(".websiteI");
    nameInput = $(".usernameI");
    emailInput = $(".emailI");
    imageInput = $(".imageI");
    board = $(".board");
    //第一次时，进行窗口的变化
    getData();

    // 邮件
    web.on("blur",function (){
        urlNotNull();
    });

    Image.on("blur",function (){
        ImageNotNull();
    });

    name.on("blur",function (){
        NameNotNUll();
    });

    desc.on("blur",function (){
        descNotNull();
    });

    Email.on("blur",function (){
        EmailNotNull();
    });

    function NameNotNUll(){
        if(name.val().trim()===""||name.val().trim()===undefined||name.val().trim()===null){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("用户名不能为空!", {
                    skin: "msg-pwd",
                    id: "t65",
                    time: "1500",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
            return false;
        }else if(name.val().trim().length>10){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("用户名不能大于10个字符!", {
                    skin: "msg-pwd",
                    id: "t66",
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

    function descNotNull(){
        if(desc.val().trim()===""||desc.val().trim()===null||desc.val().trim()===undefined){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("描述不可以为空!", {
                    skin: "msg-pwd",
                    id: "t60",
                    time: "3000",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
            return false;
        }
        return true;
    }

    function ImageNotNull(){
        let pre = Image.val().trim().substring(Image.val().trim().lastIndexOf("\.")+1);
        if(Image.val().trim()===""||Image.val().trim()===null||Image.val().trim()===undefined){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("头像地址不能为空!", {
                    skin: "msg-pwd",
                    id: "t61",
                    time: "1500",
                    area: ['350px', '50px'],
                    icon: 2,
                    anim: 6,
                    offset: "10px"
                });
            });
            return false;
        }else if(Image.val().trim().lastIndexOf("\.")===-1||!(pre==="jpeg"||pre==="jpg"||pre==="png")){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("图片后缀格式需为jpeg、jpg、png!", {
                    skin: "msg-pwd",
                    id: "t62",
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

    function urlNotNull(){
        let urlRegex = /(https?:\/\/)(([a-z]+\.)?)+([a-zA-Z0-9]+-?)+\.+[a-z]/;
        if(web.val().trim()===""||web.val().trim()===undefined||web.val().trim()===null){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("网站地址为空!", {
                    skin: "msg-pwd",
                    id: "t63",
                    time: "1500",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
            return false;
        }else if(!urlRegex.test(web.val().trim())){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("网站地址格式错误!", {
                    skin: "msg-pwd",
                    id: "t64",
                    time: "1500",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
            return false;
        }
        return true;
    }

    function EmailNotNull(){
        let regex = /^\w+((-\w)|(\.\w))*@[A-Za-z0-9]+((\.\|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if(!(Email.val().trim()===""||Email.val().trim()===undefined||Email.val().trim()===null) && !regex.test(Email.val().trim())){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('邮箱的格式不正确！', {
                    skin: "msg-pwd",
                    id: "t35",
                    time: "1500",
                    icon: 2,
                    area: ['200px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
            return false;
        }else if(Email.val().trim().length > 35){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('邮箱长度过长！', {
                    skin: "msg-pwd",
                    id: "t36",
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

    $(".friendLinkSubmit > button").on("click",function (){
        if(!urlNotNull()){
            return;
        }else if(!ImageNotNull()){
            return;
        }else if(!NameNotNUll()){
            return;
        }else if(!descNotNull()){
            return;
        }else if(!EmailNotNull()){
            return;
        }else{
            $.post({
                url:"/api/admin/v1/post/addFriendLink",
                data:{
                    "web":web.val().trim(),
                    "image":Image.val().trim(),
                    "username":name.val().trim(),
                    "desc":desc.val().trim(),
                    "email":Email.val().trim(),
                },
                dataType:"json",
                success:function (data){
                    if(data.state===1||data.state==="1"){
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg(data.message, {
                                skin: "msg-pwd",
                                id: "t70",
                                time: "1500",
                                icon: 1,
                                area: ['200px', '50px'],
                                offset: "10px"
                            });
                        });
                        setTimeout(function (){
                            $(".friendLink").css("display","none");
                            web.val("");
                            Image.val("");
                            name.val("");
                            desc.val("");
                            Email.val("");
                        },1500);
                    }else{
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg(data.message, {
                                skin: "msg-pwd",
                                id: "t71",
                                time: "1500",
                                icon: 2,
                                area: ['200px', '50px'],
                                anim: 6,
                                offset: "10px"
                            });
                        });
                    }
                },
                error:function (){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg("发布异常！", {
                            skin: "msg-pwd",
                            id: "t72",
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

    // 邮件
    $(".friendLinkClose .friendLinkCloseTop > i").on("click",function (){
        $(".friendLink").css("display","none");
        web.val("");
        Image.val("");
        name.val("");
        desc.val("");
        Email.val("");
    });

    $(".board .boardContent .adminInfo .siteInformation h3 > button").on("click",function (){
        $(".friendLink").css("display","flex");
    });

    $("#RealSubmit").on("click",function () {
        let html = $("#RealText").val().trim();
        if(html===null||html===""||html===undefined){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("文本不允许为空!", {
                    skin: "msg-pwd",
                    id: "t30",
                    time: "3000",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
        }else{
            let result;
            let nameFlag = nameNotNUll();
            if(!nameFlag){
                result = false;
                setTimeout(function (){
                    print(emailNotNull());
                },1000);
            }else{
                result = print(emailNotNull());
            }
            if(result){
                $.post({
                    url:"/api/admin/v1/post/addMessageComment",
                    data:
                        {
                            "website":webInput.val().trim(),
                            "username":nameInput.val().trim(),
                            "email":emailInput.val().trim(),
                            "headImg":imageInput.val().trim(),
                            "content":html,
                            "reply":null,
                            "state":"1",
                            "attachment":null,
                        },
                    dataType:"json",
                    success:function (data){
                        if(data.state===0||data.state==="0"){
                            layui.use('layer', function () {
                                let layer = layui.layer;
                                layer.msg(data.message, {
                                    skin: "msg-pwd",
                                    id: "t31",
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
                                    id: "t32",
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
                                id: "t33",
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
    webInput.on("blur",function (){
        urlIsNot();
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
                    id: "t34",
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
                    id: "t35",
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
                    id: "t36",
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
                    id: "t37",
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

    function urlIsNot(){
        let urlRegex = /(https?:\/\/)(([a-z]+\.)?)+([a-zA-Z0-9]+-?)+\.+[a-z]/;
        if(!(webInput.val().trim()===""||webInput.val().trim()===undefined||webInput.val().trim()===null) && !urlRegex.test(webInput.val().trim())){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("网站地址格式错误!", {
                    skin: "msg-pwd",
                    id: "t38",
                    time: "1500",
                    icon: 2,
                    anim: 6,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
            });
        }
    }


    function nameNotNUll(){
        if(nameInput.val().trim()===""||nameInput.val().trim()===undefined||nameInput.val().trim()===null){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("用户名不能为空!", {
                    skin: "msg-pwd",
                    id: "t39",
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
                    id: "t40",
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


    function changeTimeXiu(data){
        let dataTime = new Date(data);
        let hours = dataTime.getHours()<10?"0"+dataTime.getHours():dataTime.getHours();
        let minutes = dataTime.getUTCMinutes()<10?"0"+dataTime.getUTCMinutes():dataTime.getUTCMinutes();
        let seconds = dataTime.getUTCSeconds()<10?"0"+dataTime.getUTCSeconds():dataTime.getUTCSeconds();
        let day = dataTime.getHours()===0?dataTime.getUTCDate()+ONE:dataTime.getUTCDate();
        return dataTime.getUTCFullYear()+"-"+(dataTime.getUTCMonth()+ONE)+"-"+day+" "+hours+":"+minutes+":"+seconds;
    }


    function getData(){
        $.get({
            url:"/api/admin/v1/get/boardDiscuss",
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
                            $discuss.children("div").children("div").children("div").last().attr("id",result[i].boardDiscussId);
                            $discuss.children("div").children("div").children("div").last().children("button").first().addClass("t"+result[i].grade);
                            $discuss.children("div").children("div").children("div").first().children("div").children("img").first().attr("src",result[i].headImage);
                            if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].username)){
                                $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().css({"font-size":"15px"})
                            }
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().children("a").first().attr("href",result[i].websiteAddress);
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().children("a").first().text(result[i].username);
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
                                $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().children("a").first().css({"font-size":"15px"})
                            }
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().children("a").first().text(result[i].username);
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().children("a").first().attr("href",result[i].websiteAddress);
                            if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].reply)){
                                $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().css("font-size","15px");
                            }
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().text(result[i].reply);
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("span").last().text(changeTimeXiu(result[i].writeTime));
                            $doubleDiscuss.children("div").children("div").first().next().text(result[i].content);
                            $doubleDiscuss.children("div").children("div").last().attr("id",result[i].boardDiscussId);
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
                }
            },
            error:function (){
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg("出现异常，请重试!", {
                        skin: "msg-pwd",
                        id: "t42",
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
                id: "t43",
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
                id: "t44",
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
                id: "t45",
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
                id: "t46",
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
                id: "t47",
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
    let html = $(data).parent().children("textarea").val().trim();
    if(html===""||html===null||html===undefined){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg("文本不能为空!", {
                skin: "msg-pwd",
                id: "t48",
                time: "1500",
                icon: 2,
                anim: 6,
                area: ['200px', '50px'],
                offset: "10px"
            });
        });
    }else{
        let directionN = $(data).parent().prev().children("div").first().children("div").last().children("input");
        let directionE = $(data).parent().prev().children("div").last().children("div").first().children("input");
        let directionI = $(data).parent().prev().children("div").last().children("div").last().children("input");
        let dName = dUser(directionN);
        let result;
        if(!dName){
            result = false;
            setTimeout(function (){
                printDouble(dEmail(directionE),directionI);
            },1000);
        }else{
            result = printDouble(dEmail(directionE),directionI);
        }
        if(result){
            $.post({
                url:"/api/admin/v1/post/addMessageComment",
                data:
                    {
                        "website": $(data).parent().prev().children("div").first().children("div").first().children("input").val().trim(),
                        "username": $(data).parent().prev().children("div").first().children("div").last().children("input").val().trim(),
                        "email":$(data).parent().prev().children("div").last().children("div").first().children("input").val().trim(),
                        "headImg":$(data).parent().prev().children("div").last().children("div").last().children("input").val().trim(),
                        "content":html,
                        "reply":$(data).parent().children("input").first().val(),
                        "state":"2",
                        "attachment":$(data).parent().children("input").first().attr("id"),
                    },
                dataType:"json",
                success:function (data){
                    if(data.state===0||data.state==="0"){
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg(data.message, {
                                skin: "msg-pwd",
                                id: "t49",
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
                                id: "t50",
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
                            id: "t51",
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

function getData(){
    $.get({
        url:"/api/admin/v1/get/boardDiscuss",
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
                        $discuss.children("div").children("div").children("div").last().attr("id",result[i].boardDiscussId);
                        $discuss.children("div").children("div").children("div").first().children("div").children("img").first().attr("src",result[i].headImage);
                        if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].reply)){
                            $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().css("font-size","15px");
                        }
                        $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().children("a").first().attr("href",result[i].websiteAddress);
                        $discuss.children("div").children("div").children("div").first().children("div").children("div").first().children("div").first().children("span").first().children("a").first().text(result[i].username);
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
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().children("a").first().css("font-size","15px");
                        }
                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().children("a").first().text(result[i].username);
                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().children("a").first().attr("href",result[i].websiteAddress);
                        if(/.*[\u4e00-\u9fa5]+.*$/.test(result[i].reply)){
                            $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().css("font-size","15px");
                        }

                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("div").first().children("span").first().next().next().text(result[i].reply);
                        $doubleDiscuss.children("div").children("div").children("div").children("div").first().children("span").last().text(changeTimeXiu(result[i].writeTime));
                        $doubleDiscuss.children("div").children("div").first().next().text(result[i].content);
                        $doubleDiscuss.children("div").children("div").last().attr("id",result[i].boardDiscussId);
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
                        id: "t52",
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
                    id: "t53",
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
    let day = dataTime.getHours()===0?dataTime.getUTCDate()+ONE:dataTime.getUTCDate();
    return dataTime.getUTCFullYear()+"-"+(dataTime.getUTCMonth()+ONE)+"-"+day+" "+hours+":"+minutes+":"+seconds;
}

