(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('TechnologyDialogController', TechnologyDialogController);

    TechnologyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Technology', 'Project', 'TechnologyCategory'];

    function TechnologyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Technology, Project, TechnologyCategory) {
        var vm = this;

        vm.technology = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projects = Project.query();
        vm.technologycategories = TechnologyCategory.query({filter: 'technology-is-null'});
        $q.all([vm.technology.$promise, vm.technologycategories.$promise]).then(function() {
            if (!vm.technology.technologyCategoryId) {
                return $q.reject();
            }
            return TechnologyCategory.get({id : vm.technology.technologyCategoryId}).$promise;
        }).then(function(technologyCategory) {
            vm.technologycategories.push(technologyCategory);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.technology.id !== null) {
                Technology.update(vm.technology, onSaveSuccess, onSaveError);
            } else {
                Technology.save(vm.technology, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('projectListApp:technologyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
