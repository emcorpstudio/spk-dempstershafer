package emcorp.studio.pakarhnp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emcorp.studio.pakarhnp.Library.Constant;

public class DataActivity extends AppCompatActivity {
    TextView tvNama1, tvJumlah1, tvPersen1;
    TextView tvNama2, tvJumlah2, tvPersen2;
    TextView tvNama3, tvJumlah3, tvPersen3;
    TextView tvNama4, tvJumlah4, tvPersen4;
    TextView tvTotal;
    List<String> listrecid = new ArrayList<String>();
    List<String> listnama = new ArrayList<String>();
    List<String> listumur = new ArrayList<String>();
    List<String> listdiagnosa = new ArrayList<String>();
    List<String> listjumlah = new ArrayList<String>();
    List<String> listpersen = new ArrayList<String>();
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Data Diagnosa");

        tvNama1 = (TextView)findViewById(R.id.tvNama1);
        tvNama2 = (TextView)findViewById(R.id.tvNama2);
        tvNama3 = (TextView)findViewById(R.id.tvNama3);
        tvNama4 = (TextView)findViewById(R.id.tvNama4);
        tvJumlah1 = (TextView)findViewById(R.id.tvJumlah1);
        tvJumlah2 = (TextView)findViewById(R.id.tvJumlah2);
        tvJumlah3 = (TextView)findViewById(R.id.tvJumlah3);
        tvJumlah4 = (TextView)findViewById(R.id.tvJumlah4);
        tvPersen1 = (TextView)findViewById(R.id.tvPersen1);
        tvPersen2 = (TextView)findViewById(R.id.tvPersen2);
        tvPersen3 = (TextView)findViewById(R.id.tvPersen3);
        tvPersen4 = (TextView)findViewById(R.id.tvPersen4);
        tvTotal = (TextView)findViewById(R.id.tvTotal);

        LoadProcess();

    }

    public void LoadProcess(){
        progressDialog = new ProgressDialog(DataActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CETAK",response);
                        progressDialog.dismiss();
                        listrecid.clear();
                        listnama.clear();
                        listumur.clear();
                        listdiagnosa.clear();
                        listjumlah.clear();
                        listpersen.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(getApplicationContext(),"Tidak ada data", Toast.LENGTH_SHORT).show();
                            }else{
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
//                                    String recid = isiArray.getString("recid");
//                                    String nama = isiArray.getString("nama");
//                                    String umur = isiArray.getString("umur");
                                    String diagnosa = isiArray.getString("diagnosa");
                                    String jumlah = isiArray.getString("jumlah");
                                    String persen = isiArray.getString("persen");
//                                    listrecid.add(recid);
//                                    listnama.add(nama);
//                                    listumur.add(umur);
                                    listdiagnosa.add(diagnosa);
                                    listjumlah.add(jumlah);
                                    listpersen.add(persen);
                                }
                                tvNama1.setText(listdiagnosa.get(0));
                                tvNama2.setText(listdiagnosa.get(1));
                                tvNama3.setText(listdiagnosa.get(2));
                                tvNama4.setText(listdiagnosa.get(3));

                                tvJumlah1.setText(listjumlah.get(0)+" Diagnosa");
                                tvJumlah2.setText(listjumlah.get(1)+" Diagnosa");
                                tvJumlah3.setText(listjumlah.get(2)+" Diagnosa");
                                tvJumlah4.setText(listjumlah.get(3)+" Diagnosa");

                                tvPersen1.setText(listpersen.get(0)+" %");
                                tvPersen2.setText(listpersen.get(1)+" %");
                                tvPersen3.setText(listpersen.get(2)+" %");
                                tvPersen4.setText(listpersen.get(3)+" %");

                                tvTotal.setText("Total Konsultasi : "+String.valueOf(
                                        Integer.valueOf(listjumlah.get(0))+
                                        Integer.valueOf(listjumlah.get(1))+
                                        Integer.valueOf(listjumlah.get(2))+
                                        Integer.valueOf(listjumlah.get(3)))+" Diagnosa");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", Constant.FUNCTION_LISTHASIL);
                params.put("key", Constant.KEY);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(DataActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DataActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
