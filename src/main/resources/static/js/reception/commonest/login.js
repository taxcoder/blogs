let l_loginName = false;
let l_password = true;
let username;
let password;


$(function () {
    username = $("#username");
    password = $("#password");
    username.blur(function () {
        if ((username.val()).trim() === "" || (username.val()).trim() === null) {
            $(".username-input").css({"border": "2px solid red", "border-radius": "5px"});
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg('用户名不能为空!', {
                    skin: "msg-pwd",
                    id: "msg17",
                    time: "5000",
                    icon: 2,
                    area: ['300px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
        } else {
            l_loginName = true;
        }
    });

    username.focus(function () {
        $(".username-input").css({"border": "none", "border-bottom": "1px solid orange"});
    });

    password.blur(function () {
        if ((password.val()).trim() === "" || (password.val()).trim() === null) {
            $(".password-input").css({"border": "2px solid red", "border-radius": "5px"});
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("密码不能为空!", {
                    skin: "msg-pwd",
                    id: "msg18",
                    time: "5000",
                    icon: 2,
                    area: ['300px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
        } else {
            l_password = true;
        }
    });

    password.focus(function () {
        $(".password-input").css({"border": "none", "border-bottom": "1px solid orange"});
    });



    $(".button-login").click(function () {
        receptionLogin();
    });

    function receptionLogin(){
        if (l_password && l_loginName) {
            $.post({
                url: "/login",
                dataType: "json",
                data: {"loginName": (username.val()).trim(), "password": window.md5(password.val())},
                success: function (data) {
                    if (data.state === 1 || data.state === "1") {
                        window.location.replace("/home.html");
                    } else {
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg(data.message, {
                                skin: "msg-pwd",
                                id: "msg20",
                                time: "5000",
                                icon: 2,
                                area: ['300px', '50px'],
                                anim: 6,
                                offset: "10px"
                            });
                        });
                    }
                }, error: function () {
                    layui.use('layer', function () {
                        let layer = layui.layer;
                        layer.msg("发生未知错误，请与管理员联系!", {
                            skin: "msg-pwd",
                            id: "msg21",
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
                layer.msg("登录名和密码都不能为空!", {
                    skin: "msg-pwd",
                    id: "msg22",
                    time: "5000",
                    icon: 2,
                    area: ['300px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
        }
    }

    username.keyup(function () {
            if (event.keyCode === 13) {
                receptionLogin();
            }
    });

    password.keyup(function () {
        if (event.keyCode === 13) {
            receptionLogin();
        }
    });

    $("#activation").click(function () {
        if(username == null || username === ""){
            layui.use('layer', function () {
                let layer = layui.layer;
                layer.msg("登录名和密码都不能为空!", {
                    skin: "msg-pwd",
                    id: "msg54",
                    time: "5000",
                    icon: 2,
                    area: ['300px', '50px'],
                    anim: 6,
                    offset: "10px"
                });
            });
        }else{
            $.post({
                url:"/api/admin/v1/post/againSendMail",
                data:{"loginName":username.val(),"password":password.val()},
                dataType:"json"
                ,success:function (data) {
                    if(data.state === 1 || data.state === "1"){
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg(data.message, {
                                skin: "msg-pwd",
                                id: "msg55",
                                time: "5000",
                                icon: 1,
                                area: ['300px', '50px'],
                                offset: "10px"
                            });
                        });
                    }else{
                        layui.use('layer', function () {
                            let layer = layui.layer;
                            layer.msg(data.message, {
                                skin: "msg-pwd",
                                id: "msg56",
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
                        layer.msg("未知异常，邮件发送失败!", {
                            skin: "msg-pwd",
                            id: "msg57",
                            time: "5000",
                            icon: 2,
                            area: ['300px', '50px'],
                            anim: 6,
                            offset: "10px"
                        });
                    });
                }
            });
        }
    });
});
