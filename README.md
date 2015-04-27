# android-contentresolver-notification-test
A test for android contentresolver notifications
================================================

It's not always super obvious what the different parameters to functions do, even if there is ample documentation. One such case is "notifyForDescendents" when registering Android ContentObservers.

The registerContentObserver method in the ContentResolver API:
http://developer.android.com/reference/android/content/ContentResolver.html#registerContentObserver(android.net.Uri,%20boolean,%20android.database.ContentObserver)

Here is the output of some test code I wrote for testing what different types of notifications actually do, in combination with notifyForDescendents


First test

Notifying on content://base.uri

    content://base.uri onChange, notifyForDescendents=false
    content://base.uri onChange, notifyForDescendents=true
    content://base.uri/a/sub/uri onChange, notifyForDescendents=false
    content://base.uri/a/sub/uri onChange, notifyForDescendents=true
    content://base.uri/a/sub/uri/sub/uri onChange, notifyForDescendents=false
    content://base.uri/a/sub/uri/sub/uri onChange, notifyForDescendents=true

Second test

Notifying on content://base.uri/a/sub/uri

    content://base.uri onChange, notifyForDescendents=true
    content://base.uri/a/sub/uri onChange, notifyForDescendents=false
    content://base.uri/a/sub/uri onChange, notifyForDescendents=true
    content://base.uri/a/sub/uri/sub/uri onChange, notifyForDescendents=false
    content://base.uri/a/sub/uri/sub/uri onChange, notifyForDescendents=true

Third test

Notifying on content://base.uri/a/sub/uri/sub/uri

    content://base.uri onChange, notifyForDescendents=true
    content://base.uri/a/sub/uri onChange, notifyForDescendents=true
    content://base.uri/a/sub/uri/sub/uri onChange, notifyForDescendents=false
    content://base.uri/a/sub/uri/sub/uri onChange, notifyForDescendents=true

End test


The conclusions we can draw are:

Notifying on a shorter (base or super) Uri will always generate a notification for a Uri with the same, or longer (sub) Uri.

Notifying on a longer (sub) Uri will only generate a notification for a shorter (base or super) Uri, if notifyForDescendents=true
