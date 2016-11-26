$(document).ready(function(){
    $content=$(".content-completer");
    centrerElementAbsolu($content);
    function centrerElementAbsolu(element){
    	var largeur_fenetre = $(window).width();
    	var hauteur_fenetre = $(window).height();

    	var haut = (hauteur_fenetre - element.height()) / 2 + $(window).scrollTop();
    	var gauche = (largeur_fenetre - element.width()) / 2 + $(window).scrollLeft();
    	element.css({position: 'absolute', top: 64, left: gauche});
    }
});