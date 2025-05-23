package io.github.FarmLife.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import io.github.FarmLife.Main;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        AndroidDatabaseLogik DatabaseService = new AndroidDatabaseLogik(this);
        initialize(new Main(DatabaseService), configuration);
    }
}
