$(function() {

    // 提交任务
    $('#c2SubmitBtn').click(function(){

        var config = "spark.driver.memory=" + $('#c2DriverMemory').val() +
            ";spark.executor.memory="+ $('#c2ExecutorMemory').val() +
            ";spark.executor.cores="+ $('#c2ExecutorCores').val() +
            ";spark.executor.instances="+ $('#c2ExecutorNums').val() +
            ";spark.yarn.queue="+ $('#c2YarnQueue').val();

        var data = {};
        data["name"]            = $('#c2NameInput').val();
        data["appResource"]    = $('#c2JarInput').val();
        data["mainClass"]      = $('#c2ClassInput').val();
        data["config"]          = config;
        data["args"]            = $('#c2ArgsInput').val();
        data["email"]           = $('#c2EmailInput').val();
        data["webhook"]           = $('#c2WebhookInput').val();
        data["send"]            = $('input.send-email:radio:checked').val();
        data["restart"]         = $('input.restart:radio:checked').val();
        
        
        data = JSON.stringify(data);
        $.ajax({
            url: "/job/submit",
            type: "POST",
            //processData: false,
            contentType: "application/json",
            async: false,
            data: data,
            success: function(result) {
                alert(result.data);
            }
        });
    });
    // 更新任务配置
       $('#c2UpdateBtn').click(function(){
   
           var config = "spark.driver.memory=" + $('#c2DriverMemory').val() +
               ";spark.executor.memory="+ $('#c2ExecutorMemory').val() +
               ";spark.executor.cores="+ $('#c2ExecutorCores').val() +
               ";spark.executor.instances="+ $('#c2ExecutorNums').val() +
               ";spark.yarn.queue="+ $('#c2YarnQueue').val();
   
           var data = {};
           data["name"]            = $('#c2NameInput').val();
           data["appResource"]    = $('#c2JarInput').val();
           data["mainClass"]      = $('#c2ClassInput').val();
           data["appId"]      = $('#c2AppIdInput').val();
           data["config"]          = config;
           data["args"]            = $('#c2ArgsInput').val();
           data["email"]           = $('#c2EmailInput').val();
           data["webhook"]           = $('#c2WebhookInput').val();
           data["send"]            = $('input.send-email:radio:checked').val();
           data["restart"]         = $('input.restart:radio:checked').val();
           
           
           data = JSON.stringify(data);
           $.ajax({
               url: "/job/update",
               type: "POST",
               //processData: false,
               contentType: "application/json",
               async: false,
               data: data,
               success: function(result) {
                   alert(result.data);
               }
           });
       });
});

