(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('TechnologyCategoryDialogController', TechnologyCategoryDialogController);

    TechnologyCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TechnologyCategory'];

    function TechnologyCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TechnologyCategory) {
        var vm = this;

        vm.technologyCategory = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.technologyCategory.id !== null) {
                TechnologyCategory.update(vm.technologyCategory, onSaveSuccess, onSaveError);
            } else {
                TechnologyCategory.save(vm.technologyCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projectListApp:technologyCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
