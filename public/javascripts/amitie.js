$("document").ready(function(){
    $("#pub-recharge").on("click",".amitie-action",function(){
        var $load= $(".content-suggestion-liste-accueil").find(".loading");
        $load.removeClass("hidden");
        $.ajax({
            type: "GET",
            url: $(this).attr("href"),
            data: "ok",
            dataType: 'html',
            success: function(data){
                $(".content-suggestion-liste-accueil").empty();
                $(".content-suggestion-liste-accueil").append(data);
            },
            error: function(data){
                $load.addClass("hidden");
            }
        });
        return false;
    });

    $("#pub-recharge").on("click",".amitie-action-ac-refus",function(){
        var $load= $(".content-demande-liste-accueil").find(".loading");
        $load.removeClass("hidden");
        $.ajax({
            type: "GET",
            url: $(this).attr("href"),
            data: "ok",
            dataType: 'html',
            success: function(data){
                $(".content-demande-liste-accueil").empty();
                $(".content-demande-liste-accueil").append(data);
            },
            error: function(data){
                $load.addClass("hidden");
            }
        });
        return false;
    });

})