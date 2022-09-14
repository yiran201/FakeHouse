// 校验用户名
function checkUsername()
{
    // 获取用户名的值
    var username = $("#username").val();
    // 定义正则

    var reg_username=/^\w{4,10}$/;
    // 判断,给出提示信息

    var flag = reg_username.test(username);
    if(flag)
    {
        // 用户名合法,
        $("#username").css("border","");
    }else{
        // 用户名非法,加一个红色的边框
        $("#username").css("border","1px solid red");

    }
    return flag;
}


// 校验密码
function checkPassword()
{
    // 获取密码的值
    var password = $("#password").val();
    // 定义正则

    var reg_password=/^\w{5,16}$/;
    // 判断,给出提示信息

    var flag = reg_password.test(password);
    if(flag)
    {
        // 密码合法,
        $("#password").css("border","");
    }else{
        // 密码非法,加一个红色的边框
        $("#password").css("border","1px solid red");
    }
    return flag;
}

function checkEmail()
{
    // 获取密码的值
    var email = $("#email").val();

    if (email!=null&&email.length>0)
    {
        return true;
    }
    else
    {
        return false;
    }
}




// 校验验证码
function checkNumber()
{
    // 获取验证码的值
    var check_number = $("#check_number").val();
    // 定义正则

    var reg_bumber=/^\w{4}$/;
    // 判断,给出提示信息

    var flag = reg_bumber.test(check_number);
    if(flag)
    {
        // 验证码合法,
        $("#check_number").css("border","");
    }else{
        // 验证码非法,加一个红色的边框
        $("#check_number").css("border","1px solid red");
    }
    return flag;
}