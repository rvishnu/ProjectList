(function() {
    'use strict';

    angular
        .module('projectListApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('technology', {
            parent: 'entity',
            url: '/technology',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Technologies'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/technology/technologies.html',
                    controller: 'TechnologyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('technology-detail', {
            parent: 'entity',
            url: '/technology/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Technology'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/technology/technology-detail.html',
                    controller: 'TechnologyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Technology', function($stateParams, Technology) {
                    return Technology.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'technology',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('technology-detail.edit', {
            parent: 'technology-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/technology/technology-dialog.html',
                    controller: 'TechnologyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Technology', function(Technology) {
                            return Technology.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('technology.new', {
            parent: 'technology',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/technology/technology-dialog.html',
                    controller: 'TechnologyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                version: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('technology', null, { reload: 'technology' });
                }, function() {
                    $state.go('technology');
                });
            }]
        })
        .state('technology.edit', {
            parent: 'technology',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/technology/technology-dialog.html',
                    controller: 'TechnologyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Technology', function(Technology) {
                            return Technology.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('technology', null, { reload: 'technology' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('technology.delete', {
            parent: 'technology',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/technology/technology-delete-dialog.html',
                    controller: 'TechnologyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Technology', function(Technology) {
                            return Technology.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('technology', null, { reload: 'technology' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
