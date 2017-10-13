/*
 * Copyright 2015, collaboration Factory AG. All rights reserved.
 */
package de.quinscape.workshop.imdb;

import javax.annotation.Nonnull;

import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.services.app.ProgrammaticallyDefinedAppWithTypeDefs;

public class ImdbApp extends ProgrammaticallyDefinedAppWithTypeDefs {

    public static final Message displayName = new Message() {
    };

    public static final Message description = new Message() {
    };

    ImdbApp() {
        // package scope, only the plugin creates an instance
    }

    @Nonnull
    @Override
    public Message getDisplayName() {
        return displayName;
    }

    @Override
    public Message getDescription() {
        return description;
    }

    @Override
    public String getIconName() {
        return "fa-info";
    }

    @Nonnull
    @Override
    protected Class<?>[] getTypeDefContainerClasses() {
        return new Class<?>[] { ImdbAppTypes.class };
    }

}
