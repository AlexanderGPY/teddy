 loopy();
    function loopy() {
        var userName = "welove"
        var password = prompt("请输入密码");
        data = {}

         $.ajax({
             url: "/teddy/login?userName="+userName+"&password="+password,
             type: "POST",
             processData: false,
             contentType: false,
             async: false,
             data: data,
             success: function(result) {
                if(result.state == "success"){
                    window.location.href = "./page/taskm/monitor.html";
                    document.cookie = "token="+result.data+ ";expires="+"; path=/";
                } else{
                    window.location.href = "./login.html";
                }
             }
         });
    }


