$(document).ready(function (e) {
    getSituations();

    function getSituations() {

        $(".imload").fadeIn("1000");
        $('#tbodySituation').html('');
        var r = jsRoutes.controllers.SituationController.getProfils();
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                if (data.result == "ok") {

                    afficheLesSituations(data.profils,"#tbodySituation");

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

    $(".ajouter").click(createSituation);

    function createSituation(){
        var $profil=$("#profil").val();
        if(/^ {0,}$/i.test($profil)){
            alert("ajout impossible");
        }else{
            $('.ajouter').attr("disabled",true);
            $(".imload").fadeIn("1000");
            var r = jsRoutes.controllers.SituationController.ajouterProfil();
            $.ajax({url: r.url, type: r.type,
                data:{
                    "profil" :$profil
                },
                success: function (data) {
                    if(data.result=="ok"){
                        $("#profil").val("");
                        $('.ajouter').attr("disabled",false);
                        afficheUneSituation(data.profil,"#tbodySituation","all");
                        $('.error-situation').empty();
                    }else if(data.result=="nok"){
                        $('.error-situation').empty();
                        $('.error-situation').append("la situation a deja ete créée");
                         $('.ajouter').attr("disabled",false);
                    }
                    $(".imload").fadeOut("1000");
                },
                error: function(data){
                    $('.error-situation').empty();
                    $('.error-situation').append("une erreur s'est produite veuiller reessayer");
                     $('.ajouter').attr("disabled",false);
                     $(".imload").fadeOut("1000");
                }
            });
        }
    };

    $('.formulaire-situation').submit(function(){
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
        var r = jsRoutes.controllers.SituationController.getProfilById($id);
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                $this.parent("td").parent("tr").addClass("modifier-tr");
                $(".modifier").attr("name",$id);
                $("#profil").val(data.profil.profil);
                $(".imload").fadeOut("1000");
            }
        });
    });

    $(".widget-box").on("click",".supprimer-icon",function(){
        var $this=$(this);
        $("tr").removeClass("supprimer-tr")
        $this.parent("td").parent("tr").addClass("supprimer-tr");
    });

    $(".supprimer-le-situation").click(function(){
        $(".imload").fadeIn("1000");
        var $this=$(this);
        var $td=$(".supprimer-tr").find(".action");
        var $id=$td.attr("name");
        var r = jsRoutes.controllers.SituationController.supprimerProfil($id);
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                if(data.result=="ok"){
                    $('.supprimer-tr').empty();
                    afficheUneSituation(data.profil,".supprimer-tr","ok");
                }else if(data.result=="nok"){
                    alert("supprission non aboutie");
                }
                $("tr").removeClass("supprimer-tr");
                $("#supprimer-un-situation").modal("hide");
                $(".imload").fadeOut("1000");
            },
            error: function(data){
                $('.error-situation').empty();
                $('.error-situation').append("suppression non aboutie");
                $("#supprimer-un-situation").modal("hide");
                $(".imload").fadeOut("1000");
            }
        });
    });

    $(".widget-box").on("click",".modifier",modifierSituation);

    $(".widget-box").on("click",".annuler",function(){
        $(".modifier-tr").removeClass("modifier-tr");
        $(".modifier").attr("name","");
        $('.modifier').attr("disabled",true);
        $('.ajouter').attr("disabled",false);
        $('.annuler').attr("disabled",true);
        $("#profil").val("");
    });

    function afficheUneSituation(item,content,type){
        var html='';
        if(type=="all"){
            html += '<tr">';
        }
        html += '<td>' + item.profil + '</td>';
        if(item.etat==true){
             html += '<td class="status"> <span class="label label-success">Actif</span> </td>';
        }else{
            html += '<td class="status"> <span class="label label-danger">Inactif</span> </td>';
        }
        html += '<td class="action" name="'+item.id +'"> <i class="icon icon-edit modifier-icon ud"></i> <i class="icon icon-trash supprimer-icon ud" data-toggle="modal" data-target="#supprimer-un-situation"></i></td>';
        if(type=="all"){
            html += '</tr">';
        }
        $(content).append(html);

    }

    function afficheLesSituations(items,content){
        for (var i in items) {
            afficheUneSituation(items[i],content,"all");
        }
    }

    function modifierSituation(){
        var $profil=$("#profil").val();
        var $id=$(".modifier").attr("name");
        if(/^ {0,}$/i.test($profil)){
            alert("champ invalid");
        }else{
            $('.modifier').attr("disabled",true);
            $(".imload").fadeIn("1000");
            var r = jsRoutes.controllers.SituationController.modifierProfil($id);
            $.ajax({url: r.url, type: r.type,
                data:{
                    "profil" :$profil
                },
                success: function (data) {
                    if(data.result=="ok"){
                        $("#profil").val("");
                        $('.modifier').attr("disabled",true);
                        $('.annuler').attr("disabled",true);
                        $(".modifier").attr("name","");
                        $('.modifier-tr').empty();
                        afficheUneSituation(data.profil,".modifier-tr","ok");
                        $('.error-situation').empty();
                    }else if(data.result=="nok"){
                        $('.error-situation').empty();
                        $('.error-situation').append("le profil a deja ete créée");
                    }
                    $(".modifier-tr").removeClass("modifier-tr");
                    $(".imload").fadeOut("1000");
                },
                error: function(data){
                    $('.error-situation').empty();
                    $('.error-situation').append("une erreur s'est produite veuiller reessayer");
                    $(".imload").fadeOut("1000");
                }
            });
        }
    };
});

