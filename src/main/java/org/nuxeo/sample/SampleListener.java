package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.CloseableCoreSession;
import org.nuxeo.ecm.core.api.CoreInstance;
import org.nuxeo.ecm.core.api.NuxeoException;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;

public class SampleListener implements EventListener {
  
    private static final Log log = LogFactory.getLog(SampleListener.class);

    @Override
    public void handleEvent(Event event) {

        EventContext eventContext = event.getContext();

        log.debug("Hi from sample listener");

        try (CloseableCoreSession coreSession = CoreInstance.openCoreSession(null)) {
            log.debug(coreSession);
        } catch (NuxeoException e) {
            log.error("Error", e);
        }

    }
}
