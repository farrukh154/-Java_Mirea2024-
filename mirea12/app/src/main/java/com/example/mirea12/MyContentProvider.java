package com.example.mirea12;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DatabaseHelper.TABLE_NAME);

    private static final int ITEMS = 1;
    private static final int ITEM_ID = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, DatabaseHelper.TABLE_NAME, ITEMS);
        uriMatcher.addURI(AUTHORITY, DatabaseHelper.TABLE_NAME + "/#", ITEM_ID);
    }

    private DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final int match = uriMatcher.match(uri);
        if (match == ITEMS) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long id = db.insert(DatabaseHelper.TABLE_NAME, null, values);
            if (id > 0) {
                Uri returnedUri = ContentUris.withAppendedId(CONTENT_URI, id);
                getContext().getContentResolver().notifyChange(returnedUri, null);
                return returnedUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                cursor = db.query(DatabaseHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ITEM_ID:
                selection = DatabaseHelper.COLUMN_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(DatabaseHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                count = db.delete(DatabaseHelper.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEM_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + "=" + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                count = db.update(DatabaseHelper.TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEM_ID:
                String id = uri.getPathSegments().get(1);
                count = db.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + "=" + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + DatabaseHelper.TABLE_NAME;
            case ITEM_ID:
                return "vnd.android.cursor.item/vnd." + AUTHORITY + "." + DatabaseHelper.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}