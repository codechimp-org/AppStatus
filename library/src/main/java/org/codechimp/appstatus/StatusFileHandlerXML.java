package org.codechimp.appstatus;

import org.codechimp.appstatus.objects.Status;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class StatusFileHandlerXML extends DefaultHandler {
    private Status status;
    private StringBuilder builder;

    public Status getUpdate() {
        return status;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if (this.status != null) {
            builder.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException {
        super.endElement(uri, localName, name);

        if (this.status != null) {
            if (localName.equals("message")) {
                status.setMessage(builder.toString().trim());
            } else if (localName.equals("priority")) {
                status.setPriority(builder.toString().trim());
            }

            builder.setLength(0);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName,
                             String name, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);

        if (localName.equals("status")) {
            status = new Status();
        }
    }

}
