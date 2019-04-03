/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */
package de.cf.workshop.imdb.test;

import org.junit.runner.RunWith;

import cf.cplace.platform.test.util.PackageSuite.SystemPropertyDefaults;
import cf.cplace.platform.test.util.PluginSuite;

@RunWith(PluginSuite.class)
@SystemPropertyDefaults({ "platform.test.i18n.AbstractTestMessages.runTranslationTests", "false" })
public class AllTests {
}
