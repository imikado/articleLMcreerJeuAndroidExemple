package mika.dupot.articlelmminijeu;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {


    //A ECRIRE DANS ARTICLE
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new GameDisplay(this));
    }
}
