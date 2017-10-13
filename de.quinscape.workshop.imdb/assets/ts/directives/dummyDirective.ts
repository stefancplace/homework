///<reference path="../_app.d.ts"/>
module de.quinscape.workshop.imdb {
    'use strict';

    class DummyCtrl {
        constructor($scope: ng.IScope, $rootScope: ng.IRootScopeService) {
        }
    }

    function dummyDirective(): ng.IDirective {
        return {
            restrict: 'A',
            scope: true,
            controller: DummyCtrl,
            controllerAs: 'dummyCtrl',
            link: (scope: ng.IScope, element, attrs: any, dummyCtrl: DummyCtrl) => {
                console.log('linking');
            }
        };
    }

    angular.module(MODULE_NAME)
        .directive('deQuinscapeWorkshopImdb', dummyDirective);
}
