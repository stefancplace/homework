/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.createSequel;

import javax.annotation.Nullable;

import cf.cplace.platform.assets.custom.CustomSessionEntity;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.JsonPage;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.services.exceptions.ProtectedActionException;
import cf.cplace.platform.template.Escaping;
import cf.cplace.platform.template.PrintSubstitution;
import cf.cplace.platform.template.Template;
import cf.cplace.platform.util.NameValue;
import de.cf.workshop.imdb.ImdbAppTypes;
import de.cf.workshop.imdb.ImdbSessionTypes;

public class EditHandler extends Handler {

    private Page movie;

    private CustomSessionEntity customSessionEntity;

    public static final  Message targetLabel = new Message() {
    };

    final Station SHOW = new JsonPage() {

        @Override
        public Object getScopeObject() {
            return customSessionEntity;
        }

        @Override
        public void putSubstitutions(Template template) {
            template.put("submitUrl", PrintSubstitution.printing(Escaping.htmlAttribute, () ->
                    Forwarder.getFullUrl(SubmitHandler.class, new NameValue("customSessionEntityId", customSessionEntity.getId()))));
        }

    };

    @Override
    protected void checkAccess() {
        String customSessionEntityId = Parameters.getString("customSessionEntityId");
        if (customSessionEntityId != null) {
            customSessionEntity = CustomSessionEntity.SCHEMA.getEntityNotNull(customSessionEntityId);
        } else {
            String pageId = Parameters.getString("movieId");
            movie = Page.SCHEMA.getEntityNotNull(pageId);
            if (!ImdbAppTypes.MOVIE.TYPE.isTypeOf(movie)) {
                throw new ProtectedActionException();
            }
        }
    }

    @Override
    protected Station doBusinessLogic() {
        if (customSessionEntity == null) {
            customSessionEntity = CustomSessionEntity.SCHEMA.create(ImdbSessionTypes.CREATE_SEQUEL.TYPE);
            customSessionEntity.set(ImdbSessionTypes.CREATE_SEQUEL.MOVIE, movie);
        }
        return SHOW;
    }

    @Nullable
    @Override
    public Message getTargetLabel() {
        return targetLabel;
    }

    @Nullable
    @Override
    public String getActionIconName() {
        return "fa-refresh";
    }
}
