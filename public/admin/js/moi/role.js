$(document).ready(function (e) {
    getRoles();

    function getRoles() {

        $(".imload").fadeIn("1000");
        $('#tbodyRole').html('');
        var r = jsRoutes.controllers.RoleController.getRoles();
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                if (data.result == "ok") {

                    var roles = data.roles;
                    for (var i in roles) {

                        var html = '';
                        html += '<tr">';
                        html += '<td>' + roles[i].role + '</td>';
                        html += '<td>' + roles[i].code + '</td>';
                        if(roles[i].etat==true){
                             html += '<td class="status"> <span class="label label-success">Actif</span> </td>';
                        }else{
                            html += '<td class="status"> <span class="label label-danger">Inactif</span> </td>';
                        }
                        html += '<td class="action" name="'+roles[i].id +'"> <i class="icon icon-edit modifier-icon ud"></i> <i class="icon icon-trash supprimer-icon ud" data-toggle="modal" data-target="#supprimer-un-role"></i></td>';
                        html += '</tr>';
                        $('#tbodyRole').append(html);

                    }

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

    $(".ajouter").click(createRole);

    function createRole(){
        var $role=$("#role").val();
        var $code=$("#code").val();
        if(/^ {0,}$/i.test($role) || /^ {0,}$/i.test($code)){
            alert("ok");
        }else{
            $('.ajouter').attr("disabled",true);
            $(".imload").fadeIn("1000");
            var r = jsRoutes.controllers.RoleController.ajouterRole();
            $.ajax({url: r.url, type: r.type,
                data:{
                    "role" :$role,
                    "code" :$code,
                },
                success: function (data) {
                    if(data.result=="ok"){
                        $("#role").val("");
                        $("#code").val("");
                        $('.ajouter').attr("disabled",false);
                        var roles = data.role;
                        var html = '';
                        html += '<tr">';
                        html += '<td>' + roles.role + '</td>';
                        html += '<td>' + roles.code + '</td>';
                        if(roles.etat==true){
                             html += '<td class="status"> <span class="label label-success">Actif</span> </td>';
                        }else{
                            html += '<td class="status"> <span class="label label-danger">Inactif</span> </td>';
                        }
                        html += '<td class="action" name="'+roles.id +'"> <i class="icon icon-edit modifier-icon ud"></i> <i class="icon icon-trash supprimer-icon ud" data-toggle="modal" data-target="#supprimer-un-role"></i></td>';
                        html += '</tr>';
                        $('.error-role').empty();
                        $('#tbodyRole').append(html);
                    }else if(data.result=="nok"){
                        $('.error-role').empty();
                        $('.error-role').append("le role a deja ete créée");
                         $('.ajouter').attr("disabled",false);
                    }
                    $(".imload").fadeOut("1000");
                },
                error: function(data){
                    $('.error-role').empty();
                    $('.error-role').append("une erreur s'est produite veuiller reessayer");
                     $('.ajouter').attr("disabled",false);
                     $(".imload").fadeOut("1000");
                }
            });
        }
    };

    $('.formulaire-role').submit(function(){
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
        var r = jsRoutes.controllers.RoleController.getRoleById($id);
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                $this.parent("td").parent("tr").addClass("modifier-tr");
                $(".modifier").attr("name",$id);
                $("#role").val(data.role.role);
                $("#code").val(data.role.code);
                $(".imload").fadeOut("1000");
            }
        });
    });

    $(".widget-box").on("click",".supprimer-icon",function(){
        var $this=$(this);
        $("tr").removeClass("supprimer-tr")
        $this.parent("td").parent("tr").addClass("supprimer-tr");
    });

    $(".supprimer-le-role").click(function(){
        $(".imload").fadeIn("1000");
        var $this=$(this);
        var $td=$(".supprimer-tr").find(".action");
        var $id=$td.attr("name");
        var r = jsRoutes.controllers.RoleController.supprimerRole($id);
        $.ajax({url: r.url, type: r.type,
            success: function (data) {
                $("tr").removeClass("supprimer-tr");
                if(data.result=="ok"){
                    var roles = data.role;
                    var html = '';
                    html += '<td>' + roles.role + '</td>';
                    html += '<td>' + roles.code + '</td>';
                    if(roles.etat==true){
                         html += '<td class="status"> <span class="label label-success">Actif</span> </td>';
                    }else{
                        html += '<td class="status"> <span class="label label-danger">Inactif</span> </td>';
                    }
                    html += '<td class="action" name="'+roles.id +'"> <i class="icon icon-edit modifier-icon ud"></i> <i class="icon icon-trash supprimer-icon ud" data-toggle="modal" data-target="#supprimer-un-role"></i></td>';
                    $('.supprimer-tr').empty();
                    $('.supprimer-tr').append(html);
                }else if(data.result=="nok"){
                    alert("supprission non aboutie");
                }
                $("#supprimer-un-role").modal("hide");
                $(".imload").fadeOut("1000");
            },
            error: function(data){
                $('.error-role').empty();
                $('.error-role').append("suppression non aboutie");
                $("#supprimer-un-role").modal("hide");
                $(".imload").fadeOut("1000");
            }
        });
    });

    $(".widget-box").on("click",".modifier",modifierRole);

    $(".widget-box").on("click",".annuler",function(){
        $(".modifier-tr").removeClass("modifier-tr");
        $(".modifier").attr("name","");
        $('.modifier').attr("disabled",true);
        $('.ajouter').attr("disabled",false);
        $('.annuler').attr("disabled",true);
        $("#role").val("");
        $("#code").val("");
    });

    function modifierRole(){
        var $role=$("#role").val();
        var $code=$("#code").val();
        var $id=$(".modifier").attr("name");
        if(/^ {0,}$/i.test($role) || /^ {0,}$/i.test($code)){
            alert("ok");
        }else{
            $('.modifier').attr("disabled",true);
            $(".imload").fadeIn("1000");
            var r = jsRoutes.controllers.RoleController.modifierRole($id);
            $.ajax({url: r.url, type: r.type,
                data:{
                    "role" :$role,
                    "code" :$code,
                },
                success: function (data) {
                    if(data.result=="ok"){
                        $("#role").val("");
                        $("#code").val("");
                        $('.modifier').attr("disabled",true);
                        $('.annuler').attr("disabled",true);
                        $(".modifier-tr").removeClass("modifier-tr");
                        $(".modifier").attr("name","");
                        var roles = data.role;
                        var html = '';
                        html += '<td>' + roles.role + '</td>';
                        html += '<td>' + roles.code + '</td>';
                        if(roles.etat==true){
                             html += '<td class="status"> <span class="label label-success">Actif</span> </td>';
                        }else{
                            html += '<td class="status"> <span class="label label-danger">Inactif</span> </td>';
                        }
                        html += '<td class="action" name="'+roles.id +'"> <i class="icon icon-edit modifier-icon ud"></i> <i class="icon icon-trash supprimer-icon ud" data-toggle="modal" data-target="#supprimer-un-role"></i></td>';
                        $('.modifier-tr').empty();
                        $('.error-role').empty();
                        $('.modifier-tr').append(html);
                    }else if(data.result=="nok"){
                        $('.error-role').empty();
                        $('.error-role').append("le role a deja ete créée");
                    }
                    $(".imload").fadeOut("1000");
                },
                error: function(data){
                    $('.error-role').empty();
                    $('.error-role').append("une erreur s'est produite veuiller reessayer");
                    $(".imload").fadeOut("1000");
                }
            });
        }
    };
});

