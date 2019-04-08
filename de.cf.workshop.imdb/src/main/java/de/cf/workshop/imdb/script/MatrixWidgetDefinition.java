/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.script;

import javax.annotation.Nonnull;

import cf.cplace.platform.assets.custom.WidgetConfiguration;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.orm.PersistentEntity;
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

    private static class WidgetState extends Gsonable {
        String employeesUrl;
        String departmentsUrl;
    }
}
