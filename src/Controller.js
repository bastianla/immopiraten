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
	
	$scope.realestatetypes = ["Wohnung", "Haus", "Grundstück"];
	$scope.purchasetypes = ["Kaufen", "Mieten"];
	$scope.radiuses = [
		{label : "1 km", value:"1"},
		{label : "5 km", value:"5"},
		{label : "10 km", value:"10"},
		{label : "50 km", value:"50"}
	];
	$scope.sortings = ["Veröffentlichkeitsdatum absteigend", "Veröffentlichkeitsdatum aufsteigend", "Preis absteigend", "Preis aufsteigend"];
	

    $scope.submit = function() {
    	window.location.href = "suche.html#?city=" + $scope.city 
    		+ "&purchasetype=" + $scope.purchasetype 
    		+ "&realestatetype=" + $scope.realestatetype;
    	   }
	
	$scope.followBtnImgUrlImmoScout = '/images/suche/u210.png'

    $scope.toggleImageImmoScout = function () {
        if ($scope.followBtnImgUrlImmoScout === '/images/suche/u210.png') {
        	if($scope.immoNet!=='0'){
	            $scope.followBtnImgUrlImmoScout = '/images/suche/u211.png';
	            $scope.immoScout='0';
        	}
        } else {
            $scope.followBtnImgUrlImmoScout = '/images/suche/u210.png';
            $scope.immoScout='1';
        }
    }
	
	$scope.followBtnImgUrlImmoNet = '/images/suche/u210.png'

	    $scope.toggleImageImmoNet = function () {
	        if ($scope.followBtnImgUrlImmoNet === '/images/suche/u210.png') {
	        	if($scope.immoScout!=='0'){
		        	$scope.followBtnImgUrlImmoNet = '/images/suche/u211.png';
		            $scope.immoNet='0';
	        	}
	        } else {
	            $scope.followBtnImgUrlImmoNet = '/images/suche/u210.png';
	            $scope.immoNet='1';
	        }
	    }	
	
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
		
		//Default alle Portale (4)
		$scope.portal='4';
		// nur immoScout
		if ($scope.immoScout=='1' && $scope.immoNet=='0') {
			$scope.portal='1';
		// nur immonet
		} else if ($scope.immoScout=='0' && $scope.immoNet=='1'){
			$scope.portal='2';
		} // immowelt (3) nicht angebunden, (0) none nicht möglich
    
		searchparams+="&portal="+$scope.portal;
		
		if (typeof $scope.radius === 'undefined') {
			$scope.radius = $scope.radiuses[0];
	    } 
		searchparams+="&radius="+$scope.radius.value;
		
		if (typeof $scope.sorting === 'undefined') {
			$scope.sorting = $scope.sortings.indexOf($scope.sortings[0]);
	    } 
		searchparams+="&sorting="+$scope.sorting;

		if (typeof $scope.freeofcommission !== 'undefined') {
			if($scope.freeofcommission === true){
				searchparams+="&freeofcommission="+$scope.freeofcommission;
			}
		}
		if (typeof $scope.terrace !== 'undefined') {
			if($scope.terrace === true){
				searchparams+="&terrace="+$scope.terrace;
			}
		}
		if (typeof $scope.garden !== 'undefined') {
			if($scope.garden === true){
				searchparams+="&garden="+$scope.garden;
			}
		}
		if (typeof $scope.balcony !== 'undefined') {
			if($scope.balcony === true){
				searchparams+="&balcony="+$scope.balcony;
			}
		}
		if (typeof $scope.garage !== 'undefined') {
			if($scope.garage === true){
				searchparams+="&garage="+$scope.garage;
			}
		}
		if (typeof $scope.freeofcommission !== 'undefined') {
			if($scope.freeofcommission === true){
				searchparams+="&freeofcommission="+$scope.freeofcommission;
			}
		}
		if (typeof $scope.pricetill !== 'undefined' && $scope.pricetill !== null) {
			searchparams+="&pricetill="+$scope.pricetill;
		}
		if (typeof $scope.pricefrom !== 'undefined' && $scope.pricefrom !== null) {
			searchparams+="&pricefrom="+$scope.pricefrom;
		}
		if (typeof $scope.roomfrom !== 'undefined'  && $scope.roomfrom !== null) {
			searchparams+="&roomfrom="+$scope.roomfrom;
		}
		if (typeof $scope.roomtill !== 'undefined'  && $scope.roomtill !== null) {
			searchparams+="&roomtill="+$scope.roomtill;
		}
		if (typeof $scope.livingareatill !== 'undefined'  && $scope.livingareatill !== null) {
			searchparams+="&livingareatill="+$scope.livingareatill;
		}
		if (typeof $scope.livingareafrom !== 'undefined'  && $scope.livingareafrom !== null) {
			searchparams+="&livingareafrom="+$scope.livingareafrom;
		}			
	    
		$http.get("http://localhost:8080/search?"+searchparams).then(function(response) {
	    	$scope.exposedata = response.data;  
	    	//$scope.exposedata.title = response.data[0].title; 
	    });	    
    }
});
    
app.controller('exposeCtrl', function($scope, $http, $location) {
	
	$scope.doSearch2 = function() {
		$scope.debug="Debuger:";
	    //$http.get("http://localhost:8080/houses?postcode="+$scope.postcode+"&city=Essen&pricefrom=0&pricetill=1000&livingareafrom=0&livingareatill=1000&landareafrom=0&landareatill=10000&roomfrom=1&roomtill=5&constructionyearfrom=0&constructionyeartill=1000&balcony=1&terrace=0&garden=0&garage=0&commission=0").then(function(response) {
	    	$http.get("http://localhost:8080/search?realestatetype=1&purchasetype=1&input=aachen&radius=20").then(function(response) {
			$scope.exposedata = response.data;  
	    	$scope.exposedata.title = response.data[0].title; 
	    });
    }
    $scope.getDetail = function() {
		
		
		$scope.debug="Debuger:"+ "http://localhost:8080/expose?portal=1&id="+$location.search()['id'];
	    //$http.get("http://localhost:8080/expose?id="+$scope.postcode+").then(function(response) {
	    	$http.get("http://localhost:8080/expose?portal=1&id="+$location.search()['id']).then(function(response) {
			$scope.exposedata = response.data;  
	    	$scope.exposedata.title = response.data[0].title;  
			
	    });
		
    } 
});


	