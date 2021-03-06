package uncmn.eve.store.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import rx.schedulers.Schedulers;
import uncmn.eve.Converter;
import uncmn.eve.Store;
import uncmn.eve.Value;

/**
 * An implementation of {@link Store} with Sqlite operations.
 */
public class SqlStore extends Store {

  BriteDatabase db;

  SqlStore(Context context, Converter converter) {
    super(converter);

    SqlStoreOpenHelper openHelper = new SqlStoreOpenHelper(context);

    db = SqlBrite.create(new SqlBrite.Logger() {
      @Override public void log(String message) {

      }
    }).wrapDatabaseHelper(openHelper, Schedulers.io());
  }

  public static SqlStore create(Context context, Converter converter) {
    return new SqlStore(context, converter);
  }

  @Override public void set(String key, Value value) {
    ValueQuery query = ValueQuery.queryBuilder().key(key);

    final String sql = query.sql();
    final String[] args = query.args();

    Cursor cursor = db.query(sql, args);
    ContentValues contentValues = ValueQuery.contentValues(key, value);
    try {
      if (cursor != null && cursor.getCount() > 0) {
        db.update(ValueQuery.TABLE, contentValues, SQLiteDatabase.CONFLICT_REPLACE,
            ValueQuery.WHERE_KEY, args);
      } else {
        db.insert(ValueQuery.TABLE, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
  }

  @Override @SuppressWarnings("unchecked") public <T> T get(String key) {
    ValueQuery query = ValueQuery.queryBuilder().key(key);
    final String sql = query.sql();
    final String[] args = query.args();

    Cursor cursor = db.query(sql, args);
    try {
      if (cursor != null && cursor.getCount() > 0) {
        cursor.moveToFirst();
        String type = cursor.getString(cursor.getColumnIndexOrThrow(ValueQuery.TYPE));
        byte[] value = cursor.getBlob(cursor.getColumnIndexOrThrow(ValueQuery.VALUE));
        return (T) convert(value, type);
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }

    return null;
  }

  @Override public boolean delete(String key) {
    ValueQuery query = ValueQuery.queryBuilder().key(key);

    final String sql = query.sql();
    final String[] args = query.args();
    Cursor cursor = db.query(sql, args);
    try {
      if (cursor != null && cursor.getCount() > 0) {
        db.delete(ValueQuery.TABLE, ValueQuery.WHERE_KEY, args);
        return true;
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
    return false;
  }

  @Override public boolean exists(String key) {
    ValueQuery query = ValueQuery.queryBuilder().key(key);

    final String sql = query.sql();
    final String[] args = query.args();
    Cursor cursor = db.query(sql, args);

    try {
      if (cursor != null && cursor.getCount() > 0) {
        cursor.moveToFirst();
        return true;
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
    return false;
  }
}
