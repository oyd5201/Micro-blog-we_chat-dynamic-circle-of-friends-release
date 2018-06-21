package com.example.oyd.textcirle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.oyd.textcirle.adapter.AdapterViewHolder;
import com.example.oyd.textcirle.adapter.RecyclerViewAdapter;
import com.example.oyd.textcirle.model.People;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    private RecyclerViewAdapter mArticleAdapter;
    private RecyclerView mRecyclerArticle;

    private void assignViews() {
        mRecyclerArticle = (RecyclerView) findViewById(R.id.recycler_article);
    }
    


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        assignViews();
        initIncomeRecyclerView();
        loadDate();
    }


    private void initIncomeRecyclerView() {
        mRecyclerArticle.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerArticle.setItemAnimator(null);
        mArticleAdapter = new RecyclerViewAdapter<People>(R.layout.item_recyle) {
            @Override
            public void convert(People model, AdapterViewHolder holder, int position) {
                final TextView textView = holder.getView(R.id.tv_name);
                RelativeLayout relativeLayout = holder.getView(R.id.rl_item);
                holder.setText(R.id.tv_name, model.getName());
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = ArticleActivity.this.getIntent();
                        Bundle bundle = intent.getExtras();
                        bundle.putString("article", textView.getText().toString());//添加要返回给页面1的数据
                        intent.putExtras(bundle);
                        ArticleActivity.this.setResult(Activity.RESULT_OK, intent);//返回页面1
                        ArticleActivity.this.finish();
                    }
                });
            }
        };
        mRecyclerArticle.setAdapter(mArticleAdapter);
    }

    private void loadDate() {
        People people = new People();
        people.setName("文章一");
        People people1 = new People();
        people1.setName("文章二");
        People people2 = new People();
        people2.setName("文章三");


        List<People> tempList = new ArrayList<>();
        tempList.add(people);
        tempList.add(people1);
        tempList.add(people2);
        mArticleAdapter.replaceAll(tempList);
    }

}
