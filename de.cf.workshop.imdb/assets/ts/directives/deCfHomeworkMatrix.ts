/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

import {CPLACE_WIDGET_DIRECTIVE} from "@cf.cplace.platform/widgetLayout/directives/cplaceWidget";
import {WidgetCtrl} from "@cf.cplace.platform/widgetLayout/controllers/WidgetCtrl";
import {PostHeadersService} from "@cf.cplace.platform/services/PostHeadersService";
import {IAugmentedJQuery, IHttpService, ITimeoutService, IDirective, IScope} from 'angular';

export function deCfHomeworkMatrix(): IDirective {
    interface IMoviePostersState {
        departmentsUrl: string;
        employeesUrl: string;
        updateEmployeeUrl: string;
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
        url: String;
        icon: String;
    }

    interface IDepartment {
        name: String;
        id: String;
        url: String;
        icon: String;
    }

    class MatrixCtrl {
        employees: IEmployee[];
        departments: IDepartment[];
        matrix: boolean [][];
        private state: IMoviePostersState;
        widgetCtrl: WidgetCtrl;

        constructor(protected $scope: IScope, protected $element: IAugmentedJQuery, protected $http: IHttpService, protected $timeout: ITimeoutService, protected postHeadersService: PostHeadersService) {
        }

        initialize(state: IMoviePostersState, widgetCtrl: WidgetCtrl) {
            this.state = state;
            this.widgetCtrl = widgetCtrl;
            this.widgetCtrl.enableReloadAction(this.loadMatrix.bind(this), this.$scope);
            this.loadMatrix();
        }

        private loadMatrix(): void {
            this.widgetCtrl.loading = true;
            this.$http.get(this.state.employeesUrl)
                .then((response: ng.IHttpPromiseCallbackArg<IMatrixData>) => {
                    this.employees = response.data.employees;
                    this.$http.get(this.state.departmentsUrl)
                        .then((response: ng.IHttpPromiseCallbackArg<IMatrixData>) => {
                            this.departments = response.data.departments;
                            this.setupMatrix();
                            console.log(this.matrix);
                            this.widgetCtrl.loading = false;
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

        private updateEmployee(employee: IEmployee, department: String): void {

            var data = {employeeId: employee.id, departmentId: department};
            this.$http({
                url: this.state.updateEmployeeUrl,
                method: "POST",
                params: data
            }).then((response: ng.IHttpPromiseCallbackArg<IMatrixData>) => {
                this.loadMatrix()
            });
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
        require: [`^^${CPLACE_WIDGET_DIRECTIVE}`, 'deCfHomeworkMatrix'],
        controller: MatrixCtrl,
        controllerAs: 'matrixCtrl',
        link: (scope, element, attrs, ctrls: any[]) => {
            const widgetCtrl = ctrls[0] as WidgetCtrl;
            const matrixCtrl = ctrls[1] as MatrixCtrl;

            matrixCtrl.initialize(JSON.parse(attrs['state']), widgetCtrl);
        }
    };
}