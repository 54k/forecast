(function (window) {
    var location = window.location.href;
    var rootLocation = location.slice(0, location.indexOf("index.html"));

    var app = angular.module("Application", ["ngRoute", "controllers"]);
    app.config(["$routeProvider", function ($routeProvider) {
        $routeProvider
            .when("/", {
                templateUrl: "template/forecast.html",
                controller: "ForecastsController"
            }).otherwise({
                redirectTo: "/"
            });
    }]);

    var controllers = angular.module("controllers", []);
    controllers.controller("ForecastsController", ["$scope", "$http", function ($scope, $http) {
        var forecasts = function (url) {
            $http.get(url).success(function (data) {
                $scope.forecasts = data;
            });
        };
        var storedForecasts = rootLocation + "api/stored";
        var currentForecasts = rootLocation + "api/current";
        var nextMode;
        $scope.storedForecasts = function () {
            forecasts(storedForecasts);
            $scope.label = "current";
            nextMode = $scope.currentForecasts;
        };
        $scope.currentForecasts = function () {
            forecasts(currentForecasts);
            $scope.label = "stored";
            nextMode = $scope.storedForecasts;
        };
        $scope.switchForecasts = function () {
            nextMode();
        };
        $scope.storedForecasts();
    }]);
})(this);