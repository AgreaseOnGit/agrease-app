package com.bangkit.capstone.agreaseapp.data.model.dummy

import com.bangkit.capstone.agreaseapp.data.model.CategoryModel
import com.bangkit.capstone.agreaseapp.data.model.DetailTransactionModel
import com.bangkit.capstone.agreaseapp.data.model.ProductModel
import com.bangkit.capstone.agreaseapp.data.model.TransactionModel
import com.bangkit.capstone.agreaseapp.data.model.UserModel
import java.sql.Date

object DummyDataSource {
    val dummyProducts = listOf(
        ProductModel(
            "1",
            "Obat Pembasmi Pohon dan Akar, Racun Pohon, Starlon Herbisida",
            "Nufarm Starlon Herbisida Sistemik\n" +
                    "\n" +
                    "Isi : 100 ml\n" +
                    "Bahan aktif : triklopir butoksi etil ester 665 g/l (setara dengan triklopir 480 g/l)\n" +
                    "\n" +
                    "Herbisida yang digunakan untuk membasmi kayu-kayuan seperti pohon sawit, karet, mangga ,jambu, bambu,rumput dan lainnya yang sudah tidak dimanfaatkan lagi / mengganggu. Anda dapat menggunakan herbisida ini untuk membunuh pohon tersebut secara perlahan karena bersifat sistemik dimana setelah diaplikasi kan maka pohon akan mati secara perlahan sampai dengan 1 bulan mati total (daun rontok dan batang pohon akan mengering dan layu)\n" +
                    "\n" +
                    "Petunjuk penggunaan :\n" +
                    "- larutkan 100 ml starlon dengan 1 liter solar kemudian buat lubang pada pohon / gores pohon sampai bertemu lapisan hijaunya (xilem) oleskan perlahan dengan campuran starlon\n" +
                    "- bisa juga pohon disuntik dengan 10 ml larutan starlon pada kedalaman 10-15 cm dari diameter batang pohon\n" +
                    "( cara suntik dan oles lebih efisien dalam penggunaan starlon dibandingkan disiram pada pohon )",
            4.9,
            "she-basmi",
            38000,
            96653,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Pestisida dan Obat tanaman",
        ),
        ProductModel(
            "2",
            "100ml Pestisida Organik Neem Oil /Minyak Mimba dari Biosfer Organik",
            "Takaran 1 sendok utk 1 liter larutan siap pakai . Walaupun takaran tertulis di botol 1 sendok utk 300ml. Tapi pestisida organik ini tetap ampuh membasmi hama. Mimin sudah coba sendiri.\n" +
                    "\n" +
                    "PURE NEEM OIL dari BIOSFER ORGANIK 100% Dibuat dari ekstrak biji dan daun tanaman mimba, memiliki aroma yang khas, dan diolah dgn metode cold press.. sehingga tidak merusak zat2 aktif yg terkandung didalamnya. \n" +
                    "\n" +
                    "**Pestisida nabati yang mampu membasmi hama tanaman secara alami**\n" +
                    "\n" +
                    "Apa itu NEEM OIL ?\n" +
                    "Neem oil berasal dari biji tanaman mimba atau dikenal pula sebagai neem (Azadirachta indica). Minyak mimba dari Biosfer Organik adalah pestisida nabati organik yang mampu mengendalikan hama tanaman secara alami. Biji mimba mengandung 10% minyak yang di dalamnya terkandung zat azadirachtin sebanyak 1.25%. Zat azadirachtin inilah yang berperan sebagai pestisida/ fungisida/ anti-tungau yang sangat aktif dan terkuat. Cara kerja minyak mimba tidak langsung membunuh hama yang menyerang tanaman, namun mengubah fisiologi sehingga pertumbuhan dan perkembangan hama terhenti/rusak, menjadi mandul, ulat gagal bermetamorfosis, kurang nafsu makan, dan seterusnya hingga hama tidak bisa berkembang dan akhirnya mati.\n" +
                    "\n" +
                    "Apa kelebihan NEEM OIL ?\n" +
                    "\tAman bagi manusia dan lingkungan\n" +
                    "\tMengandung zat azadirachtin untuk mengendalikan hama tanaman\n" +
                    "\tAromanya dapat mengusir hama tanaman\n" +
                    "\tDapat menggangu pola makan/perkembangbiakan hama secara hormonal\n" +
                    "\n" +
                    "Setiap pembelian akan mendapakan :\n" +
                    "1 kemasan neem oil berisi 100 ml\n" +
                    "\n" +
                    "Cara Pengunaan :\n" +
                    "1.\tLarutkan 5-7 ml neem oil kedalam 1 liter air ditambah 1-2 tetes sabun cair sebagai pelarut\n" +
                    "2.\tAduk secara merata\n" +
                    "3.\tMasukkan kedalam sprayer\n" +
                    "4.\tSemprotkan ke bagian tanaman yang terserang hama atau penyakit\n" +
                    "5.\tUlangi 2 hari setelah penyemprotan pertama untuk pemberantasan larva hama\n" +
                    "\n" +
                    "Sebagai pestisida nabati neem oil sangat direkomendasikan untuk pecinta tanaman, selain dapat mengendalikan hama dan membua",
            4.9,
            "Panda Farm",
            25500,
            91,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Pestisida dan Obat tanaman",
        ),
        ProductModel(
            "3",
            "Antracol Fungisida-Obat Jamur Tanaman 70WP-250gr",
            "Isi : 250gr\n" +
                    "Exp : 01/25\n" +
                    "\n" +
                    "Antracol adalah fungisida yang memiliki kerja cepat dan telah diproduksi serta dipasarkan di Indonesia selama lebih dari 30 tahun. Antracol sangat cocok untuk mengontrol Phytophthora dan Alternaria untuk sayur-sayuran. Antracol adalah kegiatan residu yang sangat baik.\n" +
                    "\n" +
                    "Hasil yang baik telah dicapai oleh Antracol diantaranya adalah untuk mengatasi penyakit leaf spot pada sayuran dan buah-buahan. Antracol dapat ditoleransi dengan baik oleh tanaman dalam konsentrasi tertentu. Tidak ada bahaya terbentuknya resistensi (multi-site) / dapat berguna dalam program anti-resistance untuk jenis patogen yang berbeda (downy mildew, Alternaria, scab dll).\n" +
                    "\n" +
                    "Merupakan sumber zinc yang sangat baik bila terjadi kekurangan zinc pada banyak tanaman seperti kentang, tomat dan anggur.\n" +
                    "\n" +
                    "Punya kompatibilitas phyto yang sangat baik untuk beragam tanaman, termasuk dalam tahap awal pertumbuhan tanaman.\n" +
                    "\n" +
                    "Kelebihan Produk\n" +
                    ">Bekerja efektif di segala musim (musim kering dan hujan)\n" +
                    ">Cocok untuk diaplikasikan di dataran rendah atau tinggi\n" +
                    ">Dapat diandalkan, telah menjadi pemimpin pasar selama 30 tahun\n" +
                    "> Merupakan sumber elemen penting (zinc)\n" +
                    ">Dapat ditoleransi oleh beragam tanaman, juga untuk tanaman yang usianya masih muda (dalam tahap awal pertumbuhan).\n" +
                    "\n" +
                    "Cara Pemakaian :\n" +
                    "Semprotkan semua bagian tanaman yang terserang jamur. pada tanaman yang berlapis lapisan lilin seperti bawang, Frekwensi penyemprotan ditentukan berat ringannya serangan jamur dan iklim.\n" +
                    "\n" +
                    "Dosis = 1 - 2 gr per 1 liter air",
            4.9,
            "benihkita",
            49550,
            33,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Pestisida dan Obat tanaman",
        ),
        ProductModel(
            "4",
            "DESTHIN obat hama tanaman, ulat, kutu putih, hama daun",
            "kemasan 500ml\n" +
                    "\n" +
                    "sudah menggunakan spray baru, dijamin anti macet.\n" +
                    "\n" +
                    "botol rusak, bocor, spray rusak kita ganti baru free ongkir, sertakan video unboxing terimakasih.\n" +
                    "review negatif, bintang 1/2 klaim ganti baru kita tolak. terimakasih\n" +
                    "\n" +
                    "kemasan botol 1 liter klik link dibawah:\n" +
                    "https://tokopedia.link/r4KwcGsqIib di toko she-basmi Rp65.000 di Tokopedia dengan Bebas Ongkir, Sekarang!\n" +
                    "\n" +
                    "Beli ekstra bubble wrap tambahan \n" +
                    "https://tokopedia.link/mHkQokvmSfb di toko she-basmi Rp2.000 di Tokopedia dengan Bebas Ongkir, Sekarang!\n" +
                    "\n" +
                    "Deskripsi Desthin adalah Insektisida/Obat Hama siap pakai yg Efektif membasmi hama pada tanaman dan juga mengandung Obat Anti Jamur yang sangat diperlukan untuk semua jenis tanaman terutama. Pada musim hujan. Jadi Desthin Obat Hama siap pakai yang sudah ada campuran Obat Anti Jamur didalam kandungannya.\n" +
                    "\n" +
                    "pemakaian :\n" +
                    "siram dahulu tanaman yang terkena hama kemudian semprotkan desthin ketanaman yang sehat dan terserang hama atau sakit, dilakukan seminggu 1-2x, lakukan disore hari.\n" +
                    "untuk perawatan tanaman bisa sebulan 2x pemakaian",
            4.9,
            "she-basmi",
            35000,
            95969,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Pestisida dan Obat tanaman",
        ),
        ProductModel(
            "5",
            "Herbisida obat pembasmi rumput liar, gulma, alang2 - gross",
            "Kemasan botol 500ml\n" +
                    "\n" +
                    "botol rusak, bocor, spray rusak kita ganti baru free ongkir, sertakan video unboxing terimakasih.\n" +
                    "review negatif, bintang 1/2 klaim ganti baru kita tolak. terimakasih\n" +
                    "\n" +
                    "untuk kemasan Refill gross 1 liter klik link dibawah\n" +
                    "https://tokopedia.link/xfabAwh9Eib di toko she-basmi Rp98.000 di Tokopedia dengan Bebas Ongkir, Sekarang!\n" +
                    "\n" +
                    "Beli ekstra bubble wrap tambahan \n" +
                    "https://tokopedia.link/mHkQokvmSfb di toko she-basmi Rp2.000 di Tokopedia dengan Bebas Ongkir, Sekarang!\n" +
                    "\n" +
                    "GROSS adalah pembasmi rumput liat,gulma, alang-alang, hama tanaman siap pakai (tidak perlu di campur) \n" +
                    "\n" +
                    "Cara pakai :\n" +
                    "*Gunakan sarung tangan\n" +
                    "*kocok sebelum digunakan\n" +
                    "*Semprotkan secara merata \n" +
                    "*Simpan di tempat aman\n" +
                    "*Cuci tangan setelah pemakaian\n" +
                    "\n" +
                    "-Keunggulan GROSS :\n" +
                    "*Sangat praktis tinggal pakai\n" +
                    "* Sangat ampuh untuk membasmi segala jenis rumput\n" +
                    "* Bekerja dengan sangat cepat membunuh rumput (48 jam rumput pasti kering)\n" +
                    "* Dapat diaplikasikan dlaam segala cuaca baik hujan maupun kering\n" +
                    "* Tidak mencemari tanah dan air yang berada di area penyemprotan\n" +
                    "\n" +
                    "Fungsi GROSS :\n" +
                    "- Mematikan rumput cukup banyak dan komplit\n" +
                    "- Mampu mengendalikan anakan sawit liar, gulma berdaun lebar dan sempit, teki di lahan tanpa tanaman, hutan tanaman industri, tanaman perkebunan, tanaman pangan, padi, jagung, tanaman sayuran, anak kayu, pakis, hingga paku-pakuan yang mengganggu kebun atau pekarangan rumah.\n" +
                    "\n" +
                    "\n" +
                    "PERHATIAN:\n" +
                    "*Hindari terkena tanaman hias\n" +
                    "*Jauhkan dari jangkauan anak2\n" +
                    "*Simpan di tempat aman\n" +
                    "*Jika terkena Kulit atau mata segera cuci dengan air mengalir\n" +
                    "\n" +
                    "Terimakasih",
            4.9,
            "she-basmi",
            35000,
            95886,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Pestisida dan Obat tanaman",
        ),
        ProductModel(
            "6",
            "Wortel Berastagi Baby Konvensional 500 gram Sayurbox",
            "Tersedia dalam pilihan konvensional dan imperfect. Wortel imperfect memiliki ukuran yang beragam, agak berlubang, bercabang 2-3, dan lebih kecil. Namun rasa dan nutrisinya tetap sama.\n" +
                    "\n" +
                    "Wortel Brastagi baby adalah wortel brastagi muda. Sehingga rasanya lebih manis dan teksturnya lebih renyah. Wortel brastagi baby juga dapat dikonsumsi mentah.\n" +
                    "Umur Simpan 5-7 hari\n" +
                    "\n" +
                    "[SAYURBOX INFO] \n" +
                    "Jam Operasional Toko : 09.00 - 18.00 WIB \n" +
                    "\n" +
                    "Order di luar jam operasional toko, akan direspon pada jam 09.00 (di hari berikutnya) \n" +
                    "Hari Sabtu dan Minggu tetap beroperasional dan ada pengiriman \n" +
                    "\n" +
                    "Info Pengiriman \n" +
                    "1.Order yang masuk pukul 00.00 WIB - 19.00 WIB akan dikirimkan H+1 \n" +
                    "2.Order yang masuk pukul 19.01 WIB - 23.59 WIB akan dikirimkan H+2 \n" +
                    "3.Estimasi pengiriman pukul 11.00 WIB - 14.00 WIB \n" +
                    "\n" +
                    "Info Refund dan Retur (pengiriman kembali) Produk \n" +
                    "Di Sayurbox, kami selalu berusaha menjaga kualitas produk hasil pertanian yang kami sediakan untuk dikirim kepada konsumen kami. Namun, apabila Anda mengalami kendala dari produk yang Anda terima dari Sayurbox, mohon untuk mengklik 'KOMPLAIN PESANAN' agar dapat segera kami proses. \n" +
                    "\n" +
                    "Untuk klaim terkait dengan produk Sayurbox hanya untuk barang yang rusak akibat pengantaran atau terdapat barang yang tidak terkirim, maka dari itu kami memerlukan beberapa informasi berikut ini: \n" +
                    "1. Foto dari produk yang Anda terima dari Sayurbox sebagai bukti \n" +
                    "2. Proses pengembalian dana atau pengiriman kembali akan dilakukan sehari setelah solusi pada bagian komplain pesanan disetujui \n" +
                    "3. Tidak dikenanakan biaya kirim untuk pengiriman barang kembali \n" +
                    "\n" +
                    "Jika ada pertanyaan, silahkan menghubungi kami lewat pesan pribadi di akun Tokopedia Sayurbox ya SayurFriends. \n" +
                    "Thank you and happy shopping SayurFriends.",
            5.0,
            "Sayurbox",
            14000,
            103,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Produk Pertanian Segar",
        ),
        ProductModel(
            "7",
            "Selada keriting fresh",
            "Selamat Datang di Bakoel Sayur Online!\n" +
                    "Penuhi kebutuhan dapur Anda dengan berbelanja di sini.\n" +
                    "Belanja praktis, pesan hari ini besok langsung diantar!\n" +
                    "Harga murah kualitas Premium!\n" +
                    "\n" +
                    "DESKRIPSI PRODUK\n" +
                    "Selada Keriting Fresh - HARGA PER @100gr\n" +
                    "\n" +
                    "DESKRIPSI PENGIRIMAN - (PENTING!)\n" +
                    "Pesanan hari ini 00:00 - 23:59 dikirim besok (H+1)\n" +
                    "\n" +
                    "Start pengiriman setiap harinya pk. 08:00 (mengikuti antrian dan sistem pengantaran)\n" +
                    "\n" +
                    "Kami merupakan supplier utama dari banyak restaurant terkemuka di Indonesia. Kini kami hadir melayani Anda via online di Marketplace kesayangan Anda. Kami menjamin kesegaran produk kami terutama Sayur dan Buah yang kami kirim setiap harinya, sehingga, sayur yang baru dipanen akan dikirim setiap harinya waktu dini hari. Setelah datang, kami bersihkan dan siapkan untuk dikirimkan ke pelanggan. Maka tertulis dalam DESKRIPSI PENGIRIMAN, keterangan waktu pemesanan dan pengiriman belanjaan Anda. Agar Sayur dan Buah yang Anda terima adalah yang terbaik dari kami.\n" +
                    "Selamat Berbelanja dan Selamat Menikmati Sayur dan Buah segar dengan standar Restaurant dari kami!",
            4.9,
            "Bakoel Sayur Online",
            4800,
            35579,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Produk Pertanian Segar",
        ),
        ProductModel(
            "8",
            "Pisang (Banana) Cavendish (500gr) Sayurbox",
            "Pisang cavendish adalah pisang unggul dengan warna kulit kuning mulus. Teksturnya lembut dan rasanya manis. Pisang ini biasa dimakan langsung, dijadikan toping makanan, atau dikonsumsi bersama yoghurt dan oatmeal.\n" +
                    "Pisang Cavendish RTE (ready to eat) adalah pisang siap untuk dikonsumsi tanpa perlu menunggu matang dan memiliki kulit kuning ada bercak hitam, berdaging putih, juga memiliki rasa manis yang lembut di mulut.\n" +
                    "Umur Simpan 3-4 hari\n" +
                    "\n" +
                    "[SAYURBOX INFO] \n" +
                    "Jam Operasional Toko : 09.00 - 18.00 WIB \n" +
                    "\n" +
                    "Order di luar jam operasional toko, akan direspon pada jam 09.00 (di hari berikutnya) \n" +
                    "Hari Sabtu dan Minggu tetap beroperasional dan ada pengiriman \n" +
                    "\n" +
                    "Info Pengiriman \n" +
                    "1.Order yang masuk pukul 00.00 WIB - 19.00 WIB akan dikirimkan H+1 \n" +
                    "2.Order yang masuk pukul 19.01 WIB - 23.59 WIB akan dikirimkan H+2 \n" +
                    "3.Estimasi pengiriman pukul 11.00 WIB - 14.00 WIB \n" +
                    "\n" +
                    "Info Refund dan Retur (pengiriman kembali) Produk \n" +
                    "Di Sayurbox, kami selalu berusaha menjaga kualitas produk hasil pertanian yang kami sediakan untuk dikirim kepada konsumen kami. Namun, apabila Anda mengalami kendala dari produk yang Anda terima dari Sayurbox, mohon untuk mengklik 'KOMPLAIN PESANAN' agar dapat segera kami proses. \n" +
                    "\n" +
                    "Untuk klaim terkait dengan produk Sayurbox hanya untuk barang yang rusak akibat pengantaran atau terdapat barang yang tidak terkirim, maka dari itu kami memerlukan beberapa informasi berikut ini: \n" +
                    "1. Foto dari produk yang Anda terima dari Sayurbox sebagai bukti \n" +
                    "2. Proses pengembalian dana atau pengiriman kembali akan dilakukan sehari setelah solusi pada bagian komplain pesanan disetujui \n" +
                    "3. Tidak dikenanakan biaya kirim untuk pengiriman barang kembali \n" +
                    "\n" +
                    "Jika ada pertanyaan, silahkan menghubungi kami lewat pesan pribadi di akun Tokopedia Sayurbox ya SayurFriends. \n" +
                    "Thank you and happy shopping SayurFriends.",
            4.9,
            "Sayurbox",
            13100,
            91,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Produk Pertanian Segar",
        ),
        ProductModel(
            "9",
            "Pepaya California Sayurbox",
            "Tersedia dalam pilihan konvensional dan imperfect. Pepaya imperfect memiliki bercak di kulitnya dan dapat langsung dikonsumsi. Pepaya konvensional perlu ditunggu 2-3 hari agar matang sempurna.\n" +
                    "Kulit pepaya california berwarna hijau mulus. Ukurannya pas untuk 1-2 orang. Rasanya manis, lezat, dan segar. Cocok untuk dimakan langsung,dijadikan camilan sehat, dibuat jus, atau dessert.\n" +
                    "Umur Simpan 3-4 hari\n" +
                    "\n" +
                    "[SAYURBOX INFO] \n" +
                    "Jam Operasional Toko : 09.00 - 18.00 WIB \n" +
                    "\n" +
                    "Order di luar jam operasional toko, akan direspon pada jam 09.00 (di hari berikutnya) \n" +
                    "Hari Sabtu dan Minggu tetap beroperasional dan ada pengiriman \n" +
                    "\n" +
                    "Info Pengiriman \n" +
                    "1.Order yang masuk pukul 00.00 WIB - 19.00 WIB akan dikirimkan H+1 \n" +
                    "2.Order yang masuk pukul 19.01 WIB - 23.59 WIB akan dikirimkan H+2 \n" +
                    "3.Estimasi pengiriman pukul 11.00 WIB - 14.00 WIB \n" +
                    "\n" +
                    "Info Refund dan Retur (pengiriman kembali) Produk \n" +
                    "Di Sayurbox, kami selalu berusaha menjaga kualitas produk hasil pertanian yang kami sediakan untuk dikirim kepada konsumen kami. Namun, apabila Anda mengalami kendala dari produk yang Anda terima dari Sayurbox, mohon untuk mengklik 'KOMPLAIN PESANAN' agar dapat segera kami proses. \n" +
                    "\n" +
                    "Untuk klaim terkait dengan produk Sayurbox hanya untuk barang yang rusak akibat pengantaran atau terdapat barang yang tidak terkirim, maka dari itu kami memerlukan beberapa informasi berikut ini: \n" +
                    "1. Foto dari produk yang Anda terima dari Sayurbox sebagai bukti \n" +
                    "2. Proses pengembalian dana atau pengiriman kembali akan dilakukan sehari setelah solusi pada bagian komplain pesanan disetujui \n" +
                    "3. Tidak dikenanakan biaya kirim untuk pengiriman barang kembali \n" +
                    "\n" +
                    "Jika ada pertanyaan, silahkan menghubungi kami lewat pesan pribadi di akun Tokopedia Sayurbox ya SayurFriends. \n" +
                    "Thank you and happy shopping SayurFriends.",
            4.8,
            "Sayurbox",
            18700,
            56,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Produk Pertanian Segar",
        ),
        ProductModel(
            "10",
            "Mangga Harum Manis (1kg) Sayurbox",
            "Mangga akan maksimal matang pada hari ke-3 dan dapat dicek secara berkala. \n" +
                    "\n" +
                    "\n" +
                    "Sesuai namanya, Mangga Harum Manis memiliki aroma harum yang khas dengan rasa yang sangat manis ketika sudah matang sempurna. Cocok dibuat jus, dimakan sebagai camilan sehat, dibuat selai, atau kreasi lainnya.\n" +
                    "\n" +
                    "Terdapat potensi kelebihan/kekurangan gramasi +-10% per pack.\n" +
                    "\n" +
                    "[SAYURBOX INFO] \n" +
                    "Jam Operasional Toko : 09.00 - 18.00 WIB \n" +
                    "\n" +
                    "Order di luar jam operasional toko, akan direspon pada jam 09.00 (di hari berikutnya) \n" +
                    "Hari Sabtu dan Minggu tetap beroperasional dan ada pengiriman \n" +
                    "\n" +
                    "Info Pengiriman \n" +
                    "1.Order yang masuk pukul 00.00 WIB - 19.00 WIB akan dikirimkan H+1 \n" +
                    "2.Order yang masuk pukul 19.01 WIB - 23.59 WIB akan dikirimkan H+2 \n" +
                    "3.Estimasi pengiriman pukul 11.00 WIB - 14.00 WIB \n" +
                    "\n" +
                    "Info Refund dan Retur (pengiriman kembali) Produk \n" +
                    "Di Sayurbox, kami selalu berusaha menjaga kualitas produk hasil pertanian yang kami sediakan untuk dikirim kepada konsumen kami. Namun, apabila Anda mengalami kendala dari produk yang Anda terima dari Sayurbox, mohon untuk mengklik 'KOMPLAIN PESANAN' agar dapat segera kami proses. \n" +
                    "\n" +
                    "Untuk klaim terkait dengan produk Sayurbox hanya untuk barang yang rusak akibat pengantaran atau terdapat barang yang tidak terkirim, maka dari itu kami memerlukan beberapa informasi berikut ini: \n" +
                    "1. Foto dari produk yang Anda terima dari Sayurbox sebagai bukti \n" +
                    "2. Proses pengembalian dana atau pengiriman kembali akan dilakukan sehari setelah solusi pada bagian komplain pesanan disetujui \n" +
                    "3. Tidak dikenanakan biaya kirim untuk pengiriman barang kembali \n" +
                    "\n" +
                    "Jika ada pertanyaan, silahkan menghubungi kami lewat pesan pribadi di akun Tokopedia Sayurbox ya SayurFriends. \n" +
                    "Thank you and happy shopping SayurFriends.",
            4.7,
            "Sayurbox",
            26500,
            207,
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "Produk Pertanian Segar",
        ),
    )

    val dummyCatgories = listOf(
        CategoryModel(
            1,
            "Obat Tanaman",
        ),
        CategoryModel(
            2,
            "Produk Segar",
        ),
        CategoryModel(
            3,
            "Peralatan Pertanian",
        ),
        CategoryModel(
            4,
            "Logistik Pertanian",
        ),
        CategoryModel(
            5,
            "Teknologi Pertanian",
        ),
        CategoryModel(
            6,
            "Produk Olahan",
        ),
        CategoryModel(
            7,
            "Perlengkapan Pembibitan",
        ),
        CategoryModel(
            8,
            "Bibit Tanaman"
        ),
        CategoryModel(
            9,
            "Pupuk Tanaman",
        ),
        CategoryModel(
            10,
            "Mesin Pertanian",
        ),
    )

    val dummyTransaction = listOf(
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 100000,
            shop_name = "Toko Pertanian Sejahtera",
            product_name = "Benih padi Legowo",
            status = "Selesai"
        ),
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 250000,
            shop_name = "AgroShop Online",
            product_name = "Cangkul",
            status = "Selesai"
        ),
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 150000,
            shop_name = "Toko Alat Pertanian Mandiri",
            product_name = "Sabit",
            status = "Selesai"
        ),
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 300000,
            shop_name = "GreenMarket Pertanian",
            product_name = "Tank Semprot",
            status = "Selesai"
        ),
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 450000,
            shop_name = "Tanaman Hidroponik Store",
            product_name = "Benih",
            status = "Selesai"
        ),
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 250000,
            shop_name = "Go Green",
            product_name = "Plastik Polibek",
            status = "Selesai"
        ),
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 450000,
            shop_name = "Toko Pupuk",
            product_name = "Urea",
            status = "Selesai"
        ),
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 550000,
            shop_name = "Toko Benih",
            product_name = "Padi Ir 64",
            status = "Selesai"
        ),
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 50000,
            shop_name = "Green",
            product_name = "Caping",
            status = "Selesai"
        ),
        TransactionModel(
            image = "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            total_price = 550000,
            shop_name = "Plant Medicine Shop",
            product_name = "Kompos",
            status = "Selesai"
        ),
    )
    val dummyDetail = listOf(
        DetailTransactionModel(
            id_transaction = "TRX12345",
            id_buyer = "USER001",
            id_product = "PROD01",
            payment_method = "Bank Transfer",
            status = "Selesai",
            quantity = 2,
            total_payment = 110000,
            date = Date.valueOf("2024-11-30")
        ),
        DetailTransactionModel(
            id_transaction = "TRX12346",
            id_buyer = "USER002",
            id_product = "PROD02",
            payment_method = "E-Wallet",
            status = "Selesai",
            quantity = 1,
            total_payment = 30000,
            date = Date.valueOf("2024-11-29")
        ),
        DetailTransactionModel(
            id_transaction = "TRX12347",
            id_buyer = "USER003",
            id_product = "PROD03",
            payment_method = "Cash on Delivery",
            status = "Selesai",
            quantity = 5,
            total_payment = 65000,
            date = Date.valueOf("2024-11-28")
        ),
        DetailTransactionModel(
            id_transaction = "TRX12348",
            id_buyer = "USER004",
            id_product = "PROD04",
            payment_method = "Credit Card",
            status = "Selesai",
            quantity = 3,
            total_payment = 140000,
            date = Date.valueOf("2024-11-27")
        ),
        DetailTransactionModel(
            id_transaction = "TRX12349",
            id_buyer = "USER005",
            id_product = "PROD05",
            payment_method = "E-Wallet",
            status = "Selesai",
            quantity = 1,
            total_payment = 20000,
            date = Date.valueOf("2024-11-26")
        ),
        DetailTransactionModel(
            id_transaction = "TRX12350",
            id_buyer = "USER006",
            id_product = "PROD06",
            payment_method = "Bank Transfer",
            status = "Selesai",
            quantity = 10,
            total_payment = 225000,
            date = Date.valueOf("2024-11-25")
        ),
        DetailTransactionModel(
            id_transaction = "TRX12351",
            id_buyer = "USER007",
            id_product = "PROD07",
            payment_method = "E-Wallet",
            status = "Selesai",
            quantity = 7,
            total_payment = 230000,
            date = Date.valueOf("2024-11-24")
        ),
        DetailTransactionModel(
            id_transaction = "TRX12352",
            id_buyer = "USER008",
            id_product = "PROD08",
            payment_method = "Credit Card",
            status = "Selesai",
            quantity = 4,
            total_payment = 210000,
            date = Date.valueOf("2024-11-23")
        ),
        DetailTransactionModel(
            id_transaction = "TRX12353",
            id_buyer = "USER009",
            id_product = "PROD09",
            payment_method = "Cash on Delivery",
            status = "Selesai",
            quantity = 6,
            total_payment = 400000,
            date = Date.valueOf("2024-11-22")
        )
    )
}