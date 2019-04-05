/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

import {IScope, IDirective} from 'angular';

export function deCfHomeworkMatrix(): IDirective {
    interface IMoviePostersState {
        marixUrl: string;
    }

    interface IMoviesData {
        movies: IMovie[];
    }

    interface IMovie {
        title: string;
        coverUrl: string;
        date: string;
    }

    class MatrixCtrl {
        movies: IMovie[];

        private state: IMoviePostersState;

        constructor($scope: ng.IScope, private $http: ng.IHttpService) {
        }

        initialize(state: IMoviePostersState) {
            this.state = state;
            this.loadMatrix();
        }

        private loadMatrix(): void {
            this.$http.get(this.state.marixUrl)
                .then((response: ng.IHttpPromiseCallbackArg<IMoviesData>) => {
                    this.movies = response.data.movies;
                });
        }
    }

    return {
        restrict: 'E',
        scope: true,
        controller: MatrixCtrl,
        controllerAs: 'moviePostersCtrl',
        link: (scope: IScope, element, attrs: any, matrixCtrl: MatrixCtrl) => {
            matrixCtrl.initialize(JSON.parse(attrs['state']));
        }
    };
}