package com.magoo.star.gre.exam.roomdb;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.magoo.star.gre.exam.TempQuestionDaoTest;
import com.magoo.star.gre.exam.chapters.ChaptersTestDao;
import com.magoo.star.gre.exam.comprehension.Paragraph;
import com.magoo.star.gre.exam.comprehension.ParagraphDao;
import com.magoo.star.gre.exam.comprehension.ParagraphQuestion;
import com.magoo.star.gre.exam.models.Chapters;
import com.magoo.star.gre.exam.models.FavouriteWords;
import com.magoo.star.gre.exam.models.Questions;
import com.magoo.star.gre.exam.models.ReportCard;
import com.magoo.star.gre.exam.models.SortingWords;
import com.magoo.star.gre.exam.models.Subject;
import com.magoo.star.gre.exam.models.TempQuestionData;
import com.magoo.star.gre.exam.models.Words;
import com.magoo.star.gre.exam.subject.SubjectTestDao;
import com.magoo.star.gre.exam.utils.Constants;


@Database(entities = {Questions.class, Subject.class, Chapters.class, TempQuestionData.class, ReportCard.class,
        Paragraph.class, ParagraphQuestion.class,
        Words.class, SortingWords.class, FavouriteWords.class}
        , version = 3)


public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase {

    public abstract QuestionsDao questionsDao();

    public abstract SubjectTestDao getSubjectTestDao();

    public abstract ChaptersTestDao getChaptersTestDao();

    public abstract TempQuestionDao getTempQuestionDao();

    public abstract ReportCardDao getReportCardDao();

    public abstract TempQuestionDaoTest getTempQuestionDaoTest();

    public abstract ParagraphDao getParagraphDao();

    public abstract ParagraphDao.ParagraphQuestionDao getParagraphQuestionDao();


    public abstract WordsDao wordsDao();

    public abstract SortingWordsDao sortingWordsDao();

    public abstract FavouriteWordsDao favouriteWordsDao();


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            database.execSQL("ALTER TABLE 'exam_subject' ADD COLUMN 'type' TEXT ");
        }
    };


    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE ` test_table_name ` (`id` INTEGER  PRIMARY KEY , " + "` word` TEXT, " + "`meaning` TEXT," + "`paragraph` TEXT )");


            database.execSQL("CREATE TABLE IF NOT EXISTS  words_table_name (id INTEGER NOT NULL, word TEXT, meaning TEXT, paragraph TEXT, PRIMARY KEY(id))");
            database.execSQL("CREATE TABLE IF NOT EXISTS sorting_words_table_name (id INTEGER NOT NULL, word TEXT, meaning TEXT, paragraph TEXT, PRIMARY KEY(id))");
            database.execSQL("CREATE TABLE IF NOT EXISTS favourtie_table_name (id INTEGER NOT NULL, word TEXT, meaning TEXT, paragraph TEXT, PRIMARY KEY(id))");

            database.execSQL("INSERT INTO words_table_name (id, word, meaning, paragraph) SELECT id, word, paragraph, meaning  FROM words_table_name");
            database.execSQL("INSERT INTO sorting_words_table_name (id, word, meaning, paragraph) SELECT id, word, paragraph, meaning  FROM sorting_words_table_name");
            database.execSQL("INSERT INTO favourtie_table_name (id, word, meaning, paragraph) SELECT id, word, paragraph, meaning  FROM favourtie_table_name");

        }
    };


    private static RoomDatabase INSTANCE;

    public static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, Constants.RoomDbTableName.DB_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
