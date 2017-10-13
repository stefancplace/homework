/*
 * Copyright 2015, collaboration Factory AG. All rights reserved.
 */
package de.quinscape.workshop.imdb.test;

import cf.cplace.platform.test.i18n.MessagesUtility;
import de.quinscape.workshop.imdb.ImdbPlugin;

public class FixMessages {

    public static void main(String[] args) {
        MessagesUtility.fix(ImdbPlugin.INSTANCE());
    }
}
