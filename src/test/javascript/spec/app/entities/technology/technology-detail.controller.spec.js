'use strict';

describe('Controller Tests', function() {

    describe('Technology Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTechnology, MockProject, MockTechnologyCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTechnology = jasmine.createSpy('MockTechnology');
            MockProject = jasmine.createSpy('MockProject');
            MockTechnologyCategory = jasmine.createSpy('MockTechnologyCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Technology': MockTechnology,
                'Project': MockProject,
                'TechnologyCategory': MockTechnologyCategory
            };
            createController = function() {
                $injector.get('$controller')("TechnologyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projectListApp:technologyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
