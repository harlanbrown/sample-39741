package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.UnrestrictedSessionRunner;
import org.nuxeo.ecm.core.api.repository.RepositoryManager;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.runtime.api.Framework;

public class OtherSampleListener implements EventListener {
  
    private static final Log log = LogFactory.getLog(OtherSampleListener.class);

    @Override
    public void handleEvent(Event event) {
        RepositoryManager repositoryManager = Framework.getService(RepositoryManager.class);
        for (String repositoryName : repositoryManager.getRepositoryNames()) {
            new UnrestrictedSessionRunner(repositoryName) {
                @Override
                public void run() {
                    log.debug("Hi from _other_ sample listener");
                    log.debug(session);
                }
            }.runUnrestricted();
        }
    }
}
