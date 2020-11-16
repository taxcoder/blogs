let n_pwd = false;
let n_affPwd = false;
let n_code = false;
let n_loginName = false;
let n_email = false;

function emailBlur() {
    let regex = /^\w+((-\w)|(\.\w))*@[A-Za-z0-9]+((\.\|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    if (!(regex.test($("#email").val()))) {
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg('邮箱的格式不正确！', {
                skin: "msg-pwd",
                id: "msg37",
                time: "5000",
                icon: 2,
                area: ['200px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
       n_email = false;
    } else {
        $.post({
            url: "/api/admin/v1/post/queryEmail",
            data: {"email": document.getElementById("email").value},
            dataType: "json",
            success: function (data) {
                if (data.state === 0 || data.state === "0") {
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg38",
                            time: "5000",
                            icon: 2,
                            area: ['200px', '50px'],
                            anim: 6,
                            offset: "10px"
                        });
                    });
                    n_email = false;
                }else{
                    n_email = true;
                }
            }, error: function () {
                n_email = false;
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg('未知异常！', {
                        skin: "msg-pwd",
                        id: "msg39",
                        time: "5000",
                        icon: 2,
                        area: ['200px', '50px'],
                        anim: 6,
                        offset: "10px"
                    });
                });
            }
        });
    }
}

function loginNameBlur() {
    let loginName = document.getElementById("loginName");
    if (loginName.value === "" || loginName.value === null) {
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg("登录名不能为空!", {
                skin: "msg-pwd",
                id: "msg43",
                time: "2500",
                icon: 2,
                area: ['300px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        n_loginName = false;
    }else{
        n_loginName = true;
    }
}

function getCode() {
    let loginName = document.getElementById("loginName");
    let getEmail = document.getElementById("email");
    if (loginName.value === "" || loginName.value === null) {
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg("你的登录名为空!", {
                skin: "msg-pwd",
                id: "msg25",
                time: "2500",
                icon: 2,
                area: ['300px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        n_loginName = false;
    } else if (getEmail.value === "" || getEmail.value === null) {
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg("你的注册邮箱为空!", {
                skin: "msg-pwd",
                id: "msg42",
                time: "2500",
                icon: 2,
                area: ['300px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        n_email = false;
    } else {
        $.post({
            url: "/api/admin/v1/post/getCode",
            data: {"email": getEmail.value, "loginName": loginName.value},
            dataType: "json",
            success: function (data) {
                if (data.state === 1 || data.message === "1") {
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg40",
                            time: "3000",
                            icon: 1,
                            area: ['200px', '50px'],
                            offset: "10px"
                        });
                    });
                    n_code = true;
                    n_loginName = true;
                    n_email = true;
                    window.open("https://mail.qq.com/", "_blank", "height=600,width=500,top=250,left=1200");
                }else{
                    n_code = false;
                    n_loginName = false;
                    n_email = false;
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg44",
                            time: "3000",
                            icon: 2,
                            anim:6,
                            area: ['200px', '50px'],
                            offset: "10px"
                        });
                    });
                }

            },
            error: function () {
                n_code = false;
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg('邮件发送失败，未知异常！', {
                        skin: "msg-pwd",
                        id: "msg41",
                        time: "5000",
                        icon: 2,
                        area: ['200px', '50px'],
                        anim: 6,
                        offset: "10px"
                    });
                });
            }
        });
        $(".forgetPwd").css("height","410px");
        $(".forgetPwd-middle").css("height","270px");
        $(".none-password").css("display","inline-block");
    }
}

function isPasswordForget() {
    let regex = /^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,16})$/;
    let nPassword = $("#nPassword").val();
    if(nPassword === null || nPassword === ""){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg('密码不能为空！', {
                skin: "msg-pwd",
                id: "msg48",
                time: "5000",
                icon: 2,
                area: ['200px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        n_pwd = false;
    }else if(!regex.test(nPassword.trim())){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg('密码需在8到16个字符之间,且必须包含数字、字母,区分大小写！', {
                skin: "msg-pwd",
                id: "msg49",
                time: "5000",
                icon: 2,
                area: ['500px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        n_pwd = false;
    }else{
        n_pwd = true;
    }
}

function isAffPasswordForget() {
    if($("#nPassword").val() !== $("#affPassword").val()){
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg('确认密码与密码不符！', {
                skin: "msg-pwd",
                id: "msg49",
                time: "5000",
                icon: 2,
                area: ['200px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
        n_affPwd = false;
    }else{
        n_affPwd = true;
    }
}

function forgetSubmit() {
    if(n_code && n_affPwd && n_email && n_pwd && n_loginName){
        $.post({
            url:"/api/admin/v1/post/updatePassword",
            data:{
                "loginName":$("#loginName").val(),
                "email":$("#email").val(),
                "code":$("#code").val(),
                "newPassword":$("#nPassword").val()
            },
            dataType:"json",
            success:function (data) {
                if(data.state === 1 || data.state === "1"){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg50",
                            time: "5000",
                            icon: 1,
                            area: ['200px', '50px'],
                            offset: "10px"
                        });
                    });
                }else{
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg51",
                            time: "5000",
                            icon: 2,
                            area: ['400px', '50px'],
                            anim: 6,
                            offset: "10px"
                        });
                    });
                }
            },
            error:function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg('出现异常，请刷新重新尝试！', {
                        skin: "msg-pwd",
                        id: "msg52",
                        time: "5000",
                        icon: 2,
                        area: ['400px', '50px'],
                        anim: 6,
                        offset: "10px"
                    });
                });
            }
        });
    }else{
        layui.use('layer', function () {
            let layer = layui.layer;
            layer.msg('请保证全部填写正确在进行改密提交！', {
                skin: "msg-pwd",
                id: "msg53",
                time: "5000",
                icon: 2,
                area: ['400px', '50px'],
                anim: 6,
                offset: "10px"
            });
        });
    }
}