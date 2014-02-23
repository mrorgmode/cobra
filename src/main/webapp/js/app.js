var cobra = {};


function mark(pos) {
    // Place a marker on map

    var markerLoc = new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude);

    var marker0 = new google.maps.Marker({
        position: markerLoc,
        draggable:false,
        animation: google.maps.Animation.DROP,
        title: "Cykelstöld"
    });
    var infowindow0 = new google.maps.InfoWindow({
        content: "<div id='marker0-info' class='alert alert-warning'><h3>Cykelstöld</h3><br><div>Info Info Info Info Info</div><a href='http://dn.se'>Källa</a></div>"
    });
    google.maps.event.addListener(marker0, 'click', function() {
        infowindow0.open(cobra.map, marker0);
    });


    var marker1 = new google.maps.Marker({
        position: new google.maps.LatLng(pos.coords.latitude + 0.01, pos.coords.longitude+0.01),
        draggable:false,
        animation: google.maps.Animation.DROP,
        title: "Inbrott"
    });


    var marker2 = new google.maps.Marker({
        position: new google.maps.LatLng(pos.coords.latitude + 0.015, pos.coords.longitude+0.015),
        draggable:false,
        animation: google.maps.Animation.DROP,
        title: "Inbrott"
    });



    var marker3 = new google.maps.Marker({
        position: new google.maps.LatLng(pos.coords.latitude + 0.017, pos.coords.longitude+0.025),
        draggable:false,
        animation: google.maps.Animation.DROP,
        title: "Inbrott"
    });


    marker0.setMap(cobra.map);
    setTimeout(function() { marker1.setMap(cobra.map); }, 700);
    setTimeout(function() { marker2.setMap(cobra.map); }, 300);
    setTimeout(function() { marker3.setMap(cobra.map); }, 1000);




    console.log("mark - done");
}

function initializeMap(position) {

    console.log("ENTER initializeMap");

    var map;
    var centerPos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

    console.log("initializeMap - centerPos: "+JSON.stringify( centerPos ));
    map = new google.maps.Map(document.getElementById('map-canvas'),
        conf.getMapOptions(centerPos));

    var styledMapOptions = {
        name: 'Cobra Style'
    };

    var customMapType = new google.maps.StyledMapType(conf.featureOpts, styledMapOptions);
    map.mapTypes.set(conf.getMapTypeId(), customMapType);

    console.log("initializeMap - done");

    cobra.map = map;

    mark(position);

    console.log("Exit initializeMap");
}


function main() {
    console.log("In Main");
    util.centerMap(initializeMap);

}


$(document).ready(function () {

    // Trigger async loading of map,
    // which then hands over execution to 'main', once loaded.

    function loadScript() {
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = 'https://maps.googleapis.com/maps/api/js?v=3.15&sensor=true&' +
            'key='+conf.getAPIKey() + '&' +
            'callback=main';
        document.body.appendChild(script);
    }

    window.onload = loadScript; // Load map async

}); // end onReady