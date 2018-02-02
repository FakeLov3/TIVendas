package com.ticonsultoria.tivendas.tivendas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ticonsultoria.tivendas.tivendas.model.Usuario;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        TextView navTitle = navigationView.getHeaderView(0).findViewById(R.id.nav_header_title);
        TextView navSubtitle = navigationView.getHeaderView(0).findViewById(R.id.nav_header_subtitle);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Usuario usuario = new Usuario();
        usuario.setId(bundle.getInt("id"));
        usuario.setLogin(bundle.getString("login"));
        usuario.setSenha(bundle.getString("senha"));
        usuario.setAdm(bundle.getBoolean("adm"));
        usuario.setCadastrarProdutos(bundle.getBoolean("cadastrarProdutos"));
        usuario.setAtivo(bundle.getBoolean("ativo"));

        if (!usuario.isAdm()) {
            navigationView.getMenu().findItem(R.id.nav_pessoas).getSubMenu().removeItem(R.id.nav_usuarios);
            if (!usuario.isCadastrarProdutos()) {
                navigationView.getMenu().removeItem(R.id.nav_produtos);
            }
        }

        navTitle.setText(usuario.getLogin());
        navSubtitle.setText(usuario.getStringAdm());

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_sair) {
            SharedPreferences sharedPreferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("user_id", 0);
            editor.commit();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        android.support.v4.app.Fragment fragment = null;

        if (id == R.id.nav_usuarios) {
                fragment = new UsuariosFragment();
        }
        if (id == R.id.nav_clientes) {
            fragment = new ClientesFragment();
        }
        if (id == R.id.nav_produtos) {
            fragment = new ProdutosFragment();
        }
        if (id == R.id.nav_vendas){
            fragment = new PedidosFragment();
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
