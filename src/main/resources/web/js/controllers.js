'use strict';

function GabblerCtrl($scope, Fruits) {

    $scope.fruits = [];

    $scope.getFruits = function() {
        var fruits = Fruits.query();
        $scope.fruits = fruits;
    }
}
