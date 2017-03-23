package com.example.dell.escaperoom.Logic;

/**
 * Created by yaelgersh on 21/03/2017.
 */

public class Question {

    private String question ;
    private String a1;
    private String a2;
    private String a3;
    private String a4;

    private int answer;

    public String getQuestion() {
        return question;
    }

    public String getA1() {
        return a1;
    }

    public String getA2() {
        return a2;
    }

    public String getA3() {
        return a3;
    }

    public String getA4() {
        return a4;
    }

    public int getAnswer() {
        return answer;
    }
}

/*
class QuestionsList extends ArrayAdapter<Question>{
    private Context context;
    private List<Question> questionList;

    public QuestionsList(Context context, List<Question> questionList) {
        super(context, R.layout.activity_hint, questionList);
        this.context = context;
        this.questionList = questionList;
    }


    public Question getQuestion (int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // LayoutInflater inflater = context.getLayoutInflater();

        //View ListView = inflater.inflate(R.layout.activity_hint, null, true);

        //String temp =
        Question question = questionList.get(position);

        return question;
    }
}*/
