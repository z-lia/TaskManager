package com.example.taskmanager.model.database;

public class TaskDataBaseSchema {
    public static final String NAME ="taskDatabase.db";

    public static final class TaskTable {
        public static final String NAME = "Task";
        public static final class Columns {

            public static final String ID = "_id";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";



        }
    }
}
