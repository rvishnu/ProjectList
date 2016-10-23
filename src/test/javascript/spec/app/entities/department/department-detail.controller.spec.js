'use strict';

describe('Controller Tests', function() {

    describe('Department Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDepartment, MockLocation, MockProject, MockEmployee, MockCompany;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockLocation = jasmine.createSpy('MockLocation');
            MockProject = jasmine.createSpy('MockProject');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockCompany = jasmine.createSpy('MockCompany');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Department': MockDepartment,
                'Location': MockLocation,
                'Project': MockProject,
                'Employee': MockEmployee,
                'Company': MockCompany
            };
            createController = function() {
                $injector.get('$controller')("DepartmentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'projectListApp:departmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
