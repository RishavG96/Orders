package hera.com.orders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ArticleActivity extends AppCompatActivity {

    public static String article_url="http://192.168.111.15:8081/Euro99NarudzbeBack/resources/protected/artikli";
    hera.com.orders.infrastructure.service.Article service_article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        service_article = new hera.com.orders.infrastructure.service.Article();
        service_article.connect(this);
    }
}
