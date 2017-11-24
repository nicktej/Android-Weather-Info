package hu.ait.demos.weatherinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.UUID;

import hu.ait.demos.weatherinfo.data.Item;
import hu.ait.demos.weatherinfo.data.Weather;
import hu.ait.demos.weatherinfo.data.WeatherResult;
import hu.ait.demos.weatherinfo.network.WeatherAPI;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CreateWeatherActivity extends AppCompatActivity {
    public static final String KEY_ITEM = "KEY_ITEM";
    public static final String API_KEY = "9531e35f3aba37054a24263e1e4dadac";
    public static final String TOTAL = "TOTAL";
    private EditText etName;
    private Item itemToEdit = null;
    public int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(hu.ait.demos.weatherinfo.R.layout.activity_create_item);

        setupUI();

        if (getIntent().getSerializableExtra(MainActivity.KEY_EDIT) != null) {
            initEdit();
        } else {
            initCreate();
        }
    }

    private void initCreate() {
        getRealm().beginTransaction();
        itemToEdit = getRealm().createObject(Item.class, UUID.randomUUID().toString());
        getRealm().commitTransaction();
    }

    private void initEdit() {
        String itemID = getIntent().getStringExtra(MainActivity.KEY_EDIT);
        itemToEdit = getRealm().where(Item.class)
                .equalTo("itemID", itemID)
                .findFirst();

        etName.setText(itemToEdit.getCity());
    }

    private void setupUI() {
        etName = (EditText) findViewById(hu.ait.demos.weatherinfo.R.id.etName);
        Button btnSave = (Button) findViewById(hu.ait.demos.weatherinfo.R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });
    }

    public Realm getRealm() {
        return ((MainApplication) getApplication()).getRealmWeather();
    }

    private void saveItem() {

        if (!TextUtils.isEmpty(etName.getText())) {

            Intent intentResult = new Intent();
            getRealm().beginTransaction();
            itemToEdit.setCity(etName.getText().toString());
            getRealm().commitTransaction();
            intentResult.putExtra(KEY_ITEM, itemToEdit.getItemID());
            setResult(RESULT_OK, intentResult);
            finish();

        } else {

            if (TextUtils.isEmpty(etName.getText())) {
                etName.setError("Error! Try again");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TOTAL, total);
    }
}