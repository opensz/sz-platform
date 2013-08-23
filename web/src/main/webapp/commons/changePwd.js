COM.changePwd = function (path)
{
    $(document).bind('keydown.changepassword', function (e)
    {
        if (e.keyCode == 13)
        {
            doChangePassword();
        }
    });

    if (!window.changePasswordWin)
    {
        var changePasswordPanle = $("<form></form>");
        changePasswordPanle.ligerForm({
            fields: [
                { display: '旧密码', name: 'OldPassword', type: 'password', validate: { maxlength: 50, required: true, messages: { required: '请输入密码'}} },
                { display: '新密码', name: 'NewPassword', type: 'password', validate: { maxlength: 50, required: true, messages: { required: '请输入密码'}} },
                { display: '确认密码', name: 'NewPassword2', type: 'password', validate: { maxlength: 50, required: true, equalTo: '#NewPassword',messages: { required: '请输入密码', equalTo: '两次密码输入不一致'}} }
            ]
        });

        //验证
        jQuery.metadata.setType("attr", "validate");
        COM.validate(changePasswordPanle);

        window.changePasswordWin = $.ligerDialog.open({
            width: 400,
            height: 190, top: 200,
            isResize: true,
            title: '用户修改密码',
            target: changePasswordPanle,
            buttons: [
            { text: '确定', onclick: function ()
            {
                doChangePassword();
            }
            },
            { text: '取消', onclick: function ()
            {
                window.changePasswordWin.hide();
                $(document).unbind('keydown.changepassword');
            }
            }
            ]
        });
    }
    else
    {
        window.changePasswordWin.show();
        $("#OldPassword").val("");
        $("#NewPassword").val("");
        $("#NewPassword2").val("");
        
    }

    function doChangePassword()
    {
        var OldPassword = $("#OldPassword").val();
        var LoginPassword = $("#NewPassword").val();
        if (changePasswordPanle.valid())
        {
            $.ajax({
                type: "POST",
                url: path,
                data: { OldPassword: OldPassword, LoginPassword: LoginPassword },
                dataType: 'json',success: function (result){
                	if(result.msg.indexOf("成功")>=0)
                    	COM.showSuccess(result.msg);
                    else{
                    	 COM.showError(result.msg);
                    }
                    window.changePasswordWin.hide();
                    $(document).unbind('keydown.changepassword');
                },
                error: function (result)
                {
                    COM.showError(result);
                }
            });
        }
    }


};