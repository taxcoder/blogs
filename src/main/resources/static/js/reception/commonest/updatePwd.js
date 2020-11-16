let s_code = false;
let s_newPassword = false;
let s_affPassword = false;
 function contentInfo(){
    $(".user").css({"display":"flex","justify-content":"center","align-items":"center"});
    $(".login-input").css("display","none");
    $(".updatePwd").css("display","block");

    $.post({
        url:"/admin/operate/emailSend",
        data:{},
        success:function () {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('邮件已发送，请进入邮箱获取验证码！', {
                    skin: "msg-pwd",
                    id: "msg35",
                    time: "5000",
                    icon: 1,
                    area: ['400px', '50px'],
                    offset: "10px"
                });
            });
            s_code = true;
        },error:function () {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('出现异常，邮件发送失败！', {
                    skin: "msg-pwd",
                    id: "msg24",
                    time: "5000",
                    icon: 2,
                    anim:6,
                    area: ['400px', '50px'],
                    offset: "10px"
                });
            });
            s_code = false;
        }
    });
    window.open("https://mail.qq.com/","_blank","height=600,width=500,top=250,left=1200");
}

function codeBlur() {
    let code = $("#yesCode");
    $.post({
        url:"/admin/operate/correct",
        data:{"code":code.val().trim()},
        dataType:"json",
        success:function (data) {
            if(data.state === 1 || data.state === "1"){
                layer.msg(data.message, {
                    skin: "msg-pwd",
                    id: "msg29",
                    time: "5000",
                    icon: 1,
                    area: ['200px', '50px'],
                    offset: "10px"
                });
                s_code = true;
            }else{
                layer.msg(data.message, {
                    skin: "msg-pwd",
                    id: "msg30",
                    time: "5000",
                    icon: 2,
                    anim:"6",
                    area: ['200px', '50px'],
                    offset: "10px"
                });
                s_code = false;
            }
        },
        error:function () {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("出现异常，请点击修改密码按钮，获取新的验证码！", {
                    skin: "msg-pwd",
                    id: "msg28",
                    time: "4000",
                    icon: 2,
                    anim:"6",
                    area: ['500px', '50px'],
                    offset: "10px"
                });
            });
            s_code =false;
        }
    });

    if(code.val() === null || code.val() === ""){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg(data.message, {
                skin: "msg-pwd",
                id: "msg27",
                time: "5000",
                icon: 1,
                area: ['400px', '50px'],
                offset: "10px"
            });
        });
        s_code = false;
    }
}

function passwordBlur() {
    let regex = /^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,16})$/;
    if (!(regex.test($("#newPassword").val()))) {
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg('密码需在8到16个字符之间,且必须包含数字、字母,区分大小写！', {
                skin: "msg-pwd",
                id: "msg31",
                time: "5000",
                icon: 2,
                area: ['500px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        s_newPassword = false;
    } else {
        s_newPassword = true;
    }
}

function affPasswordBlur() {
    if ($("#newPassword").val() !== $("#newAffPassword").val()) {
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg('确认密码与密码不符！', {
                skin: "msg-pwd",
                id: "msg32",
                time: "5000",
                icon: 2,
                area: ['500px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        s_affPassword = false;
    } else {
        s_affPassword = true;
    }
}

function changePassword() {
     let pwd = $("#newPassword");
    let affPassword = $("#newAffPassword");
    if(s_affPassword && s_code && s_newPassword && (pwd.val() === affPassword.val())){
        $.post({
            url:"/admin/operate/updatePwd",
            data:{"newPassword":pwd.val().trim(),"code":$("#yesCode").val().trim()},
            dataType: "json",
            success:function (data) {
                if(data.state === "1" || data.state === 1){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg34",
                            time: "5000",
                            icon: 1,
                            area: ['300px', '50px'],
                            offset: "10px"
                        });
                    });
                    setTimeout(function () {
                        window.location.replace("/");
                    },1000);
                }else{
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg36`",
                            time: "5000",
                            icon: 2,
                            area: ['300px', '50px'],
                            anim: 6,
                            offset: "10px"
                        });
                    });
                }
            },
            error:function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg('未知异常，请联系管理员！', {
                        skin: "msg-pwd",
                        id: "msg33",
                        time: "5000",
                        icon: 2,
                        area: ['500px', '50px'],
                        anim: 6,
                        offset: "10px"
                    });
                });
            }
        });
    }else{
        let yes = $("#yesCode");
        if(s_code === false && (yes.val()==="" || yes.val()===　null)){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("验证码不能为空", {
                    skin: "msg-pwd",
                    id: "msg45",
                    time: "5000",
                    icon: 2,
                    anim: 6,
                    area: ['300px', '50px'],
                    offset: "10px"
                });
            });
        }else if(s_newPassword === false){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("密码不能为空", {
                    skin: "msg-pwd",
                    id: "msg46",
                    time: "5000",
                    icon: 2,
                    anim: 6,
                    area: ['300px', '50px'],
                    offset: "10px"
                });
            });
        }else if(s_affPassword === false || (pwd.val() !== affPassword.val())){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("确认密码与密码不符！", {
                    skin: "msg-pwd",
                    id: "msg47",
                    time: "5000",
                    icon: 2,
                    anim: 6,
                    area: ['300px', '50px'],
                    offset: "10px"
                });
            });
        }
    }
}
