 loopy();
    function loopy() {
        var userName = "welove"
        var password = prompt("密码","welove520");
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
                    document.cookie = "token="+result.data+ ";expires=10"+"; path=/";
                } else{
                    window.location.href = "./login.html";
                }
             }
         });
    }


