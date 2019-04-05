/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler.test;

import static cf.cplace.training.extended.handler.test.TestSetupHandler.createAbteilung;
import static cf.cplace.training.extended.handler.test.TestSetupHandler.createMitarbeiter;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import javax.annotation.Nonnull;

import cf.cplace.platform.assets.file.Document;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.file.PageSpace;
import cf.cplace.platform.assets.group.Group;
import cf.cplace.platform.assets.group.Person;
import cf.cplace.platform.client.SessionLocal;
import cf.cplace.platform.handler.AbstractTestSetupHandler;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.handler.Line;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.orm.PersistentEntity;
import cf.cplace.platform.test.TestHelper;
import cf.cplace.platform.test.page.PageTestHelper;
import cf.cplace.platform.util.Utilities;
import cf.cplace.training.extended.ExtendedAppTypes;
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


        Page headOffice = createAbteilung(space, "Head Office", TestHelper.getLocalizedString("Hauptsitz", "Head Office"), null, null, "HO", false, false, false);
        Page humanResources = createAbteilung(space, "Human Resources", TestHelper.getLocalizedString("Personal", "Human Resources"), headOffice, 0.5, "HR", true, false, false);
        Page development = createAbteilung(space, "Development", TestHelper.getLocalizedString("Entwicklung", "Development"), headOffice, 0.3, "DE", true, true, false);
        Page payroll = createAbteilung(space, "Payroll", TestHelper.getLocalizedString("Gehaltsabrechnung", "Payroll"), humanResources, null, "PA", false, false, false);
        Page minorOffice = createAbteilung(space, "Minor Office", TestHelper.getLocalizedString("Kleines Büro", "Minor Office"), headOffice, null, "MO", false, false, true);
        Page minorHumanResources = createAbteilung(space, "Minor Human Resources", TestHelper.getLocalizedString("Kleine Personalabteilung", "Minor Human Resources"), minorOffice, null, "MH", false, false, true);

        PersistentEntity.doOnWritableCopyAndPersistIfModified(payroll, page -> page._readersAreDefault().set(false));

        createMitarbeiter(space, "Lubomir", "Król", 60, ExtendedAppTypes.EMPLOYEE.Gender.male, 1800.0, LocalDate.of(1898, 12, 31), payroll, development);
        createMitarbeiter(space, "John", "Doe", 100, ExtendedAppTypes.EMPLOYEE.Gender.male, 5600.0, LocalDate.of(1912, 12, 12), payroll, development);
        createMitarbeiter(space, "Albert", "Schrumpf", 20, ExtendedAppTypes.EMPLOYEE.Gender.male, 1500.0, LocalDate.now(), headOffice);
        createMitarbeiter(space, "Hannobert", "Bader", 0, ExtendedAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.now().minusDays(1), headOffice);
        createMitarbeiter(space, "Elke", "Bader", 40, ExtendedAppTypes.EMPLOYEE.Gender.female, 0.0, LocalDate.now().plusDays(1), humanResources);
        createMitarbeiter(space, "Sabine", "Bader", 60, ExtendedAppTypes.EMPLOYEE.Gender.female, 0.0, LocalDate.of(1970, 1, 1), development);
        createMitarbeiter(space, "Benno-Richard", "Bader", 100, ExtendedAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.of(1980, 6, 6), development);
        createMitarbeiter(space, "Albert", "Wachs", 0, ExtendedAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.of(1990, 9, 9), humanResources);
        createMitarbeiter(space, "Christine", "Mauer", 40, ExtendedAppTypes.EMPLOYEE.Gender.female, 2200.0, LocalDate.of(1960, 12, 31), humanResources);
        createMitarbeiter(space, "Knödelbert", "Mauer", 80, ExtendedAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.of(2010, 10, 10), development);
        createMitarbeiter(space, "Schnabeltier", "Mauer", 60, ExtendedAppTypes.EMPLOYEE.Gender.male, 0.0, LocalDate.of(1995, 9, 5), development);
        createMitarbeiter(space, "Christoph", "Hujis", 60, ExtendedAppTypes.EMPLOYEE.Gender.male, 3600.0, LocalDate.of(1996, 6, 6), humanResources);
        createMitarbeiter(space, "Kiara", "Hufnagel", 80, ExtendedAppTypes.EMPLOYEE.Gender.female, 3300.0, LocalDate.of(1997, 7, 9), development);

        createMitarbeiter(space, "Klaus", "Kohl", 100, ExtendedAppTypes.EMPLOYEE.Gender.male, 4200.0, LocalDate.of(1998, 8, 8), development);


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

        createMovie(space, "Terminator", 5.0, action, arnold, cover1, 15000000.0, true, jamesCameron);
        createMovie(space, "Terminator 2", 5.0, action, arnold, cover1, 15000000.0, false, jamesCameron);
        createMovie(space, "Der Marsianer", 6.0, scienceFiction, mattDamon, cover2, 30000000.0, true, ridleyScott);
        createMovie(space, "Star Wars", 5.0, scienceFiction, harrisonFord, cover3, 5000000.0, true, georgeLucas);

        return GO;
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
