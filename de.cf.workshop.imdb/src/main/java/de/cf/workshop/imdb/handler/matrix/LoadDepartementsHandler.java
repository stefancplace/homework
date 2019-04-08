/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.matrix;

import static cf.cplace.platform.test.ccx.TestTypes.EMPLOYEE;
import static de.cf.workshop.imdb.ImdbAppTypes.*;

import java.util.List;
import java.util.stream.Collectors;

import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.search.Filters;
import cf.cplace.platform.assets.search.Search;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.handler.GsonAnswerStation;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.Station;
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
        final List<Department> departments = Page.SCHEMA.createQuery()
                .where(it -> it._space().isEqualTo(embeddingPage.getSpaceNotNullWithoutReadAccessCheck()))
                .where(it -> it._customType().isEqualTo(DEPARTMENT.TYPE))
                .findStream()
                .map(Department::new)
                .collect(Collectors.toList());

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

        Department(Page department) {
            name = department.getName();
            id = department.getUid();
        }
    }
}
