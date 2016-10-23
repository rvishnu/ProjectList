(function() {
    'use strict';

    angular
        .module('projectListApp')
        .factory('TechnologySearch', TechnologySearch);

    TechnologySearch.$inject = ['$resource'];

    function TechnologySearch($resource) {
        var resourceUrl =  'api/_search/technologies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
