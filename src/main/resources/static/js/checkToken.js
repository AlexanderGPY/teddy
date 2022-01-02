 checkToken();
    function checkToken() {
        var userName = "welove"


         $.ajax({
             url: "/teddy/checkToken?userName="+userName,
             type: "POST",
             async: false,
             success: function(result) {
                if(result.state == "success"){
                    //window.location.href = "./monitor.html";
                } else{
                    window.location.href = "../../login.html";
                }
             }
         });
    }