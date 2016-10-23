(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Project', 'Department', 'Employee', 'Technology'];

    function ProjectDetailController($scope, $rootScope, $stateParams, previousState, entity, Project, Department, Employee, Technology) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projectListApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
