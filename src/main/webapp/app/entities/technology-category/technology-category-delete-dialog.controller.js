(function() {
    'use strict';

    angular
        .module('projectListApp')
        .controller('TechnologyCategoryDeleteController',TechnologyCategoryDeleteController);

    TechnologyCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'TechnologyCategory'];

    function TechnologyCategoryDeleteController($uibModalInstance, entity, TechnologyCategory) {
        var vm = this;

        vm.technologyCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TechnologyCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
