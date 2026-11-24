# Jawaban Latihan 9 — M17 Kotlin Coroutines

**Nama:** Muhamad Faiz Athaurrahman  
**NIM:** 24523113

## 1. Tambah satu permintaan lagi

Waktu pemuatan tetap sekitar 2 detik karena `getTasks()`, `getAnnouncement()`, dan `getCourseCount()` dijalankan bersamaan menggunakan `async`. Jadi waktu total mengikuti request yang paling lama, bukan penjumlahan semua durasi. `getTasks()` membutuhkan sekitar 2 detik.


## 2. Tombol Retry

Tombol Retry ditampilkan saat `errorMessage` tidak bernilai `null` (terjadi error). Saat ditekan, tombol memanggil `loadData()` untuk mencoba memuat data kembali.


## 3. Menangkap terlalu banyak

Jika `catch (IOException)` diganti menjadi `catch (Exception)`, exception pembatalan coroutine juga dapat ikut tertangkap. Cancel bisa dianggap sebagai error dan pesan pembatalan ditampilkan sebagai error biasa. Karena itu, pembatalan coroutine sebaiknya tidak ditelan oleh catch umum.


## 4. Di mana macetnya?

`delay(2000L)` menangguhkan coroutine tanpa memblokir thread. `Thread.sleep(2000L)` memblokir thread selama 2 detik. Jika `Thread.sleep()` berada di `withContext(Dispatchers.IO)`, main thread tidak ikut terblokir sehingga UI masih responsif. Jika `withContext(Dispatchers.IO)` dihapus dan `Thread.sleep()` berjalan di main thread, UI akan macet selama 2 detik.


## 5. Pertanyaan konsep

**a. Perbedaan `launch` dan `async`:** `launch` menjalankan coroutine tanpa mengembalikan hasil, sedangkan `async` digunakan ketika coroutine menghasilkan nilai yang bisa diambil dengan `await()`.

**b. Kenapa `GlobalScope` sebaiknya dihindari:** `GlobalScope` tidak terikat pada lifecycle pemilik pekerjaan seperti ViewModel. Coroutine dapat tetap berjalan saat pemiliknya sudah tidak digunakan dan berpotensi membuang resource.

**c. Jika `getTasks()` gagal:** Dalam `coroutineScope`, kegagalan salah satu child coroutine membatalkan child lainnya, termasuk `getAnnouncement()`. Ini merupakan bagian dari structured concurrency.


## 6. Tantangan Sort

Sort mengurutkan daftar tugas berdasarkan judul secara alfabetis. Proses pengurutan dilakukan menggunakan `withContext(Dispatchers.Default)`, kemudian hasilnya digunakan untuk memperbarui UI state.
