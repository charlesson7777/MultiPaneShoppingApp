package com.example.multipaneshoppingapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ShoppingApp()
            }
        }
    }
}

data class Product(val id: Int, val name: String, val description: String, val price: String)

val sampleProducts = listOf(
    Product(1, "Laptop", "A laptop is a portable personal computer designed for mobility and versatility.", "$1000"),
    Product(2, "Camera", "A camera is a device used to capture images, either as still photographs or moving videos, by recording light through a lens.", "$500"),
    Product(3, "Smartphone", "A smartphone is a mobile device that combines cellular and mobile computing functions into one unit.", "$700")
)

@Composable
fun ProductList(products: List<Product>, onProductClick: (Product) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(products) { product ->
            Text(
                text = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProductClick(product) }
                    .padding(vertical = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Divider()
        }
    }
}
@Composable
fun ProductDetails(product: Product, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Name: ${product.name}",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Price: ${product.price}",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Description: ${product.description}",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onBackClick() }) {
            Text(text = "Back to Product List")
        }
    }
}

@Composable
fun PlaceholderMessage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text(text = "Select a product to see details", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ShoppingApp() {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            ProductList(
                products = sampleProducts,
                onProductClick = { selectedProduct = it },
                modifier = Modifier.weight(1f)
            )
            if (selectedProduct != null) {
                ProductDetails(
                    product = selectedProduct!!,
                    onBackClick = { selectedProduct = null },
                    modifier = Modifier.weight(1f)
                )
            } else {
                PlaceholderMessage(
                    modifier = Modifier.weight(1f)
                )
            }
        }
    } else {
        if (selectedProduct == null) {
            ProductList(
                products = sampleProducts,
                onProductClick = { selectedProduct = it }
            )
        } else {
            ProductDetails(
                product = selectedProduct!!,
                onBackClick = { selectedProduct = null }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        ShoppingApp()
    }
}




