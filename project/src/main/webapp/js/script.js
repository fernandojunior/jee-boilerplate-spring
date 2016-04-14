angular.module("app", []).controller(
		'MainController',
		[
				"$scope",
				"$http",
				"$timeout",
				function($scope, $http, $timeout) {

					$scope.all = function() {
						$http.get('/project/user/').success(function(data) {
							$scope.users = data;
						});
					}

					$scope.all();

					// Form submit handler.
					$scope.submit = function(form) {

						// Trigger validation flag.
						$scope.submitted = true;

						// If form is invalid, return and let AngularJS
						// show validation errors.
						if (form.$invalid) {
							return;
						}

						var context = {
							'name' : $scope.name,
							'email' : $scope.email,
							'phone' : $scope.phone,
						};

						console.log(context);

						var scope = $scope;

						var res = jQuery.post('/project/user/', context,
								function(data, status) {
									console.log(data);
									console.log(status);
									scope.messages = 'User has been created!';
									$scope.all();
									$scope.name = null;
									$scope.email = null;
									$scope.phone = null;
									$scope.submitted = false;

								});

						res.fail(function(data, status) {
							console.log(data.responseJSON.message);
							scope.messages = data.responseJSON.message;
						});
						res.always(function() {
							// Hide status messages after three seconds.
							$timeout(function() {
								$scope.messages = null;
							}, 3000);
						});

					};
				} ]);