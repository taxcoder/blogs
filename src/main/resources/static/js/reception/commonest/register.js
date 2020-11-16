let r_login_Name;
let r_user_Name;
let r_password;
let r_affirm_Password;
let r_email;
let r_code;
let t_loginName = false;
let t_username = false;
let t_email = false;
let t_password = false;
let t_affirm_password = false;
let t_code = false;

$(function () {
    r_login_Name = $("#registerLoginName");
    r_user_Name = $("#registerUserName");
    r_password = $("#registerPassword");
    r_affirm_Password = $("#registerAffirmPassword");
    r_email = $("#registerEmail");
    r_code = $("#checkCode");
    r_login_Name.blur(function () {
        $.post({
            url: "/api/admin/v1/post/loginName",
            data: {"loginName": r_login_Name.val()},
            dataType: "json",
            success: function (data) {
                if (data.state === 0 || data.state === "0") {
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg3",
                            time: "5000",
                            icon: 2,
                            area: ['300px', '50px'],
                            anim: 6,
                            offset: "10px"
                        });
                    });
                } else {
                    t_loginName = true;
                }
            },
            error: function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg('出现异常，请重试！', {
                        skin: "msg-pwd",
                        id: "msg4",
                        time: "5000",
                        icon: 2,
                        area: ['300px', '50px'],
                        anim: 6,
                        offset: "10px"
                    });
                });
            }
        });
    });

    r_user_Name.blur(function () {
        $.post({
            url: "/api/admin/v1/post/username",
            data: {"username": r_user_Name.val()},
            dataType: "json",
            success: function (data) {
                if (data.state === 0 || data.state === "0") {
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg5",
                            time: "5000",
                            icon: 2,
                            area: ['300px', '50px'],
                            anim: 6,
                            offset: "10px"
                        });
                    });
                } else {
                    t_username = true;
                }
            },
            error: function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg('出现异常，请重试！', {
                        skin: "msg-pwd",
                        id: "msg6",
                        time: "5000",
                        icon: 2,
                        area: ['300px', '50px'],
                        anim: 6,
                        offset: "10px"
                    });
                });
            }
        });
    });

    r_password.blur(function () {
        let regex = /^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,16})$/;
        if (!(regex.test(r_password.val()))) {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('密码需在8到16个字符之间,且必须包含数字、字母,区分大小写！', {
                    skin: "msg-pwd",
                    id: "msg7",
                    time: "5000",
                    icon: 2,
                    area: ['500px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
        } else {
            t_password = true;
        }
    });

    r_affirm_Password.blur(function () {
        if (r_affirm_Password.val() === r_password.val()) {
            t_affirm_password = true;
        } else {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('确认密码与密码不符！', {
                    skin: "msg-pwd",
                    id: "msg8",
                    time: "5000",
                    icon: 2,
                    area: ['200px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
        }
    });

    r_email.blur(function () {
        let regex = /^\w+((-\w)|(\.\w))*@[A-Za-z0-9]+((\.\|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if (!(regex.test(r_email.val()))) {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('邮箱的格式不正确！', {
                    skin: "msg-pwd",
                    id: "msg9",
                    time: "5000",
                    icon: 2,
                    area: ['200px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
        } else {
            t_email = true;
        }
    });

    $("#codeImg").click(function () {
        $(this).attr("src","/api/admin/v1/get/code"+"?time="+new Date().getTime());
    });



    r_code.blur(function () {
        $.post({
            url:"/api/admin/v1/post/checkCode",
            data:{"code":r_code.val()},
            dataType:"json",
            success:function (data) {
                if(data.state === "0" || data.state === 0){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg10",
                            time: "5000",
                            icon: 2,
                            area: ['300px', '50px'],
                            anim: 6,
                            offset: "10px"
                        });
                    });
                }else if(data.state === 0 || data.state === 1){
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg(data.message, {
                            skin: "msg-pwd",
                            id: "msg11",
                            time: "5000",
                            icon: 1,
                            area: ['300px', '50px'],
                            offset: "10px"
                        });
                    });
                    t_code = true;
                }
            },
            error:function () {
                layui.use('layer', function () {
                    let layer = layui.layer;
                    layer.msg('出现未知错误，请联系管理员！', {
                        skin: "msg-pwd",
                        id: "msg12",
                        time: "5000",
                        icon: 2,
                        area: ['300px', '50px'],
                        anim: 6,
                        offset: "10px"
                    });
                });
            }
        });
    });

    $(".register-btn").click(function () {
        if (t_username && t_loginName && t_password && t_email && t_code && t_affirm_password) {
            $.post({
                url: "/api/admin/v1/post/register",
                data: {
                    "loginName": r_login_Name.val(),
                    "username": r_user_Name.val(),
                    "password": r_password.val(),
                    "email": r_email.val(),
                    "Code":r_code.val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.state === 0 || data.state === "0") {
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg(data.message, {
                                skin: "msg-pwd",
                                id: "msg13",
                                time: "5000",
                                icon: 2,
                                area: ['250px', '50px'],
                                anim: 6,
                                offset: "10px"
                            });
                        });
                    } else if (data.state === 1 || data.state === "1") {
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg(data.message, {
                                skin: "msg-pwd",
                                id: "msg14",
                                time: "5000",
                                icon: 1,
                                area: ['500px', '50px'],
                                anim: 0,
                                offset: "10px"
                            });
                            window.open("https://mail.qq.com/","_blank","height=600,width=500,top=250,left=1200");
                            r_login_Name.val("");
                            r_user_Name.val("");
                            r_password.val("");
                            r_affirm_Password.val("");
                            r_email.val("");
                            r_code.val("");
                            goLogin();
                        });
                    }
                },
                error: function () {
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg('出现异常，请重试！', {
                            skin: "msg-pwd",
                            id: "msg15",
                            time: "5000",
                            icon: 2,
                            area: ['300px', '50px'],
                            anim: 6,
                            offset: "10px"
                        });
                    });
                }
            });
        } else {
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('请全部填写并正确在提交！', {
                    skin: "msg-pwd",
                    id: "msg16",
                    time: "5000",
                    icon: 2,
                    area: ['300px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
        }
    });
});