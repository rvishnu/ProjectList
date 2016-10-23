(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('ProjectDialogController', ProjectDialogController);

    ProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Project', 'Department', 'Employee', 'Technology'];

    function ProjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Project, Department, Employee, Technology) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.save = save;
        vm.departments = Department.query();
        vm.employees = Employee.query();
        vm.technologies = Technology.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveError);
            } else {
                Project.save(vm.project, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projectListApp:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
