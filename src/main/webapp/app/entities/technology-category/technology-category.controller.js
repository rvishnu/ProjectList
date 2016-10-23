(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('TechnologyCategoryController', TechnologyCategoryController);

    TechnologyCategoryController.$inject = ['$scope', '$state', 'TechnologyCategory', 'TechnologyCategorySearch'];

    function TechnologyCategoryController ($scope, $state, TechnologyCategory, TechnologyCategorySearch) {
        var vm = this;
        
        vm.technologyCategories = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            TechnologyCategory.query(function(result) {
                vm.technologyCategories = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TechnologyCategorySearch.query({query: vm.searchQuery}, function(result) {
                vm.technologyCategories = result;
            });
        }    }
})();
