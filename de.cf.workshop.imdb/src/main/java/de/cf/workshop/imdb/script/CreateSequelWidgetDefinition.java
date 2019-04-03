/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.script;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import cf.cplace.platform.assets.custom.WidgetConfiguration;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.orm.PersistentEntity;
import cf.cplace.platform.orm.Schema;
import cf.cplace.platform.script.WidgetEmbedding;
import cf.cplace.platform.services.App;
import cf.cplace.platform.template.Escaping;
import cf.cplace.platform.template.PrintSubstitution;
import cf.cplace.platform.template.SimpleListSubstitution;
import cf.cplace.platform.template.Template;
import cf.cplace.platform.util.NameValue;
import cf.cplace.platform.widget.NotEmbeddableException;
import cf.cplace.platform.widget.WidgetDefinition;
import cf.cplace.platform.widget.WidgetSubstitutionParameters;
import cf.cplace.platform.widget.WidgetTemplateSubstitution;
import de.cf.workshop.imdb.ImdbPlugin;
import de.cf.workshop.imdb.ImdbAppTypes.MOVIE;
import de.cf.workshop.imdb.ImdbWidgetTypes;
import de.cf.workshop.imdb.handler.createSequel.EditHandler;

public class CreateSequelWidgetDefinition extends WidgetDefinition {

    public static final Message widgetName = new Message() {
    };

    public static final String KIND = "de.cf.workshop.imdb.createSequel";

    @Override
    public String getWidgetKind() {
        return KIND;
    }

    @Override
    public Message getName() {
        return widgetName;
    }

    public WidgetTemplateSubstitution getContentSubstitution(WidgetConfiguration conf, WidgetSubstitutionParameters params) {
        return new WidgetTemplateSubstitution() {
            @Override
            public void putSubstitutions(Template template) {
                template.put("buttons", new SimpleListSubstitution<Integer>() {
                    @Override
                    protected Iterable<Integer> getItems() {
                        int numberOfButtons = conf.get(ImdbWidgetTypes.CREATE_SEQUEL_WIDGET_CONFIG.NUMBER_OF_BUTTONS).intValue();
                        List<Integer> list = Lists.newArrayList();
                        for (int i = 0; i < numberOfButtons; i++) {
                            list.add(i);
                        }
                        return list;
                    }
                });
                template.put("clickUrl", PrintSubstitution.printing(
                        Escaping.htmlAttribute, () -> Forwarder.getFullUrl(
                                EditHandler.class,
                                new NameValue("movieId", Schema.id(params.entityUid)))));
            }
        };
    }

    @Override
    public void checkEmbeddableIn(PersistentEntity entity, WidgetEmbedding embedding) throws NotEmbeddableException {
        checkIsType(entity, embedding, MOVIE.TYPE);
    }

    @Nonnull
    @Override
    public TypeDef getConfigurationType() {
        return ImdbWidgetTypes.CREATE_SEQUEL_WIDGET_CONFIG.TYPE;
    }

    @Nonnull
    @Override
    public App getApp() {
        return ImdbPlugin.INSTANCE().app;
    }
}
