(function(angular) {
  var ElevatorFactory = function($resource) {
	    
	    return $resource('/elevators', {}, {
	    	request: {
		        method: "GET", url:"/elevators/floorNo/:floorNo/toFloorNo/:toFloorNo/noOfPeople/:noOfPeople", 
		        params: {floorNo:'@floorNo', toFloorNo:'@toFloorNo', noOfPeople:'@noOfPeople'}
	      }
	    });	    
  };

  ElevatorFactory.$inject = ['$resource'];
  angular.module("myApp.services").factory("Elevator", ElevatorFactory);
}(angular));