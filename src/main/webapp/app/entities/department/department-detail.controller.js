(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('DepartmentDetailController', DepartmentDetailController);

    DepartmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Department', 'Location', 'Project', 'Employee', 'Company'];

    function DepartmentDetailController($scope, $rootScope, $stateParams, previousState, entity, Department, Location, Project, Employee, Company) {
        var vm = this;

        vm.department = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('projectListApp:departmentUpdate', function(event, result) {
            vm.department = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
