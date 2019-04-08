/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

import {IScope, IDirective} from 'angular';

export function deCfHomeworkMatrix(): IDirective {
    interface IMoviePostersState {
        departmentsUrl: string;
        employeesUrl: string;
    }

    interface IMatrixData {
        employees: IEmployee[];
        departments: IDepartment[];
    }

    interface IEmployee {
        departments: [];
        firstName: String;
        lastName: String;
        id: String;
    }

    interface IDepartment {
        name: String;
        id: String;
    }

    class MatrixCtrl {
        employees: IEmployee[];
        departments: IDepartment[];
        matrix: boolean [][];
        private state: IMoviePostersState;

        constructor($scope: ng.IScope, private $http: ng.IHttpService) {
        }

        initialize(state: IMoviePostersState) {
            this.state = state;
            this.loadMatrix();
        }

        private loadMatrix(): void {
            this.$http.get(this.state.employeesUrl)
                .then((response: ng.IHttpPromiseCallbackArg<IMatrixData>) => {
                    this.employees = response.data.employees;
                    this.$http.get(this.state.departmentsUrl)
                        .then((response: ng.IHttpPromiseCallbackArg<IMatrixData>) => {
                            this.departments = response.data.departments;
                            this.setupMatrix();
                            console.log(this.matrix);
                        });
                });

        }

        private setupMatrix(): void {
            this.matrix = [];
            for (var i: number = 0; i < this.employees.length; i++) {
                this.matrix[i] = [];
                for (var j: number = 0; j < this.departments.length; j++) {
                    this.matrix[i][j] = false;
                    if (this.employeeIsInDepartment(this.employees[i], this.departments[j].name)) {
                        this.matrix[i][j] = true;
                    }
                }
            }
        }

        private isEmployeeInDepartment(employee: IEmployee, department: String): boolean {
            let employeeIsIn: boolean = false;
            employeeIsIn = this.employeeIsInDepartment(employee, department);
            return employeeIsIn;
        }

        private employeeIsInDepartment(employee: IEmployee, department: String): boolean {
            let employeeIsIn: boolean = false;
            employee.departments.forEach((departmentOfEmployee) => {
                if (departmentOfEmployee == department) {
                    employeeIsIn = true;
                }
            });
            return employeeIsIn;
        }
    }

    return {
        restrict: 'E',
        scope: true,
        controller: MatrixCtrl,
        controllerAs: 'matrixCtrl',
        link: (scope: IScope, element, attrs: any, matrixCtrl: MatrixCtrl) => {
            matrixCtrl.initialize(JSON.parse(attrs['state']));
        }
    };
}