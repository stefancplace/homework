/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.moviePosters;

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
import cf.cplace.platform.util.Gsonable;
import de.cf.workshop.imdb.ImdbAppTypes;

public class LoadMoviesHandler extends Handler {

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
        final List<Movie> movies = Page.SCHEMA.createQuery()
                .where(it -> it._space().isEqualTo(embeddingPage.getSpaceNotNullWithoutReadAccessCheck()))
                .where(it -> it._customType().isEqualTo(ImdbAppTypes.MOVIE.TYPE))
                .findStream()
                .map(Movie::new)
                .collect(Collectors.toList());

        result = new Result(movies);

        return DATA;
    }

    private static class Result extends Gsonable {
        List<Movie> movies;

        Result(List<Movie> movies) {
            this.movies = movies;
        }
    }

    private static class Movie {
        String title;
        String coverUrl;
        Date releaseDate;

        Movie(Page movie) {
            title = movie.getName();

            final Document cover = movie.get(ImdbAppTypes.MOVIE.COVER);
            if (cover != null) {
                coverUrl = Forwarder.getFullUrl(DownloadHandler.class, cover);
            }

            releaseDate = movie.get(ImdbAppTypes.MOVIE.RELEASEDATE);
        }
    }
}
