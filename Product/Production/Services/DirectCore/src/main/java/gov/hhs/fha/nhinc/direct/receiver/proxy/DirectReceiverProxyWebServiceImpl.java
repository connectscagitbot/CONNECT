/*
 * Copyright (c) 2009-2015, United States Government, as represented by the Secretary of Health and Human Services.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the documentation
 *       and/or other materials provided with the distribution.
 *     * Neither the name of the United States Government nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE UNITED STATES GOVERNMENT BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.direct.receiver.proxy;

import gov.hhs.fha.nhinc.direct.HeaderMap;
import java.io.IOException;
import java.util.Enumeration;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author svalluripalli
 */
public class DirectReceiverProxyWebServiceImpl {

    private static final Logger LOG = Logger.getLogger(DirectReceiverProxyWebServiceImpl.class);

    /**
     * Constructor
     */
    public DirectReceiverProxyWebServiceImpl() {}

    /**
     *
     * @param inMessage
     */
    public void receiveInbound(MimeMessage inMessage) {
        LOG.debug("Begin DirectReceiverUnsecuredProxy.receiveInbound()");
        try { // Call Web Service Operation
            gov.hhs.fha.nhinc.direct.DirectReceiverService service = new gov.hhs.fha.nhinc.direct.DirectReceiverService();
            gov.hhs.fha.nhinc.direct.DirectReceiverPortType port = service.getDirectReceiverPortType();
            gov.hhs.fha.nhinc.direct.ConnectCustomMimeMessage message = new gov.hhs.fha.nhinc.direct.ConnectCustomMimeMessage();
            message.setContent(inMessage.getContent().toString());
            HeaderMap aMap = null;
            Enumeration mimeHeaders = inMessage.getAllHeaders();
            while (mimeHeaders.hasMoreElements()) {
                Header header = (Header) mimeHeaders.nextElement();
                LOG.debug("MIME Header: " + header.getName() + "= " + header.getValue());
                aMap = new HeaderMap();
                aMap.setKey(header.getName());
                aMap.setValue(header.getValue());
                message.getHeadersList().add(aMap);
            }
            InternetAddress senderAddress = (InternetAddress) inMessage.getSender();
            message.setSender(senderAddress.getAddress());
            message.setSubject(inMessage.getSubject());
            port.receiveInbound(message);
        } catch (IOException ex) {
            LOG.error("Unable to call DirectReceiver WebService :" + ex.getMessage());
        } catch (MessagingException ex) {
            LOG.error("Unable to call DirectReceiver WebService :" + ex.getMessage());
        }

        LOG.debug("End DirectReceiverUnsecuredProxy.receiveInbound()");
    }
}