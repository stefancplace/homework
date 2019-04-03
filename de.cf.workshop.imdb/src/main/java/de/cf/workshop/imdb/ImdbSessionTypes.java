/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb;

import cf.cplace.platform.assets.custom.FixedAppTypes;
import cf.cplace.platform.assets.custom.Multiplicities;
import cf.cplace.platform.assets.custom.def.AttributeDef;
import cf.cplace.platform.assets.custom.def.SingleDateAttributeDef;
import cf.cplace.platform.assets.custom.def.SinglePageReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleStringAttributeDef;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.assets.custom.typeConstraints.factory.TypeConstraintFactories;
import cf.cplace.platform.internationalization.Message;

public class ImdbSessionTypes {

    @FixedAppTypes.Fixed(orderIndex = 500)
    public static class CREATE_SEQUEL {

        public static final Message releaseDate_name = new Message() {
        };

        public static final SingleDateAttributeDef RELEASEDATE =
                AttributeDef.build("de.cf.workshop.imdb.releaseDate", releaseDate_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.exactlyOne));


        public static final Message title_name = new Message() {
        };

        public static final SingleStringAttributeDef TITLE =
                AttributeDef.build("de.cf.workshop.imdb.title", title_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, null, null));

        public static final Message movie_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef MOVIE =
                AttributeDef.build("de.cf.workshop.imdb.movie", movie_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, "de.cf.workshop.imdb.movie", null, false))
                        .withReadOnly();


        public static final TypeDef TYPE = new TypeDef("de.cf.workshop.imdb.createSequel", RELEASEDATE, TITLE, MOVIE);
    }
}
