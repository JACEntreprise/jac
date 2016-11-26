$(document).ready(function(){
    $(".imload").fadeOut(1000);
    $("#complet-info").submit(function(){
            return false;
        });

        $(".content-completer").on("click",".submit-session",function(){
            var $motDePasse=$("#complet-info #motDePasse").val();
            var $verif=true;
            if(/^ {0,}$/i.test($motDePasse)){
                $("#complet-info #motDePasse").addClass("border-bottom-danger");
                $("#complet-info #motDePasse").removeClass("border-bottom-succes");
                $verif=false;
            }else{
                $("#complet-info #motDePasse").removeClass("border-bottom-danger");
                $("#complet-info #motDePasse").addClass("border-bottom-succes");
            }
            if($verif==true){
                $(".imload").fadeIn(1000);
                $(".submit-session").attr("disabled",true);
                var r = jsRoutes.controllers.HomeController.actionConnexionSession();
                var data={
                    "motDePasse" :$motDePasse
                };
                $.ajax({url: r.url, type: r.type, contentType:"application/json",
                    data:JSON.stringify(data),
                    success: function(data){
                        if(data.result=="ok"){
                            var lien = jsRoutes.controllers.ApplicationController.accueil();
                            $(location).attr("href",lien.url);
                        }else{
                            if(data.tentative>=3){
                                var lien = jsRoutes.controllers.HomeController.index();
                                $(location).attr("href",lien.url);
                            }else{
                               var int =3-data.tentative;
                               var html="Il ne vous reste plus que "+int+" tentative(s)";
                               $(".error-form").empty();
                               $(".error-form").append(html);
                               $(".imload").fadeOut(1000);
                            }
                            $(".submit-session").attr("disabled",false);
                        }
                    },
                    error :function(data){
                        alert("une erreur s'est produite l'ors de la connexion");
                        $(".submit-session").attr("disabled",false);
                    }
                });
            }
        });

        $(".content-completer").on("click",".submit-admin",function(){
            var $motDePasse=$("#complet-info #motDePasse").val();
            var $pseudo=$("#complet-info #pseudo").val();
            var $verif=true;
            if(/^ {0,}$/i.test($motDePasse) || /^ {0,}$/i.test($pseudo)){
            }else{
                $(".imload").fadeIn(1000);
                $(".submit-admin").attr("disabled",true);
                var r = jsRoutes.controllers.HomeController.actionConnexionAdmin();
                var data={
                    "motDePasse" :$motDePasse,
                    "pseudo" :$pseudo
                };
                $.ajax({url: r.url, type: r.type, contentType:"application/json",
                    data:JSON.stringify(data),
                    success: function(data){
                        if(data.result=="ok"){
                            var lien = jsRoutes.controllers.AdminController.accueil();
                            $(location).attr("href",lien.url);
                        }else{
                           $(".imload").fadeOut(1000);
                           $(".submit-admin").attr("disabled",false);
                        }
                    },
                    error :function(data){
                        alert("une erreur s'est produite l'ors de la connexion");
                        $(".submit-admin").attr("disabled",false);
                    }
                });
            }
        });

        $(".content-completer").on("click",".submit-oublie",function(){
            var $email=$("#complet-info #email").val();
            var $verif=true;
            if(/^ {0,}$/i.test($email)){
                $("#complet-info #email").addClass("border-bottom-danger");
                $("#complet-info #email").removeClass("border-bottom-succes");
                $verif=false;
            }else{
                $("#complet-info #email").removeClass("border-bottom-danger");
                $("#complet-info #email").addClass("border-bottom-succes");
            }
            if($verif==true){
                $(".imload").fadeIn(1000);
                $(".submit-oublie").attr("disabled",true);
                var r = jsRoutes.controllers.HomeController.actionOublie();
                var data={
                    "email" :$email
                };
                $.ajax({url: r.url, type: r.type, contentType:"application/json",
                    data:JSON.stringify(data),
                    success: function(data){
                        if(data.result=="ok"){
                            var lien = jsRoutes.controllers.HomeController.index();
                            $(location).attr("href",lien.url);
                        }else{
                            $(".connect-error").removeClass("hidden");
                            $(".submit-session").attr("disabled",false);
                        }
                    },
                    error :function(data){
                        alert("une erreur s'est produite l'ors de la connexion");
                        $(".submit-session").attr("disabled",false);
                    }
                });
            }
        });
});