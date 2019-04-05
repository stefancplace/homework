/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.matrix;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cf.cplace.platform.assets.file.Document;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.handler.GsonAnswerStation;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.handler.document.DownloadHandler;
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
                .where(it -> it._customType().isEqualTo(TestTypes.EMPLOYEE.TYPE))
                .findStream()
                .map(Employee::new)
                .collect(Collectors.toList());

        result = new Result(employees);

        return DATA;
    }

    private static class Result extends Gsonable {
        List<Employee> movies;

        Result(List<Employee> movies) {
            this.movies = movies;
        }
    }

    private static class Employee {
        String title;
        String coverUrl;
        Date releaseDate;

        Employee(Page movie) {
            title = movie.getName();

            final Document cover = movie.get(ImdbAppTypes.MOVIE.COVER);
            if (cover != null) {
                coverUrl = Forwarder.getFullUrl(DownloadHandler.class, cover);
            }

            releaseDate = movie.get(ImdbAppTypes.MOVIE.RELEASEDATE);
        }
    }
}
