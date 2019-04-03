/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb;

import cf.cplace.platform.assets.WidgetWithContent;
import cf.cplace.platform.assets.custom.BooleanValue;
import cf.cplace.platform.assets.custom.CustomSerializable;
import cf.cplace.platform.assets.custom.FixedAppTypes;
import cf.cplace.platform.assets.custom.Multiplicities;
import cf.cplace.platform.assets.custom.NumberValue;
import cf.cplace.platform.assets.custom.StringValue;
import cf.cplace.platform.assets.custom.def.AttributeDef;
import cf.cplace.platform.assets.custom.def.MultiPageReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleBooleanAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleDateAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleDocumentReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleNumberAttributeDef;
import cf.cplace.platform.assets.custom.def.SinglePageReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleStringAttributeDef;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.assets.custom.typeConstraints.factory.TypeConstraintFactories;
import cf.cplace.platform.assets.layout.Column;
import cf.cplace.platform.assets.layout.Layout;
import cf.cplace.platform.assets.layout.Row;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.widget.WidgetContainerDef;
import cf.cplace.platform.widget.WidgetHelper;
import de.cf.workshop.imdb.script.CreateSequelWidgetDefinition;

public class ImdbAppTypes {


    @FixedAppTypes.Fixed(orderIndex = 500)
    public static class DIRECTOR {

        public static final Message birthDate_name = new Message() {
        };

        public static final SingleDateAttributeDef BIRTHDATE =
                AttributeDef.build("de.cf.workshop.imdb.birthDate", birthDate_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.exactlyOne));


        public static final Message dateOfDeath_name = new Message() {
        };

        public static final SingleDateAttributeDef DATEOFDEATH =
                AttributeDef.build("de.cf.workshop.imdb.dateOfDeath", dateOfDeath_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.maximalOne));

        public static final Message deathAge_name = new Message() {
        };

        public static final SingleNumberAttributeDef DEATHAGE =
                AttributeDef.build("de.cf.workshop.imdb.deathAge", deathAge_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne)).withReadOnly();

        public static final Message name_name = new Message() {
        };

        public static final SingleStringAttributeDef NAME =
                AttributeDef.build("de.cf.workshop.imdb.name", name_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, null, null));


        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetHelper.WIKI)),
                        Row.fromColumns(
                                new Column(6, WidgetHelper.ATTRIBUTES),
                                new Column(6, WidgetHelper.INCOMING_REFERENCES))));

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };
        public static final TypeDef TYPE = new TypeDef("de.cf.workshop.imdb.director", name_singular, name_plural, "fa-list",
                WIDGET_CONTAINER_DEF
                , NAME, BIRTHDATE, DATEOFDEATH, DEATHAGE)
                .withNotFixedNameGenerationPattern("<de.cf.workshop.imdb.name>")

                .withInternalAttributeNamePrefix("de.cf.workshop.imdb");
    }

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class GENRE {
        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetHelper.WIKI)),
                        Row.fromColumns(
                                new Column(6, WidgetHelper.FILES),
                                new Column(6, WidgetHelper.COMMENTS))));

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };

        public static final TypeDef TYPE = new TypeDef("de.cf.workshop.imdb.genre", name_singular, name_plural, "fa-file-movie-o",
                WIDGET_CONTAINER_DEF);
    }

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class MOVIE {

        public static final Message boxOfficeTaking_name = new Message() {
        };

        public static final SingleNumberAttributeDef BOXOFFICETAKING =
                AttributeDef.build("de.cf.workshop.imdb.boxOfficeTaking", boxOfficeTaking_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne));


        public static final Message cover_name = new Message() {
        };

        public static final SingleDocumentReferenceAttributeDef COVER =
                AttributeDef.build("de.cf.workshop.imdb.cover", cover_name,
                        TypeConstraintFactories.linkDocumentConstraint(Multiplicities.maximalOne, "default.file", null, true));


        public static final Message director_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef DIRECTOR =
                AttributeDef.build("de.cf.workshop.imdb.director", director_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, "de.cf.workshop.imdb.director", null, true));


        public static final Message genre_name = new Message() {
        };
        public static final Message genre_shortName = new Message() {
        };

        public static final SinglePageReferenceAttributeDef GENRE =
                AttributeDef.build("de.cf.workshop.imdb.genre", genre_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, "de.cf.workshop.imdb.genre", null, true))
                        .withLocalizedShortName(genre_shortName);


        public static final Message oscarWon_name = new Message() {
        };

        public static final SingleBooleanAttributeDef OSCARWON =
                AttributeDef.build("de.cf.workshop.imdb.oscarWon", oscarWon_name,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false));


        public static final Message rating_name = new Message() {
        };

        public static final SingleNumberAttributeDef RATING =
                AttributeDef.build("de.cf.workshop.imdb.rating", rating_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.exactlyOne));


        public static final Message releaseDate_name = new Message() {
        };
        public static final Message releaseDate_shortName = new Message() {
        };

        public static final SingleDateAttributeDef RELEASEDATE =
                AttributeDef.build("de.cf.workshop.imdb.releaseDate", releaseDate_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.exactlyOne))
                        .withLocalizedShortName(releaseDate_shortName);


        public static final Message title_name = new Message() {
        };
        public static final Message title_shortName = new Message() {
        };

        public static final SingleStringAttributeDef TITLE =
                AttributeDef.build("de.cf.workshop.imdb.title", title_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, null, null))
                        .withLocalizedShortName(title_shortName);


        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(6, WidgetHelper.ATTRIBUTES, WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                                                .put("de.cf.workshop.imdb.numberOfButtons", NumberValue.valueOf(5))
                                                .toJson()
                                        , CreateSequelWidgetDefinition.KIND)),
                                new Column(6, WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                                                .put("referenceAttribute", StringValue.valueOf("de.cf.workshop.imdb.cover"))
                                                .put("showFrame", BooleanValue.valueOf(true))
                                                .toJson()
                                        , "cf.cplace.dragAndDropImageView.widget"))),
                        Row.fromColumns(
                                new Column(12, WidgetHelper.COMMENTS))));


        public static final Message height_name = new Message() {
        };

        public static final SingleNumberAttributeDef HEIGHT =
                AttributeDef.build("de.cf.workshop.imdb.height", height_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.exactlyOne));

        public static final Message actor_name = new Message() {
        };
        public static final Message actor_shortName = new Message() {
        };

        public static final MultiPageReferenceAttributeDef ACTOR =
                AttributeDef.build("de.cf.workshop.imdb.actor", actor_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.atLeastOne, "de.cf.workshop.imdb.actor", null, true))
                        .withLocalizedShortName(actor_shortName);

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };
        public static final TypeDef TYPE = new TypeDef("de.cf.workshop.imdb.movie", name_singular, name_plural, "fa-file-movie-o",
                WIDGET_CONTAINER_DEF
                , TITLE, RELEASEDATE, GENRE, RATING, DIRECTOR, ACTOR, BOXOFFICETAKING, OSCARWON, COVER)
                .withNotFixedNameGenerationPattern("<de.cf.workshop.imdb.title> (<de.cf.workshop.imdb.releaseDate>)")

                .withInternalAttributeNamePrefix("de.cf.workshop.imdb.");
    }

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class ACTOR {

        public static final Message name_name = new Message() {
        };

        public static final SingleStringAttributeDef NAME =
                AttributeDef.build("de.cf.workshop.imdb.name", name_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, null, null));

        public static final Message birthDate_name = new Message() {
        };
        public static final Message birthDate_shortName = new Message() {
        };

        public static final SingleDateAttributeDef BIRTHDATE =
                AttributeDef.build("de.cf.workshop.imdb.birthDate", birthDate_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.exactlyOne))
                        .withLocalizedShortName(birthDate_shortName);

        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetHelper.WIKI)),
                        Row.fromColumns(
                                new Column(6, WidgetHelper.FILES),
                                new Column(6, WidgetHelper.COMMENTS))));

        public static final Message birthPlace_name = new Message() {
        };
        public static final Message birthPlace_shortName = new Message() {
        };

        public static final SingleStringAttributeDef BIRTHPLACE =
                AttributeDef.build("de.cf.workshop.imdb.birthPlace", birthPlace_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, null, null))
                        .withLocalizedShortName(birthPlace_shortName);

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };

        public static final TypeDef TYPE = new TypeDef("de.cf.workshop.imdb.actor", name_singular, name_plural, "fa-file-movie-o",
                WIDGET_CONTAINER_DEF);
    }

}