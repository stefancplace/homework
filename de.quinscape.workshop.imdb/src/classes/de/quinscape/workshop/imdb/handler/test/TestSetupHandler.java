package de.quinscape.workshop.imdb.handler.test;

import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.file.PageSpace;
import cf.cplace.platform.handler.AbstractTestSetupHandler;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.handler.Line;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.test.TestHelper;
import cf.cplace.platform.test.page.PageTestHelper;
import de.quinscape.workshop.imdb.ImdbPlugin;

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

    @Override
    protected Station doDoBusinessLogic() {
        PageSpace space = PageTestHelper.getRootSpace();
        space = space.createWritableCopy();
        space.addApp(ImdbPlugin.INSTANCE().app);
        space.persist();

        rootPage = space.getRootPageWithoutReadAccessCheck();
        TestHelper.setCurrentUser(TestHelper.getMustermannNoCheckAccess());


        return GO;
    }
}
