(function() {
    'use strict';

    angular
        .module('projectListApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('technology-category', {
            parent: 'entity',
            url: '/technology-category',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TechnologyCategories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/technology-category/technology-categories.html',
                    controller: 'TechnologyCategoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('technology-category-detail', {
            parent: 'entity',
            url: '/technology-category/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TechnologyCategory'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/technology-category/technology-category-detail.html',
                    controller: 'TechnologyCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TechnologyCategory', function($stateParams, TechnologyCategory) {
                    return TechnologyCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'technology-category',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('technology-category-detail.edit', {
            parent: 'technology-category-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/technology-category/technology-category-dialog.html',
                    controller: 'TechnologyCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TechnologyCategory', function(TechnologyCategory) {
                            return TechnologyCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('technology-category.new', {
            parent: 'technology-category',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/technology-category/technology-category-dialog.html',
                    controller: 'TechnologyCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                categoryId: null,
                                parentCategoryId: null,
                                path: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('technology-category', null, { reload: 'technology-category' });
                }, function() {
                    $state.go('technology-category');
                });
            }]
        })
        .state('technology-category.edit', {
            parent: 'technology-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/technology-category/technology-category-dialog.html',
                    controller: 'TechnologyCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TechnologyCategory', function(TechnologyCategory) {
                            return TechnologyCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('technology-category', null, { reload: 'technology-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('technology-category.delete', {
            parent: 'technology-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/technology-category/technology-category-delete-dialog.html',
                    controller: 'TechnologyCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TechnologyCategory', function(TechnologyCategory) {
                            return TechnologyCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('technology-category', null, { reload: 'technology-category' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
