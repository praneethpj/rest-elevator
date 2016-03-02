(function(angular) {
  var AppController = function($scope, $timeout, $sce, Elevator) {

	(function refresh() {
		Elevator.query(function(response) {
		      $scope.elevators = response ? response : [];
		});
		$timeout(refresh, 2000);
    })();
    
    
    $scope.requestElevator = function(floor) {
    	if($scope.validate(floor)) {
    		Elevator.request(floor);
    	}
    };
    
    $scope.validate = function(floor) {
    	if(floor.noOfPeople > 0 && floor.noOfPeople <= 20 && floor.floorNo != floor.toFloorNo) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    $scope.elevatorStatus = function(floor, elevatorID) {
    	var text = "";
    	angular.forEach($scope.elevators, function(elevator) {
    	      if (parseInt(elevator.currentFloorNo) == floor.floorNo && elevator.id == elevatorID){
    	    	  var moveText = '-';
    	    	  if(elevator.status == 'MOVE_DOWN') {
    	    		  moveText = "\\/";
    	    	  }
    	    	  else if(elevator.status == 'MOVE_UP') {
    	    		  moveText = "/\\";
    	    	  }
    	    	  var textColorClass = 'redColor';
    	    	  if(elevator.status == 'IDLE') {
    	    		  textColorClass = 'greenColor';
    	    	  }
    	    	  text = '<span class="' + textColorClass  + '">' + elevator.noOfPeople + moveText + '</span>';
    	    	  if(elevator.noOfPeople > 0) {
    	    		  floor.noOfPeople = 0;
    	    		  floor.toFloorNo = floor.floorNo;
    	    	  }
    	      }
    	});
    	return $sce.trustAsHtml(text);
    };  
    
    $scope.digitsOnly = function(event) {
    	  var controlKeys = [8, 9, 13, 35, 36, 37, 39];
    	  var isControlKey = controlKeys.join(",").match(new RegExp(event.which));
    	  if (!event.which ||
    	      (49 <= event.which && event.which <= 57) ||
    	      (48 == event.which && $(this).attr("value")) ||
    	      isControlKey) {
    	    return;
    	  } else {
    	    event.preventDefault();
    	  }
    };
    
    $scope.floors = [{floorNo:10,noOfPeople:0,toFloorNo:10},
                     {floorNo:9,noOfPeople:0,toFloorNo:9},
                     {floorNo:8,noOfPeople:0,toFloorNo:8},
                     {floorNo:7,noOfPeople:0,toFloorNo:7},
                     {floorNo:6,noOfPeople:0,toFloorNo:6},
                     {floorNo:5,noOfPeople:0,toFloorNo:5},
                     {floorNo:4,noOfPeople:0,toFloorNo:4},
                     {floorNo:3,noOfPeople:0,toFloorNo:3},
                     {floorNo:2,noOfPeople:0,toFloorNo:2},
                     {floorNo:1,noOfPeople:0,toFloorNo:1}
                     ];
    $scope.floorOptions = [{id:10,name:'Level 10'},
                           {id:9,name:'Level 9'},
                           {id:8,name:'Level 8'},
                           {id:7,name:'Level 7'},
                           {id:6,name:'Level 6'},
                           {id:5,name:'Level 5'},
                           {id:4,name:'Level 4'},
                           {id:3,name:'Level 3'},
                           {id:2,name:'Level 2'},
                           {id:1,name:'Level 1'}
                     ];
  };
  
  AppController.$inject = ['$scope', '$timeout', '$sce','Elevator'];
  angular.module("myApp.controllers").controller("AppController", AppController);
  
}(angular));