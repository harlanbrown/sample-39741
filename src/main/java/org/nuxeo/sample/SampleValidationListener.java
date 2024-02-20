package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentRef;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.validation.DocumentValidationException;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;

public class SampleValidationListener implements EventListener {
  
    private static final Log log = LogFactory.getLog(SampleValidationListener.class);

    @Override
    public void handleEvent(Event event) {

        EventContext eventContext = event.getContext();

        if (!(eventContext instanceof DocumentEventContext)) {
            return;
        }

        DocumentEventContext docCtx = (DocumentEventContext) eventContext;
        DocumentModel doc = docCtx.getSourceDocument();
        
        if (!doc.getType().equals("Favorites")&& !doc.getType().equals("DefaultSearch")  && !doc.getType().equals("BasicAuditSearch") && !doc.getType().equals("AdvancedContent")) {
            log.debug("Hi from sample listener");

            DocumentModel parent = eventContext.getCoreSession().getDocument(doc.getParentRef());
            NuxeoPrincipal principal = eventContext.getPrincipal(); 
                    
            if ("Picture".equals(doc.getType())) {
                if (principal.getAllGroups().contains("powerusers")) {
                    if (!parent.getName().equals("ws5")){
                        event.markBubbleException();
                        throw new DocumentValidationException("Pictures can be created only by powerusers in ws5 workspace");
                    }
                }
            }
        }
    }
}
