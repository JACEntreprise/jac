$("document").ready(function(){

    $('#recherche1').autocomplete({
        source : function(requete, reponse){
            $.ajax({
                url : "/auto",
                dataType : 'json',
                data:{
                    element: requete.term
                },
                success : function(data){
                    reponse($.map(data, function(item){
                        return{
                            label:item.name,
                            value: item.name
                        }
                    }));
                }
            });
        },
        minLenght:2
    });


    $('#recherche').keyup(function(){
        var $this=$(this);
        $.ajax({
            type: "GET",
            url: "/recherche-autocompletion",
            data:{
                element: $this.val()
            },
            dataType: 'html',
            success: function(data){
                $("#description").empty();
                $("#description").append(data);
            }
        });
    });

    $('.petit-bloc-2').on("keyup","#destinataire",function(){
        var $this=$(this);
        $.ajax({
            type: "GET",
            url: "/recherche-autocompletion",
            data:{
                element: $this.val()
            },
            dataType: 'html',
            success: function(data){
                $("#description-destinataire").empty();
                $("#description-destinataire").append(data);
                $("#description-destinataire a").attr("href","#");
            }
        });
    });

    $('.petit-bloc-2').on("click","#description-destinataire .item-ami",function(){
        var $this=$(this);
        var idMembre=$this.attr("name");
        var nomProfil=$this.find(".profil-destinataire").val();
        $("#description-destinataire").empty();
        $(".le-destinataire").append('<span>'+nomProfil+' <span class="supprimer-destinataire" aria-label="close">&times;</span></span>');
        $("#destinataire").val(idMembre);
        $("#destinataire").addClass("hidden");
        $(".le-destinataire").removeClass("hidden")

    })
    $('.petit-bloc-2').on("click",".supprimer-destinataire",function(){
        $(".le-destinataire").empty();
        $("#destinataire").val("");
        $("#destinataire").removeClass("hidden");
        $(".le-destinataire").addClass("hidden");
    })

})