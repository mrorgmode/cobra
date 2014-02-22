var util = (function () {
    var me = this;
    var defaultLocation = { "coords": {} };
    defaultLocation.coords.latitude = 18.069;
    defaultLocation.coords.longitude = 59.3296;

    return {

        /**
         * Invokes callback with either the current location or the default location (STHLM downtown).
         * Current location is used if geolocation is available.
         *
         * @param callback
         */
        centerMap: function (callback) {
            if (!!navigator.geolocation)
                navigator.geolocation.getCurrentPosition(callback);
            else
                callback(defaultLocation);

        },



    };

})
    ();