'use strict';

angular.module('workshopServices', ['ngResource']).factory('Fruits', function($resource) {
    return $resource('api/fruits');
});
