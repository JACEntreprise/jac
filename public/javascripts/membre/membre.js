$(document).ready(function (e) {
    $(".imload").fadeOut(1000);
    $("#inscription-particulier").submit(function(){
        return false;
    });

    $("#form-connexion").submit(function(){
        return false;
    });

    $(".form-connexion").on("click",".submit-connexion",function(){
        var $email=$("#form-connexion #email").val();
        var $motDePasse=$("#form-connexion #motDePasse").val();
        var $verif=true;
        if(/^ {0,}$/i.test($email)){
            $("#form-connexion #email").addClass("border-bottom-danger");
            $("#form-connexion #email").removeClass("border-bottom-succes");
            $verif=false;
        }else{
            $("#form-connexion #email").removeClass("border-bottom-danger");
            $("#form-connexion #email").addClass("border-bottom-succes");
        }
        if(/^ {0,}$/i.test($motDePasse)){
            $("#form-connexion #motDePasse").addClass("border-bottom-danger");
            $("#form-connexion #motDePasse").removeClass("border-bottom-succes");
            $verif=false;
        }else{
            $("#form-connexion #motDePasse").removeClass("border-bottom-danger");
            $("#iform-connexion #motDePasse").addClass("border-bottom-succes");
        }
        if($verif==true){
            $(".imload").fadeIn(1000);
            $(".submit-connexion").attr("disabled",true);
            var r = jsRoutes.controllers.HomeController.connexionUser();
            var data={
                "email" :$email,
                "motDePasse" :$motDePasse
            };
            $.ajax({url: r.url, type: r.type, contentType:"application/json",
                data:JSON.stringify(data),
                success: function(data){
                    if(data.result=="ok"){
                        var lien = jsRoutes.controllers.HomeController.connexionSession();
                        $(".connect-error").addClass("hidden");
                        $(location).attr("href",lien.url);
                    }else{
                        $(".submit-connexion").attr("disabled",false);
                        $(".connect-error").removeClass("hidden");
                    }
                },
                error :function(data){
                    alert("une erreur s'est produite l'ors de la connexion");
                    $(".submit-connexion").attr("disabled",false);
                }
            });
        }
    });

    $(".content-form-ins").on("click",".submit-particulier",function(){
        var $nom=$("#inscription-particulier #nom").val();
        var $prenom=$("#inscription-particulier #prenom").val();
        var $email=$("#inscription-particulier #email").val();
        var $motDePasse=$("#inscription-particulier #motDePasse").val();
        var $verif=true;
        if(/^ {0,}$/i.test($nom)){
            $("#inscription-particulier #nom").addClass("border-bottom-danger");
            $("#inscription-particulier #nom").removeClass("border-bottom-succes");
            $verif=false;
        }else{
            $("#inscription-particulier #nom").addClass("border-bottom-succes");
            $("#inscription-particulier #nom").removeClass("border-bottom-danger");
        }
        if(/^ {0,}$/i.test($prenom)){
            $("#inscription-particulier #prenom").addClass("border-bottom-danger");
            $("#inscription-particulier #prenom").removeClass("border-bottom-succes");
            $verif=false;
        }else{
            $("#inscription-particulier #prenom").removeClass("border-bottom-danger");
            $("#inscription-particulier #prenom").addClass("border-bottom-succes");
        }
        if(/^ {0,}$/i.test($email)){
            $("#inscription-particulier #email").addClass("border-bottom-danger");
            $("#inscription-particulier #email").removeClass("border-bottom-succes");
            $verif=false;
        }else{
            $("#inscription-particulier #email").removeClass("border-bottom-danger");
            $("#inscription-particulier #email").addClass("border-bottom-succes");
        }
        if(/^ {0,}$/i.test($motDePasse) || $motDePasse.length<8){
            $("#inscription-particulier #motDePasse").addClass("border-bottom-danger");
            $("#inscription-particulier #motDePasse").removeClass("border-bottom-succes");
            $("#inscription-particulier #motDePasse").val("");
            $verif=false;
        }else{
            $("#inscription-particulier #motDePasse").removeClass("border-bottom-danger");
            $("#inscription-particulier #motDePasse").addClass("border-bottom-succes");
        }
        if($verif==true){
            $(".imload").fadeIn(1000);
            $(".submit-entreprise").attr("disabled",true);
            $(".submit-particulier").attr("disabled",true);
            var r = jsRoutes.controllers.HomeController.inscriptionParticulier();
            $(".imload").fadeIn("1000");
            $.ajax({url: r.url, type: r.type,
                data:{
                    "nom" :$nom,
                    "prenom" :$prenom,
                    "email" :$email,
                    "motDePasse" :$motDePasse
                },
                success: function(data){
                    if(data.result=="ok"){
                        var lien = jsRoutes.controllers.ApplicationController.attente();
                        $(location).attr("href",lien.url);
                    }else{
                        alert(data.message);
                        $(".submit-entreprise").attr("disabled",false);
                        $(".submit-particulier").attr("disabled",false);
                    }
                },
                error :function(data){
                    alert("erreur");
                    $(".submit-entreprise").attr("disabled",false);
                    $(".submit-particulier").attr("disabled",false);
                }
            });
        }

    });


    $("#inscription-entreprise").submit(function(){
        return false;
    });

    $(".content-form-ins").on("click",".submit-entreprise",function(){
        var $raisonSocial=$("#inscription-entreprise #raisonSocial").val();
        var $email=$("#inscription-entreprise #email").val();
        var $motDePasse=$("#inscription-entreprise #motDePasse").val();
        var $verif=true;
        if(/^ {0,}$/i.test($raisonSocial)){
            $("#inscription-entreprise #raisonSocial").addClass("border-bottom-danger");
            $("#inscription-entreprise #raisonSocial").removeClass("border-bottom-succes");
            $verif=false;
        }else{
            $("#inscription-entreprise #raisonSocial").addClass("border-bottom-succes");
            $("#inscription-entreprise #raisonSocial").removeClass("border-bottom-danger");
        }
        if(/^ {0,}$/i.test($email)){
            $("#inscription-entreprise #email").addClass("border-bottom-danger");
            $("#inscription-entreprise #email").removeClass("border-bottom-succes");
            $verif=false;
        }else{
            $("#inscription-entreprise #email").removeClass("border-bottom-danger");
            $("#inscription-entreprise #email").addClass("border-bottom-succes");
        }
        if(/^ {0,}$/i.test($motDePasse) || $motDePasse.length<8){
            $("#inscription-entreprise #motDePasse").addClass("border-bottom-danger");
            $("#inscription-entreprise #motDePasse").removeClass("border-bottom-succes");
            $("#inscription-entreprise #motDePasse").val("");
            $verif=false;
        }else{
            $("#inscription-entreprise #motDePasse").removeClass("border-bottom-danger");
            $("#inscription-entreprise #motDePasse").addClass("border-bottom-succes");
        }
        if($verif==true){
            $(".imload").fadeIn(1000);
            $(".submit-entreprise").attr("disabled",true);
            $(".submit-particulier").attr("disabled",true);
            var r = jsRoutes.controllers.HomeController.inscriptionEntreprise();
            $.ajax({url: r.url, type: r.type,
                data:{
                    "raisonSocial" :$raisonSocial,
                    "email" :$email,
                    "motDePasse" :$motDePasse
                },
                success: function(data){
                    if(data.result=="ok"){
                        var lien = jsRoutes.controllers.ApplicationController.attente();
                        $(location).attr("href",lien.url);
                    }else{
                        alert(data.message);
                        $(".submit-entreprise").attr("disabled",false);
                        $(".submit-particulier").attr("disabled",false);
                    }
                },
                error :function(data){
                    alert("erreur");
                    $(".submit-entreprise").attr("disabled",false);
                    $(".submit-particulier").attr("disabled",false);
                }
            })
        }

    });

    $("#complet-info").submit(function(){
        return false;
    });

    $(".submit-complet-info-particulier").click(function(){
        var $adresse=$("#adresse").val();
        var $telephone=$("#telephone").val();
        var $siteweb=$("#siteweb").val();
        var $anneeDeNaissance=$("#anneeDeNaissance").val();
        var $lieuDeNaissance=$("#lieuDeNaissance").val();
        if($anneeDeNaissance==0){
            $("#anneeDeNaissance").addClass("border-bottom-danger");
            $("#anneeDeNaissance").removeClass("border-bottom-succes");
        }else{
            $(".imload").fadeIn(1000);
            $("#anneeDeNaissance").removeClass("border-bottom-danger");
            $("#anneeDeNaissance").addClass("border-bottom-succes");
            $(".submit-complet-info-particulier").attr("disabled",true);
            $(".submit-complet-info-particulier").attr("disabled",true);
            var r = jsRoutes.controllers.ApplicationController.actionCompleterInfo();
            $.ajax({url: r.url, type: r.type,
                data:{
                    "adresse" :$adresse,
                    "telephone" :$telephone,
                    "siteweb" :$siteweb,
                    "anneeDeNaissance" :$anneeDeNaissance,
                    "lieuDeNaissance" :$lieuDeNaissance
                },
                success: function(data){
                    if(data.result=="ok"){
                        var lien = jsRoutes.controllers.ApplicationController.infoProfil();
                        $(location).attr("href",lien.url);
                    }else{
                        alert(data.message);
                        $(".submit-complet-info-particulier").attr("disabled",false);
                        $(".submit-complet-info-particulier").attr("disabled",false);
                    }
                },
                error: function(data){
                    alert("une erreur est survenu l'ors de l'ajout");
                    $(".submit-complet-info-particulier").attr("disabled",false);
                    $(".submit-complet-info-particulier").attr("disabled",false);
                }
            });

        }
    });

    $(".submit-complet-info-entreprise").click(function(){

        var $adresse=$("#adresse").val();
        var $telephone=$("#telephone").val();
        var $siteweb=$("#siteweb").val();
        if(/^ {0,}$/i.test($siteweb)){
            $("#siteweb").addClass("border-bottom-danger");
            $("#siteweb").removeClass("border-bottom-succes");
        }else{
            $(".imload").fadeIn(1000);
            $("#siteweb").removeClass("border-bottom-danger");
            $("#siteweb").addClass("border-bottom-succes");
            $(".submit-complet-info-entreprise").attr("disabled",true);
            $(".submit-complet-info-entreprise").attr("disabled",true);
            var r = jsRoutes.controllers.ApplicationController.actionCompleterInfo();
            $.ajax({url: r.url, type: r.type,
                data:{
                    "adresse" :$adresse,
                    "telephone" :$telephone,
                    "siteweb" :$siteweb
                },
                success: function(data){
                    if(data.result=="ok"){
                        var lien = jsRoutes.controllers.ApplicationController.infoProfil();
                        $(location).attr("href",lien.url);
                    }else{
                        alert(data.message);
                        $(".submit-complet-info-entreprise").attr("disabled",false);
                        $(".submit-complet-info-entreprise").attr("disabled",false);
                    }
                },
                error: function(data){
                    alert("une erreur est survenu l'ors de l'ajout");
                    $(".submit-complet-info-entreprise").attr("disabled",false);
                    $(".submit-complet-info-entreprise").attr("disabled",false);
                }
            });
        }
    });

    $("#continuer-info").click(function(){
        var $pays=$("#pays").val();
        var $profil=$("#profil").val();
        var $secteur=$("#secteur").val();
        var $titre=$("#titre").val();
        if(/^ {0,}$/i.test($titre)){
            $("#titre").addClass("border-bottom-danger");
            $("#titre").removeClass("border-bottom-succes");
        }else{
            $(".imload").fadeIn(1000);
            $("#titre").removeClass("border-bottom-danger");
            $("#titre").addClass("border-bottom-succes");
            $("#continuer-info").attr("disabled",true);
            var r = jsRoutes.controllers.ApplicationController.actionInfoProfil();
                $.ajax({url: r.url, type: r.type,
                    data:{
                        "pays" :$pays,
                        "profil" :$profil,
                        "titre" :$titre,
                        "secteur" :$secteur
                    },
                    success: function(data){
                        var lien = jsRoutes.controllers.ApplicationController.completerImageProfil();
                        $(location).attr("href",lien.url);
                    },
                    error: function(data){
                        $("#continuer-info").attr("disabled",false);
                        alert("une erreur s'est produite l'ors de l'ajout")
                    }
                 });
        }
    });

    $('.content-completer').on('change','#image', function (e) {
        var files = $(this)[0].files;

        if (files.length > 0) {
            // On part du principe qu'il n'y qu'un seul fichier
            // étant donné que l'on a pas renseigné l'attribut "multiple"
            var file = files[0];
            var nomFichier=file.name;
            var array=nomFichier.split('.');
            var extension=array[array.length -1];
            if(extension=='png' || extension=='jpg' || extension=='jpeg' ||extension=='gif'){
                $(".body-webcam").removeClass("hidden");
                $("#imageok").removeClass("hidden");
                $("#imageok").attr('src', window.URL.createObjectURL(file));
                $("#valider-image").removeClass("hidden");
            }
            else{
                $("#valider-image").addClass("hidden");
            }
        }
    });

    $("#valider-image").click(function(){
        $(".imload").fadeIn(1000);
        if($("#imageok").attr('src') != ""){
            var $form = $("#form-image-profil");
            var formdata = (window.FormData) ? new FormData($form[0]) : null;
            var data = (formdata !== null) ? formdata : $form.serialize();
            var r = jsRoutes.controllers.ApplicationController.ajouterImageProfil();
            $.ajax({url: r.url, type:"POST",contentType: false,processData: false, dataType: 'json',
                data: data,
                success: function(data){
                    if(data.result=="ok"){
                        var lien = jsRoutes.controllers.ApplicationController.accueil();
                        $(location).attr("href",lien.url);
                    }else{
                        alert("une image svp");
                        $(".imload").fadeOut(1000);
                    }

                },
                error: function(data){
                    alert("une image svp");
                    $(".imload").fadeOut(1000);
                }
            });
        }else{
            alert("une image svp");
        }

    });
});

