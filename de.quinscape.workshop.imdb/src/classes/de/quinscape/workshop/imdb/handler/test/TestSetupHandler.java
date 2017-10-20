package de.quinscape.workshop.imdb.handler.test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cf.cplace.platform.assets.file.Document;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.file.PageSpace;
import cf.cplace.platform.assets.group.Group;
import cf.cplace.platform.assets.group.Person;
import cf.cplace.platform.handler.AbstractTestSetupHandler;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.handler.Line;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.test.TestHelper;
import cf.cplace.platform.test.page.PageTestHelper;
import de.quinscape.workshop.imdb.ImdbAppTypes;
import de.quinscape.workshop.imdb.ImdbPlugin;

public class TestSetupHandler extends AbstractTestSetupHandler {
    protected final Station GO = new Line() {
        @Override
        public void next(Forwarder f) {
            f.go(rootSpace);
        }

        @Override
        public Message getConfirmationMessage() {
            return success;
        }
    };

    private PageSpace rootSpace;

    @Override
    protected Station doDoBusinessLogic() {
        rootSpace = buildRootSpace();
        generateDummyUsers();
        generateTestData();
        loginUser();
        return GO;
    }

    private PageSpace buildRootSpace() {
        PageSpace space = PageTestHelper.getRootSpace();
        space = space.createWritableCopy();
        space.addApp(ImdbPlugin.INSTANCE().app);
        space.persist();

        return space;
    }

    private Group buildDummyGroup() {
        Group group = Group.SCHEMA.createWritableEntity();
        group._name().set("Benutzer");
        group._administrators().set(Group.getAdminGroupOrNullWithoutReadAccessCheck());
        group.persist();

        return group;
    }

    private void generateDummyUsers() {
        Group group = buildDummyGroup();

        for (int i = 1; i < 10; i++) {
            Person person = Person.createWritablePerson("person" + i, "p" + i + "@example.com",
                    "Person" + (100 + i), "xxx");
            person.persist();
            Person.createMembership(person, group, "");
        }
    }

    private void generateTestData() {
        generateMovies(buildGenreList(), buildDirectorList());
    }

    private List<Page> buildGenreList() {
        List<Page> genres = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Page genreAsPage = Page.SCHEMA.createWritablePage(rootSpace, ImdbAppTypes.GENRE.TYPE);
            genreAsPage._name().set("Genre" + i);
            genreAsPage.set(ImdbAppTypes.GENRE.GENRE_NAME, "Science-Fiction");
            genreAsPage.persist();

            genres.add(genreAsPage);
        }

        return genres;
    }

    private List<Page> buildDirectorList() {
        List<Page> directors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Page directorAsPage = Page.SCHEMA.createWritablePage(rootSpace, ImdbAppTypes.REGISSEUR.TYPE);
            directorAsPage._name().set("Director" + i);

            Calendar calendar = Calendar.getInstance();
            calendar.set(1984, 1, 26);
            directorAsPage.set(ImdbAppTypes.REGISSEUR.IMDB_DIRECTOR_BIRTHDATE, calendar.getTime());
            directorAsPage.persist();

            directors.add(directorAsPage);
        }

        return directors;
    }

    private void generateMovies(List<Page> genre, List<Page> director) {
        for (int i = 0; i < 10; i++) {
            String movieName = "Awesome Movie" + i;
            Page movie = buildMovie(genre, director, movieName);
            addCoverToMovie(movie);
        }
    }

    private void addCoverToMovie(Page movie) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("pulpFiction.jpg");
        File file = new File(resource.getFile());
        Document doc = movie.createDocument("pulpFiction.jpg", new Date().getTime());

        try {
            writeCover(file, doc);
            addCoverToCopy(movie, doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeCover(File file, Document doc) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        RandomAccessFile output = doc.getWriteRandomAccessFileAndCreateVersion();
        output.write(fileContent);
        output.close();
    }

    private void addCoverToCopy(Page movie, Document doc) {
        Page writableCopy = movie.createWritableCopy();
        writableCopy.set(ImdbAppTypes.IMDB.IMDB_COVER, doc);
        writableCopy.persist();
    }

    private Page buildMovie(List<Page> genres, List<Page> directors, String movieName) {
        Page movie = Page.SCHEMA.createWritablePage(rootSpace, ImdbAppTypes.IMDB.TYPE);
        movie._name().set(movieName);
        movie.set(ImdbAppTypes.IMDB.IMDB_RELEASEYEAR, "1985");
        movie.set(ImdbAppTypes.IMDB.IMDB_RATING, 5d);
        movie.set(ImdbAppTypes.IMDB.IMDB_GENRE, genres);

        int random = new Random().nextInt(directors.size());
        movie.set(ImdbAppTypes.IMDB.IMDB_DIRECTOR, (directors.get(random)));
        movie.set(ImdbAppTypes.IMDB.IMDB_SUM, 210609762d);
        movie.set(ImdbAppTypes.IMDB.IMDB_OSCAR, true);
        movie.persist();

        return movie;
    }

    private void loginUser() {
        TestHelper.setCurrentUser(TestHelper.getMustermannNoCheckAccess());
    }
}
