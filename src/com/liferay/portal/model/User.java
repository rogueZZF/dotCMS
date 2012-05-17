/**
 * Copyright (c) 2000-2005 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.dotmarketing.cms.factories.PublicEncryptionFactory;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.UtilMethods;
import com.liferay.portal.ejb.CompanyManagerUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.admin.ejb.AdminConfigManagerUtil;
import com.liferay.portlet.admin.model.UserConfig;
import com.liferay.util.LocaleUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

/**
 * <a href="User.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @version $Revision: 1.34 $
 *
 */
public class User extends Contentlet {

	private static final long serialVersionUID = 7685593215495607428L;
	
	private static final String EMAIL_KEY = "email";
	private static final String USERID_KEY = "userId";
	private static final String FIRSTNAME_KEY = "firstName";
	private static final String LASTNAME_KEY = "lastName";
	private static final String PASSWORD_KEY = "password";
	
	public User() {
		super();
	}
	
	public String getUserId(){
		return super.getStringProperty(USERID_KEY);
	}
	
	public String getFirstName(){
		return super.getStringProperty(FIRSTNAME_KEY);
	}
	
	public String getLastName(){
		return super.getStringProperty(LASTNAME_KEY);
	}
	
	public String getPassword(){
		return super.getStringProperty(PASSWORD_KEY);
	}
	
	public Date getCreateDate(){
		return super.getCreationDate();
	}
	
	public void setUserId(String userId){
		setStringProperty(USERID_KEY, userId);
	}
	
	public void setFirstName(String firstName){
		setStringProperty(FIRSTNAME_KEY, firstName);
	}
	
	public void setLastName(String lastName){
		setStringProperty(LASTNAME_KEY, lastName);
	}
	
	public void setPassword(String password){
		setStringProperty(PASSWORD_KEY, PublicEncryptionFactory.digestString(password));
	}
	
	public void setEmailAddress(String email){
		setStringProperty(EMAIL_KEY, email);
	}
	
	public String getEmailAddress(){
		return super.getStringProperty(EMAIL_KEY);
	}
	
	public boolean isPasswordExpired() {
		if (getPasswordExpirationDate() != null &&
			getPasswordExpirationDate().before(new Date())) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getFullName() {
		String firstName = super.getStringProperty("firstName");
		firstName = (UtilMethods.isSet(firstName) ? firstName : "");
		String lastName = super.getStringProperty("lastName");
		lastName = (UtilMethods.isSet(lastName) ? lastName : "");
		return firstName + " " + lastName;
	}


	public Locale getLocale() {
		return _locale;
	}

	public int compareTo(Object obj) {
		User user = (User)obj;

		return getFullName().toLowerCase().compareTo(
			user.getFullName().toLowerCase());
	}

	private boolean _defaultUser;
	private Locale _locale;

}
