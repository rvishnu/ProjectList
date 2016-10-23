(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('TechnologyCategoryDetailController', TechnologyCategoryDetailController);

    TechnologyCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TechnologyCategory'];

    function TechnologyCategoryDetailController($scope, $rootScope, $stateParams, previousState, entity, TechnologyCategory) {
        var vm = this;

        vm.technologyCategory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projectListApp:technologyCategoryUpdate', function(event, result) {
            vm.technologyCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
