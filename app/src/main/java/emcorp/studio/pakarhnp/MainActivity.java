package emcorp.studio.pakarhnp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import emcorp.studio.pakarhnp.Library.ButtonClick;

public class MainActivity extends AppCompatActivity {
    ImageButton btnKonsultasi, btnData, btnTentang, btnBantuan;
    TextView tvSelengkapnya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        btnKonsultasi = (ImageButton)findViewById(R.id.btnKonsultasi);
        btnData = (ImageButton)findViewById(R.id.btnData);
        btnTentang = (ImageButton)findViewById(R.id.btnTentang);
        btnBantuan = (ImageButton)findViewById(R.id.btnBantuan);
        tvSelengkapnya = (TextView) findViewById(R.id.tvSelengkapnya);

        btnKonsultasi.setOnTouchListener(new ButtonClick());
        btnData.setOnTouchListener(new ButtonClick());
        btnTentang.setOnTouchListener(new ButtonClick());
        btnBantuan.setOnTouchListener(new ButtonClick());

        btnBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,BantuanActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,TentangActivity.class);
                startActivity(i);
                finish();
            }
        });

        tvSelengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,TentangActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,DataActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnKonsultasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,KonsultasiActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
