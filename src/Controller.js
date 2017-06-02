var app = angular.module('ImmoPiratenApp', ["ngRoute"]);

app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
    	templateUrl : "startseite.html",
        controller : "searchCtrl",
    })
    .when("/startseite", {
        templateUrl : "startseite.html",
        controller : "searchCtrl",
    })
    .when("/erweitert", {
        templateUrl : "erweiterte_suche.html",
        controller : "searchCtrl",
    });
});



app.controller('searchCtrl', function($rootScope, $scope, $http, $location) {
    /*$http.get("http://localhost:8080/exposeJson?id=92756718").then(function(response) {
    	$scope.exposedata = response.data['expose:expose'];  
    	$scope.exposedata.publishDate = response.data['expose:expose'][publishDate];   
    });	*/

	$scope.doeasySearch = function() {


	    $location.path("/erweitert/");
        $scope.doSearch();
    }
	
	$scope.doSearch = function() {
		
		if (typeof $scope.radius === 'undefined') {
				$scope.radius = '100';
		    } 
		if (typeof $scope.freeofcommission === 'undefined') {
			$scope.freeofcommission = '1';
	    } 
	    $http.get("http://localhost:8080/search?realestatetype="+$scope.realestatetype+"&purchasetype="+$scope.purchasetype+"&input="+$scope.city+"&radius="+$scope.radius+"&freeofcommission="+$scope.freeofcommission).then(function(response) {
	    	$scope.exposedata = response.data;  
	    	$scope.exposedata.title = response.data[0].title; 
	    });	    
    }
	
	$scope.doSearchHouses = function() {
	    $http.get("http://localhost:8080/houses?postcode="+$scope.city+"&city=Essen&pricefrom=0&pricetill=1000&livingareafrom=0&livingareatill=1000&landareafrom=0&landareatill=10000&roomfrom=1&roomtill=5&constructionyearfrom=0&constructionyeartill=1000&balcony=1&terrace=0&garden=0&garage=0&commission=0").then(function(response) {
	    	$scope.exposedata = response.data;  
	    	$scope.exposedata.title = response.data[0].title; 
	    });
    
	
	}
	
        	
});
    

