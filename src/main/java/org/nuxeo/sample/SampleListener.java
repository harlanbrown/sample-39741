package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;

public class SampleListener implements EventListener {
  
    private static final Log log = LogFactory.getLog(SampleListener.class);

    private static final String PARENT_PATH = "parentPath";

    @Override
    public void handleEvent(Event event) {

        EventContext eventContext = event.getContext();

        log.debug(eventContext.getProperties().get("prop"));

        if (!(eventContext instanceof DocumentEventContext)) {
            return;
        }

        DocumentEventContext docCtx = (DocumentEventContext) eventContext;
        DocumentModel doc = docCtx.getSourceDocument();
        
        if (!doc.getType().equals("Favorites")&& !doc.getType().equals("DefaultSearch")  && !doc.getType().equals("BasicAuditSearch") && !doc.getType().equals("AdvancedContent")) {
            log.debug("Hi from sample listener");

            String type = doc.getType();
            log.debug(type);

            String parentPath = (String) docCtx.getProperty(PARENT_PATH);
            log.debug(parentPath);
        }

    }
}
