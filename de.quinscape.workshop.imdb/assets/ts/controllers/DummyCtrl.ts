///<reference path="../_app.d.ts"/>
module de.quinscape.workshop.imdb {
    'use strict';

    class DummyCtrl {
        static CTRL_NAME = MODULE_NAME + '.DummyCtrl';

        constructor($scope:ng.IScope) {
        }
    }

    angular.module(MODULE_NAME)
        .controller(DummyCtrl.CTRL_NAME, DummyCtrl);
}
