$(document).ready(function (e) {
    getSecteurs();

    function getSecteurs() {

        $(".imload").fadeIn("1000");
        $('#tbodySecteur').html('');
        var r = jsRoutes.controllers.DomaineController.getDomaines();
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                if (data.result == "ok") {
                    afficheLesSecteurs(data.domaines,"#tbodySecteur");
                } else if (data.result == "nok") {
                    alert(data.message);
                }
                $(".imload").fadeOut("1000");
            },
            error: function (xmlHttpReques, chaineRetourne, objetExeption) {
                if (objetExeption == "Unauthorized") {
                    $(location).attr('href', "/");
                }
                $(".imload").fadeOut("1000");
            }
        });
    }

    $(".ajouter").click(createSecteur);

    function createSecteur(){
        var $secteur=$("#secteur").val();
        if(/^ {0,}$/i.test($secteur)){
            alert("ajout impossible");
        }else{
            $('.ajouter').attr("disabled",true);
            $(".imload").fadeIn("1000");
            var r = jsRoutes.controllers.DomaineController.ajouterDomaine();
            $.ajax({url: r.url, type: r.type,
                data:{
                    "secteur" :$secteur
                },
                success: function (data) {
                    if(data.result=="ok"){
                        $("#secteur").val("");
                        $('.ajouter').attr("disabled",false);
                        afficheUnSecteur(data.domaine,"#tbodySecteur","all");
                        $('.error-secteur').empty();
                    }else if(data.result=="nok"){
                        $('.error-secteur').empty();
                        $('.error-secteur').append("le secteur a deja ete créée");
                         $('.ajouter').attr("disabled",false);
                    }
                    $(".imload").fadeOut("1000");
                },
                error: function(data){
                    $('.error-secteur').empty();
                    $('.error-secteur').append("une erreur s'est produite veuiller reessayer");
                     $('.ajouter').attr("disabled",false);
                     $(".imload").fadeOut("1000");
                }
            });
        }
    };

    $('.formulaire-secteur').submit(function(){
        return false;
    });

    $('.annuler').attr("disabled",true);
    $('.modifier').attr("disabled",true);

    $(".widget-box").on("click",".modifier-icon",function(){
        $(".imload").fadeIn("1000");
        var $this=$(this);
        var $id=$this.parent("td").attr("name");
        $('.ajouter').attr("disabled",true);
        $('.modifier').attr("disabled",false);
        $('.annuler').attr("disabled",false);
        var r = jsRoutes.controllers.DomaineController.getDomaineById($id);
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                $this.parent("td").parent("tr").addClass("modifier-tr");
                $(".modifier").attr("name",$id);
                $("#secteur").val(data.domaine.secteur);
                $(".imload").fadeOut("1000");
            }
        });
    });

    $(".widget-box").on("click",".supprimer-icon",function(){
        var $this=$(this);
        $("tr").removeClass("supprimer-tr")
        $this.parent("td").parent("tr").addClass("supprimer-tr");
    });

    $(".supprimer-le-secteur").click(function(){
        $(".imload").fadeIn("1000");
        var $this=$(this);
        var $td=$(".supprimer-tr").find(".action");
        var $id=$td.attr("name");
        var r = jsRoutes.controllers.DomaineController.supprimerDomaine($id);
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                if(data.result=="ok"){
                    $('.supprimer-tr').empty();
                    afficheUnSecteur(data.domaine,".supprimer-tr","ok");
                }else if(data.result=="nok"){
                    alert("supprission non aboutie");
                }
                $("tr").removeClass("supprimer-tr");
                $("#supprimer-un-secteur").modal("hide");
                $(".imload").fadeOut("1000");
            },
            error: function(data){
                $('.error-secteur').empty();
                $('.error-secteur').append("suppression non aboutie");
                $("#supprimer-un-secteur").modal("hide");
                $(".imload").fadeOut("1000");
            }
        });
    });

    $(".widget-box").on("click",".modifier",modifierSecteur);

    $(".widget-box").on("click",".annuler",function(){
        $(".modifier-tr").removeClass("modifier-tr");
        $(".modifier").attr("name","");
        $('.modifier').attr("disabled",true);
        $('.ajouter').attr("disabled",false);
        $('.annuler').attr("disabled",true);
        $("#secteur").val("");
    });

    function afficheUnSecteur(item,content,type){
        var html='';
        if(type=="all"){
            html += '<tr">';
        }
        html += '<td>' + item.secteur + '</td>';
        if(item.etat==true){
             html += '<td class="status"> <span class="label label-success">Actif</span> </td>';
        }else{
            html += '<td class="status"> <span class="label label-danger">Inactif</span> </td>';
        }
        html += '<td class="action" name="'+item.id +'"> <i class="icon icon-edit modifier-icon ud"></i> <i class="icon icon-trash supprimer-icon ud" data-toggle="modal" data-target="#supprimer-un-secteur"></i></td>';
        if(type=="all"){
            html += '</tr">';
        }
        $(content).append(html);

    }

    function afficheLesSecteurs(items,content){
        for (var i in items) {
            afficheUnSecteur(items[i],content,"all");
        }
    }

    function modifierSecteur(){
        var $secteur=$("#secteur").val();
        var $id=$(".modifier").attr("name");
        if(/^ {0,}$/i.test($secteur)){
            alert("champ invalid");
        }else{
            $('.modifier').attr("disabled",true);
            $(".imload").fadeIn("1000");
            var r = jsRoutes.controllers.DomaineController.modifierDomaine($id);
            $.ajax({url: r.url, type: r.type,
                data:{
                    "secteur" :$secteur
                },
                success: function (data) {
                    if(data.result=="ok"){
                        $("#secteur").val("");
                        $('.modifier').attr("disabled",true);
                        $('.annuler').attr("disabled",true);
                        $(".modifier").attr("name","");
                        $('.modifier-tr').empty();
                        afficheUnSecteur(data.domaine,".modifier-tr","ok");
                        $('.error-secteur').empty();
                    }else if(data.result=="nok"){
                        $('.error-secteur').empty();
                        $('.error-secteur').append("le secteur a deja ete créée");
                    }
                    $(".modifier-tr").removeClass("modifier-tr");
                    $(".imload").fadeOut("1000");
                },
                error: function(data){
                    $('.error-secteur').empty();
                    $('.error-secteur').append("une erreur s'est produite veuiller reessayer");
                    $(".imload").fadeOut("1000");
                }
            });
        }
    };
});

