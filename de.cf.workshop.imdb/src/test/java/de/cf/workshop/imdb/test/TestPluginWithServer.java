/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.test;

import javax.annotation.Nonnull;

import org.junit.Ignore;

import cf.cplace.platform.services.Plugin;
import cf.cplace.platform.test.AbstractTestPluginWithServer;
import de.cf.workshop.imdb.ImdbPlugin;

/**
 * Checks that the server can be started, and performs some validations.
 * This test is only required if the plugin satisfies some conditions.
 * See {@link AbstractTestPluginWithServer#requiresTestPluginWithServer(Plugin)} for details.
 */
// When you are sure that you don't need this test class, you can delete it.
@SuppressWarnings("IgnoredJUnitTest")
@Ignore
public class TestPluginWithServer extends AbstractTestPluginWithServer {
    @Nonnull
    @Override
    protected Plugin getTestPlugin() {
        return ImdbPlugin.INSTANCE();
    }
}
