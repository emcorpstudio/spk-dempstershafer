package emcorp.studio.pakarhnp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    Button btnHome, btnUlang;
    TextView tvPersen, tvGejala, tvRekomendasi, tvPenyakit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        btnHome = (Button)findViewById(R.id.btnHome);
        btnUlang = (Button)findViewById(R.id.btnUlang);
        tvPersen = (TextView)findViewById(R.id.tvPersen);
        tvGejala = (TextView)findViewById(R.id.tvGejala);
        tvPenyakit = (TextView)findViewById(R.id.tvPenyakit);
        tvRekomendasi = (TextView)findViewById(R.id.tvRekomendasi);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            tvPersen.setText((String.format("%.2f", Double.valueOf(extras.getString("persen"))))+" %");
            tvPenyakit.setText(extras.getString("penyakit"));
            tvGejala.setText("Gejala :\n"+extras.getString("gejala").substring(1,extras.getString("gejala").length()-1));
        }
        String rekomendasi = "";
        switch (extras.getString("penyakit")){
            case "HNP Lumbal":
                rekomendasi = "- Lakukan olahraga renang gaya dada secara teratur\n" +
                        "- Hindari berdiri atau duduk terlalu lama.\n" +
                        "- Konsumsi obat anti nyeri ringan seperti ibuprofen atau paracetamol\n" +
                        "- Lakukan fisioterapi bagian punggung\n" +
                        "- Perbanyak istirahat (bed rest)\n" +
                        "- Gunakan penyangga punggung (lumbar support)\n" +
                        "- Menjaga postur tubuh";
                break;
            case "HNP Servikal":
                rekomendasi = "- Aktivitas menggerakkan kepala\n" +
                        "- Hindari berdiri atau duduk terlalu lama\n" +
                        "- Konsumsi obat anti nyeri ringan seperti ibuprofen atau paracetamol\n" +
                        "- Lakukan fisioterapi bagian leher\n" +
                        "- Perbanyak istirahat\n" +
                        "- Gunakan penyangga leher (cervical collar)\n" +
                        "- Menjaga postur tubuh";
                break;
            case "Nyeri Leher Biasa":
                rekomendasi = "- Lakukan peregangan punggung dan pinggang\n" +
                        "- Lakukan pemijatan di daerah punggung\n" +
                        "- Berendam dalam air hangat\n" +
                        "- Oleskan minyak, balsem, atau salep hangat untuk relaksasi otot";
                break;
            case "Nyeri Punggung Biasa":
                rekomendasi = "- Lakukan peregangan leher\n" +
                        "- Lakukan pemijatan di daerah leher dan bahu\n" +
                        "- Oleskan minyak, balsem, atau salep hangat untuk relaksasi otot\n" +
                        "- Kompres Leher Dengan Air Hangat";
                break;
        }
        tvRekomendasi.setText("Rekomendasi :\n\n"+rekomendasi);
        btnUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this,KonsultasiActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
}
