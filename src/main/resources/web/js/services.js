'use strict';

angular.module('sprayWorkshop', ['ngResource']).factory('Fruits', function($resource) {
    return $resource('api/fruits');
});
