package co.amrit.testapp.db;

import java.util.Date;

/**
 * Created by hp on 20-05-2017.
 */

public class Question {
    private long id;
    private String question;
    private String right_answer;
    private String user_answer;
    private int user_answer_count;
    private Date created_date;
    private Date updated_date;
    private int absolete_flag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public int getUser_answer_count() {
        return user_answer_count;
    }

    public void setUser_answer_count(int user_answer_count) {
        this.user_answer_count = user_answer_count;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public int getAbsolete_flag() {
        return absolete_flag;
    }

    public void setAbsolete_flag(int absolete_flag) {
        this.absolete_flag = absolete_flag;
    }
}