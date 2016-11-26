$("document").ready(function(){
    $(".profil-profil-entete-center").on("click",".pencil-modifier-profil",function(){
        var $this=$(this);
        var $initial=$this.parents(".pays-secteur");
        $(".form-pays-secteur").addClass("hidden");
        $initial.find(".form-pays-secteur").removeClass("hidden");
        return false;

    });
    $(".profil-profil-entete-center").on("click",".annuler-form-profil",function(){
        var $this=$(this);
        var $initial=$this.parents(".pays-secteur");
        $initial.find(".form-pays-secteur").addClass("hidden");

    });

    $(".profil-profil-entete-center").on("submit",".form-complet-secteur",function(){
        var $this=$(this);
        var $initial=$this.parents(".contenu-content-profile");
        var $load=$initial.find(".loading");
        $load.removeClass("hidden");
        $.ajax({
            type: "POST",
            url: $this.attr("action"),
            data:{
                id:$this.find("#id").val(),
                libelle:"ok"
            },
            dataType: 'html',
            success: function(data){
                $initial.empty();
                $initial.append(data);
            }
        });

        return false;
    });

    $(".profil-profil-entete-center").on("mouseenter",".titre-element",function(){
        $(this).find(".pencil-modifier-profil").removeClass("hidden");
    });
    $(".profil-profil-entete-center").on("mouseleave",".titre-element",function(){
        $(this).find(".pencil-modifier-profil").addClass("hidden");
    });
})