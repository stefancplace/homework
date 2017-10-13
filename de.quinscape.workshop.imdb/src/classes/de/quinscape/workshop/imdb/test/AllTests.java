/*
 * Copyright 2015, collaboration Factory AG. All rights reserved.
 */
package de.quinscape.workshop.imdb.test;

import org.junit.runner.RunWith;

import cf.cplace.platform.test.util.PackageSuite;
import cf.cplace.platform.test.util.PackageSuite.SystemPropertyDefaults;

@RunWith(PackageSuite.class)
@SystemPropertyDefaults({ "platform.test.i18n.AbstractTestMessages.runTranslationTests", "false" })
public class AllTests {
}
