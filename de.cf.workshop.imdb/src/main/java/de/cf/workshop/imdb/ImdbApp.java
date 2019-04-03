/*
 * Copyright 2019, collaboration Factory AG. All rights reserved.
 */
package de.cf.workshop.imdb;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cf.cplace.platform.assets.custom.CustomAttribute;
import cf.cplace.platform.assets.custom.CustomAttributeChangeEvent;
import cf.cplace.platform.assets.custom.CustomAttributeChangeEventListener;
import cf.cplace.platform.assets.custom.CustomEntity;
import cf.cplace.platform.assets.custom.CustomValidatorAppExtension;
import cf.cplace.platform.assets.custom.CustomValue;
import cf.cplace.platform.assets.custom.MultiOnceCustomAttributeChangeListener;
import cf.cplace.platform.assets.custom.NumberValue;
import cf.cplace.platform.assets.custom.def.SingleNumberAttributeDef;
import cf.cplace.platform.assets.custom.def.TypeDef;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.handler.Action;
import cf.cplace.platform.handler.Forwarder;
import cf.cplace.platform.internationalization.Message;
import cf.cplace.platform.internationalization.ParameterizedMessage;
import cf.cplace.platform.orm.PersistentEntity;
import cf.cplace.platform.services.AdditionalActionAppExtension;
import cf.cplace.platform.services.CustomAttributeChangeEventListenersExtension;
import cf.cplace.platform.services.app.ProgrammaticallyDefinedAppWithTypeDefs;
import cf.cplace.platform.util.NameValue;
import de.cf.workshop.imdb.handler.HelloWorldDialogHandler;
import de.cf.workshop.imdb.ImdbAppTypes.DIRECTOR;
import de.cf.workshop.imdb.ImdbAppTypes.MOVIE;
import de.cf.workshop.imdb.handler.createSequel.EditHandler;
import de.cf.workshop.imdb.handler.exportMovie.ExportHandler;

@SuppressWarnings("unused")
public class ImdbApp extends ProgrammaticallyDefinedAppWithTypeDefs {

    public static final Message displayName = new Message() {
    };

    public static final Message description = new Message() {
    };

    ImdbApp() {
        // package scope, only the plugin creates an instance
    }

    @Nonnull
    @Override
    public Message getDisplayName() {
        return displayName;
    }

    @Override
    public Message getDescription() {
        return description;
    }

    @Override
    public String getIconName() {
        return "fa-info";
    }

    final AdditionalActionAppExtension<Page> helloWorldDialog = new AdditionalActionAppExtension<Page>() {
        @Nullable
        @Override
        public Action getAction(@Nonnull Page page) {
            return new Action() {
                @Override
                public void target(Forwarder f) {
                    f.go(HelloWorldDialogHandler.class, new NameValue("id", page.getId()));
                }

                @Override
                public boolean isButton() {
                    return true;
                }
            };
        }
    };

    final AdditionalActionAppExtension<Page> createSequelDialog = new AdditionalActionAppExtension<Page>() {
        @Nullable
        @Override
        public Action getAction(@Nonnull Page page) {
            return new Action() {
                @Override
                public void target(Forwarder f) {
                    f.go(EditHandler.class, new NameValue("movieId", page.getId()));
                }

                @Override
                public boolean isButton() {
                    return true;
                }
            };
        }
    };

    final AdditionalActionAppExtension<Page> exportMovieDialog = new AdditionalActionAppExtension<Page>() {
        @Nullable
        @Override
        public Action getAction(@Nonnull Page page) {
            return new Action() {
                @Override
                public void target(Forwarder f) {
                    f.go(ExportHandler.class, new NameValue("movieId", page.getId()));
                }

                @Override
                public boolean isButton() {
                    return true;
                }
            };
        }
    };

    public static final ParameterizedMessage noNegativeBoxOfficeTakingAllowed = new ParameterizedMessage() {
    };

    public static final ParameterizedMessage boxOfficeTakingTooBig = new ParameterizedMessage() {
    };

    public final CustomValidatorAppExtension boxOfficeTakingValidator = new CustomValidatorAppExtension() {
        @Override
        public Message validate(CustomAttribute customAttribute) {
            List<CustomValue> values = customAttribute.getValues();
            if (values.isEmpty()) {
                return null;
            }

            CustomValue customValue = values.get(0);
            if (!(customValue instanceof NumberValue)) {
                return null;
            }

            NumberValue numberValue = (NumberValue) customValue;
            if (numberValue.getNumber() < 0) {
                return noNegativeBoxOfficeTakingAllowed.setParameters(numberValue.getFormattedNumber());
            }
            if (numberValue.getNumber() > 100_000_000_000_000d ) {
                String title = customAttribute.getOwnerAsset().get(MOVIE.TITLE);
                if (!title.contains("Star Wars")) {
                    return boxOfficeTakingTooBig.setParameters(numberValue.getFormattedNumber());
                }
            }

            return null;
        }

        @Override
        public TypeDef getTypeDef() {
            return MOVIE.TYPE;
        }

        @Override
        public SingleNumberAttributeDef getAttributeDef() {
            return MOVIE.BOXOFFICETAKING;
        }
    };

    final CustomAttributeChangeEventListenersExtension computeDeathAge = new CustomAttributeChangeEventListenersExtension() {
        @Override
        public void addListeners(List<CustomAttributeChangeEventListener> listeners) {
            listeners.add(new MultiOnceCustomAttributeChangeListener(DIRECTOR.TYPE, DIRECTOR.BIRTHDATE, DIRECTOR.DATEOFDEATH) {
                @Override
                protected void doTrigger(@Nonnull CustomAttributeChangeEvent changeEvent) {
                    CustomEntity page = changeEvent.getEntity();
                    Date dateOfBirth = page.get(DIRECTOR.BIRTHDATE);
                    Date dateOfDeath = page.get(DIRECTOR.DATEOFDEATH);
                    if (dateOfBirth != null && dateOfDeath != null) {
                        LocalDate localBirthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate localDeathDate = dateOfDeath.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        Period age = Period.between(localBirthDate, localDeathDate);
                        PersistentEntity.doOnWritableCopyAndPersistIfModified(page, it ->
                             it.set(DIRECTOR.DEATHAGE, (double)age.getYears()));
                        CustomAttributeChangeEventListener.registerAttributeForRefresh(page, DIRECTOR.DEATHAGE);
                    }
                    else {
                        PersistentEntity.doOnWritableCopyAndPersistIfModified(page, it ->
                                it.set(DIRECTOR.DEATHAGE, null));
                        CustomAttributeChangeEventListener.registerAttributeForRefresh(page, DIRECTOR.DEATHAGE);
                    }
                }
            });

        }
    };



    @Nonnull
    @Override
    protected Class<?>[] getTypeDefContainerClasses() {
        return new Class<?>[] { ImdbAppTypes.class };
    }

}
