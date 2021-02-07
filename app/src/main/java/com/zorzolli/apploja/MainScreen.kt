package com.zorzolli.apploja

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.google.firebase.auth.FirebaseAuth
import com.zorzolli.apploja.Form.FormLogin
import com.zorzolli.apploja.Fragments.Products
import com.zorzolli.apploja.Fragments.RegisterProducts

class MainScreen : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val productsFragment = Products()
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.frameContainer, productsFragment)
        fragment.commit()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_products) {

            val productsFragment = Products()
            val fragment = supportFragmentManager.beginTransaction()
            fragment.replace(R.id.frameContainer, productsFragment)
            fragment.commit()

        } else if (id == R.id.nav_register_products) {

            val intent = Intent(this, RegisterProducts::class.java)
            startActivity(intent)

        } else if (id == R.id.nav_contact) {
            sendEmail()
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut()
            BackFormLogin()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun BackFormLogin() {
        intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }

    private fun sendEmail() {
        val packageGoogleEmail = "com.google.android.gm"
        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(""))
        email.putExtra(Intent.EXTRA_SUBJECT, "")
        email.putExtra(Intent.EXTRA_TEXT, "")
        email.type = "message/rec822"
        email.setPackage(packageGoogleEmail)
        startActivity(Intent.createChooser(email, R.string.choose_email_app.toString()))
    }
}