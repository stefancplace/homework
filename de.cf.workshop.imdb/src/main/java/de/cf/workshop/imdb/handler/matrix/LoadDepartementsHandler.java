/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.matrix;

import static cf.cplace.platform.test.ccx.TestTypes.EMPLOYEE;
import static de.cf.workshop.imdb.ImdbAppTypes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.search.Filters;
import cf.cplace.platform.assets.search.Search;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.handler.GsonAnswerStation;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.services.IconService;
import cf.cplace.platform.util.Gsonable;
import de.cf.workshop.imdb.ImdbAppTypes;

public class LoadDepartementsHandler extends Handler {

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
        s.add(Filters.type(DEPARTMENT.TYPE));
        s.add(Filters.customAttribute(DEPARTMENT.ISTEMPLATE, false));
        s.addAlphabeticalSort();
        List<Page> pDepartments = s.findAllPagesAsList();
        List<Department> departments = new ArrayList<>();
        for (Page pDepartment : pDepartments) {
            departments.add(new Department(pDepartment));
        }

        // search.add(Filters.customAttribute(DEPARTMENT.ISTEMPLATE, true));
        result = new Result(departments);

        return DATA;
    }

    private static class Result extends Gsonable {
        List<Department> departments;

        Result(List<Department> departments) {
            this.departments = departments;
        }
    }

    private static class Department {
        String name;
        String id;
        String url;
        String icon;

        Department(Page department) {
            name = department.getName();
            id = department.getUid();
            url = department.getUrl();
            icon = IconService.getIconClassFromIconName(department.getIconName());

        }
    }
}
