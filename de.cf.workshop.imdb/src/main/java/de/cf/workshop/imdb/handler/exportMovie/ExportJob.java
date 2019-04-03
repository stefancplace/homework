/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.exportMovie;

import java.io.File;
import java.io.RandomAccessFile;

import javax.annotation.Nullable;

import cf.cplace.platform.assets.PersistentJob;
import cf.cplace.platform.assets.QueuedBatchJob;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.handler.custom.DownloadExportHandler;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.orm.PersistentEntity;
import cf.cplace.platform.server.TenantResources;
import cf.cplace.platform.template.Template;
import cf.cplace.platform.util.Utilities;
import de.cf.workshop.imdb.ImdbAppTypes;

public class ExportJob extends QueuedBatchJob {

    public static final Message jobName = new Message() {
    };

    public static PersistentJob createJobAndStartInSeparateThread(Page movie) {
        return PersistentJob.createJobAndStartInSeparateThread(ExportJob.class, movie.getId());
    }

    @Nullable
    @Override
    protected Message getJobName(String parameter) {
        return jobName;
    }

    @Override
    protected void execute(PersistentJob job) throws Exception {
        String movieId = job._parameter().get();
        Page movie = Page.SCHEMA.getEntityNotNull(movieId);
        String title = movie.get(ImdbAppTypes.MOVIE.TITLE);
        Page genre = movie.get(ImdbAppTypes.MOVIE.GENRE);
        String toBeExported = "dfsfdffds";

        String uploadPath = TenantResources.INSTANCE().getUploadPath();
        File f = Utilities.getPathFile(uploadPath, Utilities.randomId() + ".txt");
        if (!f.createNewFile()) {
            throw new IllegalStateException("file was not new for export");
        }

        job.appendToErrorMessage("Vor der Pause");
        Utilities.pause(1);
        job.appendToErrorMessage("Nach der Pause");

        try (RandomAccessFile outputFile = new RandomAccessFile(f, "rw")) {
            outputFile.writeBytes(toBeExported);
        }

        PersistentEntity.doOnWritableCopyAndPersistIfModified(job, j -> {
            j._downloadFileName().set(f.getAbsolutePath());
            j.appendToErrorMessage(Template.getString(DownloadExportHandler.downloadLink(j,  "export.txt")));
        });
    }

    @Override
    public boolean isUserJob() {
        return false;
    }
}
