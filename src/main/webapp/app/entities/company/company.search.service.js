(function() {
    'use strict';

    angular
        .module('projectListApp')
        .factory('CompanySearch', CompanySearch);

    CompanySearch.$inject = ['$resource'];

    function CompanySearch($resource) {
        var resourceUrl =  'api/_search/companies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
