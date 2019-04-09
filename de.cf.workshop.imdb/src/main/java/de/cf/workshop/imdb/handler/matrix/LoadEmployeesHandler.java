/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.matrix;

import static cf.cplace.platform.test.ccx.TestTypes.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.search.Filters;
import cf.cplace.platform.assets.search.Search;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.handler.GsonAnswerStation;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.orm.Feature;
import cf.cplace.platform.services.IconService;
import cf.cplace.platform.util.Gsonable;
import de.cf.workshop.imdb.ImdbAppTypes;

public class LoadEmployeesHandler extends Handler {

    final Station DATA = new GsonAnswerStation() {
        @Override
        protected String getString() {
            return result.toJson();
        }
    };

    private Page embeddingPage;
    private Result result;

    @Override
    protected void checkAccess() {
        embeddingPage = Page.SCHEMA.getEntityNotNull(Parameters.getString("id"));
    }

    @Override
    protected Station doBusinessLogic() {
        Search s = new Search();
        s.add(Filters.space(embeddingPage.getSpaceNotNullWithoutReadAccessCheck()));
        s.add(Filters.type(ImdbAppTypes.EMPLOYEE.TYPE));
        s.addAlphabeticalSort();
        List<Page> pEmployees = s.findAllPagesAsList();
        List<Employee> employees = new ArrayList<>();
        for (Page pEmployee : pEmployees) {
            employees.add(new Employee(pEmployee));
        }


        // search.add(Filters.customAttribute(DEPARTMENT.ISTEMPLATE, true));
        result = new Result(employees);

        return DATA;
    }

    private static boolean mayEditDepartmentsAttribute(final Page employee) {
                /*
         * We do not ask if the current user can edit the employee as a page in general,
         * but if she/he can change the DEPARTMENTS attribute specifically. We do this
         * since there are extensions to overrule permissions on attribute level
         * (see OverruleCustomAttributeMayEditFeatureAppExtension)
         */
        return Optional.ofNullable(employee.getCustomAttribute(EMPLOYEE.DEPARTMENTS.name))
                .map(Feature::isInPlaceEditableAndMayEdit)
                .orElse(false);
    }

    private static class Result extends Gsonable {
        List<Employee> employees;

        Result(List<Employee> employees) {
            this.employees = employees;
        }
    }

    private static class Employee {
        List<String> departments;
        String firstName;
        String lastName;
        String id;
        String url;
        String icon;

        Employee(Page employee) {
            firstName = employee.get(ImdbAppTypes.EMPLOYEE.SURNAME);
            lastName = employee.get(ImdbAppTypes.EMPLOYEE.FAMILY_NAME);
            id = employee.getUid();
            url = employee.getUrl();
            List<Page> pDepartments = employee.get(ImdbAppTypes.EMPLOYEE.DEPARTMENTS);
            departments = new ArrayList<>();
            for (Page pDpartment : pDepartments) {
                departments.add(pDpartment.getName());
            }
            Set<String> sDepartments = new HashSet<>(departments);
            departments.clear();
            departments.addAll(sDepartments);
            icon = IconService.getIconClassFromIconName(employee.getIconName());
        }
    }
}
