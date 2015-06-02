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
package gov.hhs.fha.nhinc.orchestration;

import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;

public abstract class AbstractOrchestrable implements Orchestratable {

    private AssertionType assertion;
    private PolicyTransformer policyTransformer;
    private AuditTransformer auditTransformer;

    /**
     * default constructor.
     */
    protected AbstractOrchestrable() {
        assertion = null;
        policyTransformer = null;
        auditTransformer = null;

    }

    protected AbstractOrchestrable(PolicyTransformer pt, AuditTransformer at) {
        policyTransformer = pt;
        auditTransformer = at;
    }

    public AuditTransformer getAuditTransformer() {
        return auditTransformer;
    }

    public PolicyTransformer getPolicyTransformer() {
        return policyTransformer;
    }

    public AssertionType getAssertion() {
        return assertion;
    }

    /**
     * @param assertion the assertion to set
     */
    public void setAssertion(AssertionType assertion) {
        this.assertion = assertion;
    }

    /**
     * @param policyTransformer the policyTransformer to set
     */
    public void setPolicyTransformer(PolicyTransformer policyTransformer) {
        this.policyTransformer = policyTransformer;
    }

    /**
     * @param auditTransformer the auditTransformer to set
     */
    public void setAuditTransformer(AuditTransformer auditTransformer) {
        this.auditTransformer = auditTransformer;
    }



}