package ilijan.hotmath;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRAS_URL = "URL";
    public static final String EXTRAS_TITLE = "TITLE";

    StackExchangeService stackExchangeService;
    QuestionsAdapter questionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/2.2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        stackExchangeService = retrofit
                .create(StackExchangeService.class);

        questionsAdapter =
                new QuestionsAdapter(MainActivity.this, R.layout.questions_list_row,
                        new ArrayList<Item>());

        initRefreshButton();
        initQuestionsList();
    }

    private void initQuestionsList() {
        ListView questionsList = (ListView) findViewById(R.id.questions_list);

        questionsList.setAdapter(questionsAdapter);
        questionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item clickedItem = questionsAdapter.getItem(position);
                String url = clickedItem.getLink();
                Intent i = new Intent(MainActivity.this, QuestionActivity.class);
                i.putExtra(EXTRAS_URL, url);
                i.putExtra(EXTRAS_TITLE, clickedItem.getTitle());
                startActivity(i);
            }
        });

        loadQuestionsList();
    }

    private void initRefreshButton() {
        Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionsAdapter.clear();
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                loadQuestionsList();
            }
        });
    }

    private void loadQuestionsList() {
        Call<Wrapper> call = stackExchangeService.listQuestions();
        call.enqueue(new Callback<Wrapper>() {
            @Override
            public void onResponse(Response<Wrapper> response) {
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                List<Item> items = response.body().getItems();
                questionsAdapter.addAll(items);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
}
