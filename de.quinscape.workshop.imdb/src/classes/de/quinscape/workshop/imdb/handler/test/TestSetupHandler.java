package de.quinscape.workshop.imdb.handler.test;

import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.handler.AbstractTestSetupHandler;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.handler.Line;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.Message;

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

    private Page rootPage;

    @Override
    protected Station doDoBusinessLogic() {
        return GO;
    }
}