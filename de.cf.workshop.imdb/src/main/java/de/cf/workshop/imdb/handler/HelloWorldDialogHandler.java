/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */

package de.cf.workshop.imdb.handler;

import javax.annotation.Nullable;

import cf.cplace.platform.assets.custom.NumberValue;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.search.Filters;
import cf.cplace.platform.assets.search.Operator;
import cf.cplace.platform.assets.search.Search;
import cf.cplace.platform.client.Parameters;
import cf.cplace.platform.client.SessionLocal;
import cf.cplace.platform.handler.Handler;
import cf.cplace.platform.handler.JsonPage;
import cf.cplace.platform.handler.Station;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.services.exceptions.ProtectedActionException;
import cf.cplace.platform.template.ConditionalSubstitution;
import cf.cplace.platform.template.PrintSubstitution;
import cf.cplace.platform.template.SimpleListSubstitution;
import cf.cplace.platform.template.Template;
import de.cf.workshop.imdb.ImdbAppTypes.MOVIE;

public class HelloWorldDialogHandler extends Handler {

    private Page page;


    public static final  Message targetLabel = new Message() {
    };

    final Station SHOW = new JsonPage() {
        @Override
        public void putSubstitutions(Template template) {
            template.put("userName", PrintSubstitution.printing(() -> SessionLocal.getUser().getName()));
            template.put("condition", ConditionalSubstitution.testing(() -> false));
            template.put("list", new SimpleListSubstitution() {
                @Override
                protected Iterable getItems() {
                    Search search = new Search();
                    search.add(Filters.space(page.getSpaceWithoutReadAccessCheck()));
                    search.add(Filters.type(MOVIE.TYPE));
                    search.add(Filters.customAttribute(MOVIE.OSCARWON, true));
                    search.add(Filters.customAttribute(MOVIE.BOXOFFICETAKING, Operator.greater, NumberValue.valueOf(10000000)));
                    return search.findAllPages();
                }

                @Override
                protected void putAdditionalSubstitutions(Template template) {
                    template.put("name", PrintSubstitution.printing(() -> ((Page)getCurrentItem()).get(MOVIE.TITLE)));
                }
            });
        }

    };

    @Override
    protected void checkAccess() {
        String pageId = Parameters.getString("id");
        page = Page.SCHEMA.getEntityNotNull(pageId);
        if (!MOVIE.TYPE.isTypeOf(page)) {
            throw new ProtectedActionException();
        }
    }

    @Override
    protected Station doBusinessLogic() {
        return SHOW;
    }

    @Nullable
    @Override
    public Message getTargetLabel() {
        return targetLabel;
    }

    @Nullable
    @Override
    public String getActionIconName() {
        return "fa-user";
    }
}
