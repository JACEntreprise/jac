$("document").ready(function(){
    $("#pub-recharge").on("submit",".form-pub-new-pub",function(){
        var $this=$(this);
        var $reload=$this.find('.loading');
        var $bouton=$this.find('.liste-membre-jaime');
        $reload.removeClass("hidden");
        var id=$this.find(".element-cache").val();
        $.ajax({
            type: "POST",
            url: $(this).attr("action"),
            data:{
                contenu:$this.find("#contenu").val()
            },
            dataType: 'html',
            success: function(data){
                $("#pub-"+id).empty();
                $("#pub-"+id).append(data);
                $this.find("#contenu").val("");
                $reload.addClass("hidden");
                $bouton.addClass("hidden");
            },
            error: function(data){
                $reload.addClass("hidden");
            }
        });
        return false;
    });

    $("#la-pub").on("submit",".form-pub-new-pub",function(){
        var $this=$(this);
        $this.find('.loading').removeClass("hidden");
        $.ajax({
            type: "POST",
            url: $this.attr("action"),
            data:{
                contenu:$this.find("#contenu").val()
            },
            dataType: 'html',
            success: function(data){
                $("#la-pub").empty();
                $("#la-pub").append(data);
            }
        });
        return false;
    });

    $(".liste-membre-jaime").hide();
    $(".contenu-personnelle").on("mouseenter",".affiche-liste-aime-pub",function(){
        $(this).find(".liste-membre-jaime").fadeIn(500);
    });

    $(".contenu-personnelle").on("mouseleave",".affiche-liste-aime-pub",function(){
            $(this).find(".liste-membre-jaime").fadeOut(500);
    });

    $(".contenu-personnelle").on("click",".aime-la-publication",function(){
            var $this=$(this);
            var $zone=$this.parents(".comment-jaime-la-pub");
            var $load=$zone.find(".loading");
            $load.removeClass("hidden");
            $.ajax({
                type: "GET",
                url: $this.attr("href"),
                data:{
                    ok: "ok"
                },
                dataType: 'html',
                success: function(data){
                    $zone.empty();
                    $zone.append(data);
                },
                error: function(data){
                    $load.addClass("hidden");
                }
            });
            return false;
    });
})