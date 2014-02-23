var cobra = {};
cobra.lastRequestTime = -1;

/** Fetch new crime descriptions from server, then update the map
 *
 * @param map Map instance to use for marking
 * @param lastRequestTime -1 of last received server timestamp
 * @param location Center location object { "longitude" : 123.456, "latitude" : 123.456}
 * @param radiusInKm Ask for crimes within this radius (km from center)
 */
function getCrimes(map, lastRequestTime, location, radiusInKm) {

    $.ajax({
        url: conf.crimesUrl,
        data: {
            "since": lastRequestTime,
            "centerLong": location.longitude,
            "centerLat": location.latitude,
            "radius": radiusInKm
        },
        context: map
    }).done(function (crimesResponse, textStatus) {
            console.log("Data retrieved. Status: " + textStatus);
            if (crimesResponse.timestamp)
                cobra.lastRequestTime = crimesResponse.timestamp;
            mark(crimesResponse.crimes, this);
        });
}

/** Take an array of crime descriptions and place them as markers on the map
 *
 * @param crimes Array of crime descriptions
 * @param map Google Map instance to use
 */
function mark(crimes, map) {
    // Place a markers on map
    crimes.forEach(function (crime) {
        var markerLoc = new google.maps.LatLng(crime.geolocation.latitude, crime.geolocation.longitude);

        var marker = new google.maps.Marker({
            position: markerLoc,
            draggable: false,
            animation: google.maps.Animation.DROP,
            title: (crime.title ? crime.title : "Brottsplats")
        });

        var infowindow = new google.maps.InfoWindow({
            content: "<div class='alert alert-warning marker-info-window'>" +
                (crime.title ? "<h3>" + crime.title + "</h3><br>" : "") +
                (crime.description ? "<div class='marker-info-window-description'>" + crime.description + "</div><br>" : "") +
                (crime.url ? "<a href='" + crime.url + "'>Källa</a><br>" : "") +
                "</div>"
        });

        google.maps.event.addListener(marker, 'click', function () {
            infowindow.open(map, marker);
        });

        setTimeout(function () {
            marker.setMap(map);
        }, 250);

    });

    console.log("mark - done");
}

/** Center map on position and then call server to retrieve crimes and put on map
 *
 * @param position Initial map center
 */
function initializeMap(position) {

    console.log("ENTER initializeMap");

    var map;
    var centerPos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
    var styledMapOptions = {
        name: 'Cobra Style'
    };
    var customMapType = new google.maps.StyledMapType(conf.featureOpts, styledMapOptions);

    map = new google.maps.Map(document.getElementById('map-canvas'),
        conf.getMapOptions(centerPos));

    map.mapTypes.set(conf.getMapTypeId(), customMapType);

    console.log("initializeMap - done");

    cobra.map = map; // Store reference

    // Initialize markers
    getCrimes(map,
        (cobra.lastRequestTime ? cobra.lastRequestTime : -1),
        position.coords,
        10);

    // Start periodic refresh of new crimes
    cobra.fetcherHandle = setInterval(
        (function () {
            return function () {
                console.log("Refreshing data");
                getCrimes(map,
                    (cobra.lastRequestTime ? cobra.lastRequestTime : -1),
                    position.coords,
                    10);
            }
        })(),
        conf.dataRefreshInterval);

    console.log("Exit initializeMap");
}


/** Once the google map has finished loading,
 *  execution will start here.
 */
function main() {
    console.log("In Main");

    // Center map on the current location of the user
    // then initialize map by downloading markers etc
    util.centerMap(initializeMap);

}

/** Execution starts here, once the initial page loading is ready
 * - Trigger async loading of map (which then hands off to main())
 * - Initialize non-map UI
 */
$(document).ready(function () {

    // Trigger async loading of map,
    // which then hands over execution to 'main', once loaded.
    function loadScript() {
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = 'https://maps.googleapis.com/maps/api/js?v=3.15&sensor=true&' +
            'key=' + conf.getAPIKey() + '&' +
            'callback=main';
        document.body.appendChild(script);
    }

    window.onload = loadScript; // Load map async

    // Additional UI initialization


}); // end onReady