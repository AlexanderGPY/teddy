loopy();
    function loopy() {
        var sWord = prompt("请输入密码");
        var password = "welove520";
        var isCancle = false;
        while(sWord!=password){
            if(sWord==null){
                isCancle = true;
                break;
            }else{
                sWord = prompt("密码输入错误");
            }
        }
        if(!isCancle){
//          alert("邀请码输入正确了，欢迎您的光临！");
        }else{
            location.replace("about:blank");
            window.close();
        }
    }

