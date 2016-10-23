(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('TechnologyController', TechnologyController);

    TechnologyController.$inject = ['$scope', '$state', 'Technology', 'TechnologySearch'];

    function TechnologyController ($scope, $state, Technology, TechnologySearch) {
        var vm = this;
        
        vm.technologies = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Technology.query(function(result) {
                vm.technologies = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TechnologySearch.query({query: vm.searchQuery}, function(result) {
                vm.technologies = result;
            });
        }    }
})();
