package de.quinscape.workshop.imdb;

import java.util.List;

import com.google.common.collect.ImmutableList;

import cf.cplace.platform.assets.WidgetWithContent;
import cf.cplace.platform.assets.custom.BooleanValue;
import cf.cplace.platform.assets.custom.CustomSerializable;
import cf.cplace.platform.assets.custom.FixedAppTypes;
import cf.cplace.platform.assets.custom.Multiplicities;
import cf.cplace.platform.assets.custom.StringValue;
import cf.cplace.platform.assets.custom.def.AttributeDef;
import cf.cplace.platform.assets.custom.def.MultiPageReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleBooleanAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleDateAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleDocumentReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleNumberAttributeDef;
import cf.cplace.platform.assets.custom.def.SinglePageReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleStringAttributeDef;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.assets.custom.typeConstraints.factory.TypeConstraintFactories;
import cf.cplace.platform.assets.layout.Column;
import cf.cplace.platform.assets.layout.Layout;
import cf.cplace.platform.assets.layout.Row;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.widget.WidgetContainerDef;
import cf.cplace.platform.widget.WidgetHelper;

public class ImdbAppTypes {
    @FixedAppTypes.Fixed(orderIndex = 800)
    public static class GENRE {

        public static final Message genre_name_name = new Message() {
        };

        public static final SingleStringAttributeDef GENRE_NAME =
                AttributeDef.build("genre.name", genre_name_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne));
        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };
        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetHelper.WIKI)),
                        Row.fromColumns(
                                new Column(6, WidgetHelper.ATTRIBUTES),
                                new Column(6, WidgetHelper.INCOMING_REFERENCES))));
        public static final TypeDef TYPE = new TypeDef("Genre", name_singular, name_plural, " ",
                WIDGET_CONTAINER_DEF
                , GENRE_NAME);
    }

    @FixedAppTypes.Fixed(orderIndex = 700)
    public static class IMDB {

        public static final Message imdb_cover_name = new Message() {
        };
        public static final Message imdb_cover_shortName = new Message() {
        };

        public static final SingleDocumentReferenceAttributeDef IMDB_COVER =
                AttributeDef.build("imdb.cover", imdb_cover_name,
                        TypeConstraintFactories.linkDocumentConstraint(Multiplicities.maximalOne, "default.file", null, true))
                        .withLocalizedShortName(imdb_cover_shortName);


        public static final Message imdb_director_name = new Message() {
        };
        public static final Message imdb_director_shortName = new Message() {
        };

        public static final SinglePageReferenceAttributeDef IMDB_DIRECTOR =
                AttributeDef.build("imdb.director", imdb_director_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, "Regisseur", null, true))
                        .withLocalizedShortName(imdb_director_shortName);


        public static final Message imdb_genre_name = new Message() {
        };
        public static final Message imdb_genre_shortName = new Message() {
        };

        public static final MultiPageReferenceAttributeDef IMDB_GENRE =
                AttributeDef.build("imdb.genre", imdb_genre_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.nullMeansAnyNumber(), "Genre", null, true))
                        .withLocalizedShortName(imdb_genre_shortName).withAllowedDuplicates();


        public static final Message imdb_oscar_name = new Message() {
        };
        public static final Message imdb_oscar_shortName = new Message() {
        };

        public static final SingleBooleanAttributeDef IMDB_OSCAR =
                AttributeDef.build("imdb.oscar", imdb_oscar_name,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false))
                        .withLocalizedShortName(imdb_oscar_shortName);


        public static final Message imdb_rating_name = new Message() {
        };
        public static final Message imdb_rating_shortName = new Message() {
        };
        public static final List<Double> IMDB_RATING_VALUES = ImmutableList.of(1.0, 2.0, 3.0, 4.0, 5.0);

        public static final SingleNumberAttributeDef IMDB_RATING =
                AttributeDef.build("imdb.rating", imdb_rating_name,
                        TypeConstraintFactories.numberEnumerationConstraint(Multiplicities.exactlyOne, IMDB_RATING_VALUES))
                        .withLocalizedShortName(imdb_rating_shortName);


        public static final Message imdb_releaseYear_name = new Message() {
        };
        public static final Message imdb_releaseYear_shortName = new Message() {
        };

        public static final SingleStringAttributeDef IMDB_RELEASEYEAR =
                AttributeDef.build("imdb.releaseYear", imdb_releaseYear_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne))
                        .withLocalizedShortName(imdb_releaseYear_shortName);


        public static final Message imdb_sum_name = new Message() {
        };
        public static final Message imdb_sum_shortName = new Message() {
        };

        public static final SingleNumberAttributeDef IMDB_SUM =
                AttributeDef.build("imdb.sum", imdb_sum_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne))
                        .withLocalizedShortName(imdb_sum_shortName);
        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };
        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                                                .put("widgetTitle", StringValue.valueOf("Filmdetails"))
                                                .put("widgetSubTitle", StringValue.valueOf("IMDB"))
                                                .toJson()
                                        , "cf.cplace.pageViewHeadline.widget"))),
                        Row.fromColumns(
                                new Column(6, WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                                                .put("scaling", StringValue.valueOf("horizontal"))
                                                .put("referenceAttribute", StringValue.valueOf("imdb.cover"))
                                                .put("widgetTitle", StringValue.valueOf("Cover"))
                                                .put("showFrame", BooleanValue.valueOf(true))
                                                .toJson()
                                        , "cf.cplace.dragAndDropImageView.widget")),
                                new Column(6, WidgetHelper.ATTRIBUTES, WidgetHelper.COMMENTS))));
        public static final TypeDef TYPE = new TypeDef("IMDB", name_singular, name_plural, " ",
                WIDGET_CONTAINER_DEF
                , IMDB_GENRE, IMDB_RELEASEYEAR, IMDB_DIRECTOR, IMDB_RATING, IMDB_SUM, IMDB_OSCAR, IMDB_COVER);
    }

    @FixedAppTypes.Fixed(orderIndex = 900)
    public static class REGISSEUR {

        public static final Message imdb_director_birthdate_name = new Message() {
        };

        public static final SingleDateAttributeDef IMDB_DIRECTOR_BIRTHDATE =
                AttributeDef.build("imdb.director.birthdate", imdb_director_birthdate_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.maximalOne));


        public static final Message imdb_director_death_name = new Message() {
        };

        public static final SingleDateAttributeDef IMDB_DIRECTOR_DEATH =
                AttributeDef.build("imdb.director.death", imdb_director_death_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.maximalOne));
        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };
        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetHelper.WIKI)),
                        Row.fromColumns(
                                new Column(6, WidgetHelper.ATTRIBUTES),
                                new Column(6, WidgetHelper.INCOMING_REFERENCES))));
        public static final TypeDef TYPE = new TypeDef("Regisseur", name_singular, name_plural, " ",
                WIDGET_CONTAINER_DEF
                , IMDB_DIRECTOR_BIRTHDATE, IMDB_DIRECTOR_DEATH);
    }
}