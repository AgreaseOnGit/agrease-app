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
            1,
            "Obat Pembasmi Pohon dan Akar, Racun Pohon, Starlon Herbisida",
            "Rp 38.000",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
        ProductModel(
            2,
            "100ml Pestisida Organik Neem Oil /Minyak Mimba dari Biosfer Organik",
            "Rp 25.500",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
        ProductModel(
            3,
            "Antracol Fungisida-Obat Jamur Tanaman 70WP-250gr",
            "Rp 49.550",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
        ProductModel(
            4,
            "DESTHIN obat hama tanaman, ulat, kutu putih, hama daun",
            "Rp 35.000",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
        ProductModel(
            5,
            "herbisida obat pembasmi rumput liar, gulma, alang2 - gross",
            "Rp 35.000",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
        ProductModel(
            6,
            "Wortel Berastagi Baby Konvensional 500 gram Sayurbox",
            "Rp 14.000",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
        ProductModel(
            7,
            "selada keriting fresh",
            "Rp 4.800",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
        ProductModel(
            8,
            "Pisang (Banana) Cavendish (500gr) Sayurbox",
            "Rp 13.100",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
        ProductModel(
            9,
            "Pepaya California Sayurbox",
            "Rp 18.700",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
        ProductModel(
            10,
            "Mangga Harum Manis (1kg) Sayurbox",
            "Rp 26.500",
            "https://agrowell.com.tr/wp-content/uploads/2023/04/storing-agricutural-products.jpg",
            "4.9",
        ),
    )

    val dummyUser = UserModel(
        "1",
        "Risky",
        "Risky@mail.com",
        "https://images.pexels.com/photos/771742/pexels-photo-771742.jpeg",
        "sdawflkjslkdfjsda",
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