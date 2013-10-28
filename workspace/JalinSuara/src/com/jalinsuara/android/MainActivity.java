package com.jalinsuara.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	// json string
	private String jsonString = "{\"berita\":[{\"judul_berita\":\"Jaringan Pipanisasi Air Bersih di Desa Manduro-From Zero To Hero\",\"isi_berita\":\"Selama berpuluh-puluh tahun warga Desa Manduro,Kecamatan Kabuh, Kabupaten Jombang, Jawa Timur, dilanda kekeringan, apalagi jika memasuki musim kemarau. Air menjadi barang yang langka dan mahal.Tapi cerita pedih itu sudah berakhir\"},{\"judul_berita\":\"Bordir Berdayakan Kaum Ibu di Desa Widoro\",\"isi_berita\":\"Ibu rumah tangga melirik lapangan kerja di negara lain sebagai TKW (Tenaga Kerja Wanita) adalah hal lazim di lingkungan kami. Nama saya Sumini, ibu dari seorang putra usia remaja. Saya bekerja sebagai guru Bahasa Inggris honorer alias guru t\"},{\"judul_berita\":\"Geliat Usaha Kerajinan Bambu Desa Munthuk\",\"isi_berita\":\"Gempa Bantul yang terjadi pada tahun 2006 silam tak saja meluluhlantakkan rumah dan merenggut nyawa warga. Usaha kecil di kawasan ini pun ikut seret. Usaha mayoritas warga Bantul terhenti sejenak. Termasuk aktivitas usaha para pengrajin bamb\"},"
			+ "{\"judul_berita\":\"Bangkit Bersama dari Keterpurukan Gempa\",\"isi_berita\":\"Suara Ida Nursani terdengar sedikit serak. Sekuat tenaga ia berusaha menahan titik air menetes dari pelupuk mata. Gempa yang terjadi di Bantul, kembali terbayang. Terlintas kembali bagaimana anaknya, Auliyah Yudha Pradana (5,4 tahun) yang su\"},{\"judul_berita\":\"Gotong Royong Menjaring Kemandirian\",\"isi_berita\":\"Perlu nyali besar untuk mencapai Gili Iyang, sebuah pulau di Kecamatan Dungkek, Kabupaten Sumenep, Madura, Jawa Timur. Ombak Samudera Hindia menderu di musim angin barat kencang saat perahu berpenumpang sekitar 20 orang berlayar menuju Derma\"},{\"judul_berita\":\"BKR Wonolopo Berdayakan Peternak hingga Pengusaha Batik\",\"isi_berita\":\"Empatbelas tahun yang lalu Kelompok Bina Keluarga Remana (BKR) Wonolopo Desa Gulurejo, Kabupaten Wulun Progo, Yogyakarta, ini usahanya hanya ternak ayam dan kambing dengan jumlah ternak yang tak seberapa. Mereka tak memiliki modal. Tahun 200\"},"
			+ "{\"judul_berita\":\"Berkah PNPM, Petani Panen Dua Kali\",\"isi_berita\":\"Sejauh mata memandang, bulir menguning melambai diterpa semilir angin. Bergerak perlahan, menyisakan irama pepadian yangkhas. Aromanya menyeruak di antara suara gesekan batang padi.Persawahan hijau kini telah berubah warna kuning pertanda sa\"},{\"judul_berita\":\"Air Mengalir ke Rumah Tangga\",\"isi_berita\":\"Tak mandi berhari-hari adalah hal biasa di Dusun Plebengan Lor, Plebengan Kidul, dan Plebengan Tengah, Desa Candirejo, Kecamatan Semanu, Kabupaten Gunung Kidul, Provinsi DaerahIstimewa Yogyakarta (DIY). Untuk memperoleh air bersih wargaharus\"},{\"judul_berita\":\"Air Bersih Mengalir Sampai ke Desa Bengkala\",\"isi_berita\":\"Desa Bengkala, Kecamatan Kubutambahan, Kabupaten Buleleng, Provinsi Bali adalah salah satu desa tua di Pulau Dewata. Daribeberapa penemuan prasasti, desa ini diperkirakan telah berumur832 tahun. Uniknya, selama turun-temurun desa ini dihuni\"},"
			+ "{\"judul_berita\":\"Air Bersih Lancar, Pelayanan Kesehatan Meningkat\",\"isi_berita\":\"Pulau Sumbawa terkenal sebagai daerah yang kering dan gersang, tidak terkecuali Desa Sengkokang Bawah, Kecamatan Sekongkang, Kabupaten Sumbawa Barat, Propinsi Nusa Tenggara Barat. Dalam waktu setahun, hujan hanya turun sekitar dua bulan saja\"}]}";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.display_news);
//		initList();
//		ListView listView = (ListView) findViewById(R.id.daftar_berita);
//		SimpleAdapter simpleAdapter = new SimpleAdapter(this, ListBerita,
//				android.R.layout.simple_list_item_1,
//				new String[] { "employees" }, new int[] { android.R.id.text1 });
//		listView.setAdapter(simpleAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dashboard, menu);
		return true;
	}

	List<Map<String, String>> ListBerita = new ArrayList<Map<String, String>>();

	private void initList() {

		try {
			JSONObject jsonResponse = new JSONObject(jsonString);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("employee");

			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String name = jsonChildNode.optString("emp_name");
				String number = jsonChildNode.optString("emp_no");
				String outPut = name + "-" + number;
//				employeeList.add(createEmployee("employees", outPut));
			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}

	private HashMap<String, String> createEmployee(String name, String number) {
		HashMap<String, String> employeeNameNo = new HashMap<String, String>();
		employeeNameNo.put(name, number);
		return employeeNameNo;
	}

}