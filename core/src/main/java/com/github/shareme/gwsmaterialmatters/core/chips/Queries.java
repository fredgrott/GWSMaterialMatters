/*
 * Copyright (C) 2012 The Android Open Source Project
 * Modifications Copyright (C) 2016 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.shareme.gwsmaterialmatters.core.chips;

import android.content.res.Resources;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Phone and Email queries for supporting Chips UI.
 * Created by fgrott on 6/19/2016.
 */
/* package */ class Queries {

  public static final Query PHONE = new Query(new String[] {
          ContactsContract.Contacts.DISPLAY_NAME,                          // 0
          ContactsContract.CommonDataKinds.Phone.NUMBER,                                   // 1
          ContactsContract.CommonDataKinds.Phone.TYPE,                                     // 2
          ContactsContract.CommonDataKinds.Phone.LABEL,                                    // 3
          ContactsContract.CommonDataKinds.Phone.CONTACT_ID,                               // 4
          ContactsContract.CommonDataKinds.Phone._ID,                                      // 5
          ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,                   // 6
          ContactsContract.Contacts.DISPLAY_NAME_SOURCE,                   // 7
          ContactsContract.Contacts.LOOKUP_KEY,                            // 8
          ContactsContract.CommonDataKinds.Email.MIMETYPE // 9
  }, ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, ContactsContract.CommonDataKinds.Phone.CONTENT_URI) {

    @Override
    public CharSequence getTypeLabel(Resources res, int type, CharSequence label) {
      return ContactsContract.CommonDataKinds.Phone.getTypeLabel(res, type, label);
    }

  };

  public static final Query EMAIL = new Query(new String[]{
          ContactsContract.Contacts.DISPLAY_NAME,                          // 0
          ContactsContract.CommonDataKinds.Email.DATA,                                     // 1
          ContactsContract.CommonDataKinds.Email.TYPE,                                     // 2
          ContactsContract.CommonDataKinds.Email.LABEL,                                    // 3
          ContactsContract.CommonDataKinds.Email.CONTACT_ID,                               // 4
          ContactsContract.CommonDataKinds.Email._ID,                                      // 5
          ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,                   // 6
          ContactsContract.Contacts.DISPLAY_NAME_SOURCE,                   // 7
          ContactsContract.Contacts.LOOKUP_KEY,                            // 8
          ContactsContract.CommonDataKinds.Email.MIMETYPE // 9
  }, ContactsContract.CommonDataKinds.Email.CONTENT_FILTER_URI, ContactsContract.CommonDataKinds.Email.CONTENT_URI) {

    @Override
    public CharSequence getTypeLabel(Resources res, int type, CharSequence label) {
      return ContactsContract.CommonDataKinds.Email.getTypeLabel(res, type, label);
    }

  };

  static abstract class Query {
    private final String[] mProjection;
    private final Uri mContentFilterUri;
    private final Uri mContentUri;

    public static final int NAME = 0;                // String
    public static final int DESTINATION = 1;         // String
    public static final int DESTINATION_TYPE = 2;    // int
    public static final int DESTINATION_LABEL = 3;   // String
    public static final int CONTACT_ID = 4;          // long
    public static final int DATA_ID = 5;             // long
    public static final int PHOTO_THUMBNAIL_URI = 6; // String
    public static final int DISPLAY_NAME_SOURCE = 7; // int
    public static final int LOOKUP_KEY = 8;          // String
    public static final int MIME_TYPE = 9;           // String

    public Query(String[] projection, Uri contentFilter, Uri content) {
      mProjection = projection;
      mContentFilterUri = contentFilter;
      mContentUri = content;
    }

    public String[] getProjection() {
      return mProjection;
    }

    public Uri getContentFilterUri() {
      return mContentFilterUri;
    }

    public Uri getContentUri() {
      return mContentUri;
    }

    public abstract CharSequence getTypeLabel(Resources res, int type, CharSequence label);
  }
}
