import { IScope, IDirective } from 'angular';

export function deCfWorkshopImdbMoviePosters(): IDirective {
    interface IMoviePostersState {
        loadMoviesUrl: string;
    }

    interface IMoviesData {
        movies: IMovie[];
    }

    interface IMovie {
        title: string;
        coverUrl: string;
        date: string;
    }

    class MoviePostersCtrl {
        movies: IMovie[];

        private state: IMoviePostersState;

        constructor($scope: ng.IScope, private $http: ng.IHttpService) {
        }

        initialize(state: IMoviePostersState) {
            this.state = state;
            this.loadMovies();
        }

        private loadMovies(): void {
            this.$http.get(this.state.loadMoviesUrl)
                .then((response: ng.IHttpPromiseCallbackArg<IMoviesData>) => {
                    this.movies = response.data.movies;
                });
        }
    }

    return {
        restrict: 'E',
        scope: true,
        controller: MoviePostersCtrl,
        controllerAs: 'moviePostersCtrl',
        link: (scope: IScope, element, attrs: any, moviePostersCtrl: MoviePostersCtrl) => {
            moviePostersCtrl.initialize(JSON.parse(attrs['state']));
        }
    };
}