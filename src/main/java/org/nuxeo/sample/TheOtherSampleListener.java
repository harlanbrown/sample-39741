package org.nuxeo.sample;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventBundle;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.PostCommitEventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.runtime.api.Framework;
import java.util.stream.StreamSupport;

public class TheOtherSampleListener implements PostCommitEventListener {
  
    private static final Log log = LogFactory.getLog(TheOtherSampleListener.class);

    protected void printDocInfo(DocumentModel doc){
        log.debug(doc.toString());
    }

    protected void processDoc(DocumentModel doc){
        if (doc.hasSchema("picture") && !doc.isVersion()){
            // move document
            Framework.doPrivileged(() -> {   ///   privileged, but who is the principal
                CoreSession session = doc.getCoreSession();  // get principal here? is this how client code does this?
                log.debug("moving " + doc.getName());
                session.move(doc.getRef(), new PathRef("/default-domain/workspaces/ws1"), doc.getName());
            });
        }
    }

    @Override
    public void handleEvent(EventBundle events) {
        StreamSupport.stream(events.spliterator(), false)
                     .map(Event::getContext)
                     .filter(DocumentEventContext.class::isInstance)
                     .map(DocumentEventContext.class::cast)
                     .map(DocumentEventContext::getSourceDocument)
                     .filter(doc -> !doc.isVersion() && doc.hasSchema("picture"))
                     .forEach(this::processDoc);

    }
}
