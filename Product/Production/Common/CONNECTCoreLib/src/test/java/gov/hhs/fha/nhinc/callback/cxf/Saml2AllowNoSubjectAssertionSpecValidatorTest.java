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
package gov.hhs.fha.nhinc.callback.cxf;

import gov.hhs.fha.nhinc.nhinclib.NhincConstants;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Statement;
import org.opensaml.saml2.core.Subject;
import org.opensaml.xml.validation.ValidationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Saml2AllowNoSubjectAssertionSpecValidatorTest {

	@Test
	public void testValidateSubject() throws ValidationException {
		Assertion assertion = mock(Assertion.class);
		Saml2AllowNoSubjectAssertionSpecValidator validator = new Saml2AllowNoSubjectAssertionSpecValidator();

		Statement statement = mock(Statement.class);
		List<Statement> statementList = new ArrayList<Statement>();
		statementList.add(statement);
		AuthnStatement authnStatement = mock(AuthnStatement.class);
		List<AuthnStatement> authnStatementList = new ArrayList<AuthnStatement>();
		authnStatementList.add(authnStatement);
		AttributeStatement attrStatement = mock(AttributeStatement.class);
		List<AttributeStatement> attrStatementList = new ArrayList<AttributeStatement>();
		attrStatementList.add(attrStatement);
		AuthzDecisionStatement authzDecisionStatement = mock(AuthzDecisionStatement.class);
		List<AuthzDecisionStatement> authzDecisionStatementList = new ArrayList<AuthzDecisionStatement>();
		authzDecisionStatementList.add(authzDecisionStatement);
		Subject subject = mock(Subject.class);
		Issuer issuer = mock(Issuer.class);
		NameID name = mock(NameID.class);


		when(assertion.getStatements()).thenReturn(statementList);
		when(assertion.getAuthnStatements()).thenReturn(authnStatementList);
		when(assertion.getAttributeStatements()).thenReturn(attrStatementList);
		when(assertion.getAuthzDecisionStatements()).thenReturn(
				authzDecisionStatementList);
		when(assertion.getSubject()).thenReturn(subject);
		when(subject.getNameID()).thenReturn(name);
        when(name.getFormat()).thenReturn(NhincConstants.AUTH_FRWK_NAME_ID_FORMAT_X509);
        when(name.getValue()).thenReturn(NhincConstants.SAML_DEFAULT_ISSUER_NAME);
		when(assertion.getIssuer()).thenReturn(issuer);
        when(issuer.getFormat()).thenReturn(NhincConstants.AUTH_FRWK_NAME_ID_FORMAT_X509);
        when(issuer.getValue()).thenReturn(NhincConstants.SAML_DEFAULT_ISSUER_NAME);

		validator.validate(assertion);

		assertTrue(true);
	}

	@Test(expected = ValidationException.class)
	public void testValidateSubject_FailOnGetStatemtents()
			throws ValidationException {
		String expectedMessage = "Subject is required when Statements are absent";
		Assertion assertion = mock(Assertion.class);
		Saml2AllowNoSubjectAssertionSpecValidator validator = new Saml2AllowNoSubjectAssertionSpecValidator();

		List<Statement> statementList = new ArrayList<Statement>();

		when(assertion.getStatements()).thenReturn(statementList);

		try {
			validator.validate(assertion);
		} catch (ValidationException e) {
			assertEquals(e.getMessage(), expectedMessage);
			throw e;
		}
	}

	@Test(expected = ValidationException.class)
	public void testValidateSubject_FailOnGetAuthnStatements()
			throws ValidationException {
		String expectedMessage = "Assertions containing AuthnStatements require a Subject";
		Assertion assertion = mock(Assertion.class);
		Saml2AllowNoSubjectAssertionSpecValidator validator = new Saml2AllowNoSubjectAssertionSpecValidator();

		Statement statement = mock(Statement.class);
		List<Statement> statementList = new ArrayList<Statement>();
		statementList.add(statement);
		AuthnStatement authnStatement = mock(AuthnStatement.class);
		List<AuthnStatement> authnStatementList = new ArrayList<AuthnStatement>();
		authnStatementList.add(authnStatement);

		when(assertion.getStatements()).thenReturn(statementList);
		when(assertion.getAuthnStatements()).thenReturn(authnStatementList);

		try {
			validator.validate(assertion);
		} catch (ValidationException e) {
			assertEquals(e.getMessage(), expectedMessage);
			throw e;
		}
	}

	@Test(expected = ValidationException.class)
	public void testValidateSubject_FailOnGetAuthzDecisionStatements()
			throws ValidationException {
		String expectedMessage = "Assertions containing AuthzDecisionStatements require a Subject";
		Assertion assertion = mock(Assertion.class);
		Saml2AllowNoSubjectAssertionSpecValidator validator = new Saml2AllowNoSubjectAssertionSpecValidator();

		Statement statement = mock(Statement.class);
		List<Statement> statementList = new ArrayList<Statement>();
		statementList.add(statement);
		List<AuthnStatement> authnStatementList = new ArrayList<AuthnStatement>();
		AttributeStatement attrStatement = mock(AttributeStatement.class);
		List<AttributeStatement> attrStatementList = new ArrayList<AttributeStatement>();
		attrStatementList.add(attrStatement);

		AuthzDecisionStatement authzDecisionStatement = mock(AuthzDecisionStatement.class);
		List<AuthzDecisionStatement> authzDecisionStatementList = new ArrayList<AuthzDecisionStatement>();
		authzDecisionStatementList.add(authzDecisionStatement);

		when(assertion.getStatements()).thenReturn(statementList);
		when(assertion.getAuthnStatements()).thenReturn(authnStatementList);
		when(assertion.getAttributeStatements()).thenReturn(attrStatementList);
		when(assertion.getAuthzDecisionStatements()).thenReturn(
				authzDecisionStatementList);

		try {
			validator.validate(assertion);
		} catch (ValidationException e) {
			assertEquals(e.getMessage(), expectedMessage);
			throw e;
		}
	}

}