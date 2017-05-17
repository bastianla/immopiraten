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

app.controller('exposeCtrl', function($scope, $http) {
    $http.get("http://localhost:8080/exposeJson?id=92756718").then(function(response) {
    	$scope.exposedata = response.data['expose:expose'];  
    	$scope.exposedata.publishDate = response.data['expose:expose'][publishDate];    
    	
});
});