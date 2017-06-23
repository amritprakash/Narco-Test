package co.amrit.testapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hp on 20-05-2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Narco.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QuestionsContract.SQL_CREATE_TABLE);
        initializeQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(DBHelper.class.getSimpleName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion);
    }

    private void initializeQuestions(SQLiteDatabase db) {
        ContentValues question1 = new ContentValues();
        question1.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_QUESTION, "Do you think you're attractive?");
        question1.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_RIGHT_ANSWER, "Yes");

        ContentValues question2 = new ContentValues();
        question2.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_QUESTION, "Do you think ghosts are real?");
        question2.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_RIGHT_ANSWER, "No");

        ContentValues question3 = new ContentValues();
        question3.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_QUESTION, "What is the weirdest nickname people call you?");
        question3.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_RIGHT_ANSWER, "Gandhi");

        ContentValues question4 = new ContentValues();
        question4.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_QUESTION, "What is your best talent?");
        question4.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_RIGHT_ANSWER, "Lecturing");

        ContentValues question5 = new ContentValues();
        question5.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_QUESTION, "Would you miss a sports game for me?");
        question5.put(QuestionsContract.QuestionsEntry.COLUMN_NAME_RIGHT_ANSWER, "Yes");

        db.insert(QuestionsContract.QuestionsEntry.TABLE_NAME, null, question1);
        db.insert(QuestionsContract.QuestionsEntry.TABLE_NAME, null, question2);
        db.insert(QuestionsContract.QuestionsEntry.TABLE_NAME, null, question3);
        db.insert(QuestionsContract.QuestionsEntry.TABLE_NAME, null, question4);
        db.insert(QuestionsContract.QuestionsEntry.TABLE_NAME, null, question5);
    }

    public ArrayList<Question> getQuestions() {

        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                QuestionsContract.QuestionsEntry._ID,
                QuestionsContract.QuestionsEntry.COLUMN_NAME_QUESTION,
                QuestionsContract.QuestionsEntry.COLUMN_NAME_RIGHT_ANSWER,
                QuestionsContract.QuestionsEntry.COLUMN_NAME_USER_ANSWER,
                QuestionsContract.QuestionsEntry.COLUMN_NAME_USER_ANSWER_COUNT,
                QuestionsContract.QuestionsEntry.COLUMN_NAME_CREATED_DATE,
                QuestionsContract.QuestionsEntry.COLUMN_NAME_UPDATED_DATE,
                QuestionsContract.QuestionsEntry.COLUMN_NAME_ABSOLETE_FLAG
        };

        // Filter results WHERE Clause
        String selection = QuestionsContract.QuestionsEntry.COLUMN_NAME_ABSOLETE_FLAG + " = ?";
        String[] selectionArgs = { "0" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = QuestionsContract.QuestionsEntry._ID+ " DESC";

        Cursor cursor = db.query(
                QuestionsContract.QuestionsEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList questions = new ArrayList<Question>();
        while (cursor.moveToNext()) {
            // Get the value for each column for the database row pointed by
            // the cursor using the getColumnIndex method of the cursor and
            // use it to initialize a Project object by database row
            Question que = new Question();

            int idColIndex = cursor.getColumnIndex(QuestionsContract.QuestionsEntry._ID);
            long projectId = cursor.getLong(idColIndex);
            que.setId(cursor.getLong(cursor.getColumnIndex(QuestionsContract.QuestionsEntry._ID)));
            que.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionsContract.QuestionsEntry.COLUMN_NAME_QUESTION)));
            que.setRight_answer(cursor.getString(cursor.getColumnIndex(QuestionsContract.QuestionsEntry.COLUMN_NAME_RIGHT_ANSWER)));
            que.setUser_answer(cursor.getString(cursor.getColumnIndex(QuestionsContract.QuestionsEntry.COLUMN_NAME_USER_ANSWER)));
            que.setUser_answer_count(cursor.getInt(cursor.getColumnIndex(QuestionsContract.QuestionsEntry.COLUMN_NAME_USER_ANSWER_COUNT)));
            String createdDate = cursor.getString(cursor.getColumnIndex(QuestionsContract.QuestionsEntry.COLUMN_NAME_CREATED_DATE));
            String updatedDate = cursor.getString(cursor.getColumnIndex(QuestionsContract.QuestionsEntry.COLUMN_NAME_UPDATED_DATE));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                if(null != createdDate){
                    que.setCreated_date(sdf.parse(createdDate));
                }
                if(null != updatedDate){
                    que.setUpdated_date(sdf.parse(updatedDate));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            que.setAbsolete_flag(cursor.getInt(cursor.getColumnIndex(QuestionsContract.QuestionsEntry.COLUMN_NAME_ABSOLETE_FLAG)));
            questions.add(que);
        }

        cursor.close();
        db.close();
        return (questions);
    }


}