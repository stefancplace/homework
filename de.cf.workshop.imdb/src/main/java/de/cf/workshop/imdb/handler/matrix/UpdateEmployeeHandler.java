/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.matrix;

import static cf.cplace.platform.test.ccx.TestTypes.EMPLOYEE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cf.cplace.platform.assets.custom.CustomEntity;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.search.Filters;
import cf.cplace.platform.assets.search.Search;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.client.RequestLocal;
import cf.cplace.platform.handler.GsonAnswerStation;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.JsonSuccessStation;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.orm.PersistentEntity;
import cf.cplace.platform.util.Gsonable;
import cf.cplace.training.extended.ExtendedAppTypes;
import de.cf.workshop.imdb.ImdbAppTypes;

public class UpdateEmployeeHandler extends Handler {
    public static final Message successMessage = new Message() {
    };

    final Station SUCCESS = new JsonSuccessStation() {
        @Override
        protected String getTargetUrl() {
            return "";
        }

        @Override
        protected Message getConfirmationMessage() {
            return successMessage;
        }
    };

    private Page embeddingPage;

    private Page employee;
    private Page department;
    String employeeId;
    String departmentId;

    @Override
    protected void checkAccess() {
//        employee = Page.SCHEMA.getEntityAndCheckMayEdit(Parameters.getString("employeeId"));

        embeddingPage = Page.SCHEMA.getEntityNotNull(Parameters.getString("id"));
        employeeId = Parameters.getString("employeeId");
        departmentId = Parameters.getString("departmentId");

    }

    @Override
    protected Station doBusinessLogic() {
        Search s = new Search();
        s.add(Filters.space(embeddingPage.getSpaceNotNullWithoutReadAccessCheck()));
        s.add(Filters.type(ImdbAppTypes.EMPLOYEE.TYPE));
        s.add(Filters.uid(employeeId));
        s.addAlphabeticalSort();
        employee = s.findAllPagesAsList().get(0);
        s = new Search();
        s.add(Filters.space(embeddingPage.getSpaceNotNullWithoutReadAccessCheck()));
        s.add(Filters.type(ImdbAppTypes.DEPARTMENT.TYPE));
        s.add(Filters.customAttribute(ImdbAppTypes.DEPARTMENT.ISTEMPLATE, false));
        s.add(Filters.uid(departmentId));
        department = s.findAllPagesAsList().get(0);
        toggleDepartmentMembership(employee, department);
        return SUCCESS;
    }

    static void toggleDepartmentMembership(final CustomEntity employeeEntity, final CustomEntity departmentEntity) {
        final Page dept = (Page) departmentEntity;
        final List<Page> depts = employeeEntity.get(ImdbAppTypes.EMPLOYEE.DEPARTMENTS);
        if (depts.contains(dept)) {
            depts.remove(dept);
        } else {
            depts.add(dept);
        }
        PersistentEntity.doOnWritableCopyAndPersistIfModified(employeeEntity, copy -> {
            copy.set(ImdbAppTypes.EMPLOYEE.DEPARTMENTS, depts);
        });

    }
}
