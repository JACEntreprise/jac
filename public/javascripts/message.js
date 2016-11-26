$("document").ready(function(){
    $(".message-center").on("submit",".formulaire-message_complet",function(){
        var $this=$(this);
        var $reload=$this.find('.loading');
        $reload.removeClass("hidden");
        $.ajax({
            type: "POST",
            url: $this.attr("action"),
            data:{
                contenu:$this.find("#contenu").val(),
                destinataire:$this.find("#destinataire").val()
            },
            dataType: 'html',
            success: function(data){
                $this.find("#contenu").val("");
                $("#destinataire").val("");
                $("#destinataire").removeClass("hidden");
                $(".le-destinataire").addClass("hidden");
                $reload.addClass("hidden");
            },
            error: function(data){
                $reload.addClass("hidden");
            }
        });
        return false;
    });

})