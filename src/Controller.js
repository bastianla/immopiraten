var app = angular.module('ImmoPiratenApp', []);

app.controller('searchCtrl', function($scope, $http, $location) {
	
	/*$scope.doSearch = function() {
		$scope.debug="Debuger:";
	    //$http.get("http://localhost:8080/houses?postcode="+$scope.postcode+"&city=Essen&pricefrom=0&pricetill=1000&livingareafrom=0&livingareatill=1000&landareafrom=0&landareatill=10000&roomfrom=1&roomtill=5&constructionyearfrom=0&constructionyeartill=1000&balcony=1&terrace=0&garden=0&garage=0&commission=0").then(function(response) {
	    	$http.get("http://localhost:8080/search?realestatetype=1&purchasetype=1&input=aachen&radius=20").then(function(response) {
			$scope.exposedata = response.data;  
	    	$scope.exposedata.title = response.data[0].title; 
	    });
    }*/
    $scope.getDetail = function() {	
		
		$scope.debug=$scope.city;
		$scope.city= $location.search()['city'];
		$scope.realestatetype = $location.search()['realestatetype'];
		$scope.purchasetype =$location.search()['purchasetype'];
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
});
    


	