// RegistrationActivity.kt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {

    private lateinit var editTextNewUsername: EditText
    private lateinit var editTextNewPassword: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Inisialisasi UI components
        editTextNewUsername = findViewById(R.id.editTextNewUsername)
        editTextNewPassword = findViewById(R.id.editTextNewPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

        // Set listener untuk button register
        buttonRegister.setOnClickListener {
            val newUsername = editTextNewUsername.text.toString()
            val newPassword = editTextNewPassword.text.toString()

            // Proses pendaftaran (misalnya, simpan ke database)
            if (registerUser(newUsername, newPassword)) {
                // Jika pendaftaran berhasil, tampilkan pesan berhasil
                Toast.makeText(this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show()
                finish() // Menutup activity pendaftaran setelah berhasil
            } else {
                // Jika pendaftaran gagal, tampilkan pesan kesalahan
                Toast.makeText(this, "Pendaftaran gagal!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Implementasi pendaftaran sederhana
    private fun registerUser(username: String, password: String): Boolean {
        // Ganti dengan logika pendaftaran yang sesuai
        // Contoh: Selalu mengembalikan true untuk tujuan demonstrasi
        return true
    }
}
