/*******************************************************************************
 * Copyright (C) 2005-2013 Alfresco Software Limited.
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
package org.alfresco.mobile.android.api.asynchronous;

import org.alfresco.mobile.android.api.model.Site;
import org.alfresco.mobile.android.api.session.AlfrescoSession;

import android.content.Context;

/**
 * Provides an asynchronous loader to cancel a JoinSiteRequest.
 *
 * @author Jean Marie Pascal
 */
public class JoinSiteRequestCancelLoader extends AbstractBaseLoader<LoaderResult<Site>>
{
    /** Unique SiteMembershipLoader identifier. */
    public static final int ID = JoinSiteRequestCancelLoader.class.hashCode();

    private final Site site;

    public JoinSiteRequestCancelLoader(Context context, AlfrescoSession session, Site site)
    {
        super(context);
        this.session = session;
        this.site = site;
    }

    @Override
    public LoaderResult<Site> loadInBackground()
    {
        LoaderResult<Site> result = new LoaderResult<Site>();
        try
        {
            result.setData(session.getServiceRegistry().getSiteService().cancelRequestToJoinSite(site));
        }
        catch (Exception e)
        {
            result.setException(e);
        }

        return result;
    }

    public Site getJoinSiteRequest()
    {
        return site;
    }

}
