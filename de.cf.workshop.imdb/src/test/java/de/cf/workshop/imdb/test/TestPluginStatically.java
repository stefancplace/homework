/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.test;

import cf.cplace.platform.services.Plugin;
import cf.cplace.platform.test.AbstractTestPluginStatically;
import de.cf.workshop.imdb.ImdbPlugin;

public class TestPluginStatically extends AbstractTestPluginStatically {

    @Override
    protected Plugin getTestPlugin() {
        return ImdbPlugin.INSTANCE();
    }
}
