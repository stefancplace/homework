/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.matrix;

import static cf.cplace.platform.test.ccx.TestTypes.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.handler.GsonAnswerStation;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.test.ccx.TestTypes;
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
        final List<Employee> employees = Page.SCHEMA.createQuery()
                .where(it -> it._space().isEqualTo(embeddingPage.getSpaceNotNullWithoutReadAccessCheck()))
                .where(it -> it._customType().isEqualTo(EMPLOYEE.TYPE))
                .findStream()
                .map(Employee::new)
                .collect(Collectors.toList());

        // search.add(Filters.customAttribute(DEPARTMENT.ISTEMPLATE, true));
        result = new Result(employees);

        return DATA;
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

        Employee(Page employee) {
            firstName = employee.get(ImdbAppTypes.EMPLOYEE.SURNAME);
            lastName = employee.get(ImdbAppTypes.EMPLOYEE.FAMILY_NAME);
            id = employee.getUid();
            List<Page> pDepartments = employee.get(ImdbAppTypes.EMPLOYEE.DEPARTMENTS);
            departments = new ArrayList<>();
            for (Page pDpartment : pDepartments) {
                departments.add(pDepartments.get(0).getName());
            }
            Set<String> sDepartments = new HashSet<>(departments);
            departments.clear();
            departments.addAll(sDepartments);
        }
    }
}
