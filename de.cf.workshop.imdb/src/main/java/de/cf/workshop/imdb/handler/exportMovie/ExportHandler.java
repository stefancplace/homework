/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.exportMovie;

import javax.annotation.Nullable;

import cf.cplace.platform.assets.PersistentJob;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.JobCreatedJsonPage;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.services.exceptions.ProtectedActionException;
import de.cf.workshop.imdb.ImdbAppTypes;

public class ExportHandler extends Handler {

    private Page page;

    private PersistentJob job;

    public static final  Message targetLabel = new Message() {
    };

    final Station SHOW = new JobCreatedJsonPage() {
        @Override
        protected PersistentJob getJob() {
            return job;
        }
    };

    @Override
    protected void checkAccess() {
        String pageId = Parameters.getString("movieId");
        page = Page.SCHEMA.getEntityNotNull(pageId);
        if (!ImdbAppTypes.MOVIE.TYPE.isTypeOf(page)) {
            throw new ProtectedActionException();
        }
    }

    @Override
    protected Station doBusinessLogic() {
        job = ExportJob.createJobAndStartInSeparateThread(page);
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
        return "fa-file-text";
    }
}
