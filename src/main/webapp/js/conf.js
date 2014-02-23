var conf = (function () {
    var me = this;

    // TODO: BEFORE LIVE, RESTRICT KEY ACCESS TO THE LIVE DOMAIN (REFERERS)
    var apiKey = "AIzaSyCcr1fAQXMW24RIjzodbgyv61ztbGHz9Us";

    var MY_MAPTYPE_ID = 'custom_style';


    return {
        dataRefreshInterval: 15*1000,

        crimesUrl: 'http://localhost:9090/js/sampleCrimeRequestResponse.json',

        featureOpts: [
            {
                stylers: [
                    { hue: '#890000' },
                    { visibility: 'simplified' },
                    { gamma: 0.5 },
                    { weight: 0.5 }
                ]
            },
            {
                elementType: 'labels',
                stylers: [
                    { visibility: 'off' }
                ]
            },
            {
                featureType: 'water',
                stylers: [
                    { color: '#890000' }
                ]
            }
        ],

        getMapOptions: function (centerPos) {
            return {
                zoom: 12,
                center: centerPos,
                mapTypeControlOptions: {
                    mapTypeIds: [google.maps.MapTypeId.ROADMAP, MY_MAPTYPE_ID]
                },
                mapTypeId: MY_MAPTYPE_ID
            }
        },

        getAPIKey: function () {
            return apiKey;
        },

        getMapTypeId: function () {
            return MY_MAPTYPE_ID;
        }
    };

})();