package com.ua.project.android_homework_writingcontacts.helpers;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.ua.project.android_homework_writingcontacts.models.Contact;

import java.util.ArrayList;

import lombok.RequiredArgsConstructor;

public class ContactHelper {

//    public static void deleteAll(Activity activity) {
//        new AlertDialog.Builder(activity)
//                .setTitle("Delete all contacts?")
//                .setMessage("Are you sure?")
//                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
//                    activity.getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, null, null);
//                    Toast.makeText(activity, "All contacts data wiped.", Toast.LENGTH_SHORT).show();
//                })
//                .setNegativeButton(android.R.string.no, null)
//                .show();
//    }

    public static void add(Activity activity, @NonNull Contact contact) {
        ArrayList<ContentProviderOperation> providerOperations = new ArrayList<>();

        providerOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        );

        providerOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.getFirstName())
                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contact.getLastName())
                .build()
        );

        providerOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getMobilePhoneNumber())
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build()
        );

        providerOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getWorkPhoneNumber())
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                .build()
        );

        providerOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, contact.getEmailAddress())
                .build()
        );

        providerOperations.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Event.START_DATE, contact.getBirthDate())
                .withValue(ContactsContract.CommonDataKinds.Event.TYPE, ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY)
                .build()
        );

        try {
            activity.getContentResolver().applyBatch(ContactsContract.AUTHORITY, providerOperations);
        }
        catch (Exception e) {
            Log.e("TAG", "add: ", e);
        }
    }
}
