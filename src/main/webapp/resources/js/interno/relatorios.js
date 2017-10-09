window.Nisia = window.Nisia || {};

var natalmarker;
var map;

const initMap = function(){
	
	
	var uluru = { lat: -5.811409, lng: -35.2101327 }
	  var natal = { lat: -5.821409, lng: -35.2101327 }
	  var infowindow = new google.maps.InfoWindow({
		    content: '<div><h1>Anne</h1><h2>teste</h2></div>'
		  });
	  var infowindowNatal = new google.maps.InfoWindow({
	    content: '<div><h1>Natal</h1></div>'
	  });
	  
  map = new google.maps.Map(document.getElementById('map'), {
    zoom: 14,
    center: { lat: -5.811409,lng: -35.2101327 }
  });
	  	var pol = new google.maps.Polygon({
	      paths: [uluru, {lat: uluru.lat + 1, lng: uluru.lng}, {lat:uluru.lat, lng: uluru.lng + 1}, {lat: 23.04, lng: 12.09}],
	      strokeColor: '#000',
	      strokeOpacity: 0.8,
	      strokeWeight: 2,
	      fillColor: '#000',
	      fillOpacity: 0.35,
	      /* map: map */
	    })
  var marker = new google.maps.Marker({
    position: uluru,
    map: map,
    icon: {
      path: google.maps.SymbolPath.CIRCLE,
      scale: 20,
      fillColor: '#EE6363',
      fillOpacity: 0.75,
      strokeWeight: 0
    }
  });
 	 natalmarker = new google.maps.Marker({
       position: natal,
       map: map,
       icon: {
         path: google.maps.SymbolPath.CIRCLE,
         scale: 20,
         fillColor: '#214534',
         fillOpacity: 0.75,
         strokeWeight: 0
       }
     });
	  	
	 	marker.addListener('click', function(){
 	    infowindow.open(map, marker);
 	  })
 	  natalmarker.addListener('click', function(){
 		  pesquisarArtista();
 	  })
	
}

const pesquisarArtista = function(){
	var infowindowNatal = new google.maps.InfoWindow({
	    content: 'Carregando...'
	  });
	infowindowNatal.open(map, natalmarker);
	$.ajax({
		url: '/nisia-web/rest/noticia',
		success: function(data) {
			console.log(data);
			let noticias = JSON.parse(data);
			infowindowNatal.setContent(obterJanelaDetalhe(noticias[0])); 
			
		},
		error: function() {
			console.log('deu erro');
		},
		complete: function(){
			console.log('terminou');
		}
	});
}

const obterJanelaDetalhe = function(dados) {
	return '<div><h1>'+dados.titulo+'</h1><h2>'+dados.texto+'</h2></div>';	
}


window.Nisia = {
  initMap
}
