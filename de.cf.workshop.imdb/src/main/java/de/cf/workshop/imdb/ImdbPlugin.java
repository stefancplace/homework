/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */
package de.cf.workshop.imdb;

import javax.annotation.Nonnull;

import cf.cplace.platform.handler.TestSetupHandlerExtension;
import cf.cplace.platform.services.CombinedAndCompressedPluginJavaScriptExtension;
import cf.cplace.platform.services.CustomSessionEntitiesExtension;
import cf.cplace.platform.services.Plugin;
import cf.cplace.platform.services.PluginCssExtension;
import cf.cplace.platform.services.SingleAngularModuleExtension;
import de.cf.workshop.imdb.handler.test.TestSetupHandler;
import de.cf.workshop.imdb.script.CreateSequelWidgetDefinition;
import de.cf.workshop.imdb.script.MoviePostersWidgetDefinition;

@SuppressWarnings("unused")
public final class ImdbPlugin extends Plugin {

    private static ImdbPlugin instance = new ImdbPlugin();

    public static ImdbPlugin INSTANCE() {
        return instance;
    }

    private ImdbPlugin() {
    }

    public final ImdbApp app = new ImdbApp();

    final CombinedAndCompressedPluginJavaScriptExtension javaScriptIncludes = new CombinedAndCompressedPluginJavaScriptExtension();

    final SingleAngularModuleExtension angularModulesExtension = new SingleAngularModuleExtension();

    final PluginCssExtension cssIncludes = new PluginCssExtension() {
    };

    final CreateSequelWidgetDefinition createSequelWidgetDefinition = new CreateSequelWidgetDefinition();

    final MoviePostersWidgetDefinition moviePostersWidgetDefinition = new MoviePostersWidgetDefinition();

    final TestSetupHandlerExtension testSetupHandlerExtension = new TestSetupHandlerExtension(TestSetupHandler.class);

    final CustomSessionEntitiesExtension customSessionEntitiesExtension = new CustomSessionEntitiesExtension() {
        @Nonnull
        @Override
        public Class<?>[] getTypeDefContainerClasses() {
            return new Class[]{ ImdbSessionTypes.class };
        }
    };

    @Override
    public String getDefaultLanguage() {
        return "de";
    }
}
