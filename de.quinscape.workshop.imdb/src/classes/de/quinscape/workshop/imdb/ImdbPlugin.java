/*
 * Copyright 2015, collaboration Factory AG. All rights reserved.
 */
package de.quinscape.workshop.imdb;

import cf.cplace.platform.handler.TestSetupHandlerExtension;
import cf.cplace.platform.services.CombinedAndCompressedPluginJavaScriptExtension;
import cf.cplace.platform.services.MustBeActivated;
import cf.cplace.platform.services.Plugin;
import cf.cplace.platform.services.SingleAngularModuleExtension;
import de.quinscape.workshop.imdb.handler.test.TestSetupHandler;
import de.quinscape.workshop.imdb.script.ImdbWidgetDefinition;

@SuppressWarnings("unused")
public final class ImdbPlugin extends Plugin implements MustBeActivated {

    private static ImdbPlugin instance = new ImdbPlugin();

    public static ImdbPlugin INSTANCE() {
        return instance;
    }

    private ImdbPlugin() {
    }

    public final ImdbApp app = new ImdbApp();

    public final CombinedAndCompressedPluginJavaScriptExtension javaScriptIncludes = new CombinedAndCompressedPluginJavaScriptExtension();

    public final SingleAngularModuleExtension angularModulesExtension = new SingleAngularModuleExtension();

    public final ImdbWidgetDefinition imdbWidgetDefinition = new ImdbWidgetDefinition();

    public final TestSetupHandlerExtension testSetupHandlerExtension = new TestSetupHandlerExtension(TestSetupHandler.class);

    @Override
    public String getDefaultLanguage() {
        return "de";
    }
}
