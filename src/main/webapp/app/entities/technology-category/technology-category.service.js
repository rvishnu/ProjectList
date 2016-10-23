(function() {
    'use strict';
    angular
        .module('projectListApp')
        .factory('TechnologyCategory', TechnologyCategory);

    TechnologyCategory.$inject = ['$resource'];

    function TechnologyCategory ($resource) {
        var resourceUrl =  'api/technology-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
