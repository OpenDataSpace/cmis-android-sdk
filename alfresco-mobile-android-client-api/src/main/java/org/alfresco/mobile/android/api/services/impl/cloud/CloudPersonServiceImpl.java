/*******************************************************************************
 * Copyright (C) 2005-2012 Alfresco Software Limited.
 * 
 * This file is part of the Alfresco Mobile SDK.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 ******************************************************************************/
package org.alfresco.mobile.android.api.services.impl.cloud;

import java.util.Map;

import org.alfresco.mobile.android.api.constants.CloudConstant;
import org.alfresco.mobile.android.api.exceptions.AlfrescoServiceException;
import org.alfresco.mobile.android.api.model.ContentStream;
import org.alfresco.mobile.android.api.model.Person;
import org.alfresco.mobile.android.api.model.impl.PersonImpl;
import org.alfresco.mobile.android.api.services.impl.AbstractPersonService;
import org.alfresco.mobile.android.api.services.impl.AbstractDocumentFolderServiceImpl;
import org.alfresco.mobile.android.api.session.AlfrescoSession;
import org.alfresco.mobile.android.api.session.CloudSession;
import org.alfresco.mobile.android.api.utils.CloudUrlRegistry;
import org.alfresco.mobile.android.api.utils.JsonUtils;
import org.apache.chemistry.opencmis.client.bindings.spi.http.HttpUtils;
import org.apache.chemistry.opencmis.commons.impl.UrlBuilder;

/**
 * The PersonService can be used to get informations about people.
 * 
 * @author Jean Marie Pascal
 */
public class CloudPersonServiceImpl extends AbstractPersonService
{

    /**
     * Default Constructor. Only used inside ServiceRegistry.
     * 
     * @param repositorySession : Repository Session.
     */
    public CloudPersonServiceImpl(AlfrescoSession repositorySession)
    {
        super(repositorySession);
    }

    protected UrlBuilder getPersonDetailssUrl(String personIdentifier)
    {
        return new UrlBuilder(CloudUrlRegistry.getPersonDetailssUrl((CloudSession) session, personIdentifier));
    }

    /**
     * Retrieves the avatar rendition for the specified username.
     * 
     * @param username : Username of person
     * @return Returns the contentFile associated to the avatar picture.
     * @throws AlfrescoServiceException : if network or internal problems occur
     *             during the process.
     */
    public ContentStream getAvatarStream(String username)
    {
        try
        {
            Person person = getPerson(username);
            ContentStream st = ((AbstractDocumentFolderServiceImpl) session.getServiceRegistry().getDocumentFolderService())
                    .downloadContentStream(person.getAvatarIdentifier());
            return st;
        }
        catch (Exception e)
        {
            convertException(e);
        }
        return null;
    }

    // ////////////////////////////////////////////////////////////////////////////////////
    // / INTERNAL
    // ////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    protected Person computePerson(UrlBuilder url)
    {
        HttpUtils.Response resp = read(url);
        Map<String, Object> json = JsonUtils.parseObject(resp.getStream(), resp.getCharset());
        Map<String, Object> data = (Map<String, Object>) ((Map<String, Object>) json).get(CloudConstant.ENTRY_VALUE);
        return PersonImpl.parsePublicAPIJson(data);
    }

}
