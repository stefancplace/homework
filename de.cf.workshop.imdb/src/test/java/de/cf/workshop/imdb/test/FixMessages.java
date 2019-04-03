/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */
package de.cf.workshop.imdb.test;

import cf.cplace.platform.test.i18n.MessagesUtility;
import de.cf.workshop.imdb.ImdbPlugin;

public class FixMessages {

    public static void main(String[] args) {
        MessagesUtility.fix(ImdbPlugin.INSTANCE());
    }
}
