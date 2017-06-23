package co.amrit.testapp.db;

import android.provider.BaseColumns;

/**
 * Created by hp on 20-05-2017.
 */

public final class QuestionsContract {

    private QuestionsContract() {
    }
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + QuestionsEntry.TABLE_NAME + " (" +
                    QuestionsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    QuestionsEntry.COLUMN_NAME_QUESTION + " TEXT NOT NULL," +
                    QuestionsEntry.COLUMN_NAME_RIGHT_ANSWER + " TEXT," +
                    QuestionsEntry.COLUMN_NAME_USER_ANSWER + " TEXT," +
                    QuestionsEntry.COLUMN_NAME_USER_ANSWER_COUNT + " INTEGER DEFAULT 0," +
                    QuestionsEntry.COLUMN_NAME_CREATED_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    QuestionsEntry.COLUMN_NAME_UPDATED_DATE + " DATETIME," +
                    QuestionsEntry.COLUMN_NAME_ABSOLETE_FLAG + " INTEGER DEFAULT 0" + ")";


    public static class QuestionsEntry implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_RIGHT_ANSWER = "right_answer";
        public static final String COLUMN_NAME_USER_ANSWER = "user_answer";
        public static final String COLUMN_NAME_USER_ANSWER_COUNT = "user_answer_count";
        public static final String COLUMN_NAME_CREATED_DATE = "created_date";
        public static final String COLUMN_NAME_UPDATED_DATE = "updated_date";
        public static final String COLUMN_NAME_ABSOLETE_FLAG = "absolete_flag";
    }
}
