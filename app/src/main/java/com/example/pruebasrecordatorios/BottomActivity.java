package com.example.pruebasrecordatorios;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
/**no se usa **/
public class BottomActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    MaterialCardView materialCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        init();
        listen();
        Button btnPr = (Button) findViewById(R.id.accionBtn1);
        btnPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomActivity.this, "hola", Toast.LENGTH_SHORT).show();
            }
        });
        //getSupportFragmentManager().beginTransaction().replace(R.id.botomActivity,PaginaUnoFragment.newInstance("p","p")).commit();*/
    }

    private void listen() {
        materialCardView.setOnClickListener(view->{
            materialCardView.setChecked(!materialCardView.isChecked());
        });
        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentoSeleccionado = null;
                switch(item.getItemId()){
                    case R.id.page_1:
                        fragmentoSeleccionado = PaginaUnoFragment.newInstance("p","p");
                        break;
                    case R.id.page_2:
                        fragmentoSeleccionado = PaginaDosFragment.newInstance("2","2");
                        break;
                    case R.id.page_3:
                        Toast.makeText(BottomActivity.this, "Pagina 3", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.page_4:
                        Toast.makeText(BottomActivity.this, "Pagina 4", Toast.LENGTH_SHORT).show();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.botomActivity,fragmentoSeleccionado).commit();
                return true;
            }
        });*/
    }

    private void init() {
        //bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        materialCardView = (MaterialCardView) findViewById(R.id.card);
    }
}