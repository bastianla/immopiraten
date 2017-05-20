var app = angular.module('ImmoPiratenApp', []);

/*app.controller('immoCtrl', function($scope, $http) {
    $http.get("localhost:8080/expose?id=92756718")
    .then(function(response) {
        $scope.myWelcome = response.data;
    });
});*/
app.controller('customersCtrl', function($scope, $http) {
    $http.get("https://www.w3schools.com/angular/customers.php").then(function(response) {
    	$scope.myData = response.data.records;	    
	});
});

app.controller('exposeCtrl2', function($scope, $http) {
    $http.get("http://localhost:8080/exposeJson?id=92756718").then(function(response) {
    	$scope.exposedata = response.data['expose:expose'];  
    	$scope.exposedata.publishDate = response.data['expose:expose'][publishDate];   
    });	
});
app.controller('exposeCtrl', function($scope, $http) {
	
	$scope.doSearch = function() {
	    $http.get("http://localhost:8080/houses?postcode="+$scope.postcode+"&city=Essen&pricefrom=0&pricetill=1000&livingareafrom=0&livingareatill=1000&landareafrom=0&landareatill=10000&roomfrom=1&roomtill=5&constructionyearfrom=0&constructionyeartill=1000&balcony=1&terrace=0&garden=0&garage=0&commission=0").then(function(response) {
	    	$scope.exposedata = response.data;  
	    	$scope.exposedata.title = response.data[0].title; 
	    });
    }
        	
});
    

