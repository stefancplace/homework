/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import cf.cplace.platform.assets.WidgetWithContent;
import cf.cplace.platform.assets.custom.BooleanValue;
import cf.cplace.platform.assets.custom.CustomSerializable;
import cf.cplace.platform.assets.custom.FixedAppTypes;
import cf.cplace.platform.assets.custom.LocalizedPageNamesMode;
import cf.cplace.platform.assets.custom.LocalizedStringValue;
import cf.cplace.platform.assets.custom.Multiplicities;
import cf.cplace.platform.assets.custom.NumberValue;
import cf.cplace.platform.assets.custom.StringValue;
import cf.cplace.platform.assets.custom.def.AttributeDef;
import cf.cplace.platform.assets.custom.def.IAttributeDefEnum;
import cf.cplace.platform.assets.custom.def.MultiDocumentReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.MultiPageReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleBooleanAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleCustomAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleDateAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleDocumentReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleEnumAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleNumberAttributeDef;
import cf.cplace.platform.assets.custom.def.SinglePageReferenceAttributeDef;
import cf.cplace.platform.assets.custom.def.SingleStringAttributeDef;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.assets.custom.typeConstraints.factory.TypeConstraintFactories;
import cf.cplace.platform.assets.layout.Column;
import cf.cplace.platform.assets.layout.Layout;
import cf.cplace.platform.assets.layout.Row;
import cf.cplace.platform.assets.search.ReferenceOperator;
import cf.cplace.platform.assets.search.RelativeUidFilter;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.widget.WidgetContainerDef;
import cf.cplace.platform.widget.WidgetHelper;
import cf.cplace.training.extended.ExtendedApp;
import cf.cplace.training.extended.ExtendedAppTypes;
import cf.cplace.training.extended.SecurityQuestionConstraintExtension;
import cf.cplace.training.extended.script.EmployeesWidgetDefinition;
import de.cf.workshop.imdb.script.CreateSequelWidgetDefinition;

public class ImdbAppTypes {
    @FixedAppTypes.Fixed
    public static class EMPLOYEE {

        public static final Message surname = new Message() {
        };

        public static final SingleStringAttributeDef SURNAME =
                AttributeDef.build("cf.cplace.training.extended.surname", surname,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne));

        public static final Message familyName = new Message() {
        };

        public static final SingleStringAttributeDef FAMILY_NAME =
                AttributeDef.build("cf.cplace.training.extended.familyName", familyName,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne));

        public static final Message departments_name = new Message() {
        };

        public static final MultiPageReferenceAttributeDef DEPARTMENTS =
                AttributeDef.build("cf.cplace.training.extended.departments", departments_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.atLeastOne, ExtendedAppTypes.DEPARTMENT.TYPE.name, null, true));

        public static final Message favoriteDepartments_name = new Message() {
        };

        public static final Message favoriteDepartments_shortHelp = new Message() {
        };

        public static final SinglePageReferenceAttributeDef FAVORITE_DEPARTMENT =
                AttributeDef.build("cf.cplace.training.extended.favoriteDepartment", favoriteDepartments_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, ExtendedAppTypes.DEPARTMENT.TYPE.name, null, true)
                                .withAdditionalFilter(() -> new RelativeUidFilter(ReferenceOperator.customAttributeOfEmbeddingPage(DEPARTMENTS.name))))
                        .withShortHelp(favoriteDepartments_shortHelp);

        public static final Message hierarchyRoots_name = new Message() {
        };

        public static final MultiPageReferenceAttributeDef DEPARTMENT_HIERARCHY_ROOTS =
                AttributeDef.buildLinkedAttribute("cf.cplace.training.extended.hierarchyRoots", hierarchyRoots_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.nullMeansAnyNumber(), ExtendedAppTypes.DEPARTMENT.TYPE.name, null, true),
                        DEPARTMENTS, ExtendedAppTypes.DEPARTMENT.HIERARCHY_ROOT.name);

        public static final Message utilization_name = new Message() {
        };

        public static final Message utilization_label_00 = new Message() {
        };

        public static final Message utilization_label_20 = new Message() {
        };

        public static final Message utilization_label_40 = new Message() {
        };

        public static final Message utilization_label_60 = new Message() {
        };

        public static final Message utilization_label_80 = new Message() {
        };

        public static final Message utilization_label_100 = new Message() {
        };

        public static final SingleNumberAttributeDef UTILIZATION = AttributeDef.build("cf.cplace.training.extended.utilization", utilization_name,
                TypeConstraintFactories.numberEnumerationConstraint(Multiplicities.maximalOne, Lists.newArrayList(0d, 20d, 40d, 60d, 80d, 100d), Lists.newArrayList(), Lists.newArrayList(utilization_label_00, utilization_label_20, utilization_label_40, utilization_label_60, utilization_label_80, utilization_label_100))
        ).withAlternativeValueRepresentation(ExtendedApp.PROGRESS_BAR_VIEW_TEMPLATE_NAME);

        public static final Message gender_name = new Message() {
        };

        public static final Message gender_enumlabel_male = new Message() {
        };

        public static final Message gender_enumlabel_female = new Message() {
        };

        public static final Message gender_enumlabel_cyborg = new Message() {
        };

        public static final Message gender_enumlabel_other = new Message() {
        };

        public enum Gender implements IAttributeDefEnum {
            male("1.male", gender_enumlabel_male),
            female("2.female", gender_enumlabel_female),
            cyborg("3.cyborg", gender_enumlabel_cyborg),
            other("0.other", gender_enumlabel_other);

            private final String internalName;
            private final Message label;

            Gender(@Nullable String internalName, @Nullable Message label) {
                this.internalName = internalName;
                this.label = label;
            }

            @Nullable
            @Override
            public Message getLabel() {
                return label;
            }

            @Nullable
            @Override
            public String getAlternativeInternalName() {
                return internalName;
            }
        }

        public static final SingleEnumAttributeDef<ExtendedAppTypes.EMPLOYEE.Gender> GENDER = AttributeDef.build("cf.cplace.training.extended.gender", gender_name,
                TypeConstraintFactories.textEnumerationConstraint(Multiplicities.maximalOne, ExtendedAppTypes.EMPLOYEE.Gender.class));

        public static final Message salary_name = new Message() {
        };

        public static final SingleNumberAttributeDef SALARY = AttributeDef.build("cf.cplace.training.extended.salary", salary_name,
                TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne).withTextAfter("€").withPrecision(2));

        public static final Message securityQuestion_name = new Message() {
        };

        // customConstraint usage example
        public static final SingleCustomAttributeDef SECURITY_QUESTION = AttributeDef.build("cf.cplace.training.extended.securityQuestion", securityQuestion_name,
                TypeConstraintFactories.customConstraint(Multiplicities.maximalOne, "cf.cplace.training.extended.securityQuestion"));


        public static final Message name_singular = new Message() {
        };

        public static final Message name_plural = new Message() {
        };

        public static final Message leftCompany = new Message() {
        };

        public static final SingleBooleanAttributeDef LEFTCOMPANY =
                AttributeDef.build("cf.cplace.training.extended.leftCompany", leftCompany,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false)).withReadOnly();

        public static final Message dateOfBirth_name = new Message() {
        };

        public static final SingleDateAttributeDef DATE_OF_BIRTH =
                AttributeDef.build("cf.cplace.training.extended.dateOfBirth", dateOfBirth_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.maximalOne)).withShowInNewDialog();

        public static final TypeDef TYPE = new TypeDef("cf.cplace.training.extended.employee", name_singular, name_plural, "fa-user", null,
                SURNAME, FAMILY_NAME, DEPARTMENTS, FAVORITE_DEPARTMENT,
                UTILIZATION, DEPARTMENT_HIERARCHY_ROOTS, SALARY, GENDER, SECURITY_QUESTION, LEFTCOMPANY, DATE_OF_BIRTH)
                .withFixedNameGenerationPattern("<#|numberFormat:\"00000\">, <" + DATE_OF_BIRTH.name + "|dateFormat:\"MMM/yy\">, <" + SURNAME.name + "|limit:\"4\",transform:\"uppercase\">");

    }

    @FixedAppTypes.Fixed
    public static class DEPARTMENT {
        public static final String TYPE_NAME = "cf.cplace.training.extended.departments";

        public static final Message departmentId_name = new Message() {
        };

        public static final Message departmentId_errorMessage = new Message() {
        };

        public static final SingleStringAttributeDef DEPARTMENTID =
                AttributeDef.build("cf.cplace.training.extended.departmentId", departmentId_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, "^.{0,5}$", departmentId_errorMessage));

        public static final Message parentDepartment_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef PARENTDEPARTMENT =
                AttributeDef.build("cf.cplace.training.extended.parentDepartment", parentDepartment_name,
                        TypeConstraintFactories.linkPageConstraintAsHierarchy(Multiplicities.maximalOne, TYPE_NAME, null)).withTableColumnWidth(200);

        public static final Message templateDepartment_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef TEMPLATEDEPARTMENT =
                AttributeDef.build("cf.cplace.training.extended.templateDepartment", templateDepartment_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, TYPE_NAME, null, true));


        public static final Message hierarchyRoot_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef HIERARCHY_ROOT =
                AttributeDef.build("cf.cplace.training.extended.hierarchyRoot", hierarchyRoot_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, TYPE_NAME, null, true))
                        .withReadOnly();

        public static final Message isTemplate_name = new Message() {
        };

        public static final SingleBooleanAttributeDef ISTEMPLATE =
                AttributeDef.build("cf.cplace.training.extended.isTemplate", isTemplate_name,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false));

        public static final Message hideInSidebarMenu_name = new Message() {
        };

        public static final SingleBooleanAttributeDef HIDE_IN_SIDEBAR_MENU =
                AttributeDef.build("cf.cplace.training.extended.hideInSidebarMenu", hideInSidebarMenu_name,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false));

        public static final Message fileAttribute_name = new Message() {
        };

        public static final MultiDocumentReferenceAttributeDef FILE_ATTRIBUTE =
                AttributeDef.build("cf.cplace.training.extended.fileAttribute", fileAttribute_name,
                        TypeConstraintFactories.linkDocumentConstraint(Multiplicities.nullMeansAnyNumber(), (String) null, null, true))
                        .withAllowedDuplicates();

        private static final WidgetWithContent mainEmployeeTable = createEmployeeTable();

        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetHelper.ATTRIBUTES, WidgetWithContent.newWidgetWithType("cf.cplace.layoutTabsWidget.widget"))
                        ),
                        Row.fromColumns(
                                new Column(12, WidgetWithContent.newWidgetWithType("cf.cplace.training.extended.employees"),
                                        mainEmployeeTable,
                                        WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                                                        .put("cf.cplace.platform.attributesGroup.layout", StringValue.valueOf("{}"))
                                                        .put("singleSelectionWidgetId", StringValue.valueOf(mainEmployeeTable.id))
                                                        .put("cf.cplace.platform.attributesGroup.showFrame", BooleanValue.valueOf(true))
                                                        .put("cf.cplace.platform.attributesGroup.localizedTitle",
                                                                LocalizedStringValue.valueOf("{\"de\":\"mitarbeiterAttribute Main Layout\",\"en\":\"mitarbeiterAttribute Main Layout\"}"))
                                                        .put("singleColumn", BooleanValue.valueOf(false))
                                                        .toJson()
                                                , "cf.cplace.platform.connectedAttributesGroup")))));

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };

        private static final WidgetContainerDef ALTERNATIVE_LAYOUT_ALT1 = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetHelper.WIKI)),
                        Row.fromColumns(
                                new Column(6, WidgetHelper.ATTRIBUTES),
                                new Column(6, WidgetWithContent.newWidgetWithType(EmployeesWidgetDefinition.KIND))))
        );

        private static final WidgetWithContent layout2EmployeeTable = createEmployeeTable();

        private static final WidgetContainerDef ALTERNATIVE_LAYOUT_ALT2 = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetWithContent.newWidgetWithType("cf.cplace.training.extended.employees"),
                                        layout2EmployeeTable,
                                        WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                                                        .put("cf.cplace.platform.attributesGroup.layout", StringValue.valueOf("{}"))
                                                        .put("singleSelectionWidgetId", StringValue.valueOf(layout2EmployeeTable.id))
                                                        .put("cf.cplace.platform.attributesGroup.showFrame", BooleanValue.valueOf(true))
                                                        .put("cf.cplace.platform.attributesGroup.localizedTitle",
                                                                LocalizedStringValue.valueOf("{\"de\":\"mitarbeiterAttribute Layout2\",\"en\":\"mitarbeiterAttribute Layout 2\"}"))
                                                        .put("singleColumn", BooleanValue.valueOf(false))
                                                        .toJson()
                                                , "cf.cplace.platform.connectedAttributesGroup")))
                ));

        public static final TypeDef TYPE = new TypeDef(TYPE_NAME, name_singular, name_plural, "fa-group", WIDGET_CONTAINER_DEF,
                DEPARTMENTID, PARENTDEPARTMENT, TEMPLATEDEPARTMENT, HIERARCHY_ROOT, ISTEMPLATE, HIDE_IN_SIDEBAR_MENU, FILE_ATTRIBUTE)
                .withUniqueNames()
                .withLocalizedPageNamesMode(LocalizedPageNamesMode.optionalWithShowInNewDialog)
                .withAlternativeLayout("Alternative Layout mit Wiki und Attributen", ALTERNATIVE_LAYOUT_ALT1)
                .withAlternativeLayout("Alternative Layout mit Mitarbeiterliste", ALTERNATIVE_LAYOUT_ALT2);
    }

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

    private static WidgetWithContent createEmployeeTable() {
        return WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                        .put("hideTableLinks", BooleanValue.valueOf(false))
                        .put("singleSpaced", BooleanValue.valueOf(false))
                        .put("hideNames", BooleanValue.valueOf(false))
                        .put("search", StringValue.valueOf("{\"filters\":[{\"types\":[\"cf.cplace.training.extended.employee\"]}]}"))
                        .toJson()
                , WidgetHelper.EMBEDDED_SEARCH_AS_TABLE_TYPE);
    }

}