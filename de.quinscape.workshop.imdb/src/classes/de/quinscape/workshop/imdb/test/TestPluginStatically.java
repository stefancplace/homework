package de.quinscape.workshop.imdb.test;

import cf.cplace.platform.services.Plugin;
import cf.cplace.platform.test.AbstractTestPluginStatically;
import de.quinscape.workshop.imdb.ImdbPlugin;

public class TestPluginStatically extends AbstractTestPluginStatically {

    @Override
    protected Plugin getTestPlugin() {
        return ImdbPlugin.INSTANCE();
    }
}
