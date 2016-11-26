$(document).ready(function(){
    function hasGetUserMedia() {
      return !!(navigator.getUserMedia || navigator.webkitGetUserMedia ||
                navigator.mediaDevices.getUserMedia || navigator.msGetUserMedia);
    }

    function stopStream(stream) {
        console.log('stop called');
        stream.getVideoTracks().forEach(function (track) {
            track.stop();
        });
    }

    function stopWebcam(){

    }

    if (hasGetUserMedia()) {
        var canvas = document.getElementById("canvas"),
        context = canvas.getContext("2d"),
        video = document.getElementById("video"),
        videoParams = { "video": true },
        errorCallback = function(error) {
            console.log("Erreur Capture Video: ", error.code);
        };
    }

    $("#photo-webcam").click(function(){
        if (hasGetUserMedia()) {
            $(".body-webcam").removeClass("hidden");
            $("#video").removeClass("hidden");
            $("#snap-annuler").removeClass("hidden");
            $("#snap").removeClass("hidden");
            $("#photo-webcam").attr("disabled",true);
            $("#image-charger").attr("disabled",true);
            if(navigator.getUserMedia) { //Standard
                navigator.getUserMedia(videoParams, function(stream) {
                    video.src = stream;
                    video.play();
                }, errorCallback);
            } else if(navigator.webkitGetUserMedia) { //Webkit
                navigator.webkitGetUserMedia(videoParams, function(stream){
                    video.src = window.webkitURL.createObjectURL(stream);
                    video.play();
                }, errorCallback);
            } else if(navigator.mediaDevices.getUserMedia) { //Mozilla
                navigator.mozGetUserMedia(videoParams, function(stream){
                    video.src = window.URL.createObjectURL(stream);
                    video.play();
                }, errorCallback);
            } else if(navigator.msGetUserMedia) { //IE
                navigator.msGetUserMedia(videoParams, function(stream){
                    video.src = window.URL.createObjectURL(stream);
                    video.play();
                }, errorCallback);
            }
        } else {
            alert("getUserMedia() n'est pas supporté par votre navigateur !");
        }
    });

    if (hasGetUserMedia()) {
        $("#snap").click(function() {
            context.drawImage(video, 0, 0, 196, 150);
            $("#video").hide();
            $("#imageok").attr('src', canvas.toDataURL());
            $("#imageok").removeClass("hidden");
            $("#recommencer-snap").removeClass("hidden");
            $(this).addClass("hidden");
        });

        $("#snap-annuler").click(function(){
            $("#video").removeClass("hidden");
            $("#imageok").addClass("hidden");
            $("#imageok").attr('src', "");
            $(".body-webcam").addClass("hidden");
            $("#snap-annuler").addClass("hidden");
            $("#snap").addClass("hidden");
            $("#photo-webcam").attr("disabled",false);
            $("#image-charger").attr("disabled",false);
            stopWebcam();

        });

        $("#recommencer-snap").click(function() {
            if($("#imageok").attr('src') != ""){
                $(".imload").fadeIn(1000);
                 var dataURL = canvas.toDataURL("image/png");
                 var r = jsRoutes.controllers.ApplicationController.enregisterWebcam();
                 $.ajax({url: r.url, type: r.type,
                     data:{
                        "image": dataURL
                     },
                     success: function (data) {
                        if(data.result=="ok"){
                            var lien = jsRoutes.controllers.ApplicationController.accueil();
                            $(location).attr('href',lien.url);
                        }else{
                            alert("une erreur s'est produite");
                        }
                     },
                     error: function (data) {
                        alert("une erreur s'est produite");
                     }
                 });
            } else {
                alert("Il vous faut premièrement prendre une photo SVP !");
            }
        });
    }

});
