/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.file.PageSpace;
import cf.cplace.platform.client.SessionLocal;
import cf.cplace.platform.orm.PersistentEntity;
import cf.cplace.platform.test.TestHelper;
import cf.cplace.platform.test.util.StartServerRule;

public class TestDeathAgeTrigger {
    @Rule
    public TestRule startServer = new StartServerRule(ImdbPlugin.INSTANCE());

    private PageSpace space;
    private Page director;

    @Before
    public void setup() {
        SessionLocal.getSession().setUserWithoutUpdatingLoginDate(TestHelper.getMustermannNoCheckAccess());

        space = PageSpace.SCHEMA.createWritableEntity();
        space._name().set("TestSpace");
        space._administrators().set(TestHelper.getMustermannNoCheckAccess());
        space.persist();

        space = space.createWritableCopy();
        space.addApp(ImdbPlugin.INSTANCE().app);
        space.persist();

        director = Page.SCHEMA.createWritablePage(space, ImdbAppTypes.DIRECTOR.TYPE, "George Lucas");
        director.set(ImdbAppTypes.DIRECTOR.BIRTHDATE, Date.from(LocalDate.of(1945, 6,13).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        director.persist();
        }

    @Test
    public void testDeathAgeSetAndReset() {
        Date deathDate = Date.from(LocalDate.of(1990, 8,13).atStartOfDay(ZoneId.systemDefault()).toInstant());
        PersistentEntity.doOnWritableCopyAndPersistIfModified(director, it ->
            it.set(ImdbAppTypes.DIRECTOR.DATEOFDEATH, deathDate) );

        Double age = director.get(ImdbAppTypes.DIRECTOR.DEATHAGE);
        assertEquals(Double.valueOf(45), age);

        PersistentEntity.doOnWritableCopyAndPersistIfModified(director, it ->
                it.set(ImdbAppTypes.DIRECTOR.DATEOFDEATH, null) );

        age = director.get(ImdbAppTypes.DIRECTOR.DEATHAGE);
        assertNull(age);


    }


}
