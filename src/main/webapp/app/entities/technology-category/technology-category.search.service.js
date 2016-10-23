(function() {
    'use strict';

    angular
        .module('projectListApp')
        .factory('TechnologyCategorySearch', TechnologyCategorySearch);

    TechnologyCategorySearch.$inject = ['$resource'];

    function TechnologyCategorySearch($resource) {
        var resourceUrl =  'api/_search/technology-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
