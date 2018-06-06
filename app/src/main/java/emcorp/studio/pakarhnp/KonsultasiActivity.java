package emcorp.studio.pakarhnp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import emcorp.studio.pakarhnp.Library.Constant;
import emcorp.studio.pakarhnp.Library.ListViewItemCheckboxBaseAdapter;
import emcorp.studio.pakarhnp.Library.ListViewItemDTO;

public class KonsultasiActivity extends AppCompatActivity {
    List<String> listkode = new ArrayList<String>();
    List<String> listgejala = new ArrayList<String>();
    List<String> listP001 = new ArrayList<String>();
    List<String> listP002 = new ArrayList<String>();
    List<String> listP003 = new ArrayList<String>();
    List<String> listP004 = new ArrayList<String>();
    List<String> listdensitas = new ArrayList<String>();
    List<String> listplausibility = new ArrayList<String>();
    private ProgressDialog progressDialog;
    ListView listViewWithCheckbox;
    List<ListViewItemDTO> initItemList;
    Button btnDiagnosa;

    String[][] dataGejala;
    String gejalaSekarang = "";//Tampilan Gejala Sekarang
    List<Double> listMValueSebelumnya = new ArrayList<Double>();
    List<String> listMTextSebelumnya = new ArrayList<String>();
    int soalKe = 0;
    int gejalaKe = 0;
    DecimalFormat df;
    Double maxValue;
    Double presentase;
    Double sumTeta = 0.0;
    String hasilPenyakit = "", hasilPersen = "", hasilGejala = "";
    EditText edtNama, edtUmur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsultasi);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Konsultasi");

        listViewWithCheckbox = (ListView)findViewById(R.id.listView);
        btnDiagnosa = (Button)findViewById(R.id.btnDiagnosis);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtUmur = (EditText) findViewById(R.id.edtUmur);

        btnDiagnosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hasilGejala = "-";
                int jml = 0;
                for(int i=0;i<listgejala.size();i++){
                    if(dataGejala[i][8].toString().equals("1")){
                        hasilGejala = hasilGejala + "\n- " + dataGejala[i][1];
                        jml = jml + 1;
                    }
                    Log.d("GEJALA",String.valueOf(i)+" - "+dataGejala[i][8]);
                }
                if(edtNama.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Nama harus diisi",Toast.LENGTH_SHORT).show();
                    edtNama.requestFocus();
                }else{
                    if(edtUmur.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Umur harus diisi",Toast.LENGTH_SHORT).show();
                        edtUmur.requestFocus();
                    }else{
                        if(jml<=1){
                            Toast.makeText(getApplicationContext(),"Gejala harus dipilih lebih dari 1",Toast.LENGTH_SHORT).show();
                            listViewWithCheckbox.requestFocus();
                        }else{
                            cekJawaban();
                        }
                    }
                }
//                cekJawaban();

            }
        });
        LoadProcess();
    }

    private List<ListViewItemDTO> getInitViewItemDtoList()
    {
        List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();

        int length = listkode.size();

        for(int i=0;i<length;i++)
        {
            String itemText = listgejala.get(i);

            ListViewItemDTO dto = new ListViewItemDTO();
            dto.setChecked(false);
            dto.setItemText(itemText);

            ret.add(dto);
        }

        return ret;
    }

    public void LoadProcess(){
        progressDialog = new ProgressDialog(KonsultasiActivity.this);
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
                        listkode.clear();
                        listgejala.clear();
                        listP001.clear();
                        listP002.clear();
                        listP003.clear();
                        listP004.clear();
                        listdensitas.clear();
                        listplausibility.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(getApplicationContext(),"Tidak ada data", Toast.LENGTH_SHORT).show();
                                listViewWithCheckbox.setVisibility(View.INVISIBLE);
                            }else{
                                listViewWithCheckbox.setVisibility(View.VISIBLE);
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String kode = isiArray.getString("kode");
                                    String gejala = isiArray.getString("gejala");
                                    String P001 = isiArray.getString("P001");
                                    String P002 = isiArray.getString("P002");
                                    String P003 = isiArray.getString("P003");
                                    String P004 = isiArray.getString("P004");
                                    String densitas = isiArray.getString("densitas");
                                    String plausibility = isiArray.getString("plausibility");
                                    listkode.add(kode);
                                    listgejala.add(gejala);
                                    listP001.add(P001);
                                    listP002.add(P002);
                                    listP003.add(P003);
                                    listP004.add(P004);
                                    listdensitas.add(densitas);
                                    listplausibility.add(plausibility);
                                }
                                getAlllData();
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
                params.put("function", Constant.FUNCTION_LISTDATA);
                params.put("key", Constant.KEY);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getAlllData(){
        dataGejala = new String[listgejala.size()][9];
        for(int i=0;i<listgejala.size();i++){
            dataGejala[i][0] = listkode.get(i);
            dataGejala[i][1] = listgejala.get(i);
            dataGejala[i][2] = listP001.get(i);
            dataGejala[i][3] = listP002.get(i);
            dataGejala[i][4] = listP003.get(i);
            dataGejala[i][5] = listP004.get(i);
            dataGejala[i][6] = listdensitas.get(i);
            dataGejala[i][7] = listplausibility.get(i);
            dataGejala[i][8] = "";
        }

        initItemList = getInitViewItemDtoList();
        ListViewItemCheckboxBaseAdapter listViewDataAdapter = new ListViewItemCheckboxBaseAdapter(getApplicationContext(), initItemList);
        listViewDataAdapter.notifyDataSetChanged();

        // Set data adapter to list view.
        listViewWithCheckbox.setAdapter(listViewDataAdapter);

        listViewWithCheckbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                // Translate the selected item to DTO object.
                ListViewItemDTO itemDto = (ListViewItemDTO)itemObject;

                // Get the checkbox.
                CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.list_view_item_checkbox);

                // Reverse the checkbox and clicked item check state.
                if(itemDto.isChecked())
                {
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);
                    dataGejala[itemIndex][8] = "";
                }else
                {
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                    dataGejala[itemIndex][8] = "1";
                }

                //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cekJawaban(){
        hasilPenyakit = "";
        hasilPersen = "";
        gejalaKe = 0;
        for(int i=0;i<dataGejala.length;i++){
            if(dataGejala[i][8].equals("1")){
                List<Double> listMValuePerkalian = new ArrayList<Double>();
                List<String> listMTextPerkalian = new ArrayList<String>();
                gejalaKe = gejalaKe + 1;
                sumTeta = 0.0;
                if(gejalaKe==1){
                    double mPenyakit = Double.valueOf(dataGejala[i][6]);
                    double mTeta = 1 - mPenyakit;
                    listMValueSebelumnya.add(mPenyakit);
                    listMTextSebelumnya.add(cekPenyakit(i));
                    listMValueSebelumnya.add(mTeta);
                    listMTextSebelumnya.add("Teta");
                    for(int j=0;j<listMValueSebelumnya.size();j++){
                        double mValueSebelumnya = Double.valueOf(listMValueSebelumnya.get(j));
                        String mTextSebelumnya = listMTextSebelumnya.get(j);
                        System.out.println("Gejala ke-"+gejalaKe+" ("+mTextSebelumnya+" "+mValueSebelumnya+")");
                    }
                }else if(gejalaKe>1){
                    //Kolom Penyakit
                    for(int j=0;j<listMValueSebelumnya.size();j++){
                        String mPenyakitText = cekPenyakit(i);
                        double mPenyakit = Double.valueOf(dataGejala[i][6]);
                        double mValueSebelumnya = Double.valueOf(listMValueSebelumnya.get(j));
                        String mTextSebelumnya = listMTextSebelumnya.get(j);
                        double hasilPerkalian = mValueSebelumnya * mPenyakit;
                        listMValuePerkalian.add(hasilPerkalian);
                        listMTextPerkalian.add(cekSameStringPenyakit(mPenyakitText,mTextSebelumnya));
                        if(cekSameStringPenyakit(mPenyakitText,mTextSebelumnya).equals("Teta")){
                            sumTeta = sumTeta + hasilPerkalian;
                        }
                        System.out.println("Gejala ke-"+gejalaKe+" "+mPenyakitText+" X "+mTextSebelumnya+" -> "+cekSameStringPenyakit(mPenyakitText,mTextSebelumnya)+" = "+hasilPerkalian);
                    }
                    //Kolom Teta
                    for(int j=0;j<listMValueSebelumnya.size();j++){
                        double mPenyakit = Double.valueOf(dataGejala[i][6]);
                        double mTeta = 1 - mPenyakit;
                        double mValueSebelumnya = Double.valueOf(listMValueSebelumnya.get(j));
                        String mTextSebelumnya = listMTextSebelumnya.get(j);
                        double hasilPerkalian = mValueSebelumnya * mTeta;
                        listMValuePerkalian.add(hasilPerkalian);
                        listMTextPerkalian.add(mTextSebelumnya);
                        System.out.println("Gejala ke-"+gejalaKe+" "+mTextSebelumnya+" = "+hasilPerkalian);
                    }
                    cekMax(listMTextPerkalian,listMValuePerkalian);
                }
            }
        }
        AddData();
//        System.out.println("Max Final "+namaPenyakit(hasilPenyakit)+" - "+hasilPersen+" %");
//        Toast.makeText(getApplicationContext(),namaPenyakit(hasilPenyakit)+" - "+hasilPersen+" %",Toast.LENGTH_LONG).show();
    }

    public void cekMax(List<String>arr1, List<Double>arr2){
        List<String> listTextUnik = new ArrayList<String>(arr1);
        List<Double> listValueUnik = new ArrayList<Double>();
        double tetaValue = 0;
        Object[] st = listTextUnik.toArray();
        for (Object s : st) {
            if (listTextUnik.indexOf(s) != listTextUnik.lastIndexOf(s)) {
                listTextUnik.remove(listTextUnik.lastIndexOf(s));
            }
        }
        //SUM
        for(int i=0;i<listTextUnik.size();i++){
            double sum = 0;
            for(int j=0;j<arr1.size();j++){
                if(listTextUnik.get(i).equals(arr1.get(j))){
                    sum = sum + arr2.get(j);
                }
            }
            listValueUnik.add(sum);
        }

        //Hasil Bukan Teta
        for(int j=0;j<listTextUnik.size();j++){
            if(!listTextUnik.get(j).equals("Teta")){
                listValueUnik.set(j,listValueUnik.get(j)/(1-sumTeta));
                tetaValue = tetaValue + (listValueUnik.get(j));
            }
        }

        //Hasil Teta
        for(int j=0;j<listTextUnik.size();j++){
            if(listTextUnik.get(j).equals("Teta")){
                listValueUnik.set(j,1-tetaValue);
            }
        }

        //Find Max
        double max = 0;
        String maxText = "";
        for(int j=0;j<listValueUnik.size();j++){
            if(listValueUnik.get(j)>max){
                max = listValueUnik.get(j);
                maxText = listTextUnik.get(j);
            }
        }

        maxValue = max;
        presentase = maxValue * 100;
//        System.out.println("Max "+maxText+" "+df.format(max)+" - "+df.format(presentase)+" %");
        hasilPenyakit = namaPenyakit(maxText.substring(0,4));
        hasilPersen = String.valueOf(presentase);
        listMValueSebelumnya = new ArrayList<Double>(listValueUnik);
        listMTextSebelumnya = new ArrayList<String>(listTextUnik);
    }

    public static String cekSameStringPenyakit(String str1, String str2){
        String result = "";
        int jml = 0;
        String[] array1 = str1.split(",");
        String[] array2 = str2.split(",");
        for(int i=0;i<array1.length;i++){
            for(int j=0;j<array2.length;j++){
                if(array1[i].equals(array2[j])){
                    result = result + array1[i];
                    jml = jml + 1;
                }
            }
        }
        if(jml>0){
            if(jml==2){
                String resultAdd = result.substring(0,4) +","+result.substring(4,8);
                result = resultAdd;
            }else if(jml==3){
                String resultAdd = result.substring(0,4) +","+result.substring(4,8)+","+result.substring(8,12);
                result = resultAdd;
            }else if(jml==4){
                String resultAdd = result.substring(0,4) +","+result.substring(4,8)+","+result.substring(8,12)+","+result.substring(12,16);
                result = resultAdd;
            }
        }

        if(result.equals("")){
            if(str2.equals("Teta")){
                result = str1;
            }else{
                result = "Teta";
            }
        }
        return result;
    }

    public String cekPenyakit(int ke){
        String result = "";
        for(int i=2;i<=5;i++){
            result = result + dataGejala[ke][i];
        }
        return result;
    }

    public String namaPenyakit(String hasilPenyakit){
        String namaPenyakit = "";
        switch(hasilPenyakit.substring(0,4)){
            case "P001":
                namaPenyakit = "HNP Lumbal";
                break;
            case "P002":
                namaPenyakit = "HNP Servikal";
                break;
            case "P003":
                namaPenyakit = "Nyeri Leher Biasa";
                break;
            case "P004":
                namaPenyakit = "Nyeri Punggung Biasa";
                break;
        }
        return namaPenyakit;
    }

    public void AddData(){
        progressDialog = new ProgressDialog(KonsultasiActivity.this);
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
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject userDetails = obj.getJSONObject("hasil");
                            String message = userDetails.getString("message");
                            String success = userDetails.getString("success");
                            if(success.equals("1")){
                                //Succes
                                Intent i = new Intent(KonsultasiActivity.this,ResultActivity.class);
                                i.putExtra("persen",hasilPersen);
                                i.putExtra("penyakit",hasilPenyakit);
                                i.putExtra("gejala",hasilGejala);
                                startActivity(i);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
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
                params.put("function", Constant.FUNCTION_DIAGNOSA);
                params.put("key", Constant.KEY);
                params.put("nama", edtNama.getText().toString());
                params.put("umur", edtUmur.getText().toString());
                params.put("diagnosa", hasilPenyakit);
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
                Intent i = new Intent(KonsultasiActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(KonsultasiActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
