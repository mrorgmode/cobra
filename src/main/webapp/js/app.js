
function initializeMap(position) {

    var map;
    var centerPos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);


    map = new google.maps.Map(document.getElementById('map-canvas'),
        conf.getMapOptions(centerPos));

    var styledMapOptions = {
        name: 'Custom Style'
    };

    var customMapType = new google.maps.StyledMapType(conf.featureOpts, styledMapOptions);
    map.mapTypes.set(conf.getMapTypeId(), customMapType);

}

function initialize() {
    util.centerMap(initializeMap);
}


$(document).ready(function () {



    function loadScript() {
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = 'https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=true&' +
            'callback=initialize';
        document.body.appendChild(script);
        console.log("Loaded")
    }

    window.onload = loadScript; // Load map async

}); // end onReady