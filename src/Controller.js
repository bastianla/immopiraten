var app = angular.module('ImmoPiratenApp', []);

app.directive('onlyDigits', function () {
    return {
      require: 'ngModel',
      restrict: 'A',
      link: function (scope, element, attr, ctrl) {
        function inputValue(val) {
          if (val) {
            var digits = val.replace(/[^0-9]/g, '');

            if (digits !== val) {
              ctrl.$setViewValue(digits);
              ctrl.$render();
            }
            return parseInt(digits,10);
          }
          return undefined;
        }            
        ctrl.$parsers.push(inputValue);
      }
    };
});

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
				
		$scope.realestatetype = $location.search()['realestatetype'];
		if (typeof $scope.realestatetype === 'undefined') {
			$scope.realestatetype='0';
		}
		$scope.purchasetype =$location.search()['purchasetype'];
		if (typeof $scope.purchasetype === 'undefined') {
			$scope.purchasetype='0';
		}
		
		$scope.city= $location.search()['city'];				
		if (typeof $scope.city !== 'undefined') {
			$scope.doSearch();
		}
		
	}    	
    
    $scope.doSearch = function() {	
    	
		var searchparams="realestatetype="+$scope.realestatetype+"&purchasetype="+$scope.purchasetype+"&input="+$scope.city;
		
		
		if (typeof $scope.radius === 'undefined') {
			$scope.radius = '100';
	    } 
		searchparams+="&radius="+$scope.radius;

		if (typeof $scope.freeofcommission === 'undefined') {
			$scope.freeofcommission = '1';
	    }
		searchparams+="&freeofcommission="+$scope.freeofcommission;

		if (typeof $scope.pricetill !== 'undefined') {
			searchparams+="&pricetill="+$scope.pricetill;
		}
		if (typeof $scope.pricefrom !== 'undefined') {
			searchparams+="&pricefrom="+$scope.pricefrom;
		}
		if (typeof $scope.livingareatill !== 'undefined') {
			searchparams+="&livingareatill="+$scope.livingareatill;
		}
		if (typeof $scope.livingareafrom !== 'undefined') {
			searchparams+="&livingareafrom="+$scope.livingareafrom;
		}
		
		
	    
		$http.get("http://localhost:8080/search?"+searchparams).then(function(response) {
	    	$scope.exposedata = response.data;  
	    	$scope.exposedata.title = response.data[0].title; 
	    });	    
    }
});
    


	