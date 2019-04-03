/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.createSequel;

import com.google.common.base.Preconditions;

import cf.cplace.platform.assets.custom.CustomSessionEntity;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.JsonSuccessStation;
import cf.cplace.platform.handler.Line;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.util.NameValue;
import de.cf.workshop.imdb.ImdbAppTypes.MOVIE;
import de.cf.workshop.imdb.ImdbSessionTypes.CREATE_SEQUEL;

public class SubmitHandler extends Handler {

    public static final Message successMessage = new Message() {
    };

    final Station SUCCESS = new JsonSuccessStation() {
        @Override
        protected String getTargetUrl() {
            return sequel.getUrl();
        }

        @Override
        protected Message getConfirmationMessage() {
            return successMessage;
        }
    };

    final Station INVALID = new Line() {
        @Override
        public void next(Forwarder f) {
            f.go(EditHandler.class, new NameValue("customSessionEntityId", customSessionEntity.getId()));
        }
    };

    Page sequel;

    CustomSessionEntity customSessionEntity;

    @Override
    protected void checkAccess() {
        String customSessionEntityId = Parameters.getString("customSessionEntityId");
        customSessionEntity = CustomSessionEntity.SCHEMA.getEntityNotNull(customSessionEntityId);
    }

    @Override
    protected Station doBusinessLogic() {
        customSessionEntity.applyParameters();
        if(customSessionEntity.isUiSubmitValidAndNotModifiedAndGenerateErrorMessagesIfNot()) {
            sequel = Page.SCHEMA.createWritableEntity();

            Page movie = customSessionEntity.get(CREATE_SEQUEL.MOVIE);
            Preconditions.checkNotNull(movie);

            sequel.applyForAllButExclude(movie);

            sequel.set(MOVIE.TITLE, customSessionEntity.get(CREATE_SEQUEL.TITLE));
            sequel.set(MOVIE.RELEASEDATE, customSessionEntity.get(CREATE_SEQUEL.RELEASEDATE));
            sequel.set(MOVIE.COVER, null);

            sequel.persist();
            return SUCCESS;
        } else {
            return INVALID;
        }
    }
}
