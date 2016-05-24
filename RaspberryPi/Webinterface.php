<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <title>Webinterface</title>

        <!-- Bootstrap -->
        <link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <script type="text/javascript">
            function request(paras) { 
                var url = location.href; 
                var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
                var paraObj = {} 
                for (i=0; j=paraString[i]; i++)
                    paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
                var returnValue = paraObj[paras.toLowerCase()]; 
                if(typeof(returnValue)=="undefined")
                    return ""; 
                else
                    return returnValue; 
            } 
            var uid=request("uid");
            $.get("top.php", function(data) {
                var uids=data.split(",");
                for(var i in uids) {
                    var option=$("<option>").val(uids[i]).text(uids[i])
                    if(uids[i]==uid) {
                        option.attr("selected", "selected");
                    }
                    option.appendTo("#uid-selector");
                }
            });
            $(document).ready(function() {
                $("#uid-submit").click(function() {
                    $.post("top.php?uid="+$("#uid-selector").val(), function(data) {
                        $("#uid-info").html(data);
                    });
                });
            });
            
        </script>
    </head>
    <body>
        <div class="container">
            <div class="page-header">
                <h3>Webinterface</h3>
            </div>
            <select id="uid-selector" class="form-control">
            </select>
            <button id="uid-submit" class="btn btn-success">Query</button>
            <div id="uid-info" class="alert-info">
                
            </div>
        </div>
    </body>
</html>