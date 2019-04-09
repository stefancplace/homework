/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.script;

import javax.annotation.Nonnull;

import cf.cplace.platform.assets.custom.Multiplicities;
import cf.cplace.platform.assets.custom.WidgetConfiguration;
import cf.cplace.platform.assets.custom.def.AttributeDef;
import cf.cplace.platform.assets.custom.def.SingleLocalizedStringAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleNumberAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleStringAttributeDef;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.assets.custom.typeConstraints.factory.TypeConstraintFactories;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.orm.PersistentEntity;
import cf.cplace.platform.script.WidgetTypes;
import cf.cplace.platform.services.App;
import cf.cplace.platform.template.Escaping;
import cf.cplace.platform.template.PrintSubstitution;
import cf.cplace.platform.template.Template;
import cf.cplace.platform.util.Gsonable;
import cf.cplace.platform.widget.WidgetDefinition;
import cf.cplace.platform.widget.WidgetSubstitution;
import cf.cplace.platform.widget.WidgetSubstitutionParameters;
import cf.cplace.platform.widget.WidgetTemplateSubstitution;
import de.cf.workshop.imdb.ImdbPlugin;
import de.cf.workshop.imdb.handler.matrix.LoadDepartementsHandler;
import de.cf.workshop.imdb.handler.matrix.LoadEmployeesHandler;
import de.cf.workshop.imdb.handler.matrix.UpdateEmployeeHandler;

public class MatrixWidgetDefinition extends WidgetDefinition {

    public static final String KIND = "de.cf.homework.matrix";

    public static final Message name = new Message() {
    };

    @Override
    public String getWidgetKind() {
        return KIND;
    }

    @Override
    public Message getName() {
        return name;
    }

    @Override
    public WidgetSubstitution getContentSubstitution(WidgetConfiguration conf, WidgetSubstitutionParameters params) {
        return new WidgetTemplateSubstitution() {

            private WidgetState state;

            @Override
            protected void init() {
                final PersistentEntity embeddedIn = conf._embeddedIn().getNotNull();
                if (!(embeddedIn instanceof Page)) {
                    return;
                }

                state = new WidgetState();
                state.employeesUrl = Forwarder.getFullUrl(LoadEmployeesHandler.class, embeddedIn);
                state.departmentsUrl = Forwarder.getFullUrl(LoadDepartementsHandler.class, embeddedIn);
                state.updateEmployeeUrl = Forwarder.getFullUrl(UpdateEmployeeHandler.class, embeddedIn);

            }

            @Override
            public void putSubstitutions(Template template) {
                template.put("state", PrintSubstitution.printing(Escaping.htmlAttribute, () -> state.toJson()));
            }
        };
    }

    @Nonnull
    @Override
    public App getApp() {
        return ImdbPlugin.INSTANCE().app;
    }

    @Nonnull
    @Override
    public TypeDef getConfigurationType() {
        return CONFIGURATION.TYPE;
    }

    /*
     * Defines the configuration options for the widget
     */
    public static class CONFIGURATION {

        /*
         * An optional localized widget title
         */
        public static final SingleLocalizedStringAttributeDef TITLE = WidgetTypes.BASE_MIXIN.TITLE.copy();

        public static final Message height_shortHelp = new Message() {
        };

        /*
         * An attribute to optionally set a fixed height for the widget
         */
        public static final SingleNumberAttributeDef HEIGHT = WidgetTypes.BASE_MIXIN.HEIGHT.copy()
                .withShortHelp(height_shortHelp);

        public static final Message checkedIcon_name = new Message() {
        };

        /*
         * An icon attribute to make the icon to be used as a "checkmark" within the adjacency matrix view
         */
        public static final SingleStringAttributeDef CHECKED_ICON =
                AttributeDef.build("cf.cplace.training.employeeDepartmentAdjacencyMatrix.checkedIcon", checkedIcon_name,
                        TypeConstraintFactories.iconConstraint(Multiplicities.exactlyOne)
                                .withDefaultValue("fa-check"));

        public static final TypeDef TYPE = new TypeDef(KIND, TITLE, HEIGHT, CHECKED_ICON);
    }

    private static class WidgetState extends Gsonable {
        String employeesUrl;
        String departmentsUrl;
        String updateEmployeeUrl;
    }
}
