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
	
	$scope.followBtnImgUrlImmoScout = '/images/erweiterte_suche/u210.png'

    $scope.toggleImageImmoScout = function () {
        if ($scope.followBtnImgUrlImmoScout === '/images/erweiterte_suche/u210.png') {
        	if($scope.immoNet!=='0'){
	            $scope.followBtnImgUrlImmoScout = '/images/erweiterte_suche/u211.png';
	            $scope.immoScout='0';
        	}
        } else {
            $scope.followBtnImgUrlImmoScout = '/images/erweiterte_suche/u210.png';
            $scope.immoScout='1';
        }
    }
	
	$scope.followBtnImgUrlImmoNet = '/images/erweiterte_suche/u210.png'

	    $scope.toggleImageImmoNet = function () {
	        if ($scope.followBtnImgUrlImmoNet === '/images/erweiterte_suche/u210.png') {
	        	if($scope.immoScout!=='0'){
		        	$scope.followBtnImgUrlImmoNet = '/images/erweiterte_suche/u211.png';
		            $scope.immoNet='0';
	        	}
	        } else {
	            $scope.followBtnImgUrlImmoNet = '/images/erweiterte_suche/u210.png';
	            $scope.immoNet='1';
	        }
	    }
	
	$scope.markField = function(fieldname) {
		angular.element( document.querySelector( fieldname ) ).addClass('ng-touched');
		angular.element( document.querySelector( fieldname ) ).removeClass('ng-untouched');
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
    	
    	//wenn Feld Ort leer ist, keine Suche durchführen und rot markieren
    	if (typeof $scope.city === 'undefined') {
    		$scope.markField('#u97_input');    		

    	} else {
    		    	
			var searchparams="realestatetype="+$scope.realestatetype+"&purchasetype="+$scope.purchasetype+"&input="+$scope.city;
			
			//Default alle Portale (4)
			$scope.portal='4';
			//nur immoScout
			if ($scope.immoScout=='1' && $scope.immoNet=='0') {
				$scope.portal='1';
			//nur immonet
			} else if ($scope.immoScout=='0' && $scope.immoNet=='1'){
				$scope.portal='2';
			} //immowelt (3) nicht angebunden, (0) none nicht möglich
	    
			searchparams+="&portal="+$scope.portal;
			
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
    }
});
    


	