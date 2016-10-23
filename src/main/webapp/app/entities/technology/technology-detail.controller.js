(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('TechnologyDetailController', TechnologyDetailController);

    TechnologyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Technology', 'Project', 'TechnologyCategory'];

    function TechnologyDetailController($scope, $rootScope, $stateParams, previousState, entity, Technology, Project, TechnologyCategory) {
        var vm = this;

        vm.technology = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projectListApp:technologyUpdate', function(event, result) {
            vm.technology = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
