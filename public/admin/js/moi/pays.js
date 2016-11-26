$(document).ready(function (e) {
    getPays();

    function getPays() {

        $(".imload").fadeIn("1000");
        $('#tbodyPays').html('');
        var r = jsRoutes.controllers.PaysController.getPays();
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                if (data.result == "ok") {

                    afficheLesPays(data.pays,"#tbodyPays");

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

    $(".ajouter").click(createPays);

    function createPays(){
        var $pays=$("#pays").val();
        if(/^ {0,}$/i.test($pays)){
            alert("ajout impossible");
        }else{
            $('.ajouter').attr("disabled",true);
            $(".imload").fadeIn("1000");
            var r = jsRoutes.controllers.PaysController.ajouterPays();
            $.ajax({url: r.url, type: r.type,
                data:{
                    "pays" :$pays
                },
                success: function (data) {
                    if(data.result=="ok"){
                        $("#pays").val("");
                        $('.ajouter').attr("disabled",false);
                        afficheUnPays(data.pays,"#tbodyPays","all");
                        $('.error-pays').empty();
                    }else if(data.result=="nok"){
                        $('.error-pays').empty();
                        $('.error-pays').append("le pays a deja ete créée");
                         $('.ajouter').attr("disabled",false);
                    }
                    $(".imload").fadeOut("1000");
                },
                error: function(data){
                    $('.error-pays').empty();
                    $('.error-pays').append("une erreur s'est produite veuiller reessayer");
                     $('.ajouter').attr("disabled",false);
                     $(".imload").fadeOut("1000");
                }
            });
        }
    };

    $('.formulaire-pays').submit(function(){
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
        var r = jsRoutes.controllers.PaysController.getPaysById($id);
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                $this.parent("td").parent("tr").addClass("modifier-tr");
                $(".modifier").attr("name",$id);
                $("#pays").val(data.pays.pays);
                $(".imload").fadeOut("1000");
            }
        });
    });

    $(".widget-box").on("click",".supprimer-icon",function(){
        var $this=$(this);
        $("tr").removeClass("supprimer-tr")
        $this.parent("td").parent("tr").addClass("supprimer-tr");
    });

    $(".supprimer-le-pays").click(function(){
        $(".imload").fadeIn("1000");
        var $this=$(this);
        var $td=$(".supprimer-tr").find(".action");
        var $id=$td.attr("name");
        var r = jsRoutes.controllers.PaysController.supprimerPays($id);
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                if(data.result=="ok"){
                    $('.supprimer-tr').empty();
                    afficheUnPays(data.pays,".supprimer-tr","ok");
                }else if(data.result=="nok"){
                    alert("supprission non aboutie");
                }
                $("tr").removeClass("supprimer-tr");
                $("#supprimer-un-pays").modal("hide");
                $(".imload").fadeOut("1000");
            },
            error: function(data){
                $('.error-pays').empty();
                $('.error-pays').append("suppression non aboutie");
                $("#supprimer-un-pays").modal("hide");
                $(".imload").fadeOut("1000");
            }
        });
    });

    $(".widget-box").on("click",".modifier",modifierPays);

    $(".widget-box").on("click",".annuler",function(){
        $(".modifier-tr").removeClass("modifier-tr");
        $(".modifier").attr("name","");
        $('.modifier').attr("disabled",true);
        $('.ajouter').attr("disabled",false);
        $('.annuler').attr("disabled",true);
        $("#pays").val("");
    });

    function afficheUnPays(item,content,type){
        var html='';
        if(type=="all"){
            html += '<tr">';
        }
        html += '<td>' + item.pays + '</td>';
        if(item.etat==true){
             html += '<td class="status"> <span class="label label-success">Actif</span> </td>';
        }else{
            html += '<td class="status"> <span class="label label-danger">Inactif</span> </td>';
        }
        html += '<td class="action" name="'+item.id +'"> <i class="icon icon-edit modifier-icon ud"></i> <i class="icon icon-trash supprimer-icon ud" data-toggle="modal" data-target="#supprimer-un-pays"></i></td>';
        if(type=="all"){
            html += '</tr">';
        }
        $(content).append(html);

    }

    function afficheLesPays(items,content){
        for (var i in items) {
            afficheUnPays(items[i],content,"all");
        }
    }

    function modifierPays(){
        var $pays=$("#pays").val();
        var $id=$(".modifier").attr("name");
        if(/^ {0,}$/i.test($pays)){
            alert("champ invalid");
        }else{
            $('.modifier').attr("disabled",true);
            $(".imload").fadeIn("1000");
            var r = jsRoutes.controllers.PaysController.modifierPays($id);
            $.ajax({url: r.url, type: r.type,
                data:{
                    "pays" :$pays
                },
                success: function (data) {
                    if(data.result=="ok"){
                        $("#pays").val("");
                        $('.modifier').attr("disabled",true);
                        $('.annuler').attr("disabled",true);
                        $(".modifier").attr("name","");
                        $('.modifier-tr').empty();
                        afficheUnPays(data.pays,".modifier-tr","ok");
                        $('.error-pays').empty();
                    }else if(data.result=="nok"){
                        $('.error-pays').empty();
                        $('.error-pays').append("le pays a deja ete créée");
                    }
                    $(".modifier-tr").removeClass("modifier-tr");
                    $(".imload").fadeOut("1000");
                },
                error: function(data){
                    $('.error-pays').empty();
                    $('.error-pays').append("une erreur s'est produite veuiller reessayer");
                    $(".imload").fadeOut("1000");
                }
            });
        }
    };
});

