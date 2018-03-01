

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.joao.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    String tipoDeCafe = " ";
    String tipoDeCobertura = "Sem Cobertura";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameInput = (EditText)findViewById(R.id.name_view);
        String nameCustomer =  nameInput.getText().toString();

        CheckBox expressoCheckBox = (CheckBox) findViewById(R.id.checkbox_expresso);
        boolean temExpresso = expressoCheckBox.isChecked();
            if(expressoCheckBox.isChecked()){
                tipoDeCafe = "Expresso";
            }

        CheckBox cafecomleiteCheckBox = (CheckBox) findViewById(R.id.checkbox_cafecomleite);
        boolean temCafeComLeite = cafecomleiteCheckBox.isChecked();
            if(cafecomleiteCheckBox.isChecked()){
                tipoDeCafe = "Café com Leite";
                expressoCheckBox.setChecked(false);
            }

        CheckBox cremeCheckBox = (CheckBox) findViewById(R.id.checkbox_creme);
        boolean temCreme = cremeCheckBox.isChecked();
            if(cremeCheckBox.isChecked()){
                tipoDeCobertura = "Creme";
            }

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean temChocolate = chocolateCheckBox.isChecked();
            if(chocolateCheckBox.isChecked()) {
                tipoDeCobertura = "Chocolate";
            }
            if(cremeCheckBox.isChecked() && chocolateCheckBox.isChecked()){
                tipoDeCobertura = "Creme + Chocolate";
            }

        int price = calculatePrice(temChocolate, temCreme, temCafeComLeite, temExpresso);
        String priceMessage = createOrderSummary(nameCustomer, price, tipoDeCafe, tipoDeCobertura);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de Café " + nameCustomer);
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"atendimento@cafe.com.br"});
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
    }

    /**
     * Create summary of the order.
     *
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(String nameCustomer, int price, String tipoDeCafe, String tipoDeCobertura) {
        String priceMessage = "Total: R$ " + price + ",00";
        priceMessage += "\nCliente: " + nameCustomer;
        priceMessage += "\n ";
        priceMessage += "\nTipo de Café: " + tipoDeCafe;
        priceMessage += "\nTipo de Cobertura: " + tipoDeCobertura;
        priceMessage += "\nQuantidade: " + quantity;
        priceMessage += "\n ";
        priceMessage += "\nObrigado!";
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean adicionarChocolate, boolean adicionarCreme, boolean temCafeComLeite, boolean temExpresso) {
        int precoBase = 0;


        if(temExpresso){
            precoBase = precoBase + 3;
        }

        if(temCafeComLeite){
            precoBase = precoBase + 4;
        }

        // Adiciona R$2 se pedir chocolate
        if(adicionarChocolate){
            precoBase = precoBase + 2;
        }

        // Adiciona R$1 se pedir creme
        if (adicionarCreme){
            precoBase = precoBase + 1;
        }

        // Calcula o preco total do pedido
        return quantity * precoBase;
    }

    public void increment(View view) {
        if (quantity == 10) {
            Toast.makeText(this, "Pedido limitado a 10 cafés", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "Pedido mínimo de 1 café", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.price_text_view);
//        orderSummaryTextView.setText(message);
//    }

    public void resetApp(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}