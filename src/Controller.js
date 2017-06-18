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
	$scope.sortings = ["Neuste zuerst", "Älteste zuerst", "Preis absteigend", "Preis aufsteigend"];
	

    $scope.submit = function() {
    	window.location.href = "suche.html#?city=" + $scope.city 
    		+ "&purchasetype=" + $scope.purchasetype 
    		+ "&realestatetype=" + $scope.realestatetype;
    	   }
	
	$scope.followBtnImgUrlImmoScout = '/images/suche/checked.png'

    $scope.toggleImageImmoScout = function () {
        if ($scope.followBtnImgUrlImmoScout === '/images/suche/checked.png') {
        	if($scope.immoNet!=='0'){
	            $scope.followBtnImgUrlImmoScout = '/images/suche/unchecked.png';
	            $scope.immoScout='0';
        	}
        } else {
            $scope.followBtnImgUrlImmoScout = '/images/suche/checked.png';
            $scope.immoScout='1';
        }
    }
	
	$scope.toggleSortingValue = 'display:none; visibility: hidden'

	$scope.toggleSorting = function () {
	    if ($scope.toggleSortingValue === 'display:none; visibility: hidden') {	        	
        	$scope.toggleSortingValue = '';
    	}
    }	
	
	$scope.followBtnImgUrlImmoNet = '/images/suche/checked.png'

	    $scope.toggleImageImmoNet = function () {
	        if ($scope.followBtnImgUrlImmoNet === '/images/suche/checked.png') {
	        	if($scope.immoScout!=='0'){
		        	$scope.followBtnImgUrlImmoNet = '/images/suche/unchecked.png';
		            $scope.immoNet='0';
	        	}
	        } else {
	            $scope.followBtnImgUrlImmoNet = '/images/suche/checked.png';
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
		if (typeof $scope.city !== 'undefined' && $scope.city !=='') {
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
	    	$scope.exposedata.title = response.data[0].title; 
	    	$scope.toggleSorting(); 
	    })
		
		
    }
});
    
app.controller('exposeCtrl', function($scope, $http, $location) {
	
	  $scope.getDetail = function() {
		
		
		$scope.debug="Debuger:"+ "http://localhost:8080/expose?portal=1&id="+$location.search()['id'];
	  $http.get("http://localhost:8080/expose?portal="+$location.search()['portal']+"&id="+$location.search()['id']).then(function(response) {

          $scope.exposedata = response.data; 
        switch(response.data.residentialRealEstate)
		{
			case "NO_INFORMATION": $scope.DEresidentialRealEstate="keineAngabe";break;
			case "SINGLE_FAMILY_HOUSE": $scope.DEresidentialRealEstate="Einfamilienhaus";break;
			case "MID_TERRACE_HOUSE": $scope.DEresidentialRealEstate="Reihenmittelhaus";break;
			case "END_TERRACE_HOUSE":$scope.DEresidentialRealEstate="Reiheneckhaus";break;
			case "MULTI_FAMILY_HOUSE": $scope.DEresidentialRealEstate="Mehrfamilienhaus";break;
			case "BUNGALOW": $scope.DEresidentialRealEstate="Bungalow";break;
			case "FARMHOUSE": $scope.DEresidentialRealEstate="Bauernhaus";break;
			case "SEMIDETACHED_HOUSE": $scope.DEresidentialRealEstate="Doppelhaushaelfte";break;
			case "VILLA": $scope.DEresidentialRealEstate="Villa";break;
			case "CASTLE_MANOR_HOUSE":$scope.DEresidentialRealEstate="BurgSchloss";break;
			case "SPECIAL_REAL_ESTATE": $scope.DEresidentialRealEstate="BesondereImmobilie";break;
			case "ROOF_STOREY": $scope.DEresidentialRealEstate="Dachgeschoss";break;
			case "LOFT":$scope.DEresidentialRealEstate="LOFT";break;
			case "MAISONETTE":$scope.DEresidentialRealEstate="MAISONETTE";break;
			case "PENTHOUSE":$scope.DEresidentialRealEstate="PENTHOUSE";break;
			case "TERRACED_FLAT": $scope.DEresidentialRealEstate="Terrassenwohnung";break;
			case "GROUND_FLOOR": $scope.DEresidentialRealEstate="Erdgeschosswohnung";break;
			case "APARTMENT": $scope.DEresidentialRealEstate="Etagenwohnung";break;
			case "RAISED_GROUND_FLOOR":$scope.DEresidentialRealEstate="Hochparterre";break;
			case "HALF_BASEMENT":$scope.DEresidentialRealEstate="Souterrain";break;
			case "OTHER": $scope.DEresidentialRealEstate="Sonstiges";break;
			default:$scope.DEresidentialRealEstate="Sonstiges";
		}
		
		switch(response.data.firing)
		{
			case "NO_INFORMATION": $scope.DEfiring="KeineAngabe";break;
			case "GEOTHERMAL" : $scope.DEfiring="Erdwaerme";break;
			case "SOLAR_HEATING" : $scope.DEfiring="Solarheizung";break;
			case "PELLET_HEATING" : $scope.DEfiring="Pelletheizung";break;
			case "GAS" : $scope.DEfiring="Gas";break;
			case "OIL" : $scope.DEfiring="Oel";break;
			case "DISTRICT_HEATING" : $scope.DEfiring="Fernwaerme";break;
			case "ELECTRICITY" : $scope.DEfiring="Strom";break;
			case "COAL" : $scope.DEfiring="Kohle";break;
			default:$scope.DEfiring="Sonstiges";
		}	
		switch(response.data.heating)
		{
			case "NO_INFORMATION": $scope.DEheating="keine Angabe";break;
			case "SELF_CONTAINED_CENTRAL_HEATING": $scope.DEheating="Etagenheizung";break;
			case "STOVE_HEATING": $scope.DEheating="Ofenheizung";break;
			case "CENTRAL_HEATING": $scope.DEheating="Zentralheizung";break;
			case "COMBINED_HEAT_AND_POWER_PLANT": $scope.DEheating="Blockheizkraftwerk";break;
			case "ELECTRIC_HEATING": $scope.DEheating="Elektro-Heizung";break;
			case "DISTRICT_HEATING": $scope.DEheating="Fernwärme";break;
			case "FLOOR_HEATING": $scope.DEheating="Fußbodenheizung";break;
			case "GAS_HEATING": $scope.DEheating="Gas-Heizung";break;
			case "WOOD_PELLET_HEATING": $scope.DEheating="Holz-Pelletheizung";break;
			case "NIGHT_STORAGE_HEATER": $scope.DEheating="Nachtspeicherofen";break;
			case "OIL_HEATING": $scope.DEheating="Öl-Heizung";break;
			case "SOLAR_HEATING": $scope.DEheating="Solar-Heizung";break;
			case "HEAT_PUMP": $scope.DEheating="Wärmepumpe";break;
			default:$scope.DEheating="Sonstiges";
		}
		switch(response.data.BuildingEnergyRating )
		{
			case "NO_INFORMATION": $scope.DEBuildingEnergyRating ="Keine Angabe";break;
			case "ENERGY_REQUIRED": $scope.DEBuildingEnergyRating ="Endenergiebedarf";break;
			case "ENERGY_CONSUMPTION": $scope.DEBuildingEnergyRating ="Sonstiges";break;
			default:$scope.DEBuildingEnergyRating="Sonstiges";
		}
		switch(response.data.interiorQuality)
		{
			case "NO_INFORMANTION": $scope.DEinteriorQuality ="keine Angabe";break;
			case "LUXURY": $scope.DEinteriorQuality ="luxus";break;
			case "SOPHISTICATED": $scope.DEinteriorQuality ="gehoben";break;
			case "NORMAL SIMPLE": $scope.DEinteriorQuality ="einfach";break;
			default:$scope.DEinteriorQuality="Sonstiges";
		}
		
	switch(response.data.objectstate)
		{		
		case "FIRST_TIME_USE": $scope.DEobjectstate ="Erstbezug";break;
		case "FIRST_TIME_USE_AFTER_REFURBISHMENT": $scope.DEobjectstate ="Erstbezug nach Sanierung";break;
		case "MINT_CONDITION": $scope.DEobjectstate ="Neuwertig";break;
		case "REFURBISHED": $scope.DEobjectstate ="Saniert";break;
		case "MODERNIZED": $scope.DEobjectstate ="Modernisiert";break;
		case "FULLY_RENOVATED": $scope.DEobjectstate ="VollstaendigReonviert";break;
		case "WELL_KEPT": $scope.DEobjectstate ="Gepflegt";break;
		case "NEED_OF_RENOVATION": $scope.DEobjectstate ="Renovierungsbedürftig";break;
		case "NEGOTIABLE": $scope.DEobjectstate ="NachVereinbarung";break;
		case "RIPE_FOR_DEMOLITION": $scope.DEobjectstate ="Abbruchreif";break;
		default:$scope.DEobjectstate="Sonstiges";
		}
		
		switch(response.data.energyCertificate)
		{
			case false:	$scope.DEenergyCertificate="nein";break;
			default: $scope.DEenergyCertificate="ja";
		}
		
		switch(response.data.balcony)
		{
			case false:	$scope.DEbalcony="nein";break;
			default: $scope.DEbalcony="ja";
		}
	    
		switch(response.data.terrace)
		{
			case false:	$scope.DEterrace="nein";break;
			default:$scope.DEterrace="ja";
		
		}
		switch(response.data.garden)
		{
			case false:	$scope.DEgarden="nein";	break;
			default: $scope.DEgarden="ja";
		}
		switch(response.data.commission)
		{
			case false:	$scope.DEcommission="nein";	break;
			default: $scope.DEcommission="ja";
		}
		switch(response.data.availabilityDate)
		{
			case null:	$scope.DEavailabilityDate="Sofort";	break;
			default: $scope.DEavailabilityDate=response.data.availabilityDate;
		}

		switch(response.data.floor)
		{
			case null:	$scope.DEfloor="";	break;
			default: $scope.DEfloor=response.data.floor+" von ";
		}
		
		switch(response.data.purchaseType)
		{
			case "Buy":	$scope.DEpreis="Kaufpreis";	break;
			default: $scope.DEpreis="Mietpreis";
		}
		switch(response.data.image)
		{
			case null:	$scope.DEimage="images/detail/no-photo-available-big.gif";	break;
			default: $scope.DEimage=response.data.image;
		}  
	    	//$scope.exposedata.title = response.data[0].title;  
			
	    });
		
    } 
});


	
