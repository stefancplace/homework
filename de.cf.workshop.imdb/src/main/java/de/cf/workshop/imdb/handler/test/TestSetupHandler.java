/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.test;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import cf.cplace.platform.assets.WidgetContainer;
import cf.cplace.platform.assets.WidgetWithContent;
import cf.cplace.platform.assets.custom.CustomSerializable;
import cf.cplace.platform.assets.custom.CustomValue;
import cf.cplace.platform.assets.custom.ReferenceValue;
import cf.cplace.platform.assets.file.Document;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.file.PageSpace;
import cf.cplace.platform.assets.group.Group;
import cf.cplace.platform.assets.group.Person;
import cf.cplace.platform.assets.layout.Column;
import cf.cplace.platform.assets.layout.Layout;
import cf.cplace.platform.assets.layout.Row;
import cf.cplace.platform.client.SessionLocal;
import cf.cplace.platform.handler.AbstractTestSetupHandler;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.handler.Line;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.LocalizedString;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.orm.PersistentEntity;
import cf.cplace.platform.script.widgets.BatchJobQuickGlanceWidgetDefinition;
import cf.cplace.platform.test.TestHelper;
import cf.cplace.platform.test.page.PageTestHelper;
import cf.cplace.platform.util.DateUtil;
import cf.cplace.platform.util.Utilities;
import cf.cplace.platform.widget.WidgetContainerDef;
import cf.cplace.platform.widget.WidgetHelper;
import cf.cplace.training.extended.ExtendedPlugin;
import de.cf.workshop.imdb.ImdbAppTypes;
import de.cf.workshop.imdb.ImdbAppTypes.ACTOR;
import de.cf.workshop.imdb.ImdbAppTypes.DIRECTOR;
import de.cf.workshop.imdb.ImdbAppTypes.MOVIE;
import de.cf.workshop.imdb.ImdbPlugin;

public class TestSetupHandler extends AbstractTestSetupHandler {
    protected final Station GO = new Line() {
        @Override
        public void next(Forwarder f) {
            f.go(rootPage);
        }

        @Override
        public Message getConfirmationMessage() {
            return success;
        }
    };

    Page rootPage;
    Page allDepartments;
    Page allEmployees;

    @Override
    protected Station doDoBusinessLogic() {
        PageSpace space = PageTestHelper.getRootSpace();
        space = space.createWritableCopy();
        space.addApp(ImdbPlugin.INSTANCE().app);
        space.persist();
        SessionLocal.getSession().setUserWithoutUpdatingLoginDate(TestHelper.getMustermannNoCheckAccess());

        rootPage = space.getRootPageWithoutReadAccessCheck();


        Page headOffice = createDepartment(space, "Head Office", TestHelper.getLocalizedString("Hauptsitz", "Head Office"), null, null, "HO", false, false, false);
        Page humanResources = createDepartment(space, "Human Resources", TestHelper.getLocalizedString("Personal", "Human Resources"), headOffice, 0.5, "HR", true, false, false);
        Page development = createDepartment(space, "Development", TestHelper.getLocalizedString("Entwicklung", "Development"), headOffice, 0.3, "DE", true, true, false);
        Page payroll = createDepartment(space, "Payroll", TestHelper.getLocalizedString("Gehaltsabrechnung", "Payroll"), humanResources, null, "PA", false, false, false);
        Page minorOffice = createDepartment(space, "Minor Office", TestHelper.getLocalizedString("Kleines Büro", "Minor Office"), headOffice, null, "MO", false, false, true);
        Page minorHumanResources = createDepartment(space, "Minor Human Resources", TestHelper.getLocalizedString("Kleine Personalabteilung", "Minor Human Resources"), minorOffice, null, "MH", false, false, true);

        PersistentEntity.doOnWritableCopyAndPersistIfModified(payroll, page -> page._readersAreDefault().set(false));

        this.createEmployee(space, "Lubomir", "Król", 60, ImdbAppTypes.EMPLOYEE.Gender.male, 1800.0, LocalDate.of(1898, 12, 31), payroll, development);
        createEmployee(space, "John", "Doe", 100, ImdbAppTypes.EMPLOYEE.Gender.male, 5600.0, LocalDate.of(1912, 12, 12), payroll, development);
        createEmployee(space, "Albert", "Schrumpf", 20, ImdbAppTypes.EMPLOYEE.Gender.male, 1500.0, LocalDate.now(), headOffice);
        createEmployee(space, "Hannobert", "Bader", 0, ImdbAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.now().minusDays(1), headOffice);
        createEmployee(space, "Elke", "Bader", 40, ImdbAppTypes.EMPLOYEE.Gender.female, 0.0, LocalDate.now().plusDays(1), humanResources);
        createEmployee(space, "Sabine", "Bader", 60, ImdbAppTypes.EMPLOYEE.Gender.female, 0.0, LocalDate.of(1970, 1, 1), development);
        createEmployee(space, "Benno-Richard", "Bader", 100, ImdbAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.of(1980, 6, 6), development);
        createEmployee(space, "Albert", "Wachs", 0, ImdbAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.of(1990, 9, 9), humanResources);
        createEmployee(space, "Christine", "Mauer", 40, ImdbAppTypes.EMPLOYEE.Gender.female, 2200.0, LocalDate.of(1960, 12, 31), humanResources);
        createEmployee(space, "Knödelbert", "Mauer", 80, ImdbAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.of(2010, 10, 10), development);
        createEmployee(space, "Schnabeltier", "Mauer", 60, ImdbAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.of(1995, 9, 5), development);
        createEmployee(space, "Christoph", "Hujis", 60, ImdbAppTypes.EMPLOYEE.Gender.male, 3600.0, LocalDate.of(1996, 6, 6), humanResources);
        createEmployee(space, "Kiara", "Hufnagel", 80, ImdbAppTypes.EMPLOYEE.Gender.female, 3300.0, LocalDate.of(1997, 7, 9), development);

        createEmployee(space, "Klaus", "Kohl", 100, ImdbAppTypes.EMPLOYEE.Gender.male, 4200.0, LocalDate.of(1998, 8, 8), development);


        Group imdbUsers = createGroupAndPersist("ImdbUsers", null);
        for (int i = 0; i <= 10; i++) {
            Person person = createPersonAndPersist("benutzer" + i + "@gmail.com", "Benutzer" + i);
            Person.createMembership(person, imdbUsers, null);
        }

        final Page action = createGenre(space, "Action");
        final Page scienceFiction = createGenre(space, "Science Fiction");
        createGenre(space, "Comedy");

        final Page ridleyScott = createDirector(space, "Ridley Scott");
        final Page georgeLucas = createDirector(space, "George Lucas");
        final Page jamesCameron = createDirector(space, "James Cameron");

        final Page arnold = createActor(space, "Arnold Schwarzenegger", "Österreich");
        final Page mattDamon = createActor(space, "Matt Damon", "L.A.");
        final Page harrisonFord = createActor(space, "Harrison Ford", "L.A.");

        Document cover1 = setTemplateFile(rootPage, "terminator.jpg");
        Document cover2 = setTemplateFile(rootPage, "marsian.jpg");
        Document cover3 = setTemplateFile(rootPage, "StarWars.jpg");

        Page movie = createMovie(space, "Terminator", 5.0, action, arnold, cover1, 15000000.0, true, jamesCameron);
        createMovie(space, "Terminator 2", 5.0, action, arnold, cover1, 15000000.0, false, jamesCameron);
        createMovie(space, "Der Marsianer", 6.0, scienceFiction, mattDamon, cover2, 30000000.0, true, ridleyScott);
        createMovie(space, "Star Wars", 5.0, scienceFiction, harrisonFord, cover3, 5000000.0, true, georgeLucas);

        rootPage = movie;
        return GO;
    }

    public static Page createEmployee(PageSpace space, String firstName, String lastName, int utilization, ImdbAppTypes.EMPLOYEE.Gender gender, Double salary, LocalDate dateOfBirth, Page... abteilungen) {
        Page result = Page.SCHEMA.createWritablePage(space, ImdbAppTypes.EMPLOYEE.TYPE);
        result.set(ImdbAppTypes.EMPLOYEE.SURNAME, firstName);
        result.set(ImdbAppTypes.EMPLOYEE.FAMILY_NAME, lastName);
        result.set(ImdbAppTypes.EMPLOYEE.SALARY, utilization * 1000d);
        result.set(ImdbAppTypes.EMPLOYEE.DEPARTMENTS, Lists.newArrayList(abteilungen));
        result.set(ImdbAppTypes.EMPLOYEE.GENDER, gender);
        result.set(ImdbAppTypes.EMPLOYEE.SALARY, salary);
        result.set(ImdbAppTypes.EMPLOYEE.DATE_OF_BIRTH, DateUtil.getStartOfDay(dateOfBirth));
        result._name().setGeneratedName();
        result._content().set("<p>Change the family name of the employee. Next to the employee there is an icon. As soon as you move the mouse over it you can see the old value (Pattern: <a href=\"https://intranet.collaboration-factory.de/pages/fd9ay3qaq8x1z53e66bnp959z/Show-additional-information-next-to-the-value\">Show additional information next to the value</a>).</p>");
        result.persist();
        return result;
    }

    public  static Page createDepartment(PageSpace space, String name, LocalizedString displayName, Page parent, Double number1, String departmentId, boolean isTemplate, boolean includeQuickGlance, boolean hideInSidebarMenu) {
        Page result = Page.SCHEMA.createWritablePage(space, ImdbAppTypes.DEPARTMENT.TYPE, name);
        result._localizedName().setLocalizedString(displayName);
        CustomValue parentCustomValue = null;
        if (parent != null) {
            parentCustomValue = ReferenceValue.valueOf(parent, null, number1);
        }
        result.setCustomValue(ImdbAppTypes.DEPARTMENT.PARENTDEPARTMENT.name, parentCustomValue);
        result.set(ImdbAppTypes.DEPARTMENT.DEPARTMENTID, departmentId);
        result.set(ImdbAppTypes.DEPARTMENT.ISTEMPLATE, isTemplate);
        result.set(ImdbAppTypes.DEPARTMENT.HIDE_IN_SIDEBAR_MENU, hideInSidebarMenu);
        result._content().set("<ul>" +
                "<li>For exporting the employees of the department click the button &quot;Export Employees&quot;. This is an action, which is assigned to a Page (Pattern: <a href=\"https://intranet.collaboration-factory.de/pages/spa5y7ck0f11n0yh00ksotkwi/Add-an-Action-to-a-Page\">Add an Action to a Page</a>).</li>" +
                "<li>With the icon-link you get to the parent page (Pattern: <a href=\"https://intranet.collaboration-factory.de/pages/wrfnb5pk89yo7wbd2z31252oz/Icon-Link-to-parent-page\">Icon-Link to parent page</a>)</li>" +
                "<li>It is possible to hide a department in the sidebar menu (Pattern:&nbsp;<a href=\"https://intranet.collaboration-factory.de/pages/kua0opvn3a07ymsc68200idoa/Hide-certain-menu-items-in-the-sidebar-menu\">Hide certain menu items in the sidebar menu</a>).</li>" +
                "<li>If you remove a department, in the removal dialog there is an additional checkbox, which asks if the employees should be removed as well. This is done with the extension point&nbsp;<code>AdditionalConfirmDeletePage</code>.</li>" +
                "</ul>");
        result.persist();

        if (includeQuickGlance) {
            PersistentEntity.doOnWritableCopyAndPersistIfModified(result, r -> {
                final WidgetContainer wc = r.adapt(WidgetContainer.class);
                WidgetContainerDef.createFromLayoutWithWidgetsWithContent(Layout.fromRows(Row.fromColumns(
                        new Column(3,
                                WidgetWithContent.newWidgetWithConfigurationAndType(CustomSerializable.jsonBuilder()
                                        .put(BatchJobQuickGlanceWidgetDefinition.CONFIGURATION.QUICK_GLANCE_PROVIDER_ID, ExtendedPlugin.EXPORT_EMPLOYEES_QUICK_GLANCE_ID)
                                        .toJson(), BatchJobQuickGlanceWidgetDefinition.KIND)
                        ),
                        new Column(9, WidgetHelper.getWikiWidget())
                ))).apply(wc);
            });
        }

        return result;
    }

    @Nonnull
    private static Person createPersonAndPersist(String login, String name) {
        Person harryPotter = Person.createWritablePerson(login, name, "123123");
        harryPotter.persist();
        return harryPotter;
    }

    @Nonnull
    private static Group createGroupAndPersist(String groupName, Person notify) {
        Group group = Group.SCHEMA.createWritableEntity();
        group._name().set(groupName);
        group._administrators().set(Group.getAdminGroupWithoutReadAccessCheck());
        if (notify != null) {
            group._notified().create(notify);
        }
        group.persist();
        return group;
    }

    public static Page createGenre(PageSpace space, String genreName) {
        Page result = Page.SCHEMA.createWritablePage(space, ImdbAppTypes.GENRE.TYPE);
        result._name().set(genreName);
        result.persist();
        return result;
    }

    public static Page createDirector(PageSpace space, String name) {
        Page result = Page.SCHEMA.createWritablePage(space, DIRECTOR.TYPE);
        result.set(DIRECTOR.NAME, name);
        result.set(DIRECTOR.BIRTHDATE, new Date());
        result._name().setGeneratedName();
        result.persist();
        return result;
    }

    public static Page createActor(PageSpace space, String name, String birthPlace) {
        Page result = Page.SCHEMA.createWritablePage(space, ACTOR.TYPE);
        result.set(ACTOR.NAME, name);
        result.set(ACTOR.BIRTHDATE, new Date());
        result.set(ACTOR.BIRTHPLACE, birthPlace);
        result._name().set(name);
        result.persist();
        return result;
    }

    public static Page createMovie(PageSpace space, String title, Double rating, Page genre, Page actor, Document cover, Double boxOfficeTaking, Boolean oscarWon, Page director) {
        Page result = Page.SCHEMA.createWritablePage(space, MOVIE.TYPE);
        result.set(MOVIE.TITLE, title);
        result.set(MOVIE.RATING, rating);
        result.set(MOVIE.GENRE, genre);
        result.set(MOVIE.ACTOR, actor);
        result.set(MOVIE.RELEASEDATE, new Date());
        result.set(MOVIE.COVER, cover);
        result.set(MOVIE.BOXOFFICETAKING, boxOfficeTaking);
        result.set(MOVIE.OSCARWON, oscarWon);
        result.set(MOVIE.DIRECTOR, director);

        result._name().setGeneratedName();

        result.persist();
        return result;
    }

    @Nonnull
    private Document setTemplateFile(Page page, String filename) {
        Document document = Document.SCHEMA.createWritableEntity();
        document._parent().set(page);
        document._name().set(filename);
        document._fileModificationDate().set(Utilities.get(new Date()));
        document.persist();

        File file = new File(ImdbPlugin.INSTANCE().getDirectory(), "assets/covers/" + filename);
        try {
            document.copyFileIntoNewVersion(file);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return document;
    }

}
