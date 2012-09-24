/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The CONNECT_git
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This CONNECT_git Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the CONNECT_git Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the CONNECT_git Software; (ii) distribute and
 * have distributed to and by third parties the CONNECT_git Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.hhs.fha.nhinc.openSAML.extraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import gov.hhs.fha.nhinc.common.nhinccommon.AssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.CeType;
import gov.hhs.fha.nhinc.common.nhinccommon.HomeCommunityType;
import gov.hhs.fha.nhinc.common.nhinccommon.PersonNameType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthnStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceAssertionType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceConditionsType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementEvidenceType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlAuthzDecisionStatementType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlIssuerType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlSignatureKeyInfoType;
import gov.hhs.fha.nhinc.common.nhinccommon.SamlSignatureType;
import gov.hhs.fha.nhinc.common.nhinccommon.UserType;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ws.security.saml.ext.OpenSAMLUtil;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author dharley
 * 
 */
public class OpenSAMLAssertionExtractorImplTest {
    
    private final OpenSAMLAssertionExtractorImpl openSAMLAssertionExtractorImpl = new OpenSAMLAssertionExtractorImpl();
    static {
        OpenSAMLUtil.initSamlEngine();
    }

    /**
     * When the SAML file is null, assertion extracted will be null.
     * @throws Exception on error.
     */
    @Test
    public void testNullAssertionElement() throws Exception {
        //assertNull(openSAMLAssertionExtractorImpl.extractSAMLAssertion(null));
    }

    /**
     * Tests SAML Assertion populated with all possible Assertion elements and attributes, verify they are populated.
     * @throws Exception on error.
     */
    @Test
    public void testCompleteSamlAssertion() throws Exception {
        
//        AssertionType assertionType = openSAMLAssertionExtractorImpl.extractSAMLAssertion(getElementForSamlFile(
//                File.separator + "testing_saml" + File.separator + "complete_saml.xml"));
//        assertNotNull(assertionType);
//
//        verifyHomeCommunity(assertionType.getHomeCommunity(), "2.16.840.1.113883.3.424", null);
//        verifyIssuer(assertionType.getSamlIssuer());        
//        verifyDecisionStatement(assertionType.getSamlAuthzDecisionStatement());        
//        verifyUser(assertionType.getUserInfo());        
//        verifyAuthnStatement(assertionType.getSamlAuthnStatement());        
//        verifyUniquePatientId(assertionType.getUniquePatientId());
//        verifyCeType(assertionType.getPurposeOfDisclosureCoded(), "OPERATIONS", "2.16.840.1.113883.3.18.7.1",
//                "nhin-purpose", "Healthcare Operations");
//        verifySignature(assertionType.getSamlSignature());
    }
     
    private void verifyIssuer(SamlIssuerType issuer) {
        assertEquals("CN=SAML User,OU=SU,O=SAML User,L=Los Angeles,ST=CA,C=US", issuer.getIssuer());
        assertEquals("urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName", issuer.getIssuerFormat());        
    }
    
    private void verifyHomeCommunity(HomeCommunityType homeCommunity, String id, String name) {
        assertEquals(id, homeCommunity.getHomeCommunityId());
        assertEquals(name, homeCommunity.getName());        
    }
    
    private void verifyUniquePatientId(List<String> uniquePatientId) {
        assertEquals(1, uniquePatientId.size());
        assertEquals("RI1.101.00043^^^&2.16.840.1.113883.3.424&ISO", uniquePatientId.get(0));        
    }

    private void verifyAuthnStatement(SamlAuthnStatementType statement) {
        assertEquals("urn:oasis:names:tc:SAML:2.0:ac:classes:X509", statement.getAuthContextClassRef());
        assertEquals("2010-05-01T02:09:01.089Z", statement.getAuthInstant());
        assertEquals("123456", statement.getSessionIndex());
        assertEquals(null, statement.getSubjectLocalityAddress());
        assertEquals(null, statement.getSubjectLocalityDNSName());
    }
    
    private void verifyDecisionStatement(SamlAuthzDecisionStatementType decisionStatement) {

        assertNotNull(decisionStatement); 
        
        // verify decision statement
        assertEquals("Permit", decisionStatement.getDecision());
        assertEquals("https://nhinri1c23.aegis.net:8181/NhinConnect/EntityPatientDiscoverySecured",
                decisionStatement.getResource());
        assertEquals("Execute", decisionStatement.getAction());

        // verify decision statement evidence
        SamlAuthzDecisionStatementEvidenceType evidence = decisionStatement.getEvidence();
        SamlAuthzDecisionStatementEvidenceAssertionType evidenceAssertion = evidence.getAssertion();
        assertEquals("759724ff-e9ce-4a7f-a55b-fc41ffe21a75", evidenceAssertion.getId());
        assertEquals("2010-05-01T02:09:01.104Z", evidenceAssertion.getIssueInstant());
        assertEquals("2.0", evidenceAssertion.getVersion());
        assertEquals("CN=SAML User,OU=SU,O=SAML User,L=Los Angeles,ST=CA,C=US", evidenceAssertion.getIssuer());
        assertEquals("urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName", evidenceAssertion.getIssuerFormat());
        
        SamlAuthzDecisionStatementEvidenceConditionsType evidenceConditions = evidenceAssertion.getConditions();
        assertEquals("2010-05-01T02:09:01.104Z", evidenceConditions.getNotBefore());
        assertEquals("2010-05-01T02:09:01.104Z", evidenceConditions.getNotOnOrAfter());

    }
    
    private void verifyUser(UserType user) {

        verifyHomeCommunity(user.getOrg(), "2.16.840.1.113883.3.424", "2.16.840.1.113883.3.424");
        
        PersonNameType personName = user.getPersonName();
        assertEquals("Testcase", personName.getFamilyName());
        assertEquals("Interop\n                IT Testcase", personName.getFullName());
        assertEquals("Interop", personName.getGivenName());
        assertEquals("IT", personName.getSecondNameOrInitials());
        assertNull(personName.getNameType());
        assertNull(personName.getPrefix());
        assertNull(personName.getSuffix());

        verifyCeType(user.getRoleCoded(), "46255001", "2.16.840.1.113883.6.96", "SNOMED_CT", "Pharmacist");
        assertEquals("UID=Scenario 45 PDR-5.7", user.getUserName());        
    }

    private void verifyCeType(CeType ceType, String code, String codeSystem, String codeSystemName, 
            String displayName) {
        assertEquals(code, ceType.getCode());
        assertEquals(codeSystem, ceType.getCodeSystem());
        assertEquals(codeSystemName, ceType.getCodeSystemName());
        assertEquals(displayName, ceType.getDisplayName());
    }
    
    private void verifySignature(SamlSignatureType signature) {                
        assertNotNull(signature.getSignatureValue());
        SamlSignatureKeyInfoType keyInfo = signature.getKeyInfo();
        assertNotNull(keyInfo.getRsaKeyValueExponent());
        assertNotNull(keyInfo.getRsaKeyValueModulus());        
    }
    
    private Element getElementForSamlFile(String samlFileName) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(getSamlFile(samlFileName));
        return document.getDocumentElement();
    }

    private File getSamlFile(String samlFileName) {
        URI uri = null;
        try {
            uri = this.getClass().getResource(samlFileName).toURI();
        } catch (URISyntaxException e) {
            fail("Could not build URI for filepath. " + e.getMessage());
        }
        return new File(uri);        
    }

}