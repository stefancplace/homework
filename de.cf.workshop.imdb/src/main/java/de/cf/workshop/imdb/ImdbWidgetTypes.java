/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */
package de.cf.workshop.imdb;

import cf.cplace.platform.assets.custom.FixedAppTypes;
import cf.cplace.platform.assets.custom.Multiplicities;
import cf.cplace.platform.assets.custom.def.AttributeDef;
import cf.cplace.platform.assets.custom.def.SingleNumberAttributeDef;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.assets.custom.typeConstraints.factory.TypeConstraintFactories;
import cf.cplace.platform.internationalization.Message;

/**
 * Custom types used to configure this plugin's widget definitions.
 */
public class ImdbWidgetTypes {
    @FixedAppTypes.Fixed(orderIndex = 500)
    public static class CREATE_SEQUEL_WIDGET_CONFIG {

        public static final Message numberOfButtons_name = new Message() {
        };

        public static final SingleNumberAttributeDef NUMBER_OF_BUTTONS =
                AttributeDef.build("de.cf.workshop.imdb.numberOfButtons", numberOfButtons_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.exactlyOne));

        public static final TypeDef TYPE = new TypeDef("de.cf.workshop.imdb.createSequelWidgetConfig", NUMBER_OF_BUTTONS);
    }
}
