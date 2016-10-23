(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('CompanyController', CompanyController);

    CompanyController.$inject = ['$scope', '$state', 'Company', 'CompanySearch'];

    function CompanyController ($scope, $state, Company, CompanySearch) {
        var vm = this;
        
        vm.companies = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Company.query(function(result) {
                vm.companies = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CompanySearch.query({query: vm.searchQuery}, function(result) {
                vm.companies = result;
            });
        }    }
})();
