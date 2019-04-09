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
import de.cf.workshop.imdb.script.CreateSequelWidgetDefinition;
import de.cf.workshop.imdb.script.MatrixWidgetDefinition;

public class ImdbAppTypes {
    @FixedAppTypes.Fixed
    public static class EMPLOYEE {

        public static final Message surname = new Message() {
        };

        public static final SingleStringAttributeDef SURNAME =
                AttributeDef.build("cf.cplace.homework.surname", surname,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne));

        public static final Message familyName = new Message() {
        };

        public static final SingleStringAttributeDef FAMILY_NAME =
                AttributeDef.build("cf.cplace.homework.familyName", familyName,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne));

        public static final Message departments_name = new Message() {
        };

        public static final MultiPageReferenceAttributeDef DEPARTMENTS =
                AttributeDef.build("cf.cplace.homework.departments", departments_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.atLeastOne, ImdbAppTypes.DEPARTMENT.TYPE.name, null, true));

        public static final Message favoriteDepartments_name = new Message() {
        };

        public static final Message favoriteDepartments_shortHelp = new Message() {
        };

        public static final SinglePageReferenceAttributeDef FAVORITE_DEPARTMENT =
                AttributeDef.build("cf.cplace.homework.favoriteDepartment", favoriteDepartments_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, DEPARTMENT.TYPE.name, null, true)
                                .withAdditionalFilter(() -> new RelativeUidFilter(ReferenceOperator.customAttributeOfEmbeddingPage(DEPARTMENTS.name))))
                        .withShortHelp(favoriteDepartments_shortHelp);

        public static final Message hierarchyRoots_name = new Message() {
        };

        public static final MultiPageReferenceAttributeDef DEPARTMENT_HIERARCHY_ROOTS =
                AttributeDef.buildLinkedAttribute("cf.cplace.homework.hierarchyRoots", hierarchyRoots_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.nullMeansAnyNumber(), ImdbAppTypes.DEPARTMENT.TYPE.name, null, true),
                        DEPARTMENTS, ImdbAppTypes.DEPARTMENT.HIERARCHY_ROOT.name);

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

        public static final SingleEnumAttributeDef<ImdbAppTypes.EMPLOYEE.Gender> GENDER = AttributeDef.build("cf.cplace.homework.gender", gender_name,
                TypeConstraintFactories.textEnumerationConstraint(Multiplicities.maximalOne, ImdbAppTypes.EMPLOYEE.Gender.class));

        public static final Message salary_name = new Message() {
        };

        public static final SingleNumberAttributeDef SALARY = AttributeDef.build("cf.cplace.homework.salary", salary_name,
                TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne).withTextAfter("€").withPrecision(2));

        public static final Message securityQuestion_name = new Message() {
        };

        // customConstraint usage example
        public static final SingleCustomAttributeDef SECURITY_QUESTION = AttributeDef.build("cf.cplace.homework.securityQuestion", securityQuestion_name,
                TypeConstraintFactories.customConstraint(Multiplicities.maximalOne, "cf.cplace.homework.securityQuestion"));


        public static final Message name_singular = new Message() {
        };

        public static final Message name_plural = new Message() {
        };

        public static final Message leftCompany = new Message() {
        };

        public static final SingleBooleanAttributeDef LEFTCOMPANY =
                AttributeDef.build("cf.cplace.homework.leftCompany", leftCompany,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false)).withReadOnly();

        public static final Message dateOfBirth_name = new Message() {
        };

        public static final SingleDateAttributeDef DATE_OF_BIRTH =
                AttributeDef.build("cf.cplace.homework.dateOfBirth", dateOfBirth_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.maximalOne)).withShowInNewDialog();

        public static final TypeDef TYPE = new TypeDef("cf.cplace.homework.employee", name_singular, name_plural, "fa-user", null,
                SURNAME, FAMILY_NAME, DEPARTMENTS, FAVORITE_DEPARTMENT,
                DEPARTMENT_HIERARCHY_ROOTS, SALARY, GENDER, SECURITY_QUESTION, LEFTCOMPANY, DATE_OF_BIRTH)
                .withFixedNameGenerationPattern("<#|numberFormat:\"00000\">, <" + DATE_OF_BIRTH.name + "|dateFormat:\"MMM/yy\">, <" + SURNAME.name + "|limit:\"4\",transform:\"uppercase\">");

    }

    @FixedAppTypes.Fixed
    public static class DEPARTMENT {
        public static final String TYPE_NAME = "cf.cplace.homework.departments";

        public static final Message departmentId_name = new Message() {
        };

        public static final Message departmentId_errorMessage = new Message() {
        };

        public static final SingleStringAttributeDef DEPARTMENTID =
                AttributeDef.build("cf.cplace.homework.departmentId", departmentId_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, "^.{0,5}$", departmentId_errorMessage));

        public static final Message parentDepartment_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef PARENTDEPARTMENT =
                AttributeDef.build("cf.cplace.homework.parentDepartment", parentDepartment_name,
                        TypeConstraintFactories.linkPageConstraintAsHierarchy(Multiplicities.maximalOne, TYPE_NAME, null)).withTableColumnWidth(200);

        public static final Message templateDepartment_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef TEMPLATEDEPARTMENT =
                AttributeDef.build("cf.cplace.homework.templateDepartment", templateDepartment_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, TYPE_NAME, null, true));


        public static final Message hierarchyRoot_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef HIERARCHY_ROOT =
                AttributeDef.build("cf.cplace.homework.hierarchyRoot", hierarchyRoot_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, TYPE_NAME, null, true))
                        .withReadOnly();

        public static final Message isTemplate_name = new Message() {
        };

        public static final SingleBooleanAttributeDef ISTEMPLATE =
                AttributeDef.build("cf.cplace.homework.isTemplate", isTemplate_name,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false));

        public static final Message hideInSidebarMenu_name = new Message() {
        };

        public static final SingleBooleanAttributeDef HIDE_IN_SIDEBAR_MENU =
                AttributeDef.build("cf.cplace.homework.hideInSidebarMenu", hideInSidebarMenu_name,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false));

        public static final Message fileAttribute_name = new Message() {
        };

        public static final MultiDocumentReferenceAttributeDef FILE_ATTRIBUTE =
                AttributeDef.build("cf.cplace.homework.fileAttribute", fileAttribute_name,
                        TypeConstraintFactories.linkDocumentConstraint(Multiplicities.nullMeansAnyNumber(), (String) null, null, true))
                        .withAllowedDuplicates();

        private static final WidgetWithContent mainEmployeeTable = createEmployeeTable();

        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetHelper.ATTRIBUTES, WidgetWithContent.newWidgetWithType("cf.cplace.layoutTabsWidget.widget"))
                        ),
                        Row.fromColumns(
                                new Column(12, WidgetWithContent.newWidgetWithType("cf.cplace.homework.employees"),
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
                                new Column(6, WidgetWithContent.newWidgetWithType(MatrixWidgetDefinition.KIND))))
        );

        private static final WidgetWithContent layout2EmployeeTable = createEmployeeTable();

        private static final WidgetContainerDef ALTERNATIVE_LAYOUT_ALT2 = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(12, WidgetWithContent.newWidgetWithType("cf.cplace.homework.employees"),
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
                AttributeDef.build("cf.cplace.homework.birthDate", birthDate_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.exactlyOne));


        public static final Message dateOfDeath_name = new Message() {
        };

        public static final SingleDateAttributeDef DATEOFDEATH =
                AttributeDef.build("cf.cplace.homework.dateOfDeath", dateOfDeath_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.maximalOne));

        public static final Message deathAge_name = new Message() {
        };

        public static final SingleNumberAttributeDef DEATHAGE =
                AttributeDef.build("cf.cplace.homework.deathAge", deathAge_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne)).withReadOnly();

        public static final Message name_name = new Message() {
        };

        public static final SingleStringAttributeDef NAME =
                AttributeDef.build("cf.cplace.homework.name", name_name,
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
        public static final TypeDef TYPE = new TypeDef("cf.cplace.homework.director", name_singular, name_plural, "fa-list",
                WIDGET_CONTAINER_DEF
                , NAME, BIRTHDATE, DATEOFDEATH, DEATHAGE)
                .withNotFixedNameGenerationPattern("<cf.cplace.homework.name>")

                .withInternalAttributeNamePrefix("cf.cplace.homework");
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

        public static final TypeDef TYPE = new TypeDef("cf.cplace.homework.genre", name_singular, name_plural, "fa-file-movie-o",
                WIDGET_CONTAINER_DEF);
    }

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class MOVIE {

        public static final Message boxOfficeTaking_name = new Message() {
        };

        public static final SingleNumberAttributeDef BOXOFFICETAKING =
                AttributeDef.build("cf.cplace.homework.boxOfficeTaking", boxOfficeTaking_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.maximalOne));


        public static final Message cover_name = new Message() {
        };

        public static final SingleDocumentReferenceAttributeDef COVER =
                AttributeDef.build("cf.cplace.homework.cover", cover_name,
                        TypeConstraintFactories.linkDocumentConstraint(Multiplicities.maximalOne, "default.file", null, true));


        public static final Message director_name = new Message() {
        };

        public static final SinglePageReferenceAttributeDef DIRECTOR =
                AttributeDef.build("cf.cplace.homework.director", director_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, "cf.cplace.homework.director", null, true));


        public static final Message genre_name = new Message() {
        };
        public static final Message genre_shortName = new Message() {
        };

        public static final SinglePageReferenceAttributeDef GENRE =
                AttributeDef.build("cf.cplace.homework.genre", genre_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.maximalOne, "cf.cplace.homework.genre", null, true))
                        .withLocalizedShortName(genre_shortName);


        public static final Message oscarWon_name = new Message() {
        };

        public static final SingleBooleanAttributeDef OSCARWON =
                AttributeDef.build("cf.cplace.homework.oscarWon", oscarWon_name,
                        TypeConstraintFactories.booleanConstraint().withDefaultValue(false));


        public static final Message rating_name = new Message() {
        };

        public static final SingleNumberAttributeDef RATING =
                AttributeDef.build("cf.cplace.homework.rating", rating_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.exactlyOne));


        public static final Message releaseDate_name = new Message() {
        };
        public static final Message releaseDate_shortName = new Message() {
        };

        public static final SingleDateAttributeDef RELEASEDATE =
                AttributeDef.build("cf.cplace.homework.releaseDate", releaseDate_name,
                        TypeConstraintFactories.dateConstraint(Multiplicities.exactlyOne))
                        .withLocalizedShortName(releaseDate_shortName);


        public static final Message title_name = new Message() {
        };
        public static final Message title_shortName = new Message() {
        };

        public static final SingleStringAttributeDef TITLE =
                AttributeDef.build("cf.cplace.homework.title", title_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, null, null))
                        .withLocalizedShortName(title_shortName);


        private static final WidgetContainerDef WIDGET_CONTAINER_DEF = WidgetContainerDef.createFromLayoutWithWidgetsWithContent(
                Layout.fromRows(
                        Row.fromColumns(
                                new Column(6, WidgetHelper.ATTRIBUTES, WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                                                .put("cf.cplace.homework.numberOfButtons", NumberValue.valueOf(5))
                                                .toJson()
                                        , CreateSequelWidgetDefinition.KIND)),
                                new Column(6, WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                                                .put("referenceAttribute", StringValue.valueOf("cf.cplace.homework.cover"))
                                                .put("showFrame", BooleanValue.valueOf(true))
                                                .toJson()
                                        , "cf.cplace.dragAndDropImageView.widget"))),
                        Row.fromColumns(
                                new Column(12, WidgetHelper.COMMENTS))));


        public static final Message height_name = new Message() {
        };

        public static final SingleNumberAttributeDef HEIGHT =
                AttributeDef.build("cf.cplace.homework.height", height_name,
                        TypeConstraintFactories.numberConstraint(Multiplicities.exactlyOne));

        public static final Message actor_name = new Message() {
        };
        public static final Message actor_shortName = new Message() {
        };

        public static final MultiPageReferenceAttributeDef ACTOR =
                AttributeDef.build("cf.cplace.homework.actor", actor_name,
                        TypeConstraintFactories.linkPageConstraint(Multiplicities.atLeastOne, "cf.cplace.homework.actor", null, true))
                        .withLocalizedShortName(actor_shortName);

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };
        public static final TypeDef TYPE = new TypeDef("cf.cplace.homework.movie", name_singular, name_plural, "fa-file-movie-o",
                WIDGET_CONTAINER_DEF
                , TITLE, RELEASEDATE, GENRE, RATING, DIRECTOR, ACTOR, BOXOFFICETAKING, OSCARWON, COVER)
                .withNotFixedNameGenerationPattern("<cf.cplace.homework.title> (<cf.cplace.homework.releaseDate>)")

                .withInternalAttributeNamePrefix("cf.cplace.homework.");
    }

    @FixedAppTypes.Fixed(orderIndex = 400)
    public static class ACTOR {

        public static final Message name_name = new Message() {
        };

        public static final SingleStringAttributeDef NAME =
                AttributeDef.build("cf.cplace.homework.name", name_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, null, null));

        public static final Message birthDate_name = new Message() {
        };
        public static final Message birthDate_shortName = new Message() {
        };

        public static final SingleDateAttributeDef BIRTHDATE =
                AttributeDef.build("cf.cplace.homework.birthDate", birthDate_name,
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
                AttributeDef.build("cf.cplace.homework.birthPlace", birthPlace_name,
                        TypeConstraintFactories.stringConstraint(Multiplicities.exactlyOne, null, null))
                        .withLocalizedShortName(birthPlace_shortName);

        public static final Message name_singular = new Message() {
        };
        public static final Message name_plural = new Message() {
        };

        public static final TypeDef TYPE = new TypeDef("cf.cplace.homework.actor", name_singular, name_plural, "fa-file-movie-o",
                WIDGET_CONTAINER_DEF);
    }

    private static WidgetWithContent createEmployeeTable() {
        return WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                        .put("hideTableLinks", BooleanValue.valueOf(false))
                        .put("singleSpaced", BooleanValue.valueOf(false))
                        .put("hideNames", BooleanValue.valueOf(false))
                        .put("search", StringValue.valueOf("{\"filters\":[{\"types\":[\"cf.cplace.homework.employee\"]}]}"))
                        .toJson()
                , WidgetHelper.EMBEDDED_SEARCH_AS_TABLE_TYPE);
    }


}