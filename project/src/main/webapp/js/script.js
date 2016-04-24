var app = angular.module("app", []);

app.controller(
	'MainController',
	["$scope", "$http", "$timeout", function($scope, $http, $timeout) {
		$scope.all = function() {
			$http.get('/project/user/').success(function(data) {
				$scope.users = data;
			});
		}

		$scope.all();

		$scope.submit = function(form) {
			$scope.submitted = true;

			if (form.$invalid)
				return;

			var context = {
				'name' : $scope.name,
				'email' : $scope.email,
				'phone' : $scope.phone,
			};

			var url = '/project/user/';

			var res = jQuery.post(url, context, function(data, status) {
				$scope.message = 'User has been created!';
				$scope.all();
				$scope.name = null;
				$scope.email = null;
				$scope.phone = null;
			});

			res.error(function(data, status) {
				console.log(data.responseJSON.message);
				$scope.message = data.responseJSON.message;
			});

			res.done(function() {
				$scope.submitted = false;
				// Hide status messages after three seconds.
				 $timeout(function() {
					 $scope.message = null;
				 }, 3000);
			});

		};
	}]
);
