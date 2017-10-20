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
    @FixedAppTypes.Fixed(orderIndex = 300)
    public static class CF_CPLACE_LANDINGPAGE_LEVEL0 {

        public static final Message cf_cplace_landingPage_color_name = new Message() {
        };
        public static final List<String> CF_CPLACE_LANDINGPAGE_COLOR_VALUES = ImmutableList.of("Red", "Dark-Red", "Pink", "Purple", "Deep-Purple", "Indigo", "Blue", "Light-Blue", "Dark-Blue", "Blue-Grey", "Blue-Alt", "Cyan", "Teal", "Green", "Light-Green", "Dark-Green", "Lime", "Yellow", "Amber", "Orange", "Deep-Orange", "Brown", "Grey", "White");

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_COLOR =
                AttributeDef.build("cf.cplace.landingPage.color", cf_cplace_landingPage_color_name,
                        TypeConstraintFactories.textEnumerationConstraint(Multiplicities.maximalOne, CF_CPLACE_LANDINGPAGE_COLOR_VALUES));


        public static final Message cf_cplace_landingPage_description_name = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_DESCRIPTION =
                AttributeDef.build("cf.cplace.landingPage.description", cf_cplace_landingPage_description_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_name_name = new Message() {
        };
        public static final Message cf_cplace_landingPage_name_shortHelp = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_NAME =
                AttributeDef.build("cf.cplace.landingPage.name", cf_cplace_landingPage_name_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne))
                        .withShortHelp(cf_cplace_landingPage_name_shortHelp);


        public static final Message cf_cplace_landingPage_order_name = new Message() {
        };

        public static final SingleNumberAttributeDef CF_CPLACE_LANDINGPAGE_ORDER =
                AttributeDef.build("cf.cplace.landingPage.order", cf_cplace_landingPage_order_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_pageReference_name = new Message() {
        };
        public static final Message cf_cplace_landingPage_pageReference_shortHelp = new Message() {
        };

        public static final SinglePageReferenceAttributeDef CF_CPLACE_LANDINGPAGE_PAGEREFERENCE =
                AttributeDef.build("cf.cplace.landingPage.pageReference", cf_cplace_landingPage_pageReference_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, TypeConstraintFactories.NO_TYPE_NAMES, null, false))
                        .withShortHelp(cf_cplace_landingPage_pageReference_shortHelp);


        public static final Message cf_cplace_landingPage_tabReference_name = new Message() {
        };

        public static final MultiPageReferenceAttributeDef CF_CPLACE_LANDINGPAGE_TABREFERENCE =
                AttributeDef.build("cf.cplace.landingPage.tabReference", cf_cplace_landingPage_tabReference_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.atLeastOne, "cf.cplace.landingPage.tab", null, true))
                        .withAllowedDuplicates();


        public static final Message cf_cplace_landingPage_thumbnail_name = new Message() {
        };

        public static final SingleDocumentReferenceAttributeDef CF_CPLACE_LANDINGPAGE_THUMBNAIL =
                AttributeDef.build("cf.cplace.landingPage.thumbnail", cf_cplace_landingPage_thumbnail_name,
                        TypeConstraintFactories.linkDocumentConstraint(Multiplicities.exactlyOne, TypeConstraintFactories.NO_TYPE_NAMES, null, true));


        public static final Message cf_cplace_landingPage_urlReference_name = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_URLREFERENCE =
                AttributeDef.build("cf.cplace.landingPage.urlReference", cf_cplace_landingPage_urlReference_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_urlReferenceModal_name = new Message() {
        };
        public static final List<String> CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL_VALUES = ImmutableList.of("page", "page_blank", "modal", "modalLarge", "none");

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL =
                AttributeDef.build("cf.cplace.landingPage.urlReferenceModal", cf_cplace_landingPage_urlReferenceModal_name,
                        TypeConstraintFactories.textEnumerationConstraint(Multiplicities.maximalOne, CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL_VALUES).withDefaultValues(StringValue.valueOf("page")));
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
        public static final TypeDef TYPE = new TypeDef("cf.cplace.landingPage.level0", name_singular, name_plural, "fa-book",
                WIDGET_CONTAINER_DEF
                , CF_CPLACE_LANDINGPAGE_NAME, CF_CPLACE_LANDINGPAGE_DESCRIPTION, CF_CPLACE_LANDINGPAGE_THUMBNAIL, CF_CPLACE_LANDINGPAGE_COLOR, CF_CPLACE_LANDINGPAGE_ORDER, CF_CPLACE_LANDINGPAGE_PAGEREFERENCE, CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL, CF_CPLACE_LANDINGPAGE_URLREFERENCE, CF_CPLACE_LANDINGPAGE_TABREFERENCE)
                .withNotFixedNameGenerationPattern("<cf.cplace.landingPage.name>")


                .withHiddenInGlobalSearch();
    }

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class CF_CPLACE_LANDINGPAGE_LEVEL1 {

        public static final Message cf_cplace_landingPage_isDividerTile_name = new Message() {
        };

        public static final SingleBooleanAttributeDef CF_CPLACE_LANDINGPAGE_ISDIVIDERTILE =
                AttributeDef.build("cf.cplace.landingPage.isDividerTile", cf_cplace_landingPage_isDividerTile_name,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false));


        public static final Message cf_cplace_landingPage_level0reference_name = new Message() {
        };

        public static final MultiPageReferenceAttributeDef CF_CPLACE_LANDINGPAGE_LEVEL0REFERENCE =
                AttributeDef.build("cf.cplace.landingPage.level0reference", cf_cplace_landingPage_level0reference_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.nullMeansAnyNumber(), "cf.cplace.landingPage.level0", null, true)).withShowInNewDialog()
                        .withAllowedDuplicates();


        public static final Message cf_cplace_landingPage_name_name = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_NAME =
                AttributeDef.build("cf.cplace.landingPage.name", cf_cplace_landingPage_name_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_order_name = new Message() {
        };

        public static final SingleNumberAttributeDef CF_CPLACE_LANDINGPAGE_ORDER =
                AttributeDef.build("cf.cplace.landingPage.order", cf_cplace_landingPage_order_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_pageReference_name = new Message() {
        };
        public static final Message cf_cplace_landingPage_pageReference_shortHelp = new Message() {
        };

        public static final SinglePageReferenceAttributeDef CF_CPLACE_LANDINGPAGE_PAGEREFERENCE =
                AttributeDef.build("cf.cplace.landingPage.pageReference", cf_cplace_landingPage_pageReference_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, TypeConstraintFactories.NO_TYPE_NAMES, null, false))
                        .withShortHelp(cf_cplace_landingPage_pageReference_shortHelp);


        public static final Message cf_cplace_landingPage_urlReference_name = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_URLREFERENCE =
                AttributeDef.build("cf.cplace.landingPage.urlReference", cf_cplace_landingPage_urlReference_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_urlReferenceModal_name = new Message() {
        };
        public static final List<String> CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL_VALUES = ImmutableList.of("page", "page_blank", "modal", "modalLarge", "none");

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL =
                AttributeDef.build("cf.cplace.landingPage.urlReferenceModal", cf_cplace_landingPage_urlReferenceModal_name,
                        TypeConstraintFactories.textEnumerationConstraint(Multiplicities.maximalOne, CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL_VALUES).withDefaultValues(StringValue.valueOf("page")));


        public static final Message cf_cplace_landingPage_width_name = new Message() {
        };
        public static final List<String> CF_CPLACE_LANDINGPAGE_WIDTH_VALUES = ImmutableList.of("1/4", "1/3", "1/2", "2/3", "3/4", "1");

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_WIDTH =
                AttributeDef.build("cf.cplace.landingPage.width", cf_cplace_landingPage_width_name,
                        TypeConstraintFactories.textEnumerationConstraint(Multiplicities.exactlyOne, CF_CPLACE_LANDINGPAGE_WIDTH_VALUES).withDefaultValues(StringValue.valueOf("1/3")));
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
        public static final TypeDef TYPE = new TypeDef("cf.cplace.landingPage.level1", name_singular, name_plural, " ",
                WIDGET_CONTAINER_DEF
                , CF_CPLACE_LANDINGPAGE_NAME, CF_CPLACE_LANDINGPAGE_LEVEL0REFERENCE, CF_CPLACE_LANDINGPAGE_PAGEREFERENCE, CF_CPLACE_LANDINGPAGE_URLREFERENCE, CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL, CF_CPLACE_LANDINGPAGE_ORDER, CF_CPLACE_LANDINGPAGE_WIDTH, CF_CPLACE_LANDINGPAGE_ISDIVIDERTILE)
                .withNotFixedNameGenerationPattern("<cf.cplace.landingPage.name>")


                .withHiddenInGlobalSearch();
    }

    @FixedAppTypes.Fixed(orderIndex = 500)
    public static class CF_CPLACE_LANDINGPAGE_QUICKLINK {

        public static final Message cf_cplace_landingPage_icon_name = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_ICON =
                AttributeDef.build("cf.cplace.landingPage.icon", cf_cplace_landingPage_icon_name,
                        TypeConstraintFactories.iconConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_level0reference_name = new Message() {
        };

        public static final MultiPageReferenceAttributeDef CF_CPLACE_LANDINGPAGE_LEVEL0REFERENCE =
                AttributeDef.build("cf.cplace.landingPage.level0reference", cf_cplace_landingPage_level0reference_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.nullMeansAnyNumber(), "cf.cplace.landingPage.level0", null, true)).withShowInNewDialog()
                        .withAllowedDuplicates();


        public static final Message cf_cplace_landingPage_name_name = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_NAME =
                AttributeDef.build("cf.cplace.landingPage.name", cf_cplace_landingPage_name_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_order_name = new Message() {
        };

        public static final SingleNumberAttributeDef CF_CPLACE_LANDINGPAGE_ORDER =
                AttributeDef.build("cf.cplace.landingPage.order", cf_cplace_landingPage_order_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_pageReference_name = new Message() {
        };
        public static final Message cf_cplace_landingPage_pageReference_shortHelp = new Message() {
        };

        public static final SinglePageReferenceAttributeDef CF_CPLACE_LANDINGPAGE_PAGEREFERENCE =
                AttributeDef.build("cf.cplace.landingPage.pageReference", cf_cplace_landingPage_pageReference_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, TypeConstraintFactories.NO_TYPE_NAMES, null, false))
                        .withShortHelp(cf_cplace_landingPage_pageReference_shortHelp);


        public static final Message cf_cplace_landingPage_urlReference_name = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_URLREFERENCE =
                AttributeDef.build("cf.cplace.landingPage.urlReference", cf_cplace_landingPage_urlReference_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_urlReferenceModal_name = new Message() {
        };
        public static final List<String> CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL_VALUES = ImmutableList.of("page", "page_blank", "modal", "modalLarge", "none");

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL =
                AttributeDef.build("cf.cplace.landingPage.urlReferenceModal", cf_cplace_landingPage_urlReferenceModal_name,
                        TypeConstraintFactories.textEnumerationConstraint(Multiplicities.maximalOne, CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL_VALUES).withDefaultValues(StringValue.valueOf("page")));
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
        public static final TypeDef TYPE = new TypeDef("cf.cplace.landingPage.quicklink", name_singular, name_plural, " ",
                WIDGET_CONTAINER_DEF
                , CF_CPLACE_LANDINGPAGE_NAME, CF_CPLACE_LANDINGPAGE_LEVEL0REFERENCE, CF_CPLACE_LANDINGPAGE_ICON, CF_CPLACE_LANDINGPAGE_ORDER, CF_CPLACE_LANDINGPAGE_PAGEREFERENCE, CF_CPLACE_LANDINGPAGE_URLREFERENCE, CF_CPLACE_LANDINGPAGE_URLREFERENCEMODAL)
                .withNotFixedNameGenerationPattern("<cf.cplace.landingPage.name>")
                .withHiddenInGlobalSearch();
    }

    @FixedAppTypes.Fixed(orderIndex = 600)
    public static class CF_CPLACE_LANDINGPAGE_TAB {

        public static final Message cf_cplace_landingPage_icon_name = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_ICON =
                AttributeDef.build("cf.cplace.landingPage.icon", cf_cplace_landingPage_icon_name,
                        TypeConstraintFactories.iconConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_name_name = new Message() {
        };

        public static final SingleStringAttributeDef CF_CPLACE_LANDINGPAGE_NAME =
                AttributeDef.build("cf.cplace.landingPage.name", cf_cplace_landingPage_name_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.maximalOne));


        public static final Message cf_cplace_landingPage_order_name = new Message() {
        };

        public static final SingleNumberAttributeDef CF_CPLACE_LANDINGPAGE_ORDER =
                AttributeDef.build("cf.cplace.landingPage.order", cf_cplace_landingPage_order_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne));

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
        public static final TypeDef TYPE = new TypeDef("cf.cplace.landingPage.tab", name_singular, name_plural, " ",
                WIDGET_CONTAINER_DEF
                , CF_CPLACE_LANDINGPAGE_NAME, CF_CPLACE_LANDINGPAGE_ICON, CF_CPLACE_LANDINGPAGE_ORDER)
                .withNotFixedNameGenerationPattern("<cf.cplace.landingPage.name>")


                .withHiddenInGlobalSearch();
    }

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